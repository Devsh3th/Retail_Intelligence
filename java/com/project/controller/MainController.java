package com.project.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.project.service.LoginService;
import com.project.vo.VisitorVO;

@Controller
public class MainController {
	
	@Autowired LoginService loginService;

	@RequestMapping(value="/")
	public ModelAndView loadIndex(){
		return new ModelAndView("/login");
	}
	
	@RequestMapping(value="/admin/index")
	public ModelAndView adminIndex(){
		Date d = new Date();
		DateFormat a = new SimpleDateFormat("yyyy-MM-dd");
		List ls=this.loginService.generateGraph(a.format(d));
		System.out.println(ls.size());
		return new ModelAndView("/admin/index").addObject("list", ls);
	}
	
	@RequestMapping(value="/admin/viewDetection")
	public ModelAndView viewDetection(){
		return new ModelAndView("/admin/viewDetection");
	}
	
	@RequestMapping(value="/staff/index")
	public ModelAndView staffIndex(){
		
		Date d = new Date();
		DateFormat a = new SimpleDateFormat("yyyy-MM-dd");
		List ls=this.loginService.generateGraph(a.format(d));
		System.out.println(ls.size());
		return new ModelAndView("/staff/index").addObject("list",ls);
	}
	
	@RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})	
	public String viewUserDetails(ModelMap model,HttpServletResponse response,HttpServletRequest request) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = user.getUsername();
		
		
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if (auth != null) {
	            new SecurityContextLogoutHandler().logout(request, response, auth);
	            request.getSession().invalidate();
	            request.getSession().setAttribute("tempStatus", "success");
	            request.getSession().setAttribute("statusText", "Logout Successfully!");
	        }
	        return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView load() {

		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView load403() {

		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/error", method = RequestMethod.POST)
	public ModelAndView error() {

		return new ModelAndView("login");
	}
	
	
}
