package com.barclays.homeloan.utils;


import org.springframework.stereotype.Component;


@Component
public class EmiManager {
	
	public static final float INTEREST=(float)(0.07/12);
	
	public float CalculateEmi(float principal,int tenure) {
		float num = (float) ((principal * INTEREST)*Math.pow(1+INTEREST, tenure ));
		float denum = (float) (Math.pow(1+ INTEREST, tenure)-1);
		
		return (float)(num/denum);
	}

}
