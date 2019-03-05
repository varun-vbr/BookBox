package com.bookbox.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookbox.models.UserRating;
import com.bookbox.repositories.UserReviewDao;

@Service
public class UserReviewService {
    @Autowired
	private UserReviewDao userReviewDao;
    
    public List<UserRating> getReviewsForaBook(int bookId){
    	List<UserRating> ratings=new ArrayList<UserRating>();
    	List<UserRating> reviews=userReviewDao.getReviewsForaBook(bookId);
    	if(reviews!=null)
    		return reviews;
    	else
    		return ratings;
    }
    
    private int recalculateAvgRating(int bookId) {
    	List<UserRating> ratings=this.getReviewsForaBook(bookId);
    	if(ratings.size()>0) {
    		int sum=0;
    		for(UserRating rating:ratings) {
    			sum=sum+rating.getRating();
    		}
    		return Math.round(sum/ratings.size());
    	}
    	return -1;
    }
    
    public boolean createUserRating(Map<String,Object> review) {
    	if(review != null) {
    		boolean success= userReviewDao.addReview(review);
    		if(success) {
    			int avgRating=this.recalculateAvgRating((int) review.get("bookId"));
    			if(avgRating>-1) {
    				return userReviewDao.updateAverageRating((int) review.get("bookId"), avgRating);
    			}
    			return false;
    		}
    		return false;
    	}
    	return false;
    }
    
    
}
