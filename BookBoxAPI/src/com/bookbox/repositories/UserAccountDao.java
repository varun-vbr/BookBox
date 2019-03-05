package com.bookbox.repositories;

import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.bookbox.exceptions.DuplicateUserCreationException;
import com.bookbox.exceptions.IllegalLoginException;
import com.bookbox.exceptions.IllegalAccessException;
import com.bookbox.exceptions.UserNotFoundException;
import com.bookbox.models.Bill;
import com.bookbox.models.Book;
import com.bookbox.models.BookCategory;
import com.bookbox.models.CurrentBook;
import com.bookbox.models.PaymentType;
import com.bookbox.models.Plan;
import com.bookbox.models.PreferedCategory;
import com.bookbox.models.User;
@Repository
public class UserAccountDao {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	public User authenticateUser(String inputString, String password) {
	Session session=sessionFactory.getCurrentSession();
	session.beginTransaction();
	String queryByUsername="from User where username=:username";
	String queryByEmail="from User where email=:email";
	Query query=session.createQuery(queryByUsername);
	query.setString("username",inputString);
	Object queryResult=query.uniqueResult();
	if(queryResult!=null) {
		User user=(User)queryResult;
		session.getTransaction().commit();
		if(password.equals(user.getPassword())) {
			if(user.getEnabled()) {
			return user;
			}
			else {
				throw new IllegalAccessException();
			}
		}
		else {
			throw new IllegalLoginException();
		}
		
	}
	
	else {
		Query anotherQuery=session.createQuery(queryByEmail);
		anotherQuery.setString("email",inputString);
		Object anotherQueryResult=anotherQuery.uniqueResult();
		if(anotherQueryResult!=null) {
			User user=(User)anotherQueryResult;
			session.getTransaction().commit();
			if(password.equals(user.getPassword())) {
				if(user.getEnabled()) {
					return user;
					}
					else {
						throw new IllegalAccessException();
					}
			}
			else {
				throw new IllegalLoginException();
			}
		}
		else {
			throw new UserNotFoundException();
		}
	}
  }
	
	public List<CurrentBook> getCurrentReads(String username){
	    List<CurrentBook> currentReads=new ArrayList<CurrentBook>();	
		List<CurrentBook> bookHistory=getUserReadingHistory(username);
		if(bookHistory != null) {
			for(CurrentBook cbook:bookHistory) {
				if(cbook.getPercentageCompleted()<100) {
					currentReads.add(cbook);
				}
			}
			
		}
		return currentReads;
	}
	
	public List<CurrentBook> getUserReadingHistory(String username){
		String queryForId="from User where username=:username";
		String queryForCurrentBooks="from CurrentBook c where c.user.username=:username";
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		//User user=(User)session.createQuery(queryForId).setString("username",username).uniqueResult();
		List<CurrentBook> bookHistory=(List<CurrentBook>)session.createQuery(queryForCurrentBooks).setString("username",username).list();
		session.getTransaction().commit();
		return bookHistory;
	}
	
	public List<CurrentBook> getCurrentReadingTrend(Date fromTimestamp, Date toTimestamp){
		String hqlQuery="from CurrentBook where lastRead between :from and :to";
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<CurrentBook> readingTrends=(List<CurrentBook>)session.createQuery(hqlQuery).setParameter("from", fromTimestamp).setParameter("to", toTimestamp).list();
		session.getTransaction().commit();
		return readingTrends;
	}
	
	public List<Book> getRecentInclusions(Date fromTimestamp, Date toTimestamp){
		String hqlQuery="from Book where uploadedDate between :from and :to";
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<Book> newBooks=(List<Book>)session.createQuery(hqlQuery).setParameter("from", fromTimestamp).setParameter("to", toTimestamp).list();
		session.getTransaction().commit();
		return newBooks;
	}
	
	public List<Book> getPopularBooksForCategory(int categoryId, int rowCount){
		String hqlQuery="from Book b where b.categoryId.categoryId=:categoryId order by readCount desc";
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		return (List<Book>)session.createQuery(hqlQuery).setInteger("categoryId",categoryId).setMaxResults(rowCount).list();
		
	}
	
	public BookCategory getCategoryFromId(int categoryId) {
      String hql="from BookCategory where categoryId=:categoryId";
      Session session=sessionFactory.getCurrentSession();
      session.beginTransaction();
      return (BookCategory)session.createQuery(hql).setInteger("categoryId", categoryId).uniqueResult();
	}
	
	public List<PreferedCategory> getPreferredCategoriesForUser(String username){
		String queryForUser="from User where username=:username";
		String queryForPreferredCategories="from PreferedCategory p where p.user.username=:username";
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		//User user=(User)session.createQuery(queryForUser).setString("username", username).uniqueResult();
		return (List<PreferedCategory>)session.createQuery(queryForPreferredCategories).setString("username", username).list();
	}
	
	public List<BookCategory> getAllCategories(){
		String hql="from BookCategory";
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		return (List<BookCategory>)session.createQuery(hql).list();
	}
	
	public void validateUserName(String username, String email) {
		String hql="from User where username=:username";
		String newHql="from User where email=:email";
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		User user=(User)session.createQuery(hql).setString("username", username).uniqueResult();
		User userFromEmail=(User)session.createQuery(newHql).setString("email", email).uniqueResult();
		if(user!=null || userFromEmail!=null) {
                 	throw new DuplicateUserCreationException();
				}
	}
	
