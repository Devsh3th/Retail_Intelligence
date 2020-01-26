package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.service.DetectionService;
import com.project.service.LoginService;
import com.project.util.BaseMethods;
import com.project.vo.LoginVO;
import com.project.vo.QueueDetectionVO;
import com.project.vo.VisitorVO;

@Controller
public class DetectionController {
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	DetectionService detectionService;
	
	@RequestMapping(value="/staff/viewDetection.html")
	public ModelAndView viewDetection1(@ModelAttribute VisitorVO visitorVO){
		
		List visitorList=this.detectionService.viewVisitorDetection();
		return new ModelAndView("/staff/viewDetection","visitorList",visitorList);
	    
		
	}
	
	@RequestMapping(value="/staff/viewQueueDetection.html")
	public ModelAndView viewQueueDetection0(@ModelAttribute QueueDetectionVO qvo){
		
		 String userName = BaseMethods.getUser();
		 List loginList = this.loginService.serchByUserName(userName);
		 LoginVO loginVO = (LoginVO)loginList.get(0);
		 
		 List queue=this.detectionService.viewQueueDetection();
		 
		return new ModelAndView("/staff/viewQueueDetection","userName",userName).addObject("queue", queue);
	}
	

}
