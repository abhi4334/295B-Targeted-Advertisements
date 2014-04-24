package webservice.demo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import webservice.beans.InventoryInfo;

// Plain old Java Object it does not extend as class or implements 
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/hello")
public class demo1 {
	
	JSONObject obj = new JSONObject();

  // This method is called if TEXT_PLAIN is request
  @GET
  //@Produces(MediaType.APPLICATION_OCTET_STREAM)
  public String sayPlainTextHello() throws JSONException {
	  InventoryInfo inventoryInfo = new InventoryInfo();
	  inventoryInfo.setId("abhi");
	  System.out.println(inventoryInfo.getId());
	  System.out.println(inventoryInfo);
	  
	  return "abhi shah";
  }

} 