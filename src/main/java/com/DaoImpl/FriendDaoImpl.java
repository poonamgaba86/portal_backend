package com.DaoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dao.FriendDao;
import com.model.Friend;
import com.model.User;
@Repository("friendDao")


public class FriendDaoImpl implements FriendDao{
	@Autowired
private SessionFactory sessionFactory;
	
	
	
	public List<User> getSuggestedUsers(User user) {
		Session session=sessionFactory.openSession();
		SQLQuery query=session.createSQLQuery("select * from users_c where name in (select name from users_c where name!=? minus (select from_id from friend where to_id=?"
				+ "union select to_id from friend where from_id=?"
				+ "))");
		query.setString(0, user.getName());
		query.setString(1, user.getName());
		query.setString(2, user.getName());
		query.addEntity(User.class);
		List<User> users=query.list();
		session.close();
		return users;
	}

	
	public void friendRequest(String from, String to){
		Session session=sessionFactory.openSession();
		Friend friend =new Friend();
		friend.setFrom(from);
		friend.setTo(to);
		friend.setStatus('P');
		session.save(friend);
		session.flush();
		session.close();
	}


	public List<Friend> pendingRequests(String toUsername) {
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from Friend where to=? and status=?");
		query.setString(0, toUsername);
		query.setCharacter(1, 'P');
		List<Friend> pendingRequests=query.list();
		session.close();
		return pendingRequests;
	}	
}