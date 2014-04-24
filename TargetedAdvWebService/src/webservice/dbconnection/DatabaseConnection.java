package webservice.dbconnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.bson.types.ObjectId;

import webservice.beans.ProductInfo;
import net.sf.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class DatabaseConnection implements java.io.Serializable {
	
	final private String DB_CONFIG_PROP_FILE_NAME = "DBConfiguration.properties";
	final private String NUMBER_OF_AD_DELIVERED = "numberOfAdDelivered";
	final private String USER_NAME = "userName";
	
	private Properties DBConfigurationProperties = new Properties();
	private MongoClient mongo;
	private DB db;
	private DBCollection productDataCollection;
	private DBCollection locationDataCollection;
	
	public DatabaseConnection() throws Exception {
		try {
			DBConfigurationProperties.load(getClass().getClassLoader().getResourceAsStream(DB_CONFIG_PROP_FILE_NAME));
			
			/**Mongo DB*/
			mongo = new MongoClient(DBConfigurationProperties.getProperty("server"), 27017);	//DBConfigurationProperties.getProperty("server")
			 
			db = mongo.getDB(DBConfigurationProperties.getProperty("database_name"));	// if database doesn't exists, MongoDB will create new
		
			productDataCollection = db.getCollection("ProductDataCollection"); //create new collections if not exists
			locationDataCollection = db.getCollection("LocationDataCollection");
			
		} catch (IOException e) {
			System.out.println("Property file does not exists..Please contact Admin..");
			//e.printStackTrace();
		}
		catch(Exception e)
		{
			System.out.println("Oops something else occured...");
			e.printStackTrace();
		}
	}
	
	public boolean addProductToDB(JSONObject jsonObj)
	{
		boolean isSuccess = false;
		BasicDBObject basicObject = new BasicDBObject();
		
		try{
			System.out.println("In Add Product DB Method");
			basicObject.putAll(jsonObj);
			basicObject.put(NUMBER_OF_AD_DELIVERED, 0);
			productDataCollection.insert(basicObject);
			isSuccess = true;
			return isSuccess;
		}
		catch (MongoException e) {
			System.out.println("Error: Mongo Db Error.");
			System.out.println("Please check server and mongo db on server is ruuning/installed.");
			System.out.println("Please check access to Mongo DB.");
			System.out.println("========================================");
			e.printStackTrace();
			return isSuccess;
		  }
		finally {
				mongo.close();
		}
	}
	
	public boolean editProductToDB(JSONObject jsonObj)
	{
		boolean isSuccess = false;
		BasicDBObject basicObject = new BasicDBObject();
		BasicDBObject searchBasicObject = new BasicDBObject();
		
		try{
			System.out.println("In Edit Product DB Method");
			basicObject.putAll(jsonObj);
			basicObject.put(NUMBER_OF_AD_DELIVERED, 0);
			searchBasicObject.put("_id", (ObjectId)jsonObj.get("id"));
			productDataCollection.update(searchBasicObject,basicObject,true,false);
			isSuccess = true;
			return isSuccess;
		}
		catch (MongoException e) {
			System.out.println("Error: Mongo Db Error.");
			System.out.println("Please check server and mongo db on server is ruuning/installed.");
			System.out.println("Please check access to Mongo DB.");
			System.out.println("========================================");
			e.printStackTrace();
			return isSuccess;
		  }
		finally {
				mongo.close();
		}
	}
	
	public ArrayList<ProductInfo> getAllProducts(String userName)
	{
		ArrayList<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
		
		try{
			System.out.println("In Get All Products DB Method");
			
			BasicDBObject searchUserDbObj = new BasicDBObject();
			searchUserDbObj.put(USER_NAME, userName);
			
			DBCursor cursor =  productDataCollection.find(searchUserDbObj);
			//System.out.println("Cursor Count: "+cursor.count());
			while(cursor.hasNext())
			{
				BasicDBObject cursorBasicObj = (BasicDBObject) cursor.next();
				ProductInfo productInfo = new ProductInfo();
				productInfo.setName(cursorBasicObj.getString("name"));
				productInfo.setBrand(cursorBasicObj.getString("brand"));
				productInfo.setCategory(cursorBasicObj.getString("category"));
				productInfo.setSize(cursorBasicObj.getString("size"));
				productInfo.setManufacturer(cursorBasicObj.getString("manufacturer"));
				productInfo.setAvgPrice(cursorBasicObj.getString("avgPrice"));
				productInfo.setId(cursorBasicObj.get("_id").toString());
				
				//System.out.println(cursorBasicObj.get("_id").toString());
				productInfoList.add(productInfo);
			}
			
			return productInfoList;
		}
		catch (MongoException e) {
			System.out.println("Error: Mongo Db Error.");
			System.out.println("Please check server and mongo db on server is ruuning/installed.");
			System.out.println("Please check access to Mongo DB.");
			System.out.println("========================================");
			e.printStackTrace();
			return productInfoList;
		  }
		finally {
				mongo.close();
		}
	}
}
