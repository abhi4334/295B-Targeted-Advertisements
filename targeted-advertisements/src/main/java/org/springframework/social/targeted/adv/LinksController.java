package org.springframework.social.targeted.adv;

import java.util.ArrayList;

import mongo.MongoController;
import mongo.ProductInfo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;


@Controller
public class LinksController {
	
	@RequestMapping(value="/links/tables",method=RequestMethod.GET)
	public String tablesCall(Model model,WebRequest request) {
		
		model.addAttribute("userName", request.getParameter("userName"));
		return "tables";
	}
	
	@RequestMapping(value="/links/forms",method=RequestMethod.GET)
	public String formsCall(Model model,WebRequest request) {
		
		model.addAttribute("userName", request.getParameter("userName"));
		return "forms";
	}
	
	@RequestMapping(value="/links/index",method=RequestMethod.GET)
	public String indexCall(Model model,WebRequest request) {
		
		model.addAttribute("userName", request.getParameter("userName"));
		return "index";
	}
	
	@RequestMapping(value="/links/userindex",method=RequestMethod.GET)
	public String userindexCall(Model model,WebRequest request) {
		
		model.addAttribute("userName", request.getParameter("userName"));
		return "userindex";
	}
	
	@RequestMapping(value="/links/viewAds",method=RequestMethod.GET)
	public String viewAdsCall(WebRequest request,Model model) {
		
		String userName = request.getParameter("userName");
		model.addAttribute("userName", request.getParameter("userName"));
		//System.out.println("user name:"+userName);
		try {
			MongoController mongoController = new MongoController();
			
			ArrayList<ProductInfo> productInfoList =  mongoController.getAllProducts(userName);
			
			if(productInfoList.size()!=0 && productInfoList!=null)
			{
				model.addAttribute("ads", productInfoList);
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return "viewAds";
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logoutCall(Model model,WebRequest request) {
		
		model.addAttribute("userName", null);
		return "logout";
	}
	
	@RequestMapping(value="/signinAgain",method=RequestMethod.GET)
	public String signinCall(Model model,WebRequest request) {
		
		model.addAttribute("userName", null);
		return "signin";
	}
	
}
