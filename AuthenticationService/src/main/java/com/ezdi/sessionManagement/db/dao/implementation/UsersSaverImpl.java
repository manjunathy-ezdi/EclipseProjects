package com.ezdi.sessionManagement.db.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezdi.sessionManagement.db.dao.UsersSaver;
import com.ezdi.sessionManagement.db.model.EzdiUser;

public class UsersSaverImpl implements UsersSaver {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(EzdiUser user) {
		Session session = sessionFactory.openSession();
		Transaction tx =  session.beginTransaction();
		session.saveOrUpdate(user);
		tx.commit();
		session.close();
	}

	public EzdiUser getByUsername(String username) {
		Session session = sessionFactory.openSession();
		EzdiUser user = (EzdiUser)session.get(EzdiUser.class, username);
		session.close();
		return user;
	}
}
