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
import com.project.vo.BranchVO;
import com.project.vo.LocationVO;


@Controller
public class BranchController {
	
	@Autowired LocationService locationService;
	@Autowired BranchService branchService;
	@RequestMapping(value="/admin/loadBranch.html",method=RequestMethod.GET)
	public ModelAndView loadBranch(){
		List ls=this.locationService.viewLocation();
		return new ModelAndView("/admin/addBranch","locationList",ls).addObject("branchVO",new BranchVO());
	}
	
	@RequestMapping(value="/admin/insertBranch.html",method=RequestMethod.POST)
	public ModelAndView insertBranch(@ModelAttribute BranchVO branchVO){
		this.branchService.insertBranch(branchVO);
		return new ModelAndView("redirect:/admin/loadBranch.html","branchVO", new BranchVO());
	}
	
	@RequestMapping(value="/admin/viewBranch.html",method=RequestMethod.GET)
	public ModelAndView viewBranch(@ModelAttribute BranchVO branchVO){
		List branchList=this.branchService.viewBranch();
		return new ModelAndView("/admin/viewBranch","branchList",branchList);
	}
	
	@RequestMapping(value="/admin/deleteBranch.html",method=RequestMethod.GET)
	public ModelAndView deleteLocation(@ModelAttribute BranchVO branchVO,@RequestParam String id){
		branchVO.setBranchID(Integer.parseInt(id));
		List ls = this.branchService.deleteBranch(branchVO);
		BranchVO branchVO2 = (BranchVO)ls.get(0);
		branchVO2.setStatus(false);
		this.branchService.updateBranch(branchVO2);
		return new ModelAndView("redirect:/admin/viewBranch.html");
	}
	
	@RequestMapping(value="/admin/editBranch.html",method=RequestMethod.GET)
	public ModelAndView editLocation(@ModelAttribute BranchVO branchVO,@RequestParam String id){
		branchVO.setBranchID(Integer.parseInt(id));
		List ls1=this.locationService.viewLocation();
		List ls=this.branchService.editBranch(branchVO);
		BranchVO branchVO2=(BranchVO)ls.get(0);
		
		return new ModelAndView("/admin/editBranch","branchList",branchVO2).addObject("locationList",ls1);
	}
	
	@RequestMapping(value="/admin/updateBranch.html",method=RequestMethod.POST)
	public ModelAndView updateBranch(@ModelAttribute BranchVO branchList){
		
		this.branchService.updateBranch(branchList);
		return new ModelAndView("redirect:/admin/viewBranch.html");
	}
	

	@RequestMapping(value="/admin/fetchBrach.html",method=RequestMethod.GET)
	public ModelAndView fetchBrach(@ModelAttribute BranchVO branchVO,@ModelAttribute LocationVO locationVO,@RequestParam String locationId){
		
		
		locationVO.setLocationID(Integer.parseInt(locationId));
		branchVO.setLocationVO(locationVO);

		List branchList = this.branchService.fetchBranch(branchVO);
		
		return new ModelAndView("/admin/branchJson","branchList",branchList);
	}
}
