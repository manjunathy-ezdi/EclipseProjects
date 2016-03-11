package com.ezdi.sessionManagement.Controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
	
	@RequestMapping(value="/")
	public String indexPage(Principal principal, HttpSession httpSession){
		StringBuilder ret = new StringBuilder();
		if(principal!=null){
			ret.append("username: "+principal.getName()+"\n");
		}
		if(httpSession != null){
			ret.append("session: "+httpSession.getId());
		}
		return ret.toString();
	}
}
