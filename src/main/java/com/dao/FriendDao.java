package com.dao;

import java.util.List;

import com.model.Friend;
import com.model.User;

public interface FriendDao
{
	List<User> getSuggestedUsers(User user);
	public void friendRequest(String from, String to);
	public List<Friend> pendingRequests(String toUsername);

}
