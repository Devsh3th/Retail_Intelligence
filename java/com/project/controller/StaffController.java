package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.service.BranchService;
import com.project.service.LocationService;
import com.project.service.LoginService;
import com.project.service.StaffService;
import com.project.util.BaseMethods;
import com.project.vo.LoginVO;
import com.project.vo.StaffVO;

@Controller
public class StaffController {

	@Autowired 
	StaffService staffService;

	@Autowired
	BranchService branchService;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	LocationService locationService;
	
	@RequestMapping(value="admin/loadStaff.html")
	public ModelAndView addStaff(){
		List ls0=this.locationService.viewLocation();
		List ls=this.branchService.viewBranch();
		return new ModelAndView("/admin/addStaff","branchList",ls).addObject("staffVO", new StaffVO()).addObject("locationList", ls0);
	}
	
	@RequestMapping(value="/admin/insertStaff.html" , method=RequestMethod.POST)
	public ModelAndView insertStaff(@ModelAttribute StaffVO staffVO,@ModelAttribute LoginVO loginVO){

		String to = staffVO.getLoginVO().getUsername();
		String passwordToSend = BaseMethods.getAlphaNumericString();
		
		BaseMethods.send(to, passwordToSend);
		
		loginVO.setUsername(to);
		loginVO.setPassword(passwordToSend);
		loginVO.setRole("ROLE_STAFF");
		staffVO.setStatus(true);
		this.loginService.insert(loginVO);
		
		staffVO.setLoginVO(loginVO);
		this.staffService.insertStaff(staffVO);
		
		return new ModelAndView("admin/addStaff","staffVO",new StaffVO());
	}
	
	@RequestMapping(value="/admin/viewStaff.html",method=RequestMethod.GET)
	public ModelAndView viewStaff(@ModelAttribute StaffVO staffVO){
		List ls=this.staffService.viewStaff();	
		return new ModelAndView("/admin/viewStaff","staffList",ls);	
	}
	
	@RequestMapping(value="/admin/deleteStaff.html",method=RequestMethod.GET)
	public ModelAndView deleteStaff(@ModelAttribute StaffVO staffVO,@RequestParam String id,@ModelAttribute LoginVO loginVO){
		staffVO.setStaffID(Integer.parseInt(id));
		///loginVO.setEnabled("blocked");
		List staffList=this.staffService.editStaff(staffVO);
		StaffVO staffVO2=(StaffVO)staffList.get(0);
		staffVO2.setStatus(false);
		
		this.staffService.updateStaff(staffVO2);
		return new ModelAndView("redirect:/admin/viewStaff.html");
	}
	
	@RequestMapping(value="/staff/editStaff.html",method=RequestMethod.GET)
	public ModelAndView editStaff(@ModelAttribute StaffVO staffVO/*,@RequestParam String id*/){
		List ls0=this.locationService.viewLocation();
		List ls=this.branchService.viewBranch();
		//staffVO.setStaffID(Integer.parseInt(id));
		
		String userName = BaseMethods.getUser();
		 List loginList = this.loginService.serchByUserName(userName);
		 LoginVO loginVO = (LoginVO)loginList.get(0);
		 System.out.println(userName+"user name=========================");
		 
		 staffVO.setLoginVO(loginVO);

		List staffList=this.staffService.editStaff(staffVO);
		
		StaffVO staffVO2=(StaffVO)staffList.get(0);
		
		return new ModelAndView("/staff/editStaff","staffList",staffVO2).addObject("branchList",ls).addObject("locationList", ls0);
	}
	
	@RequestMapping(value="/staff/updateStaff.html",method=RequestMethod.POST)
	public ModelAndView updateStaff(@ModelAttribute StaffVO staffVO){
		this.staffService.updateStaff(staffVO);
		/*loginVO.setUsername(staffVO.getLoginVO().getUsername());
		this.loginService.insert(loginVO);
		*/return new ModelAndView("redirect:/staff/editStaff.html");
		}
}
	

