package com.bookbox.repositories;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.bookbox.exceptions.DuplicateBookInPlaylistException;
import com.bookbox.exceptions.NoBooksFoundException;
import com.bookbox.models.Book;
import com.bookbox.models.BookBoxConstants;
import com.bookbox.models.CurrentBook;
import com.bookbox.models.PlayListDetails;
import com.bookbox.models.Playlist;
import com.bookbox.models.PlaylistItem;
import com.bookbox.models.User;

@Repository
public class UserActionDao {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	@Autowired
	private UserAccountDao userAccountDao;
	
	public List<Book> getAllBooksForCategory(int categoryId){
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<Book> books=(List<Book>) session.createQuery("from Book b where b.categoryId.categoryId=:id").setInteger("id", categoryId).list();
		if(books!=null)
			return books;
		else
			throw new NoBooksFoundException();
	}
	
	public boolean setCurrentBook(Map<String,Integer> currentRead) {
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		Timestamp currentTimestamp=new Timestamp(System.currentTimeMillis());
		CurrentBook currentBook=(CurrentBook) session.createQuery("from CurrentBook cb where cb.book.bookId=:bookId and cb.user.userId=:userId and cb.percentageCompleted<100").
				                setInteger("bookId", currentRead.get("bookId")).setInteger("userId", currentRead.get("userId")).uniqueResult();
		if(currentBook!=null) {
			currentBook.setCurrentPage(currentRead.get("currentPage"));
			currentBook.setPercentageCompleted(currentRead.get("percentageCompleted"));
			currentBook.setLastRead(currentTimestamp);
			session.update(currentBook);
			session.getTransaction().commit();
		}
		else {
			Book book=(Book) session.createQuery("from Book b where b.bookId=:bookId").setInteger("bookId", currentRead.get("bookId")).uniqueResult();
			User user=(User) session.createQuery("from User u where u.userId=:userId").setInteger("userId", currentRead.get("userId")).uniqueResult();
			CurrentBook cbook=new CurrentBook();
			cbook.setBook(book);
			cbook.setUser(user);
			cbook.setCurrentPage(currentRead.get("currentPage"));
			cbook.setNumberOfPages(currentRead.get("numberOfPages"));
			cbook.setLastRead(currentTimestamp);
			session.save(cbook);
			session.getTransaction().commit();
		}
		
		return true;
		
	}
	
	public boolean updateBookReadCount(int bookId) {
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		Book book=(Book)session.createQuery("from Book b where b.bookId=:bookId").setInteger("bookId", bookId).uniqueResult();
		if(book!=null) {
			book.setReadCount(book.getReadCount()+1);
			session.update(book);
			session.getTransaction().commit();
			return true;
		}
		return false;
	}
	
	public List<PlaylistItem> getPlayListItemsForPlaylist(int playlistId){
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		return (List<PlaylistItem>)session.createQuery("from PlaylistItem p where p.playlist.id=:pid").setInteger("pid", playlistId).list();
		
	}
	
	public List<Playlist> getAllUserPlaylist(int userId){
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		return (List<Playlist>)session.createQuery("from Playlist p where p.user.userId=:uid").setInteger("uid", userId).list();
	}

	public Map<String, Object> getAllPlaylistWithDetails(int userId, String userName){
		List<PlayListDetails> formatedPlaylists=new ArrayList<PlayListDetails>();
		List<Playlist> allPlaylistsForUser=getAllUserPlaylist(userId);
		for(Playlist unformatedPlaylist:allPlaylistsForUser) {
			PlayListDetails details=new PlayListDetails();
			List<Book> books=new ArrayList<Book>();
			details.setPlayListName(unformatedPlaylist.getName());
			details.setPlayListId(unformatedPlaylist.getId());
			List<PlaylistItem> itemsInPlayList=getPlayListItemsForPlaylist(unformatedPlaylist.getId());
			for(PlaylistItem item:itemsInPlayList) {
				books.add(item.getBook());
			}
			details.setBooks(books);
			formatedPlaylists.add(details);
		}
		List<CurrentBook> readingProgress=userAccountDao.getUserReadingHistory(userName);
		Map<String, Object> playlistDetails=new HashMap<String, Object>();
		playlistDetails.put("PLAYLISTS", formatedPlaylists);
		playlistDetails.put("READING_PROGRESS",readingProgress);
		return playlistDetails;
		}
	
