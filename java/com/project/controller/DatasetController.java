package com.project.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
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

import com.project.service.DatasetService;
import com.project.vo.DatasetVO;

@Controller
public class DatasetController {
	
	@Autowired DatasetService datasetService;
	
	@RequestMapping(value="admin/loadDataset.html")
	public ModelAndView loadDataset(){
		return new ModelAndView("/admin/addDataset","datasetVO",new DatasetVO());	
	}
	
	@RequestMapping(value="admin/insertDataset.html",method=RequestMethod.POST)
	public ModelAndView insertDataset(@ModelAttribute DatasetVO datasetVO,@RequestParam ("file") MultipartFile file,HttpServletRequest request){
		
		System.out.println("in method");
		
		String path =request.getSession().getServletContext().getRealPath("/");
		
		String datasetFileName= file.getOriginalFilename();
		
		String datasetFilePath = path + "documents\\dataset\\";
		
		try {
			byte barr[]=file.getBytes();  
			
	        BufferedOutputStream bout=new BufferedOutputStream(new FileOutputStream(datasetFilePath+"/"+datasetFileName));  
	        
	        bout.write(barr);  
	        bout.flush();  
	        bout.close();
	        
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		datasetVO.setDatasetFilePath(datasetFilePath);
		datasetVO.setDatasetFileName(datasetFileName);
		this.datasetService.insertDataset(datasetVO);
		datasetVO.setStatus(true);
		return new ModelAndView("/admin/addDataset","datasetVO",new DatasetVO());
	}
	
	@RequestMapping(value="admin/viewDataset.html")
	public ModelAndView viewDataset(@ModelAttribute DatasetVO datasetVO){
		List datasetList= this.datasetService.viewDataset();
		return new ModelAndView("/admin/viewDataset","datasetList",datasetList);
	}
	
	@RequestMapping(value="/admin/deleteDataset.html",method=RequestMethod.GET)
	public ModelAndView deleteDataset(@ModelAttribute DatasetVO datasetVO,@RequestParam String id){
		datasetVO.setDatasetID(Integer.parseInt(id));
		List ls = this.datasetService.deleteDataset(datasetVO);
		DatasetVO datasetVO2 = (DatasetVO)ls.get(0);
		datasetVO2.setStatus(false);
		this.datasetService.updateDataset(datasetVO2);
		return new ModelAndView("redirect:/admin/viewDataset.html");
	}
	
	@RequestMapping(value="/admin/editDataset.html",method=RequestMethod.GET)
	public ModelAndView editDataset(@ModelAttribute DatasetVO datasetVO,@RequestParam String id){
		
		datasetVO.setDatasetID(Integer.parseInt(id));
		
		List datasetList=this.datasetService.editDataset(datasetVO);
		
		DatasetVO datasetVO2=(DatasetVO)datasetList.get(0);
		
		return new ModelAndView("/admin/editDataset","datasetList",datasetVO2);
	}
	
	@RequestMapping(value="/admin/updateDataset.html",method=RequestMethod.POST)
	public ModelAndView updateDataset(@ModelAttribute DatasetVO datasetVO){
		
		//System.out.println("in update method");
		this.datasetService.updateDataset(datasetVO);
		
		return new ModelAndView("redirect:/admin/viewDataset.html");
	}
}

