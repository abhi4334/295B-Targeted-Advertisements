package org.springframework.social.targeted.adv;

import java.util.ArrayList;

import mongo.MongoController;
import mongo.ProductInfo;

import org.springframework.social.targeted.adv.facebook.RetailerForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.mongodb.BasicDBObject;


@Controller
public class ProductDataController {
	
	@RequestMapping(value="/addProduct",method=RequestMethod.POST)
	public String tablesCall(ProductInfo form, BindingResult formBinding, WebRequest request) {
		
		boolean isProductAdded = false;
		try {
			String userName = form.getUserName();
			MongoController mongoController = new MongoController();
			isProductAdded = mongoController.addProductToDB(userName,form.getName(),form.getBrand(),form.getSize(),form.getCategory(),
					form.getManufacturer(),form.getAvgPrice());
			if(isProductAdded)
			{
				return "adminSuccess";
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return "redirect:/forms";
	}
	
	@RequestMapping(value = "/getGraphTestData", method = RequestMethod.GET)
	public String getGraphTestDataCall(Model model,WebRequest request) {

		ArrayList<BasicDBObject> graphResults = new ArrayList<BasicDBObject>();

		try {
			model.addAttribute("userName", request.getParameter("userName"));
			MongoController mongoController = new MongoController();
			graphResults = mongoController.getNumberOfRestarant();
			// for(BasicDBObject graphresult: graphResults){
			// // System.out.println(graphresult.get("a"));
			// // System.out.println(graphresult.get("b"));
			// System.out.println(graphresult);
			// }
			model.addAttribute("graphresults", graphResults);

			return "GetGraphData";

		} catch (Exception e) {

			e.printStackTrace();
		}

		return "GetGraphData";
	}

	
}
