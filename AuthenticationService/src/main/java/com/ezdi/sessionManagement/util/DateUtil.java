package com.ezdi.sessionManagement.util;

import java.util.Date;

public class DateUtil {
	
	public static double differenceInHours(Date a, Date b){
		int sign = 1;
		if(a.compareTo(b) < 0){
			Date temp = a;
			a = b;
			b = temp;
			sign = -1;
		}
		long milliDiff = a.getTime() - b.getTime();
		return sign*(milliDiff*1.0/(1000*60*60));
	}

}
