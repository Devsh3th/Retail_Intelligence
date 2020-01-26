package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.service.LocationService;
import com.project.vo.LocationVO;

@Controller
public class LocationController {
	
	@Autowired LocationService service;
	
	@RequestMapping(value="/admin/loadLocation.html")
	public ModelAndView loadLocation(){
		return new ModelAndView("/admin/addLocation","locationVO",new LocationVO());
	}
	
	@RequestMapping(value="/admin/insertLocation.html",method=RequestMethod.POST)
	public ModelAndView insertLocation(@ModelAttribute LocationVO locationVO){
		this.service.insertLocation(locationVO);
		return new ModelAndView("admin/addLocation","locationVO", new LocationVO());
	}
	
	@RequestMapping(value="/admin/viewLocation.html")
	public ModelAndView viewLocation(@ModelAttribute LocationVO locationVO){
		List locationList=this.service.viewLocation();
		return new ModelAndView("admin/viewLocation","locationList",locationList);	
	}
	
	@RequestMapping(value="/admin/deleteLocation.html",method=RequestMethod.GET)
	public ModelAndView deleteLocation(@ModelAttribute LocationVO locationVO,@RequestParam String id){
		locationVO.setLocationID(Integer.parseInt(id));
		List ls = this.service.deleteLocation(locationVO);
		LocationVO locationVO2 = (LocationVO)ls.get(0);
		locationVO2.setStatus(false);
		this.service.updateLocation(locationVO2);
		return new ModelAndView("redirect:/admin/viewLocation.html");
	}
	
	@RequestMapping(value="/admin/editLocation.html",method=RequestMethod.GET)
	public ModelAndView editLocation(@ModelAttribute LocationVO locationVO,@RequestParam String id){
		locationVO.setLocationID(Integer.parseInt(id));
		List locationList1=this.service.editLocation(locationVO);
		LocationVO locationVO2=(LocationVO)locationList1.get(0);
		return new ModelAndView("/admin/editLocation","locationList",locationVO2);
	}
	
	@RequestMapping(value="/admin/updateLocation.html",method=RequestMethod.POST)
	public ModelAndView updateLocation(@ModelAttribute LocationVO locationVO){
		this.service.updateLocation(locationVO);
		return new ModelAndView("redirect:/admin/viewLocation.html");
	}
	
	
}


