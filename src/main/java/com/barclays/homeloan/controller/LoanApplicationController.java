package com.barclays.homeloan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barclays.homeloan.constants.SystemConstants;
import com.barclays.homeloan.entity.LoanApplication;
import com.barclays.homeloan.repository.LoanApplicationRepository;
import com.barclays.homeloan.service.LoanApplicationService;


@RestController
@RequestMapping("/application")
public class LoanApplicationController {
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	
	@Autowired
	LoanApplicationRepository loanAppRepository;
	
	@Autowired
	LoanApplicationService loanAppService;
	
	
	@PostMapping(value = SystemConstants.APPLY_HOME_LOAN)
	public ResponseEntity<?>applyHomeLoan(@RequestBody LoanApplication req) {
		try {
			return new ResponseEntity<>(loanAppService.addrequest(req), HttpStatus.CREATED);
		}
		catch(Exception e){
			logger.error("Error occurred while adding Account: " + e.getMessage());
            return new ResponseEntity<>("Error occurred while adding Account", HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	@GetMapping(value = SystemConstants.VALIDATE_HOME_LOAN)
	public ResponseEntity<?> validateApplication(@PathVariable int id){
		try {
			String out = loanAppService.validate(id);
			if(out==null) {
				return new ResponseEntity<>("LoanApplication with given Id not present", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(out, HttpStatus.OK);
		}
		catch(Exception e){
			logger.error("Error occurred validating: " + e.getMessage());
            return new ResponseEntity<>("Error occurred while validating ", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
