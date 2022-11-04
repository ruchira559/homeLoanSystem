package com.barclays.homeloan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barclays.homeloan.constants.SystemConstants;
import com.barclays.homeloan.entity.SavingAccount;
import com.barclays.homeloan.repository.SavingRepository;
import com.barclays.homeloan.service.SavingService;

/**
 * SavingsController(working::)
 * 
 * @author ace
 *
 */
@RestController
@RequestMapping("/saving")
public class SavingsController {

	
	@Autowired
	SavingRepository savingRepository;
	
	@Autowired
	SavingService savingService;
	
	/**
	 * logger
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping(value = SystemConstants.GET_ALL_ACCOUNT)
	public ResponseEntity<?> findAll(){
		try {
			logger.info("api running !!");
			return new ResponseEntity<>(savingService.getAllAccounts(), HttpStatus.OK);
		}
		catch(Exception e){
			logger.error("Error occurred while fetching all Acount data: " + e.getMessage());
            return new ResponseEntity<>("Error occurred while fetching all Savings account data", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * addAccount(working::)
	 * @param acc
	 * @return
	 */
	@PostMapping(value = SystemConstants.ADD_SAVINGS_ACCOUNT)
	public ResponseEntity<?> addAccount(@RequestBody SavingAccount acc) {
		try {
			return new ResponseEntity<>(savingService.addAccount(acc), HttpStatus.CREATED);
		}
		catch(Exception e){
			logger.error("Error occurred while adding Account: " + e.getMessage());
            return new ResponseEntity<>("Error occurred while adding Account", HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
}
