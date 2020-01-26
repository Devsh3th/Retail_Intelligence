package com.project.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.project.service.ComplaintService;
import com.project.service.LoginService;
import com.project.util.BaseMethods;
import com.project.vo.ComplaintVO;
import com.project.vo.LoginVO;


@Controller
public class ComplaintController {
	
	
	@Autowired ComplaintService complaintService;
	@Autowired LoginService loginService;
	
	
	@RequestMapping(value="staff/loadComplaint.html")
	public ModelAndView loadComplaint(){
		
		return new ModelAndView("staff/addComplaint","complaintVO",new ComplaintVO());	
	}
	
	
	@RequestMapping(value="/staff/insertComplaint.html",method=RequestMethod.POST)
	public ModelAndView insertComplaint(@ModelAttribute ComplaintVO complaintVO,@RequestParam ("file") MultipartFile complaintFile,HttpServletRequest request/*@ModelAttribute LoginVO loginVO*/){
		
		System.out.println("in complaint insert method");
		String path =request.getSession().getServletContext().getRealPath("/");
		
		String complaintFileName= complaintFile.getOriginalFilename();
		
		String complaintFilePath = path + "documents\\complaint\\";
		
		try {
			byte barr[]=complaintFile.getBytes();  
			
	        BufferedOutputStream bout=new BufferedOutputStream(new FileOutputStream(complaintFilePath+"/"+complaintFileName));  
	        
	        bout.write(barr);  
	        bout.flush();  
	        bout.close();
	        
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		complaintVO.setComplaintFilePath(complaintFilePath);
		complaintVO.setComplaintFileName(complaintFileName);
		
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		 Date date = new Date();  
		 complaintVO.setComplaintDate(formatter.format(date));
		 
		 complaintVO.setComplaintStatus("pending");
		 complaintVO.setStatus(true);
		
		 
		 String userName = BaseMethods.getUser();
		 List loginList = this.loginService.serchByUserName(userName);
		 LoginVO loginVO = (LoginVO)loginList.get(0);
		 
		 System.out.println(userName+"user name=========================");
		 
		 complaintVO.setLoginVO(loginVO);

		 this.complaintService.insertComplaint(complaintVO);
     	
		return new ModelAndView("/staff/addComplaint","complaintVO",new ComplaintVO());
	}
	
	@RequestMapping(value="/admin/viewComplaint.html")
	public ModelAndView viewComplaint(@ModelAttribute ComplaintVO complaintVO){
		List complaintList=this.complaintService.viewComplaint();
		return new ModelAndView("/admin/viewComplaint","complaintList",complaintList);
	}
		
	@RequestMapping(value="/admin/replyComplaint.html",method=RequestMethod.GET)
	public ModelAndView replyComplaint(@ModelAttribute ComplaintVO complaintVO,@RequestParam String id){
		complaintVO.setComplaintID(Integer.parseInt(id));
		
		List complaintList=this.complaintService.replyComplaint(complaintVO);
		
		ComplaintVO complaintVO2=(ComplaintVO)complaintList.get(0);
		//complaintVO2.getLoginVO();
		return new ModelAndView("/admin/replyComplaint","complaintList",complaintVO2);
	}
	
	@RequestMapping(value="admin/sendComplaintReply.html",method=RequestMethod.POST)
	public ModelAndView sendReply(@ModelAttribute ComplaintVO complaintVO){
		 
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		 Date date = new Date();  
		 complaintVO.setReplyDate(formatter.format(date));
		 
		 complaintVO.setComplaintStatus("resolved");
		 this.complaintService.updateComplaint(complaintVO);
		 List complaintList=this.complaintService.viewComplaint();
		 return new ModelAndView("/admin/viewComplaint","complaintList",complaintList);
	}
	
	@RequestMapping(value="/staff/viewComplaintReply.html",method=RequestMethod.GET)
	public ModelAndView viewReply(@ModelAttribute ComplaintVO complaintVO){
		String username=BaseMethods.getUser();
		 List loginList = this.loginService.serchByUserName(username);
		 LoginVO loginVO = (LoginVO)loginList.get(0);
		 complaintVO.setLoginVO(loginVO);
		 List ls=this.complaintService.viewReply(complaintVO);
		 return new ModelAndView("staff/viewReply","complaintList",ls);
	}
	}

	
