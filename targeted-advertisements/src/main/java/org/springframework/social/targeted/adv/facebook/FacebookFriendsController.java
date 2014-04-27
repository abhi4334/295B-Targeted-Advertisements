/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.targeted.adv.facebook;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import mongo.MongoController;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FacebookFriendsController {

	private final Facebook facebook;
	
	@Inject
	public FacebookFriendsController(Facebook facebook) {
		this.facebook = facebook;
	}

	@RequestMapping(value="/facebook/friends", method=RequestMethod.GET)
	public String showFeed(Model model) {
		model.addAttribute("friends", facebook.friendOperations().getFriendProfiles());
		
		try {
		HashMap<String, String> friendMap = new HashMap<String, String>();
		List<FacebookProfile> friends = facebook.friendOperations().getFriendProfiles();
		System.out.println("List size:"+friends.size());
		for(FacebookProfile profile : friends)
		{
			friendMap.put(profile.getId(), profile.getName());
		}
		FacebookProfile myself = facebook.userOperations().getUserProfile("100001581674631");
		MongoController dbConnection = new MongoController();
		dbConnection.addUserInfo(myself.getEmail(), "abcd", myself.getUsername(), myself.getEmail(), myself.getName(), myself.getLastName());
		System.out.println("Map size:"+friendMap.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("facebook/friends");
		return "facebook/friends";
	}
	
	/**
	 * Abhi
	 * 
	 */
	@RequestMapping(value="/facebook/likesdemo", method=RequestMethod.GET)
	public String showLikes(Model model) {
		model.addAttribute("likesdemo", facebook.likeOperations().getPagesLiked("501968890")); //facebook.likeOperations().getPagesLiked("dhaivat")
		FacebookProfile firstFriend = facebook.userOperations().getUserProfile("501968890");
		System.out.println(firstFriend.getId());
		
		System.out.println("in controller.................");
		
		FacebookProfile myself = facebook.userOperations().getUserProfile("100001581674631");
		System.out.println(myself.getAbout());
		System.out.println(myself.getBio());
		System.out.println(myself.getEmail());
		System.out.println(myself.getFirstName());
		System.out.println(myself.getGender());
		System.out.println(myself.getId());
		System.out.println(myself.getLastName());
		System.out.println(myself.getUsername());
		System.out.println(myself.getInterestedIn());
		/*Iterator it;
		it = myself.getSports().iterator();
		while(it.hasNext())
		{
			System.out.println(it.next().toString());
		}*/
		System.out.println(myself.getLocation().getName());
		System.out.println(myself.getHometown().getName());
		return "facebook/likesdemo";
	}
	
}
