package com.zacamy.pwmana.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zacamy.pwmana.model.User;
@Repository
public class UserDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return this.sessionFactory.openSession();
	}
	
	public void close(Session session){
		if(session != null)
			session.close();
	}
		
	public User findByUsernameAndPassword(String username,String password){
    	String hsql="from User u where u.username= :username and u.password= :password";
        Session session = getSession();
        Query query = session.createQuery(hsql);
        query.setParameter("username", username).setParameter("password", password);
        User user = (User) query.uniqueResult();
        close(session);
        return user;
    }

}
