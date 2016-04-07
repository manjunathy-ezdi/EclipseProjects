package com.ezdi.userdetailsmgmt.util;

import java.util.Random;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class RandomMathUtil {
	
	private Random rand;
	
	public RandomMathUtil() {
		rand = new Random(37);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADD_PERMISSION','ROLE_EDIT_PERMISSION')")
	public int generateRandomInt(){
		return rand.nextInt(100);
	}

}
