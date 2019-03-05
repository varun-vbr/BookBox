package com.bookbox.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookbox.exceptions.IllegalLoginException;
import com.bookbox.exceptions.UserNotFoundException;
import com.bookbox.models.Book;
import com.bookbox.models.BookCategory;
import com.bookbox.models.CurrentBook;
import com.bookbox.models.PreferedCategory;
import com.bookbox.models.User;
import com.bookbox.models.UserInfo;
import com.bookbox.repositories.UserAccountDao;

@Service
public class UserAccountService {
    @Autowired
	private UserAccountDao userDao;
    
    public List<CurrentBook> getCurrentReadsByUser(String username){
    	//List<Book> books=new ArrayList<Book>();
    	//List<CurrentBook> userReadingTrend=userDao.getCurrentReads(username);
    	//for(CurrentBook cbook:userReadingTrend) {
    		//books.add(cbook.getBook());
    	//}
    	return userDao.getCurrentReads(username);
    }
    
    public Map<Book,Integer> getCurrentBookCount(){
    	Map<Book,Integer> booksMap=new TreeMap<Book,Integer>();
    	Timestamp fromTimestamp=new Timestamp(System.currentTimeMillis());
    	Calendar cal=Calendar.getInstance();
    	cal.add(Calendar.DATE, -15);
    	Timestamp toTimestamp=new Timestamp(cal.getTime().getTime()); 
    	List<CurrentBook> readingTrends=userDao.getCurrentReadingTrend(fromTimestamp, toTimestamp);
    	if(readingTrends != null) {
    	for(CurrentBook cbook:readingTrends) {
    		if(booksMap.containsKey(cbook.getBook())) {
    			booksMap.put(cbook.getBook(), booksMap.get(cbook.getBook())+1);
    		}
    		else{
    			booksMap.put(cbook.getBook(),1);
    		}
    	}
    	}
    	
    	return booksMap;
    }
    
    public SortedSet<Map.Entry<Book, Integer>> sortBooksByCount(Map<Book, Integer> mapToSort){
    	SortedSet<Map.Entry<Book,Integer>> sortedEntries = new TreeSet<Map.Entry<Book,Integer>>(
    	        new Comparator<Map.Entry<Book,Integer>>() {
    	            @Override public int compare(Map.Entry<Book,Integer> e1, Map.Entry<Book,Integer> e2) {
    	                int res = e2.getValue().compareTo(e1.getValue());
    	                return res != 0 ? res : 1;
    	            }
    	        }
    	    );
    	 sortedEntries.addAll(mapToSort.entrySet());
    	    return sortedEntries;
    }
    
    public Map<Integer, Integer> getCurrentCategoryCount(){
    	Timestamp fromTimestamp=new Timestamp(System.currentTimeMillis());
    	Calendar cal=Calendar.getInstance();
    	cal.add(Calendar.DATE, -180);
    	Timestamp toTimestamp=new Timestamp(cal.getTime().getTime());
    	Map<Integer, Integer> categoryMap= new TreeMap<Integer, Integer>();
    	List<CurrentBook> readingTrends=userDao.getCurrentReadingTrend(fromTimestamp, toTimestamp);
    	if(readingTrends != null) {
    	for(CurrentBook cbook:readingTrends) {
    		if(categoryMap.containsKey(cbook.getBook().getCategoryId().getCategoryId())) {
    			categoryMap.put(cbook.getBook().getCategoryId().getCategoryId(), categoryMap.get(cbook.getBook().getCategoryId().getCategoryId())+1);
    		}
    		else{
    			categoryMap.put(cbook.getBook().getCategoryId().getCategoryId(),1);
    		}
    	}
    	}
    	return categoryMap;
    	
    }
    
    public SortedSet<Map.Entry<Integer, Integer>> sortCategoriesByCount(Map<Integer, Integer> mapToSort){
    	SortedSet<Map.Entry<Integer,Integer>> sortedEntries = new TreeSet<Map.Entry<Integer,Integer>>(
    	        new Comparator<Map.Entry<Integer,Integer>>() {
    	            @Override public int compare(Map.Entry<Integer,Integer> e1, Map.Entry<Integer,Integer> e2) {
    	                int res = e2.getValue().compareTo(e1.getValue());
    	                return res != 0 ? res : 1;
    	            }
    	        }
    	    );
    	 sortedEntries.addAll(mapToSort.entrySet());
    	    return sortedEntries;
    }
    
    public List<Book> getNewBooks(){
    	Timestamp fromTimestamp=new Timestamp(System.currentTimeMillis());
    	Calendar cal=Calendar.getInstance();
    	cal.add(Calendar.DATE, -15);
    	Timestamp toTimestamp=new Timestamp(cal.getTime().getTime());
    	return userDao.getRecentInclusions(fromTimestamp,toTimestamp);
    }
    
