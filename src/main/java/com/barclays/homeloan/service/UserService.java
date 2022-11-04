package com.barclays.homeloan.service;

import java.util.List;

import com.barclays.homeloan.entity.User;

public interface UserService {
	
	public List<User> getAllUsers();
	public User addAccount(User user);

}