	public boolean addUser(Map<String, Object> userDetails) {
		validateUserName((String)userDetails.get("username"), (String)userDetails.get("email"));
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		Plan plan=(Plan)session.createQuery("from Plan where planName=:plan").setString("plan", (String)userDetails.get("plan")).uniqueResult();
		PaymentType paymentType=(PaymentType)session.createQuery("from PaymentType where type=:paymentType").setString("paymentType",(String)userDetails.get("paymentType")).uniqueResult();
		Timestamp currentTimestamp=new Timestamp(System.currentTimeMillis());
		User user=new User();
		user.setName((String)userDetails.get("name"));
		user.setUsername((String)userDetails.get("username"));
		user.setEmail((String)userDetails.get("email"));
		user.setCardNumber((String)userDetails.get("cardNumber"));
		user.setAddress((String)userDetails.get("address"));
		user.setAuthority("USER");
		user.setAutoDeduction((Boolean)userDetails.get("autoDeduct"));
		user.setPassword((String)userDetails.get("password"));
		user.setPhone((String)userDetails.get("phone"));
		user.setPinCode((String)userDetails.get("pincode"));
		user.setEnabled(true);
		user.setPlanSince(currentTimestamp);
		user.setUserSince(currentTimestamp);
		user.setPlan(plan);
		user.setPaymenttype(paymentType);
		List<String> preferredCategories=(List<String>)userDetails.get("preferredCategories");
		List<PreferedCategory> userPreferredCategories=new ArrayList();
		if(preferredCategories!=null) {
			for(String catName:preferredCategories) {
				BookCategory category=(BookCategory)session.createQuery("from BookCategory where categoryName=:name").setString("name",catName).uniqueResult();
				if(category!=null) {
					PreferedCategory prefCat=new PreferedCategory();
					prefCat.setUser(user);
					prefCat.setCategory(category);
					userPreferredCategories.add(prefCat);
				}
			}
		}
		user.setPreferredCategories(userPreferredCategories);
		Bill bill=new Bill();
		bill.setUserId(user);
		bill.setBilledOn(currentTimestamp);
		bill.setBillDue(false);
		bill.setDaysOfBilling(1);
		bill.setBillingPeriod(new SimpleDateFormat("MMMM-yyyy").format(currentTimestamp));
		if(((String)userDetails.get("plan")).equalsIgnoreCase("Basic"))
			bill.setPaymentAmount((float) 2.34);
		if(((String)userDetails.get("plan")).equalsIgnoreCase("Premium"))
			bill.setPaymentAmount((float) 3.34);
		if(((String)userDetails.get("plan")).equalsIgnoreCase("Elite"))
			bill.setPaymentAmount((float) 4.17);
		session.save(user);
		session.save(bill);
		session.getTransaction().commit();
		return true;
	}
	
	public boolean updateUser(Map<String, Object> newDetails) {
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		if(newDetails.containsKey("username")) {
		User user=(User)session.createQuery("from User where username=:username").setString("username",(String) newDetails.get("username")).uniqueResult();
		if(user!=null) {
			if(newDetails.containsKey("name")) {
				user.setName((String)newDetails.get("name"));
			}
			if(newDetails.containsKey("email")) {
				validateUserName("", (String)newDetails.get("email"));
				user.setEmail((String)newDetails.get("email"));
			}
			if(newDetails.containsKey("cardNumber")) {
				user.setCardNumber((String) newDetails.get("cardNumber"));
			}
			if(newDetails.containsKey("address")) {
				user.setAddress((String) newDetails.get("address"));
			}
			if(newDetails.containsKey("autoDeduct")) {
				user.setAutoDeduction((Boolean) newDetails.get("autoDeduct"));
			}
			if(newDetails.containsKey("password")) {
				user.setPassword((String) newDetails.get("password"));
			}
			if(newDetails.containsKey("phone")) {
				user.setPhone((String) newDetails.get("phone"));
			}
			if(newDetails.containsKey("pincode")) {
				user.setPinCode((String) newDetails.get("pincode"));
			}
			if(newDetails.containsKey("plan")) {
				Plan plan=(Plan)session.createQuery("from Plan where planName=:plan").setString("plan", (String)newDetails.get("plan")).uniqueResult();
				Timestamp currentTimestamp=new Timestamp(System.currentTimeMillis());
				if(plan!=null) {
				user.setPlan(plan);
				user.setPlanSince(currentTimestamp);
				}
			}
			if(newDetails.containsKey("paymentType")) {
				PaymentType paymentType=(PaymentType)session.createQuery("from PaymentType where type=:paymentType").setString("paymentType",(String)newDetails.get("paymentType")).uniqueResult();
				if(paymentType!=null) {
					user.setPaymenttype(paymentType);
				}
			}
			session.update(user);
			session.getTransaction().commit();
			return true;
		}
		else {
			throw new UserNotFoundException();
		}
		}
		else {
			throw new UserNotFoundException();
		}
	}
	
	public List<Book> getRandomBooks(int firstRow){
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		return session.createQuery("from Book").setFirstResult(firstRow).setMaxResults(15).list();
	}
	
	
}