	public boolean createPlayList(String playListName, int userId) {
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		User user=(User)session.createQuery("from User u where u.userId=:uid").setInteger("uid",userId).uniqueResult();
		Playlist newPlaylist=new Playlist();
		newPlaylist.setName(playListName);
		newPlaylist.setUser(user);
		session.save(newPlaylist);
		session.getTransaction().commit();
		return true;
	 }
	
	public boolean deletePlaylist(int playlistId) {
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<PlaylistItem> itemsInPlaylist=(List<PlaylistItem>)session.createQuery("from PlaylistItem p where p.playlist.id=:pid").setInteger("pid", playlistId).list();
		if(itemsInPlaylist!=null) {
			for(PlaylistItem item: itemsInPlaylist) {
				session.delete(item);
				
			}
		}
		Playlist playlist=(Playlist)session.createQuery("from Playlist p where p.id=:pid").setInteger("pid", playlistId).uniqueResult();
		session.delete(playlist);
		session.getTransaction().commit();
		return true;
	}
	
	public boolean addBookToPlaylist(int playlistId, int bookId) {
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		PlaylistItem playlistItem=(PlaylistItem)session.createQuery("from PlaylistItem p where p.playlist.id=:pid and p.book.bookId=:bid").setInteger("pid", playlistId).setInteger("bid",bookId).uniqueResult();
		if(playlistItem==null) {
		Playlist playlist=(Playlist)session.createQuery("from Playlist p where p.id=:pid").setInteger("pid", playlistId).uniqueResult();
		Book book=(Book)session.createQuery("from Book b where b.bookId=:bid").setInteger("bid", bookId).uniqueResult();
		PlaylistItem pItem=new PlaylistItem();
		pItem.setPlaylist(playlist);
		pItem.setBook(book);
		session.save(pItem);
		session.getTransaction().commit();
		return true;
		}
		else {
			throw new DuplicateBookInPlaylistException();
		}
	}
	
	public boolean removeBookFromPlaylist(int playlistId, int bookId) {
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		PlaylistItem pItem=(PlaylistItem)session.createQuery("from PlaylistItem p where p.playlist.id=:pid and p.book.bookId=:bid").setInteger("pid", playlistId).setInteger("bid",bookId).uniqueResult();
		session.delete(pItem);
		session.getTransaction().commit();
		return true;
	}
	
	public List<Book> search(String key, BookBoxConstants.SearchType searchType){
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<Book> searchResult=null;
		if(searchType.equals(BookBoxConstants.SearchType.TITLE)) {
			searchResult=(List<Book>)session.createQuery("from Book where title like :key").setString("key", "%"+key+"%").list();
		}
		else if(searchType.equals(BookBoxConstants.SearchType.AUTHOR)) {
			searchResult=(List<Book>)session.createQuery("from Book where author like :key").setString("key", "%"+key+"%").list();
		}
		else if(searchType.equals(BookBoxConstants.SearchType.PUBLISHER)) {
			searchResult=(List<Book>)session.createQuery("from Book where publisher like :key").setString("key", "%"+key+"%").list();
		}
		else if(searchType.equals(BookBoxConstants.SearchType.NONE)) {
			searchResult=new ArrayList<Book>();
			List<Book> titles=(List<Book>)session.createQuery("from Book where title like :key").setString("key", "%"+key+"%").list();
			List<Book> authors=(List<Book>)session.createQuery("from Book where author like :key").setString("key", "%"+key+"%").list();
			List<Book> publishers=(List<Book>)session.createQuery("from Book where publisher like :key").setString("key", "%"+key+"%").list();
			if(titles != null) {
				searchResult.addAll(titles);
			}
			if(authors != null) {
				searchResult.addAll(authors);
			}
			if(publishers != null) {
				searchResult.addAll(publishers);
			}
		}
		if(searchResult==null) {
			searchResult=(List<Book>)Collections.EMPTY_LIST;
		}
		return searchResult;
		
	}
}
