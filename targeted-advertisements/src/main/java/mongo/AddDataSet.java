package mongo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class AddDataSet {
	
	final private String DB_CONFIG_PROP_FILE_NAME = "DBConfiguration.properties";
	
	private Properties DBConfigurationProperties = new Properties();
	private MongoClient mongo;
	private DB db;
	private DBCollection productDataCollection;
	private DBCollection locationDataCollection;
	
	private final String NUMBER_OF_AD_DELIVERED = "numberOfAdDelivered";
	private final String USER_NAME = "userName";
	private final String[] userNames = {"abhi4334","nishant.rangrej","samyak.shah","chandra.v"};
	
	public AddDataSet() throws Exception {
		try {
			DBConfigurationProperties.load(getClass().getClassLoader().getResourceAsStream(DB_CONFIG_PROP_FILE_NAME));
			
			/**Mongo DB*/
			String temp = DBConfigurationProperties.getProperty("server");
			System.out.println(temp);
			mongo = new MongoClient(DBConfigurationProperties.getProperty("server"), 27017);	//DBConfigurationProperties.getProperty("server")
			 
			db = mongo.getDB(DBConfigurationProperties.getProperty("database_name"));	// if database doesn't exists, MongoDB will create new
		
			productDataCollection = db.getCollection("ProductDataCollection"); //create new collections if not exists
			locationDataCollection = db.getCollection("LocationDataCollection");
			
		} catch (IOException e) {
			System.out.println("Property file does not exists..Please contact Admin..");
			//e.printStackTrace();
		}
	}
	
	public void insertProductDataToDB(String filePath)
	{
		/**File Read*/
		BufferedReader br = null;
		 
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(filePath));
			sCurrentLine = br.readLine();
			//System.out.println(sCurrentLine);
			String[] titleKeys = sCurrentLine.split(",");
			int titleNum = 1;
			for(String temp:titleKeys)
			{
				System.out.println(titleNum +" "+ temp);
				titleNum++;
			}
			BasicDBObject basicDBObject;
			int lineNumber = 2;
			while((sCurrentLine = br.readLine())!= null)
			{
				//sCurrentLine = sCurrentLine.replaceAll("", "");	//"[""  ""]"
				//System.out.println("in while..");
				String productValues[] = sCurrentLine.split(",");
				//System.out.println(productValues.length);
				//System.out.println(titleKeys.length);
				if(titleKeys.length == productValues.length)
				{
					basicDBObject = new BasicDBObject();
					for(int i=0;i<titleKeys.length;i++)
					{
						
						if(i==8||i==25)
						{
							String[] subStringProduct = productValues[i].split("#");
							basicDBObject.put(titleKeys[i], subStringProduct);
						}
						else{
							basicDBObject.put(titleKeys[i], productValues[i]);
						}
					}
					basicDBObject.put(NUMBER_OF_AD_DELIVERED, 0);
					int randomNum = 0 + (int)(Math.random() * ((3 - 0) + 1));
					basicDBObject.put(USER_NAME,userNames[randomNum]);
					productDataCollection.insert(basicDBObject);
					
				}
				else{
					System.out.println("Data values does not match with column at line "+lineNumber +". Products: "+productValues.length);
				}
				lineNumber++;
			}
			
		}
		catch (IOException e) {
			System.out.println("Error uploading file..");
			System.out.println("Data is not uploaded to Mongo DB.");
			//e.printStackTrace();
			return;
		}
		catch (MongoException e) {
			System.out.println("Error: Mongo Db Error.");
			System.out.println("Please check server and mongo db on server is ruuning/installed.");
			System.out.println("Please check access to Mongo DB.");
			System.out.println("========================================");
			e.printStackTrace();
			return;
		  }
		finally {
			try {
				if (br != null)br.close();
				mongo.close();
			} catch (IOException ex) {
				//ex.printStackTrace();
			}
		}
	}
	
	public void insertPlacesDataToDB(String filePath)
	{
		/**File Read*/
		BufferedReader br = null;
		 
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(filePath));
			sCurrentLine = br.readLine();
			//System.out.println(sCurrentLine);
			String[] titleKeys = sCurrentLine.split(",");
			BasicDBObject basicDBObject;
			int lineNumber = 2;
			while((sCurrentLine = br.readLine())!= null)
			{
				//System.out.println("in while..");
				String productValues[] = sCurrentLine.split(",");
				//System.out.println(productValues.length);
				//System.out.println(titleKeys.length);
				if(titleKeys.length == productValues.length)
				{
					basicDBObject = new BasicDBObject();
					for(int i=0;i<titleKeys.length;i++)
					{
						
						if(i==15||i==19)
						{
							String[] subStringProduct = productValues[i].split("#");
							basicDBObject.put(titleKeys[i], subStringProduct);
						}
						else{
							basicDBObject.put(titleKeys[i], productValues[i]);
						}
					}
					int random = 1 + (int)(Math.random() * ((100 - 1) + 1));
					basicDBObject.put(NUMBER_OF_AD_DELIVERED, random);
					
					locationDataCollection.insert(basicDBObject);
					
				}
				else{
					System.out.println("Data values does not match with column at line "+lineNumber +". Products: "+productValues.length);
				}
				lineNumber++;
			}
			
		}
		catch (IOException e) {
			System.out.println("Error uploading file..");
			System.out.println("Data is not uploaded to Mongo DB.");
			//e.printStackTrace();
			return;
		}
		catch (MongoException e) {
			System.out.println("Error: Mongo Db Error.");
			System.out.println("Please check server and mongo db on server is ruuning/installed.");
			System.out.println("Please check access to Mongo DB.");
			System.out.println("========================================");
			//e.printStackTrace();
			return;
		  }
		finally {
			try {
				if (br != null)br.close();
				mongo.close();
			} catch (IOException ex) {
				//ex.printStackTrace();
			}
		}
	}
	
	public static void main(String args[])
	{
		AddDataSet insertProductObj;
		AddDataSet insertPlacesObj;
		try {
			insertProductObj = new AddDataSet();
			String filePath = "C:\\Users\\Abhi\\Desktop\\dataset\\products.csv";
			insertProductObj.insertProductDataToDB(filePath);
			insertPlacesObj = new AddDataSet();
			insertPlacesObj.insertPlacesDataToDB("C:\\Users\\Abhi\\Desktop\\dataset\\places.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
