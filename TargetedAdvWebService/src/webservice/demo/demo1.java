package webservice.demo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.sf.json.JSONArray;
//import org.json.simple.JSONObject;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.codehaus.jettison.json.JSONException;

import webservice.beans.ProductInfo;
import webservice.dbconnection.DatabaseConnection;

@Path("/demo")
public class demo1 {

	@Path("/hi")
	@GET
	  public String sayPlainTextHello() throws JSONException, IOException {
		  System.out.println("in demo1.Hi Method");
		  return "Hi Abhi..."; 
	  }
	

@Path("/addInventory/{invjson}")
@POST
@Produces({MediaType.APPLICATION_JSON})
public Response addInventory(@PathParam("invjson") String invInfoString){
	
	System.out.println("In Add Inventory Method.");
	System.out.println("invInfoString"+invInfoString);
	boolean isSuccess =false;
	JSONObject obj = new JSONObject();
	try {
	obj = (JSONObject) JSONSerializer.toJSON(invInfoString);  
	//invInfo = (InventoryInfo) JSONObject.toBean(obj,InventoryInfo.class);
	DatabaseConnection dbConnection = new DatabaseConnection();
	isSuccess = dbConnection.addProductToDB(obj);
	//System.out.println("Name: "+obj.getString("name"));
	} catch (Exception e) {
		e.printStackTrace();
	}
	if(isSuccess)
	{
		return Response.ok(invInfoString).build();
	}
	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
}

@Path("/updateInventory/{invjson}")
@POST
@Produces({MediaType.APPLICATION_JSON})
public Response updateInventory(@PathParam("invjson") String invInfoString){
	
	boolean isSuccess =false;
	System.out.println("In Update Inventory Method.");
	
	try {
		JSONObject obj = (JSONObject) JSONSerializer.toJSON(invInfoString); 
		//ProductInfo invInfo = (ProductInfo) JSONObject.toBean(obj,ProductInfo.class);
		DatabaseConnection dbConnection = new DatabaseConnection();
		isSuccess = dbConnection.editProductToDB(obj);
	} catch (Exception e) {
		e.printStackTrace();
	} 
	if(isSuccess)
	{
		return Response.ok(invInfoString).build();
	}
	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
}

@Path("/getAllInventory/{userName}")
@GET
@Produces({MediaType.APPLICATION_JSON})
public ArrayList<ProductInfo> getAllInventory(@PathParam("userName") String userName){
	ArrayList<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
	try {
		System.out.println("In get all inventory Inventory Method.");
		System.out.println(userName);
		DatabaseConnection dbConnection = new DatabaseConnection();
		productInfoList = dbConnection.getAllProducts(userName);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return productInfoList;
}

@Path("/getInventoryById/{id}")
@POST
@Produces({MediaType.APPLICATION_JSON})
public Response getInventoryById(@PathParam("id") String id){
	
	System.out.println("In get inventoryById Method.");
	
	ProductInfo invInfo = null;
	try {
		DatabaseConnection dbConnection = new DatabaseConnection();
		//invInfo = dbConnection.getInventoryById(id);
		return Response.status(Response.Status.OK).entity(invInfo).build();	
	} catch (Exception e) {
		e.printStackTrace();
	} 
	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();	
}



} 