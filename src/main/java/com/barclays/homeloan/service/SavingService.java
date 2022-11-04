package com.barclays.homeloan.service;

import java.util.List;

import com.barclays.homeloan.entity.SavingAccount;

public interface SavingService {

	public List<SavingAccount> getAllAccounts();
	public SavingAccount addAccount(SavingAccount acc);
	
	
}
