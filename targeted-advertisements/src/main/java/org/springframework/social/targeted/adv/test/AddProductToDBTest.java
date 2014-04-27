package org.springframework.social.targeted.adv.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mongodb.Mongo;

import mongo.*;

public class AddProductToDBTest {

	// Single Null Value Test
	@Test
	public void SingleNullValueTest() {
		try {
			MongoController mongo = new MongoController();
			boolean success = mongo.addProductToDB("nishant.rangrej", "Nike Zero", "Nike", "5", "Sport", "Nike Inc.", null);
			assertEquals(success, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	
	// Multiple Null Value Test
		@Test
		public void MultipleNullValueTest() {
			try {
				MongoController mongo = new MongoController();
				boolean success = mongo.addProductToDB(null, "Nike Zero", "Nike", "5", "Sport", "Nike Inc.", null);
				assertEquals(success, false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		
		
		// All Null Value Test	
		@Test
		public void AllNullValueTest() {
			try {
				MongoController mongo = new MongoController();
				boolean success = mongo.addProductToDB(null, null, null, null, null, null, null);
				assertEquals(success, false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		
		
		// Single Blank Value Test
		@Test
		public void SingleBlankValueTest() {
			try {
				MongoController mongo = new MongoController();
				boolean success = mongo.addProductToDB("", "Nike Zero", "Nike", "5", "Sport", "Nike Inc.", "50");
				assertEquals(success, false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		
		
		// Multiple Blank Value Test
		@Test
		public void MultipleBlankValueTest() {
			try {
				MongoController mongo = new MongoController();
				boolean success = mongo.addProductToDB("", "", "Nike", "5", "Sport", "Nike Inc.", "");
				assertEquals(success, false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		
		
		// All Blank Value Test
		@Test
		public void AllBlankValueTest() {
			try {
				MongoController mongo = new MongoController();
				boolean success = mongo.addProductToDB("", "", "", "", "", "", "");
				assertEquals(success, false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		
		
		// Negative Value Test
				@Test
				public void NegativeTest() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "Nike Zero", "Nike", "5", "Sport", "Nike Inc.", "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
		
		
				// Negative Value Test
				@Test
				public void NegativeTest2() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "Nike", "5", "Sport", "Nike Inc.", "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest2() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("???//", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest3() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("///", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest4() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest5() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("O>O", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest6() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "[]", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest7() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "", "{}{}", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest8() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "dsa", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest9() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest10() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest11() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest12() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest13() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest14() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest15() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest16() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest17() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest18() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest19() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest20() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest21() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				// Invalid Value Test
				@Test
				public void InvalideValueTest22() {
					try {
						MongoController mongo = new MongoController();
						boolean success = mongo.addProductToDB("", "**&", "^%&", "", "", null, "50");
						assertEquals(success, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				
				
				
}