    public Map<Integer, Integer> getCategoryCountForUser(String username){
    	Map<Integer, Integer> categoryMap= new TreeMap<Integer, Integer>();
    	List<CurrentBook> userReadingTrend=userDao.getUserReadingHistory(username);
    	if(userReadingTrend != null) {
    	for(CurrentBook cbook:userReadingTrend) {
    		if(categoryMap.containsKey(cbook.getBook().getCategoryId().getCategoryId())) {
    			categoryMap.put(cbook.getBook().getCategoryId().getCategoryId(), categoryMap.get(cbook.getBook().getCategoryId().getCategoryId())+1);
    		}
    		else{
    			categoryMap.put(cbook.getBook().getCategoryId().getCategoryId(),1);
    		}
    	}
    	}
    	return categoryMap;
    }
    
    public List<Book> getTrendingBooksByUser(String username){
    	List<Book> readingTrendByUser=new ArrayList<Book>();
    	Map<Integer, Integer> categoryMap=this.getCategoryCountForUser(username);
    	TreeSet<Map.Entry<Integer,Integer>> sortedEntries=(TreeSet<Map.Entry<Integer,Integer>>)this.sortCategoriesByCount(categoryMap);
    	if(sortedEntries.size()>0) {
    		Iterator<Map.Entry<Integer,Integer>> categoryIterator=sortedEntries.iterator();
    		int count=5;
    		while(categoryIterator.hasNext() && count>0) {
    			Integer categoryId=categoryIterator.next().getKey();
    			readingTrendByUser.addAll(userDao.getPopularBooksForCategory(categoryId, 3));
    			count--;
    		}
    	}
    	return readingTrendByUser;
    }
    
    public List<Book> getPopularBooksFromPreferredCategories(String username){
    	List<Book> popularBooksInPreferredCategories=new ArrayList<Book>();
    	List<PreferedCategory> preferedCategories=userDao.getPreferredCategoriesForUser(username);
    	for(PreferedCategory pcat: preferedCategories) {
    		popularBooksInPreferredCategories.addAll(userDao.getPopularBooksForCategory(pcat.getCategory().getCategoryId(), 3));
    	}
    	return popularBooksInPreferredCategories;
    }
    
    public Map<BookCategory, List<Book>> getPopularBooksFromAllCategories(int rowCount){
         Map<BookCategory, List<Book>> booksByCategory=new HashMap<BookCategory, List<Book>>();
         for(BookCategory category:userDao.getAllCategories()) {
        	 booksByCategory.put(category, userDao.getPopularBooksForCategory(category.getCategoryId(), rowCount));
         }
         return booksByCategory;
    }
    
    
    
    public UserInfo loginForUserInfo(String inputString, String password) throws IllegalLoginException, UserNotFoundException {
    	UserInfo userInfo=new UserInfo();
    	User user=userDao.authenticateUser(inputString, password);
        userInfo.setUser(user);
        List<CurrentBook> currentReads=userDao.getCurrentReads(user.getUsername());
        List<Book> booksOnUserTrend=getTrendingBooksByUser(user.getUsername());
        List<Book> popularBooks=new ArrayList<Book>();
        List<BookCategory> popularCategories=new ArrayList<BookCategory>();
        List<Book> trendingBooksNow=new ArrayList<Book>();
        List<Book> popularBooksFromPreferredCategories=null;
        
        Map<BookCategory, List<Book>> booksByCategory=getPopularBooksFromAllCategories(15);
        if(!booksByCategory.isEmpty()) {
        for (Map.Entry<BookCategory,List<Book>> entry : booksByCategory.entrySet()) {
        	popularBooks.add(entry.getValue().get(0));
        	popularBooks.add(entry.getValue().get(1));
        	popularBooks.add(entry.getValue().get(2));
        }
        }
        Map<Integer, Integer> currentCategoriesByCount=getCurrentCategoryCount();
        if(!currentCategoriesByCount.isEmpty()) {
        	TreeSet<Map.Entry<Integer,Integer>> sortedEntries=(TreeSet<Map.Entry<Integer,Integer>>)this.sortCategoriesByCount(currentCategoriesByCount);
        	if(sortedEntries.size()>0) {
        		Iterator<Map.Entry<Integer,Integer>> categoryIterator=sortedEntries.iterator();
        		int count=5;
        		while(categoryIterator.hasNext() && count>0) {
        			popularCategories.add(userDao.getCategoryFromId(categoryIterator.next().getKey()));
        			count--;
        		}
        	}
        }
        List<Book> newBooks=getNewBooks();
        Map<Book,Integer> currentBookCount=getCurrentBookCount();
        if(!currentBookCount.isEmpty()) {
        	TreeSet<Map.Entry<Book,Integer>> sortedEntries=(TreeSet<Map.Entry<Book,Integer>>)this.sortBooksByCount(currentBookCount);
        	if(sortedEntries.size()>0) {
        		Iterator<Map.Entry<Book,Integer>> booksIterator=sortedEntries.iterator();
        		int count=15;
        		while(booksIterator.hasNext() && count>0) {
        			trendingBooksNow.add(booksIterator.next().getKey());
        			count--;
        		}
        	}
        }
        
        if(booksOnUserTrend.size()==0) {
        	popularBooksFromPreferredCategories=this.getPopularBooksFromPreferredCategories(user.getUsername());
        }
        
        List<BookCategory> allCategories=userDao.getAllCategories();
        
        userInfo.setCurrentReads(currentReads);
        userInfo.setNewBooks(newBooks);
        userInfo.setPopularBooks(popularBooks);
        userInfo.setPopularCategories(popularCategories);
        userInfo.setReadingSuggestions(booksOnUserTrend);
        userInfo.setAllCategories(allCategories);
        userInfo.setPopularBooksFromPreferredCategories(popularBooksFromPreferredCategories);
        userInfo.setTrendingBooks(trendingBooksNow);
        
        return userInfo;
        
    }
    
