package com.project.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.project.service.FeedbackService;
import com.project.service.LoginService;
import com.project.util.BaseMethods;
import com.project.vo.ComplaintVO;
import com.project.vo.FeedbackVO;
import com.project.vo.LoginVO;


@Controller
public class FeedbackController {
	

	@Autowired FeedbackService feedbackService;
	@Autowired LoginService loginService;
	
	@RequestMapping(value="staff/loadFeedback.html")
	public ModelAndView loadFeedback(){
		return new ModelAndView("staff/addFeedback","feedbackVO",new FeedbackVO());	
	}
	
	@RequestMapping(value="staff/insertFeedback.html",method=RequestMethod.POST)
	public ModelAndView insertFeedback(@ModelAttribute FeedbackVO feedbackVO){
		
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		 Date date = new Date();  
		 feedbackVO.setFeedbackDate(formatter.format(date));
		 
		 feedbackVO.setStatus(true);
		
		 
		 String userName = BaseMethods.getUser();
		 List loginList = this.loginService.serchByUserName(userName);
		 LoginVO loginVO = (LoginVO)loginList.get(0);
		 
		 System.out.println(userName+"user name=========================");
		 
		 feedbackVO.setLoginVO(loginVO);

		 this.feedbackService.insertFeedback(feedbackVO);
     	
		return new ModelAndView("/staff/addFeedback","feedbackVO",new FeedbackVO());
	}
	
	@RequestMapping(value="admin/viewFeedback.html")
	public ModelAndView viewFeedback(@ModelAttribute FeedbackVO feedbackVO){
		List feedbackList=this.feedbackService.viewFeedback();
		return new ModelAndView("/admin/viewFeedback","feedbackList",feedbackList);
	}
	
	@RequestMapping(value="staff/seeFeedback.html")
	public ModelAndView seeFeedback(@ModelAttribute FeedbackVO feedbackVO){
		List feedbackList=this.feedbackService.viewFeedback();
		return new ModelAndView("/staff/seeFeedback","feedbackList",feedbackList);
	}
	
	
}
