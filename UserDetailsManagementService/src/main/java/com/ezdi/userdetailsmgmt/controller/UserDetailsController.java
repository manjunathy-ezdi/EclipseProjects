package com.ezdi.userdetailsmgmt.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezdi.userdetailsmgmt.util.RandomMathUtil;

@RestController
@RequestMapping(path="/user")
public class UserDetailsController {
	
	@Autowired
	private RandomMathUtil rnd;
	
	@RequestMapping(method=RequestMethod.POST, path="/add", consumes="application/json")
	public String addUser(@RequestParam String jsonNewUser, HttpSession session){
		return "Session: "+session.getId()+"This is addUser() && You can see me! And you passed this --> "+jsonNewUser;
	}
	
	
	@RequestMapping(method=RequestMethod.GET, path="/")
	public String getUser(@RequestParam String username, HttpSession session){
		return "Session: "+session.getId()+"Congratulations!! You got as far as getUser()! And you passed this --> "+username;
	}	
	
	@RequestMapping(method=RequestMethod.DELETE, path="/")
	public String deleteUser(@RequestParam String username, HttpSession session){
		return "Session: "+session.getId()+"You are at deleteUser()! And how dare you delete --> "+username;
	}
	
	@RequestMapping(method=RequestMethod.POST, path="/edit")
	public String editUser(@RequestParam String username){
		return "editUser() - "+username;
	}
	
	@RequestMapping(path="/random")
	public String random(){
		String ret = "At random(). Going to call a method which is Pre authorize 'add_permission' or 'edit_permission' only";
		ret = ret+rnd.generateRandomInt();
		return ret;
	}

}
