package mongo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.bson.types.ObjectId;
import org.springframework.social.facebook.api.Reference;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class MongoController {
	
	final private String DB_CONFIG_PROP_FILE_NAME = "DBConfiguration.properties";
	final private String N_LOGIN_ID = "login_id";
	final private String N_PWD = "pwd";
	final private String N_FACEBOOK_ID = "facebook_id";
	final private String N_EMAIL = "email";
	final private String N_NAME = "name";
	final private String N_LNAME = "lname";
	
	private final String USER_NAME = "userName";
	private final String USER_INFO_ID = "user_info_id";
	private final String GENDER = "gender";
	private final String FB_ID = "fb_id";
	private final String LOCATIONS = "locations";
	private final String HOMETOWN = "hometown";
	private final String BIRTHDATE = "birthdate";
	private final String RELATIONSHIP_STATUS = "relationship_status";
	private final String LOCALE = "locale";
	private final String INTERESTS = "interests";
	private final String SCHOOLS = "schools";
	private final String ATHELETES = "atheletes";
	private final String FAV_TEAMS = "fav_teams";
	private final String INSPI_PPL = "inspi_ppl";
	private final String SPORTS = "sports";
	private final String LANGUAGES = "languages";
	private final String EMPLOYER = "employer";
	private final String ACTIVITY = "activity";
	private final String BOOKS = "books";
	private final String GAMES = "games";
	private final String MOVIES = "movies";
	private final String MUSIC = "music";
	private final String PAGESLIKE = "pageslike";
	private final String TELEVISIONS = "televisions";
	private final String CHECKINS = "checkins";
	private final String NUMBER_OF_AD_DELIVERED = "numberOfAdDelivered"; 
	private final String FRIENDLIST = "friendlist";
	private final String P_NAME = "name";
	private final String P_BRAND = "brand";
	private final String P_SIZE = "size";
	private final String P_CATEGORY = "category";
	private final String P_MANUFACTURER = "manufacturer";
	private final String p_AVG_PRICE = "avgPrice";
	private final String CATEGORY_JSON = "category_JSON";
	private final String l_ZIPCODE = "postcode";
	private final String l_NAME = "name";
	private final String l_ADDRESS = "address";
	private final String l_LOCALITY = "locality";
	private final String l_TEL = "tel";
	private final String l_WEBSITE = "website";
	
	private Properties DBConfigurationProperties = new Properties();
	private MongoClient mongo;
	private DB db;
	private DBCollection userInfoCollection;
	private DBCollection userBehaviorCollection;
	private DBCollection userSocialCollection;
	private DBCollection productDataCollection;
	private DBCollection locationDataCollection;
	
	public MongoController() throws Exception {
		try {
			DBConfigurationProperties.load(getClass().getClassLoader().getResourceAsStream(DB_CONFIG_PROP_FILE_NAME));
			
			/**Mongo DB*/
			mongo = new MongoClient(DBConfigurationProperties.getProperty("server"), 27017);	//DBConfigurationProperties.getProperty("server")
			 
			db = mongo.getDB(DBConfigurationProperties.getProperty("database_name"));	// if database doesn't exists, MongoDB will create new
			
			userInfoCollection = db.getCollection("userInfoCollection"); //create new collections if not exists
			userBehaviorCollection = db.getCollection("userBehaviorCollection");
			userSocialCollection = db.getCollection("userSocialCollection");
			productDataCollection = db.getCollection("ProductDataCollection"); //create new collections if not exists
			locationDataCollection = db.getCollection("LocationDataCollection");
			
		} catch (IOException e) {
			System.out.println("Property file does not exists..Please contact Admin..");
			//e.printStackTrace();
		}
	}
		
		public String addUserInfo(String login_id, String pwd,String facebook_id, String email, String name, String lname)
		{
			boolean isUserExists = false;
			ObjectId documentId = null;
			
			try {
				BasicDBObject newUserDBObject = new BasicDBObject();
				newUserDBObject.put(N_LOGIN_ID, login_id);
				newUserDBObject.put(N_PWD, pwd);
				newUserDBObject.put(N_FACEBOOK_ID, facebook_id);
				newUserDBObject.put(N_EMAIL, email);
				newUserDBObject.put(N_NAME, name);
				newUserDBObject.put(N_LNAME, lname);
				newUserDBObject.put("totalAdsReceived", 0);
				
				BasicDBObject userSearchDbObj = new BasicDBObject();
				userSearchDbObj.put(N_LOGIN_ID, login_id);
				
				if (userInfoCollection.findOne(userSearchDbObj) != null)
				{
					isUserExists = true;
				}
				
				if(isUserExists)
				{
					BasicDBObject updateUserDBObject = new BasicDBObject();
					updateUserDBObject.put(N_LOGIN_ID, login_id);
					updateUserDBObject.put(N_PWD, pwd);
					updateUserDBObject.put(N_FACEBOOK_ID, facebook_id);
					updateUserDBObject.put(N_EMAIL, email);
					updateUserDBObject.put(N_NAME, name);
					updateUserDBObject.put(N_LNAME, lname);
					userInfoCollection.update(userSearchDbObj, updateUserDBObject,true,false);
					documentId = (ObjectId) userInfoCollection.findOne(userSearchDbObj).get("_id");
					System.out.println("After update..ID: "+documentId);
				}
				else{
					userInfoCollection.insert(newUserDBObject);
					documentId = (ObjectId) newUserDBObject.get("_id");
				}

				
			} 
			catch (MongoException e) {
				System.out.println("Error: Mongo Db Error.");
				System.out.println("Please check server and mongo db on server is ruuning/installed.");
				System.out.println("Please check access to Mongo DB.");
				System.out.println("========================================");
				//e.printStackTrace();
				
			  }
			finally {
				mongo.close();
			}
			System.out.println("Done Uploading to Mongo DB.");
			System.out.println("========================================");
			System.out.println("Document ID: "+documentId.toString());
			return documentId.toString();
		}
		
		public boolean addToUserBehavior(String user_object_id,String gender,String fb_id,List<String> locationList,
				List<String> hometownList,String birthdate,String relationshipStatus,String locale,List<String> interestList,
				List<String> edu_schoolList,List<String> atheleteList_db,List<String> favTeamList_db,
				List<Reference> inspirationPeopleList,List<String> sportsList_db,List<String> languagesList_db,
				List<String> work_employerList_db,List<String> activityList_db,List<String> booksList_db,
				List<String> gameNamesList,List<String> moviesNamesList,List<String> musicList_db,List<String> pagesLikeList_db,
				List<String> televisionList_db,List<HashMap<String, String>> checkinsListMap_db)
		{
			
			boolean isUserExists = false;
			try{
				//System.out.println("In user behavior db method...");
				BasicDBObject userSearchDbObj = new BasicDBObject();
				userSearchDbObj.put("_id", new ObjectId(user_object_id));
				
				if (userInfoCollection.findOne(userSearchDbObj) != null)
				{
					isUserExists = true;
				}
				BasicDBObject userBehaviorObject = new BasicDBObject();
				userBehaviorObject.put(USER_INFO_ID, user_object_id);
				userBehaviorObject.put(GENDER,gender);
				userBehaviorObject.put(FB_ID,fb_id);
				userBehaviorObject.put(LOCATIONS,locationList);
				userBehaviorObject.put(HOMETOWN,hometownList);
				userBehaviorObject.put(BIRTHDATE,birthdate);
				userBehaviorObject.put(RELATIONSHIP_STATUS,relationshipStatus);
				userBehaviorObject.put(LOCALE,locale);
				userBehaviorObject.put(INTERESTS,interestList);
				userBehaviorObject.put(SCHOOLS,edu_schoolList);
				userBehaviorObject.put(ATHELETES,atheleteList_db);
				userBehaviorObject.put(FAV_TEAMS,favTeamList_db);
				userBehaviorObject.put(INSPI_PPL,inspirationPeopleList);
				userBehaviorObject.put(SPORTS,sportsList_db);
				userBehaviorObject.put(LANGUAGES,languagesList_db);
				userBehaviorObject.put(EMPLOYER,work_employerList_db);
				userBehaviorObject.put(ACTIVITY,activityList_db);
				userBehaviorObject.put(BOOKS,booksList_db);
				userBehaviorObject.put(GAMES,gameNamesList);
				userBehaviorObject.put(MOVIES,moviesNamesList);
				userBehaviorObject.put(MUSIC,musicList_db);
				userBehaviorObject.put(PAGESLIKE,pagesLikeList_db);
				userBehaviorObject.put(TELEVISIONS,televisionList_db);
				userBehaviorObject.put(CHECKINS,checkinsListMap_db);
				
				if(isUserExists)
				{
					BasicDBObject seachUserInfoId = new BasicDBObject();
					seachUserInfoId.put(USER_INFO_ID , user_object_id);
					if(userBehaviorCollection.findOne(seachUserInfoId) != null)
					{
						userBehaviorCollection.update(seachUserInfoId, userBehaviorObject,true,false);
						//System.out.println("After Update..");
					}
					else{
						userBehaviorCollection.insert(userBehaviorObject);
						//System.out.println("After insert..");
					}
					
				}
				else{
					//error as user have to create account first.
					//userBehaviorCollection.insert(userBehaviorObject);
				}
				
			}
			finally {
				mongo.close();
			}
			return true;
		}

		public void addToSocialdata(String user_object_id,HashMap<String, String> friendMap) 
		{
			boolean isUserExists = false;
			try{
				BasicDBObject userSearchDbObj = new BasicDBObject();
				userSearchDbObj.put("_id", new ObjectId(user_object_id));
				
				BasicDBObject userSocialObject = new BasicDBObject();
				userSocialObject.put(USER_INFO_ID, user_object_id);
				userSocialObject.put(FRIENDLIST,friendMap);
				
				if (userInfoCollection.findOne(userSearchDbObj) != null)
				{
					isUserExists = true;
				}
				
				if(isUserExists)
				{
					BasicDBObject searchUserInfoId = new BasicDBObject();
					searchUserInfoId.put(USER_INFO_ID , user_object_id);
					if(userSocialCollection.findOne(searchUserInfoId) != null)
					{
						userSocialCollection.update(searchUserInfoId, userSocialObject,true,false);
						//System.out.println("After Update..");
					}
					else{
						userSocialCollection.insert(userSocialObject);
						//System.out.println("After insert..");
					}
					
				}
				else{
					//error as user have to create account first.
					//userBehaviorCollection.insert(userBehaviorObject);
				}
			}
			finally {
				mongo.close();
			}
				
		}
		
		public boolean addProductToDB(String userName,String name,String brand,String size,String category,String manufacturer,
				String avgPrice)
		{
			boolean isSuccess = false;
			BasicDBObject basicObject = new BasicDBObject();
			
			try{
				//System.out.println("In Add Product DB Method");
				basicObject.put(P_NAME,name);
				basicObject.put(P_BRAND,brand);
				basicObject.put(P_SIZE,size);
				basicObject.put(P_CATEGORY,category);
				basicObject.put(P_MANUFACTURER,manufacturer);
				basicObject.put(p_AVG_PRICE,avgPrice);
				basicObject.put(NUMBER_OF_AD_DELIVERED, 0);
				basicObject.put(USER_NAME, userName);
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
				productInfoList= null;
				return productInfoList;
			  }
			finally {
					mongo.close();
			}
		}
		
		public BasicDBObject getAllProductCategories(String userName)
		{
			BasicDBObject categoriesJson = new BasicDBObject();
			ArrayList<String> catogoriesList = new ArrayList<String>();
			
			try{
				System.out.println("In Get All Product Categories DB Method");
				
				BasicDBObject searchUserDbObj = new BasicDBObject();
				searchUserDbObj.put(USER_NAME, userName);
				
				DBCursor cursor =  productDataCollection.find(searchUserDbObj);
				//System.out.println("Cursor Count: "+cursor.count());
				while(cursor.hasNext())
				{
					BasicDBObject cursorBasicObj = (BasicDBObject) cursor.next();
					catogoriesList.add(cursorBasicObj.getString("category"));
				}
				categoriesJson.put(CATEGORY_JSON, catogoriesList);
				return categoriesJson;
			}
			catch (MongoException e) {
				System.out.println("Error: Mongo Db Error.");
				System.out.println("Please check server and mongo db on server is ruuning/installed.");
				System.out.println("Please check access to Mongo DB.");
				System.out.println("========================================");
				e.printStackTrace();
				categoriesJson = null;
				return categoriesJson;
			  }
			finally {
					mongo.close();
			}
		}
		
		public ArrayList<BasicDBObject> getAdsDeliveredOfRetailer(String userName)
		{
			ArrayList<BasicDBObject> adsDeliveredOfRetailerList = new ArrayList<BasicDBObject>();
			
			try{
				System.out.println("In Get NumOfAdForRetailer DB Method");
				
				BasicDBObject searchUserDbObj = new BasicDBObject();
				searchUserDbObj.put(USER_NAME, userName);
				
				DBCursor cursor =  productDataCollection.find(searchUserDbObj);
				//System.out.println("Cursor Count: "+cursor.count());
				while(cursor.hasNext())
				{
					BasicDBObject productNameAdsObj = new BasicDBObject();
					BasicDBObject cursorBasicObj = (BasicDBObject) cursor.next();
					productNameAdsObj.put(P_NAME,cursorBasicObj.getString(P_NAME));
					productNameAdsObj.put(NUMBER_OF_AD_DELIVERED,cursorBasicObj.getString(NUMBER_OF_AD_DELIVERED));
					adsDeliveredOfRetailerList.add(productNameAdsObj);
				}
				
				return adsDeliveredOfRetailerList;
			}
			catch (MongoException e) {
				System.out.println("Error: Mongo Db Error.");
				System.out.println("Please check server and mongo db on server is ruuning/installed.");
				System.out.println("Please check access to Mongo DB.");
				System.out.println("========================================");
				e.printStackTrace();
				adsDeliveredOfRetailerList = null;
				return adsDeliveredOfRetailerList;
			  }
			finally {
					mongo.close();
			}
	}
		
		public BasicDBObject getAdsDeliveredOfProduct(String productId)
		{
			BasicDBObject adsDeliveredOfproductJson = new BasicDBObject();
			
			try{
				System.out.println("In Get NumOfAdOfProduct DB Method");
				
				BasicDBObject searchProductDbObj = new BasicDBObject();
				searchProductDbObj.put("_id", productId);		// TODO change...
				
				DBCursor cursor =  productDataCollection.find(searchProductDbObj);
				//System.out.println("Cursor Count: "+cursor.count());
				while(cursor.hasNext())
				{
					BasicDBObject cursorBasicObj = (BasicDBObject) cursor.next();
					adsDeliveredOfproductJson.put(P_NAME, cursorBasicObj.getString(P_NAME));
					adsDeliveredOfproductJson.put(NUMBER_OF_AD_DELIVERED, cursorBasicObj.getString(NUMBER_OF_AD_DELIVERED));
					break;
				}
				return adsDeliveredOfproductJson;
			}
			catch (MongoException e) {
				System.out.println("Error: Mongo Db Error.");
				System.out.println("Please check server and mongo db on server is ruuning/installed.");
				System.out.println("Please check access to Mongo DB.");
				System.out.println("========================================");
				e.printStackTrace();
				adsDeliveredOfproductJson = null;
				return adsDeliveredOfproductJson;
			  }
			finally {
					mongo.close();
			}
		}
		
		public ArrayList<BasicDBObject> getNumOfAdsForManufacturers(String userName,ArrayList<String> manufacturers)
		{
			ArrayList<BasicDBObject> numOfAdsForAllManufacturersList = new ArrayList<BasicDBObject>();
			
			try{
				System.out.println("In Get getNumOfAdsForManufacturers DB Method");
				DBCursor cursor;
				for(String manufacturer:manufacturers)
				{
					BasicDBObject numOfAdsForEachManufacturers = new BasicDBObject();
					int totalAds = 0;
					BasicDBObject searchProductDbObj = new BasicDBObject();
					searchProductDbObj.put(USER_NAME, userName);
					searchProductDbObj.put(P_MANUFACTURER, manufacturer);	
					cursor  =  productDataCollection.find(searchProductDbObj);
					
					while(cursor.hasNext())
					{
						BasicDBObject cursorBasicObj = (BasicDBObject) cursor.next();
						totalAds += cursorBasicObj.getInt(NUMBER_OF_AD_DELIVERED);
					}
					numOfAdsForEachManufacturers.put("manufacturer", manufacturer);
					numOfAdsForEachManufacturers.put("totalAds", totalAds);
					numOfAdsForAllManufacturersList.add(numOfAdsForEachManufacturers);
				}

				return numOfAdsForAllManufacturersList;
			}
			catch (MongoException e) {
				System.out.println("Error: Mongo Db Error.");
				System.out.println("Please check server and mongo db on server is ruuning/installed.");
				System.out.println("Please check access to Mongo DB.");
				System.out.println("========================================");
				e.printStackTrace();
				numOfAdsForAllManufacturersList = null;
				return numOfAdsForAllManufacturersList;
			  }
			finally {
					mongo.close();
			}
		}
		
		public ArrayList<BasicDBObject> testGraphData()
		{
			ArrayList<BasicDBObject> graphDataresult = new ArrayList<BasicDBObject>();
			
			try{
				for(int i=0;i<10;i++)
				{
					BasicDBObject graphDataJson = new BasicDBObject();
					graphDataJson.put("a","a"+i);
					graphDataJson.put("b",i);
					graphDataresult.add(graphDataJson);
				}
				
				return graphDataresult;
			}
			catch (MongoException e) {
				System.out.println("Error: Mongo Db Error.");
				System.out.println("Please check server and mongo db on server is ruuning/installed.");
				System.out.println("Please check access to Mongo DB.");
				System.out.println("========================================");
				e.printStackTrace();
				graphDataresult = null;
				return graphDataresult;
			  }
			finally {
					mongo.close();
			}
	}

		public HashMap<String, String> getRestaurantAdsFromZipcode(String zipcode) 
		{
			HashMap<String, String> restaurantAdMap = new HashMap<String, String>();
			final int TOTALADS = 1;
			try{
				int adCounter = 0;
				BasicDBObject searchRestaurantDbObj = new BasicDBObject();
				searchRestaurantDbObj.put(l_ZIPCODE, zipcode);
				DBCursor cursor;
				if(locationDataCollection.findOne(searchRestaurantDbObj)!=null){
					
					cursor  =  locationDataCollection.find(searchRestaurantDbObj);
					while(cursor.hasNext())
					{
						if(adCounter!=TOTALADS)
						{
							BasicDBObject cursorBasicObj = (BasicDBObject) cursor.next();
							restaurantAdMap.put(l_NAME,cursorBasicObj.getString(l_NAME));
							restaurantAdMap.put(l_ADDRESS,cursorBasicObj.getString(l_ADDRESS));
							restaurantAdMap.put(l_LOCALITY,cursorBasicObj.getString(l_LOCALITY));
							restaurantAdMap.put(l_ZIPCODE,cursorBasicObj.getString(l_ZIPCODE));
							restaurantAdMap.put(l_TEL,cursorBasicObj.getString(l_TEL));
							restaurantAdMap.put(l_WEBSITE,cursorBasicObj.getString(l_WEBSITE));
							//BasicDBObject incrementAdNumberObj = new BasicDBObject();
							//incrementAdNumberObj.put(NUMBER_OF_AD_DELIVERED,oldNumber+1);
							int oldNumber = (int) cursorBasicObj.get(NUMBER_OF_AD_DELIVERED);
							int newNumber = oldNumber+1;
							ObjectId documentId = (ObjectId) cursorBasicObj.get("_id");
							BasicDBObject searchRestaurantEntry = new BasicDBObject();
							searchRestaurantEntry.put("_id", documentId);
							System.out.println("Documet ID: "+documentId);
							BasicDBObject updateDbObject = new BasicDBObject();
							updateDbObject.append("$set", new BasicDBObject().append(NUMBER_OF_AD_DELIVERED, newNumber));
							locationDataCollection.update(searchRestaurantEntry,updateDbObject,true,false);
							System.out.println("New Count for "+cursorBasicObj.getString(l_NAME)+": "+cursorBasicObj.get(NUMBER_OF_AD_DELIVERED));
							adCounter++;
						}
						else{
							break;
						}
					}
					
				}
				
				return restaurantAdMap;
			}
			catch (MongoException e) {
				System.out.println("Error: Mongo Db Error.");
				System.out.println("Please check server and mongo db on server is ruuning/installed.");
				System.out.println("Please check access to Mongo DB.");
				System.out.println("========================================");
				e.printStackTrace();
				restaurantAdMap = null;
				return restaurantAdMap;
			  }
			finally {
					mongo.close();
			}
		}
		
		public ArrayList<BasicDBObject> getNumberOfRestarant() 
		{
			ArrayList<BasicDBObject> graphDataresult = new ArrayList<BasicDBObject>();
			try{
				DBCursor cursor;
				if(locationDataCollection.find()!=null){
					
					cursor  =  locationDataCollection.find();
					while(cursor.hasNext())
					{
						BasicDBObject graphJsosObj = new BasicDBObject();
						BasicDBObject cursorBasicObj = (BasicDBObject) cursor.next();
						if((int) cursorBasicObj.get(NUMBER_OF_AD_DELIVERED)<2)
						{
							continue;
						}
						graphJsosObj.put("a",cursorBasicObj.get(l_NAME).toString());
						graphJsosObj.put("b",(int) cursorBasicObj.get(NUMBER_OF_AD_DELIVERED));
						graphDataresult.add(graphJsosObj);
						if(graphDataresult.size()==10)
						{
							break;
						}
					}
				}
					
				System.out.println("Graph size for restaurant: "+graphDataresult.size());
				
				return graphDataresult;
			}
			catch (MongoException e) {
				System.out.println("Error: Mongo Db Error.");
				System.out.println("Please check server and mongo db on server is ruuning/installed.");
				System.out.println("Please check access to Mongo DB.");
				System.out.println("========================================");
				e.printStackTrace();
				graphDataresult = null;
				return graphDataresult;
			  }
			finally {
					mongo.close();
			}
		}
		
}

