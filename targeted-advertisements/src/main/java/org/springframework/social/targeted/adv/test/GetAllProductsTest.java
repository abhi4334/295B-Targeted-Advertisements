package org.springframework.social.targeted.adv.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import mongo.*;

public class GetAllProductsTest {

		// Null Value Test
		@Test
		public void NullValueTest() {
			try {
				MongoController mongo = new MongoController();
				ArrayList<ProductInfo> success = null;
				success = mongo.getAllProducts(null);
				assertEquals(success, null);	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}

	
		//Blank Value Test
		@Test
		public void BlankValueTest() {
			try {
				MongoController mongo = new MongoController();
				ArrayList<ProductInfo> success = null;
				success = mongo.getAllProducts("");
				assertEquals(success, null);	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		//Invalid Value Test
		@Test
		public void InvalidValueTest() {
			try {
				MongoController mongo = new MongoController();
				ArrayList<ProductInfo> success = null; 
				success = mongo.getAllProducts(null);
				assertEquals(success, null);	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
}
