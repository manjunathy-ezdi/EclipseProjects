package com.ezdi.springsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by EZDI\manjunath.y on 8/2/16.
 */
@RestController
@RequestMapping(path = "/")
public class IndexController {


    /*
    @RequestMapping(path="/", method= RequestMethod.GET)
    @ResponseBody
    public String hello(HttpSession session, Principal principal){
        if(principal == null){
            return "principal is null";
        }
        if(principal.getName() == null){
            return "principal.getName() is null.";
        }
        return principal.getName()+"::yoyoi";
    }
    */

    @RequestMapping(path="/", method= RequestMethod.GET)
    @ResponseBody
    public String hello(HttpSession session){
        if(session == null){
            return  "session is null";
        }
        String s = "BEGIN SESSION "+session.getId();
        /*
        Enumeration<String> atts = session.getAttributeNames();
        while(atts.hasMoreElements()){
            s = s+" ;; "+atts.nextElement();
        }
        */
        return s;
    }
}
