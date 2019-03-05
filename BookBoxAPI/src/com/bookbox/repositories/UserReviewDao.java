package com.bookbox.repositories;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.bookbox.models.Book;
import com.bookbox.models.User;
import com.bookbox.models.UserRating;

@Repository
public class UserReviewDao {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	public List<UserRating> getReviewsForaBook(int bookId){
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		return session.createQuery("from UserRating ur where ur.book.bookId=:id").setInteger("id", bookId).list();
	} 
	
	public boolean addReview(Map<String, Object> review) {
		if(review.containsKey("userId") && review.containsKey("bookId") && review.containsKey("rating") && review.containsKey("comment")) {
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		User user=(User)session.createQuery("from User u where u.userId=:id").setInteger("id", (int) review.get("userId")).uniqueResult();
		Book book=(Book)session.createQuery("from Book b where b.bookId=:bookId").setInteger("bookId", (int) review.get("bookId")).uniqueResult();
		if(user!=null && book!=null) {
		UserRating userRating=new UserRating();
		userRating.setBook(book);
		userRating.setUser(user);
		userRating.setRating((int) review.get("rating"));
		userRating.setReviewComment((String) review.get("comment"));
		session.save(userRating);
		session.getTransaction().commit();
		return true;
		}
		else {
			return false;
		}
		}
		else {
			return false;
		}
	}
	
	public boolean updateAverageRating(int bookId, int avgRating) {
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		Book book=(Book)session.createQuery("from Book b where b.bookId=:bookId").setInteger("bookId",bookId).uniqueResult();
		if(book!=null) {
			book.setAverageRating(avgRating);
			session.update(book);
			session.getTransaction().commit();
			return true;
		}
		else {
			return false;
		}
	}
}
