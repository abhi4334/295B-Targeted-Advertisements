package org.springframework.social.targeted.adv.facebook;

import java.util.ArrayList;
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
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.WorkEntry;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OperationsController {

	private final Facebook facebook;
	
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
			if(user_object_id != null)
			{
				addToUserBehavior(myprofile,connection,user_object_id);
				//addToSocialData(connection,user_object_id);
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
	
	
}
//facebook.userOperations().getUserProfile().getId())
