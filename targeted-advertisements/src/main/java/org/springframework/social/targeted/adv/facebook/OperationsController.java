package org.springframework.social.targeted.adv.facebook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import mongo.MongoController;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Checkin;
import org.springframework.social.facebook.api.EducationEntry;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.Page;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.WorkEntry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OperationsController {

	private final Facebook facebook;
	private final String LA = "Los Angeles";
	private final String SF = "San Francisco";
	
	@Inject
	private ConnectionRepository connectionRepository;
	@Inject
	public OperationsController(Facebook facebook) {
		this.facebook = facebook;
	}
	
	@RequestMapping(value="/facebook/UpdateProfile", method=RequestMethod.GET)
	public String addToUserInfo(Model model) {
		//model.addAttribute("friends", facebook.friendOperations().getFriendProfiles());
		
		try {
			Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);
			MongoController dbConnection = new MongoController();
			
			FacebookProfile myprofile = connection.getApi().userOperations().getUserProfile();
			String  user_object_id = dbConnection.addUserInfo(myprofile.getEmail(), "abcd", myprofile.getUsername(), myprofile.getEmail(), myprofile.getName(), myprofile.getLastName());
			String toemail = myprofile.getEmail();
			if(user_object_id != null)
			{
				//addToUserBehavior(myprofile,connection,user_object_id);
				//addToSocialData(connection,user_object_id);
				ArrayList<HashMap<String, String>> checkinMapList = getFriendsLatestCheckins25();
				ArrayList<HashMap<String, String>> restaurantMapList = new ArrayList<HashMap<String,String>>();
				if(checkinMapList.size()>0)
				{
					for(HashMap<String, String> newCheckinMap : checkinMapList)
					{
						MongoController dbConnection1 = new MongoController();
						String zipcode = newCheckinMap.get("zipcode");
						System.out.println(zipcode);
						if(zipcode!=null)
						{
							if(zipcode.length()>5){
								if(zipcode.charAt(5)=='-')
								{
									String zipExtn[] = zipcode.split("-");
									zipcode = zipExtn[1];
								}
							}
							HashMap<String, String> restaurantAdMap = dbConnection1.getRestaurantAdsFromZipcode(zipcode);
							restaurantAdMap.put("profilename",newCheckinMap.get("profilename"));
							restaurantAdMap.put("placename",newCheckinMap.get("placename"));
							restaurantAdMap.put("city",newCheckinMap.get("city"));
							restaurantAdMap.put("zipcode",newCheckinMap.get("zipcode"));
							restaurantMapList.add(restaurantAdMap);
						}
						
					}
					//System.out.println("Restaurant Map from DB. Size: "+restaurantMapList.size());
					AdsGeneration adGenerationObj = new AdsGeneration();
					boolean isRestaurantAdsGeneration = adGenerationObj.generateAds(restaurantMapList,toemail);
					if(isRestaurantAdsGeneration)
					{
						//redirect to success page
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "facebook/UpdateProfile";
	}
	
	private boolean addToUserBehavior(FacebookProfile myprofile,Connection<Facebook> connection,String user_object_id) throws NoSuchElementException {
		
		boolean isSuccessAddToSocialBehavior = false;
		try {
			MongoController dbConnection = new MongoController();
		
		List<String> locationList = new ArrayList<String>();
		List<String> hometownList = new ArrayList<String>();
		List<String> interestList = new ArrayList<String>();
		List<String> edu_schoolList = new ArrayList<String>();
		List<String> atheleteList_db = new ArrayList<String>();
		List<String> favTeamList_db = new ArrayList<String>();
		List<String> inspirationalPeopleList_db = new ArrayList<String>();
		List<String> sportsList_db = new ArrayList<String>();
		List<String> languagesList_db = new ArrayList<String>();
		List<String> work_employerList_db = new ArrayList<String>();	
		List<String> activityList_db = new ArrayList<String>();
		List<String> booksList_db = new ArrayList<String>();
		List<String> gameNamesList_db = new ArrayList<String>();
		List<String> moviesNamesList_db = new ArrayList<String>();
		List<String> musicList_db = new ArrayList<String>();		
		List<String> pagesLikeList_db = new ArrayList<String>();
		List<String> televisionList_db = new ArrayList<String>();	
		
		//System.out.println("Bio: "+myprofile.getBio());
		System.out.println("Gender: "+myprofile.getGender());
		System.out.println("FB ID: "+myprofile.getId());
		
		System.out.println("Location: "+myprofile.getLocation().getName()); //+ " ID: "+myprofile.getLocation().getId()
		locationList.add(myprofile.getLocation().getName());
		//locationList.add(myprofile.getLocation().getId());
		try{
		System.out.println("Hometown: "+myprofile.getHometown().getName()); //+ " ID: "+myprofile.getHometown().getId()
		hometownList.add(myprofile.getHometown().getName());
		//hometownList.add(myprofile.getHometown().getId());
		}
		catch(NullPointerException ne)
		{
			
		}
		System.out.println("Birthday: "+myprofile.getBirthday());
		System.out.println("Status: "+myprofile.getRelationshipStatus());
		System.out.println("Locale: "+myprofile.getLocale());
		
		System.out.println("Interests:=============================================================");
		interestList = myprofile.getInterestedIn();
		if(interestList!=null)
		{
			Iterator interestIte = interestList.iterator();
			while(interestIte.hasNext())
			{
				System.out.println(interestIte.next());
			}
		}
		
		System.out.println("Edu Schools:=============================================================");
		List<EducationEntry> eduList = myprofile.getEducation();
		if(eduList!=null)
		{
		Iterator<EducationEntry> eduIte = eduList.iterator();
		while(eduIte.hasNext())
		{
			try{
				String schoolname = eduIte.next().getSchool().getName(); 
				edu_schoolList.add(schoolname);
				System.out.println(schoolname);
			}
			catch(NoSuchElementException nex)
			{
				continue;
			}
		}
		}
		
		System.out.println("Athelete=============================================================");
		List<Reference> atheletesList = myprofile.getFavoriteAtheletes();
		if(atheletesList!=null)
		{
		Iterator<Reference> athIte = atheletesList.iterator();
		while(athIte.hasNext())
		{
			try{
				String atheletename = athIte.next().getName();
			atheleteList_db.add(atheletename);
			System.out.println(atheletename); 
			}
			catch(NoSuchElementException nex)
			{
				continue;
			}
		}
		}
		
		System.out.println("FavTeams=============================================================");
		List<Reference> favTeamList = myprofile.getFavoriteTeams();
		if(favTeamList!=null)
		{
		Iterator<Reference> favTeamIte = favTeamList.iterator();
		while(favTeamIte.hasNext())
		{
			String favteamname = favTeamIte.next().getName();
			favTeamList_db.add(favteamname);
			System.out.println(favteamname);
		}
		}
		
		System.out.println("Inspi ppl=============================================================");
		List<Reference> inspirationPeopleList = myprofile.getInspirationalPeople();
		if(inspirationPeopleList!=null)
		{
		Iterator<Reference> inspirationPeopleIte = inspirationPeopleList.iterator();
		while(inspirationPeopleIte.hasNext())
		{
			try{
				String inspipplname = inspirationPeopleIte.next().getName();
			inspirationalPeopleList_db.add(inspipplname);
			System.out.println(inspipplname);
			}
			catch(NoSuchElementException nex)
			{
				continue;
			}
		}
		}

		System.out.println("Sports=============================================================");
		List<Reference> sportsList = myprofile.getSports();
		if(sportsList!=null)
		{
		Iterator<Reference> sportsIte = sportsList.iterator();
		while(sportsIte.hasNext())
		{
			try{
				String sportsname = sportsIte.next().getName();
			sportsList_db.add(sportsname);
			System.out.println(sportsname);
			}
			catch(NoSuchElementException nex)
			{
				continue;
			}
		}
		}

		System.out.println("Languages=============================================================");
		List<Reference> languagesList = myprofile.getLanguages();
		if(languagesList!=null)
		{
		Iterator<Reference> languagesIte = languagesList.iterator();
		while(languagesIte.hasNext())
		{
			try{
				String languagename = languagesIte.next().getName();
				languagesList_db.add(languagename);
				System.out.println(languagename);
			}
			catch(NoSuchElementException nex)
			{
				continue;
			}
		}
		}
		
		System.out.println("Work=============================================================");
		List<WorkEntry> workEntryList = myprofile.getWork();
		if(workEntryList!=null)
		{
		Iterator<WorkEntry> workEntryIte = workEntryList.iterator();
		while(workEntryIte.hasNext())
		{
			try{
				String employername = workEntryIte.next().getEmployer().getName();
			work_employerList_db.add(employername);
			System.out.println(employername);
			}
			catch(NoSuchElementException nex)
			{
				continue;
			}
		}
		}
		
		/********************
		 * All Like Operations
		 * *******************/
		System.out.println("Activities=============================================================");
		PagedList<Page> activityList = connection.getApi().likeOperations().getActivities();
		if(activityList!=null)
		{
		Iterator<Page> activityItr = activityList.iterator();
		while(activityItr.hasNext())
		{
			try{
				String activityname = activityItr.next().getName();
				activityList_db.add(activityname);
				System.out.println(activityname);
			}
			catch(NoSuchElementException nex)
			{
				continue;
			}
		}
		}
		
		System.out.println("Books=============================================================");
		PagedList<Page> booksList = connection.getApi().likeOperations().getBooks();
		if (booksList != null) {
			Iterator<Page> booksItr = booksList.iterator();
			while (booksItr.hasNext()) {
				try{
					String bookname = booksItr.next().getName(); 
					booksList_db.add(bookname);
					System.out.println(bookname);
				}
				catch(NoSuchElementException nex)
				{
					continue;
				}
			}
		}
		
		System.out.println("Games=============================================================");
		PagedList<Page> allGamesList = connection.getApi().likeOperations().getGames();
		if (allGamesList != null) {
			Iterator<Page> gamesItr = allGamesList.iterator();
			while (gamesItr.hasNext()) {
				try{
					String gamesname = gamesItr.next().getName();
					gameNamesList_db.add(gamesname);
					System.out.println("Games: "+gamesname);
				}
				catch(NoSuchElementException nex)
				{
					continue;
				}
			}
		}
		
		System.out.println("Movies=============================================================");
		PagedList<Page> moviesList = connection.getApi().likeOperations().getMovies();
		if (moviesList != null) {
			Iterator<Page> moviesItr = moviesList.iterator();
			while (moviesItr.hasNext()) {
				try{
					String moviesname = moviesItr.next().getName();
					moviesNamesList_db.add(moviesname);
					System.out.println(moviesname);
				}catch(NoSuchElementException nex)
				{
					continue;
				}
			}
		}
		
		System.out.println("Music=============================================================");
		PagedList<Page> musicList = connection.getApi().likeOperations().getMusic();
		if (musicList != null) {
			Iterator<Page> musicItr = musicList.iterator();
			while (musicItr.hasNext()) {
				try{
					String musicname = musicItr.next().getName();
					musicList_db.add(musicname);
					System.out.println(musicname);
				}catch(NoSuchElementException nex)
				{
					continue;
				}
			}
		}
		
		System.out.println("PagesLike=============================================================");
		PagedList<Page> pagesLikeList = connection.getApi().likeOperations().getPagesLiked();
		if (pagesLikeList != null) {
			Iterator<Page> pagesLikeItr = pagesLikeList.iterator();
			
			while (pagesLikeItr.hasNext()) {
				try{
					String pageslikename = pagesLikeItr.next().getName();
					pagesLikeList_db.add(pageslikename);
					System.out.println(pageslikename);
				
				}catch(NoSuchElementException nex)
				{
					continue;
				}
			}
		}
		
		System.out.println("Television=============================================================");
		PagedList<Page> televisionList = connection.getApi().likeOperations().getTelevision();
		if (televisionList != null) {
			Iterator<Page> televisionItr = televisionList.iterator();
			while (televisionItr.hasNext()) {
				try{
					String televisionname = televisionItr.next().getName();
					televisionList_db.add(televisionname);
					System.out.println(televisionname);
				}
				catch(NoSuchElementException nex)
				{
					continue;
				}
			}
		}
		
		System.out.println("Checkins=============================================================");
		List<HashMap<String,String>> chekinsListMap_db = new ArrayList<HashMap<String,String>>();
		List<Checkin> checkinsPageList = facebook.placesOperations().getCheckins();
		System.out.println("Size: "+checkinsPageList.size());
		if(checkinsPageList.size() > 0)
		{
			System.out.println("inside if..");
			//System.out.println("Size: "+checkinsPageList.size());
			Iterator<Checkin> checkinPageListItr = checkinsPageList.iterator();
			while(checkinPageListItr.hasNext())
			{
				try{
				System.out.println("inside while..");
				HashMap<String, String> checkinMap = new HashMap<String, String>();
				Checkin tempCheckin = checkinPageListItr.next();
				checkinMap.put("city", tempCheckin.getPlace().getLocation().getCity());
				checkinMap.put("state", tempCheckin.getPlace().getLocation().getState());
				checkinMap.put("zipcode", tempCheckin.getPlace().getLocation().getZip());
				checkinMap.put("latitude", String.valueOf(tempCheckin.getPlace().getLocation().getLatitude()));
				checkinMap.put("longitude", String.valueOf(tempCheckin.getPlace().getLocation().getLongitude()));
				chekinsListMap_db.add(checkinMap);
				
				System.out.println("ID:"+tempCheckin.getId()); // getPlace().getName()
				System.out.println("location:"+tempCheckin.getPlace().getLocation().toString());
				System.out.println("place name:"+tempCheckin.getPlace().getName());
				System.out.println("Message:"+tempCheckin.getMessage());
				System.out.println("city:"+tempCheckin.getPlace().getLocation().getCity());
				System.out.println("zipcode:"+tempCheckin.getPlace().getLocation().getZip());
				System.out.println("lat:"+String.valueOf(tempCheckin.getPlace().getLocation().getLatitude()));
				System.out.println("lon:"+String.valueOf(tempCheckin.getPlace().getLocation().getLongitude()));
				System.out.println("****************************");
				}
				catch(NoSuchElementException nex)
				{
					System.out.println("Exception...");
					continue;
				}
			}
		}
		else{
			System.out.println("No Checkins..."+checkinsPageList.isEmpty());
		}
		
		dbConnection.addToUserBehavior(user_object_id,myprofile.getGender(),myprofile.getId(),locationList,hometownList,myprofile.getBirthday(),myprofile.getRelationshipStatus(),
				myprofile.getLocale().toString(),interestList,edu_schoolList,atheleteList_db,favTeamList_db,inspirationPeopleList,sportsList_db,languagesList_db,work_employerList_db,
				activityList_db,booksList_db,gameNamesList_db,moviesNamesList_db,musicList_db,pagesLikeList_db,televisionList_db,chekinsListMap_db);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isSuccessAddToSocialBehavior;
	}
	
	private boolean addToSocialData(Connection<Facebook> connection,String user_object_id)
	{
		boolean isSucessAddToSocialData = false;
		try {
			MongoController dbConnection = new MongoController();
			HashMap<String, String> friendMap = new HashMap<String, String>();
			List<FacebookProfile> friends = facebook.friendOperations()
					.getFriendProfiles();
			System.out.println("List size:" + friends.size());
			for (FacebookProfile profile : friends) {
				friendMap.put(profile.getId(), profile.getName());
			}
			// FacebookProfile myself =
			// facebook.userOperations().getUserProfile("100001581674631");
			System.out.println("Map size:" + friendMap.size());
			
			dbConnection.addToSocialdata(user_object_id,friendMap);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return isSucessAddToSocialData;
	}
	
	
	public ArrayList<HashMap<String, String>> getFriendsLatestCheckins25()
	{
		System.out.println("In getFriendsLatestCheckins25");
		Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);
		FacebookProfile myprofile = connection.getApi().userOperations().getUserProfile();
		ArrayList<HashMap<String,String>> chekinsMapList = new ArrayList<HashMap<String,String>>();
		ArrayList<HashMap<String, String>> newchekinsMapList = new ArrayList<HashMap<String,String>>();
		List<Checkin> checkinsPageList = facebook.placesOperations().getCheckins();
		System.out.println("Size: "+checkinsPageList.size());
		
		try{
		if(checkinsPageList.size() > 0)
		{
			Checkin myLatestCheckin = null;
			Iterator<Checkin> checkinPageListItr = checkinsPageList.iterator();
			String dateValue="01/01/2000";
	        Date latestCheckinDate =new SimpleDateFormat("dd/MM/yyyy").parse(dateValue);
			while(checkinPageListItr.hasNext())
			{
				System.out.println("in while..");
				Checkin myCheckins = checkinPageListItr.next();
				Date checkinDate = myCheckins.getCreatedTime();
				System.out.println("date: "+checkinDate);
				System.out.println("latest date: "+latestCheckinDate);
				if(latestCheckinDate.before(checkinDate))
				{
					System.out.println("in if..");
					latestCheckinDate = checkinDate;
					myLatestCheckin = myCheckins;
				}
			}
				
			
			HashMap<String, String> MyCheckinMap = new HashMap<String, String>();
			MyCheckinMap.put("profilename",myprofile.getFirstName());
			MyCheckinMap.put("placename",myLatestCheckin.getPlace().getName());
			MyCheckinMap.put("checkintime",myLatestCheckin.getCreatedTime().toString());
			MyCheckinMap.put("city", myLatestCheckin.getPlace().getLocation().getCity());
			MyCheckinMap.put("state", myLatestCheckin.getPlace().getLocation().getState());
			MyCheckinMap.put("zipcode", myLatestCheckin.getPlace().getLocation().getZip());
			MyCheckinMap.put("latitude", String.valueOf(myLatestCheckin.getPlace().getLocation().getLatitude()));
			MyCheckinMap.put("longitude", String.valueOf(myLatestCheckin.getPlace().getLocation().getLongitude()));
			chekinsMapList.add(MyCheckinMap);

			System.out.println("My Latest Checkin" + myLatestCheckin.getPlace().getName());
			
			List<FacebookProfile> friends = facebook.friendOperations()
					.getFriendProfiles();
			//System.out.println("List size:" + friends.size());
			int sameStateFriendNum = 0;
			int checkinCounter = 0;
			for (FacebookProfile profile : friends) {
				try{
				if(profile.getLocation()!=null&&profile.getLocation().getName()!=null&&profile.getLocation().getName()!="")
				{
				System.out.println("Friend Location: "+profile.getLocation().getName());
				String myState[] = myprofile.getLocation().getName().split(",");
				String userState[] = profile.getLocation().getName().split(",");
				if(userState[1].equalsIgnoreCase(myState[1])) //same as my state
				{
					sameStateFriendNum++;
					List<Checkin> friendsCheckinsPageList = facebook.placesOperations().getCheckins(profile.getId());
					System.out.println("Check ins: "+friendsCheckinsPageList.size() + " Profile: "+ profile.getFirstName());
					if(friendsCheckinsPageList.size() > 0)
					{
						Iterator<Checkin> friendCheckinPageListItr = friendsCheckinsPageList.iterator();
						//String dateValue="01/01/2000";
				        Date friendLatestCheckinDate =new SimpleDateFormat("dd/MM/yyyy").parse(dateValue);
						Checkin friendLatestCheckin = null;
						while(friendCheckinPageListItr.hasNext())
						{
							Checkin friendCheckins = friendCheckinPageListItr.next();
							Date checkinDate = friendCheckins.getCreatedTime();
							if(friendLatestCheckinDate.before(checkinDate))
							{
								latestCheckinDate = checkinDate;
								friendLatestCheckin = friendCheckins;
							}
						}
						try{
							checkinCounter++;
						HashMap<String, String> friendCheckinMap = new HashMap<String, String>();
						friendCheckinMap.put("profilename",profile.getFirstName());
						friendCheckinMap.put("placename",friendLatestCheckin.getPlace().getName());
						friendCheckinMap.put("checkintime",friendLatestCheckin.getCreatedTime().toString());
						friendCheckinMap.put("city", friendLatestCheckin.getPlace().getLocation().getCity());
						friendCheckinMap.put("state", friendLatestCheckin.getPlace().getLocation().getState());
						friendCheckinMap.put("zipcode", friendLatestCheckin.getPlace().getLocation().getZip());
						friendCheckinMap.put("latitude", String.valueOf(friendLatestCheckin.getPlace().getLocation().getLatitude()));
						friendCheckinMap.put("longitude", String.valueOf(friendLatestCheckin.getPlace().getLocation().getLongitude()));
						chekinsMapList.add(friendCheckinMap);
						}catch(NoSuchElementException nex)
						{
							continue;
						}
					}
					else{
						checkinCounter++;
					}
				}
				}
				else{
					System.out.println("==========Name: "+profile.getFirstName()+" does not have location.");
					continue;
				}
				}catch(ArrayIndexOutOfBoundsException ae)
				{
					continue;
				}
			}
			System.out.println("Same State friend Number: " +sameStateFriendNum);
			System.out.println("Checkin Counter: " +checkinCounter);
			System.out.println("Total Checkin Suggestions: "+chekinsMapList.size());
			}
			else{
				System.out.println("No Checkins..."+checkinsPageList.isEmpty());
			}
		
		
			for(HashMap<String, String> checkinMap : chekinsMapList)
			{
				System.out.println("City: "+ checkinMap.get("city"));
				try{
				if(checkinMap.get("city").equalsIgnoreCase(SF) || checkinMap.get("city").equalsIgnoreCase(LA))
				{
					newchekinsMapList.add(checkinMap);
				}
				
				}catch(NullPointerException ne)
				{
					continue;
				}
			}
			System.out.println("Total New Checkin Suggestions: "+newchekinsMapList.size());
			return newchekinsMapList;
		}
		catch(NoSuchElementException nex)
		{
			System.out.println("No Such Element Exception...");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return newchekinsMapList;
	}
	
	private void GetAllPagesLike()
	{
		PagedList<Page> pagesLikeList = facebook.likeOperations().getPagesLiked();
		if (pagesLikeList != null) {
			Iterator<Page> pagesLikeItr = pagesLikeList.iterator();
			
			while (pagesLikeItr.hasNext()) {
				try{
					String pageslikename = pagesLikeItr.next().getName();
					//pagesLikeList_db.add(pageslikename);
					//System.out.println(pageslikename);
				
				}catch(NoSuchElementException nex)
				{
					continue;
				}
			}
		}
		
		List<FacebookProfile> friends = facebook.friendOperations()
				.getFriendProfiles();
		//System.out.println("List size:" + friends.size());
		for (FacebookProfile profile : friends) 
		{
			try{
				PagedList<Page> friendPagesLikeList = facebook.likeOperations().getPagesLiked(profile.getId());
				if (friendPagesLikeList != null) 
				{
					Iterator<Page> pagesLikeItr = friendPagesLikeList.iterator();
					
					while (pagesLikeItr.hasNext()) {
						try{
							String pageslikename = pagesLikeItr.next().getName();
							//pagesLikeList_db.add(pageslikename);
							//System.out.println(pageslikename);
						
						}catch(NoSuchElementException nex)
						{
							continue;
						}
					}
				}
				
			}catch(ArrayIndexOutOfBoundsException ae){
				
			}
			
		}
			
	}
	
	/*public HashMap<String, String> getUserdetails()
	{
		HashMap<String, String> userDetailsMap = new HashMap<String,String>();
		try{
			Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);
			FacebookProfile myprofile = connection.getApi().userOperations().getUserProfile();
			userDetailsMap.put(myprofile.getId(), myprofile.getFirstName());
			
			List<FacebookProfile> friends = connection.getApi().friendOperations().getFriendProfiles();
			for(FacebookProfile profile : friends)
			{
				userDetailsMap.put(profile.getId(), profile.getName());
			}
			
		}catch(ArrayIndexOutOfBoundsException ae)
		{
			userDetailsMap = null;
			return userDetailsMap;
		}
		
		return userDetailsMap;
	}*/
	
}