    public UserInfo getUserInfoForAnonymousLogin() {
    	UserInfo userInfo=new UserInfo();
    	
        userInfo.setUser(null);
        
        List<Book> popularBooks=new ArrayList<Book>();
        List<BookCategory> popularCategories=new ArrayList<BookCategory>();
        List<Book> trendingBooksNow=new ArrayList<Book>();
        
        
        Map<BookCategory, List<Book>> booksByCategory=getPopularBooksFromAllCategories(15);
        if(!booksByCategory.isEmpty()) {
        for (Map.Entry<BookCategory,List<Book>> entry : booksByCategory.entrySet()) {
        	popularBooks.add(entry.getValue().get(0));
        	popularBooks.add(entry.getValue().get(1));
        	popularBooks.add(entry.getValue().get(2));
        }
        }
        Map<Integer, Integer> currentCategoriesByCount=getCurrentCategoryCount();
        if(!currentCategoriesByCount.isEmpty()) {
        	TreeSet<Map.Entry<Integer,Integer>> sortedEntries=(TreeSet<Map.Entry<Integer,Integer>>)this.sortCategoriesByCount(currentCategoriesByCount);
        	if(sortedEntries.size()>0) {
        		Iterator<Map.Entry<Integer,Integer>> categoryIterator=sortedEntries.iterator();
        		int count=5;
        		while(categoryIterator.hasNext() && count>0) {
        			popularCategories.add(userDao.getCategoryFromId(categoryIterator.next().getKey()));
        			count--;
        		}
        	}
        }
        List<Book> newBooks=getNewBooks();
        Map<Book,Integer> currentBookCount=getCurrentBookCount();
        if(!currentBookCount.isEmpty()) {
        	TreeSet<Map.Entry<Book,Integer>> sortedEntries=(TreeSet<Map.Entry<Book,Integer>>)this.sortBooksByCount(currentBookCount);
        	if(sortedEntries.size()>0) {
        		Iterator<Map.Entry<Book,Integer>> booksIterator=sortedEntries.iterator();
        		int count=15;
        		while(booksIterator.hasNext() && count>0) {
        			trendingBooksNow.add(booksIterator.next().getKey());
        			count--;
        		}
        	}
        }
        
        if(popularBooks.size()==0) {
        	popularBooks=userDao.getRandomBooks(0);
        }
        if(trendingBooksNow.size()==0) {
        	trendingBooksNow=userDao.getRandomBooks(20);
        }
        if(newBooks.size()==0) {
        	newBooks=userDao.getRandomBooks(40);
        }
        List<BookCategory> allCategories=userDao.getAllCategories();
        
        userInfo.setCurrentReads(null);
        userInfo.setNewBooks(newBooks);
        userInfo.setPopularBooks(popularBooks);
        userInfo.setPopularCategories(popularCategories);
        userInfo.setReadingSuggestions(null);
        userInfo.setAllCategories(allCategories);
        userInfo.setPopularBooksFromPreferredCategories(null);
        userInfo.setTrendingBooks(trendingBooksNow);
        
        return userInfo;
    }
    
    public boolean addUser(Map<String, Object> details) {
    	if(details != null) {
    	return userDao.addUser(details);
    	}
    	else {
    		return false;
    	}
    }
    
    public boolean updateUser(Map<String, Object> newDetails) {
    	if(newDetails != null) {
    		return userDao.updateUser(newDetails);
    	}
    	else {
    		return false;
    	}
    }
    
    
}
