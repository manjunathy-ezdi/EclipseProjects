package com.ezdi.sessionManagement.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RestDemoController {

	@RequestMapping(value="/login",produces = "application/json")
	public Map<String,String> helloUser(Principal principal, HttpSession session) {
		HashMap<String,String> result = new HashMap<String,String>();
		result.put("username", principal.getName());
		return result;
	}
	
	@RequestMapping(value="/addSessionInformation",produces = "application/json", method=RequestMethod.GET)
	public Map<String,String> addSessionInformation(Principal principal, HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		HashMap<String,String> result = new HashMap<String,String>();
		String parameter = req.getParameter("sessionInformation");
		session.setAttribute("sessionInformation", parameter);
		result.put("ID",session.getId());
		if(session.getAttribute("sessionInformation")!=null)
			result.put("sessionInformation", session.getAttribute("sessionInformation").toString());
		return result;
	}
	
	@RequestMapping(value="/getSessionInformation",produces = "application/json", method=RequestMethod.GET)
	public Map<String,String> getSessionInformation(Principal principal, HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		HashMap<String,String> result = new HashMap<String,String>();
		result.put("username", principal.getName());
        result.put("ID",session.getId());
		if(session.getAttribute("sessionInformation")!=null)
			result.put("sessionInformation", session.getAttribute("sessionInformation").toString());
		return result;
	}

	@RequestMapping("/logout")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void logout(HttpSession session) {
		session.invalidate();
	}
}
