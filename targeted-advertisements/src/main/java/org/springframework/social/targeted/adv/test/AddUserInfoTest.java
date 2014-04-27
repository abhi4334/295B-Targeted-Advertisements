package org.springframework.social.targeted.adv.test;

import static org.junit.Assert.*;
import mongo.MongoController;

import org.junit.Test;

import mongo.*;

public class AddUserInfoTest {

	//Single Null Value Test
	@Test
	public void NullValueTest() {
		try {
			MongoController mongo = new MongoController();
			String success = mongo.addUserInfo("nishant.rangrej", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
										"Nishant", null);
		assertEquals(success, "false");	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
		
		//Multiple Null Values Test
		@Test
		public void MultipleNullValueTest() {
			try {
				MongoController mongo = new MongoController();
				String success = mongo.addUserInfo("nishant.rangrej", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
											null, null);
			assertEquals(success, "false");	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
			//All Null Values Test
			@Test
			public void AllNullValueTest() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo(null, null, null, null,
												null, null);
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
			
		
			//Single Blank Value Test
			@Test
			public void SingleBlankValueTest() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("nishant.rangrej", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			
			//Multiple Blank Value Test
			@Test
			public void MultipleBlankValueTest() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("nishant.rangrej", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//All Blank Value Test
			@Test
			public void AllBlankValueTest() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "", "", "",
												"", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			//Negative Single Value Test
			@Test
			public void NegativeSingleValueTest() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			//Valid Data  Test
			@Test
			public void ValidDataTest() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("nishant.rangrej", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "Rangrej");
				assertEquals(success, "true");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Value Test
			@Test
			public void NegativeTest() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("----", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"s__++))", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest2() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "%^&&", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest3() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"||||", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest4() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest5() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"(()", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest6() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												")__)", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest7() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest8() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest9() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest10() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest11() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest12() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest13() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest14() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest15() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest16() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest17() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest18() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest19() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest20() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest21() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest22() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest23() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest25() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest26() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest27() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest28() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest29() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest30() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Negative Single Value Test
			@Test
			public void NegativeTest31() {
				try {
					MongoController mongo = new MongoController();
					String success = mongo.addUserInfo("", "abcd", "nishant.rangrej", "nishant.rangrej@gmail.com",
												"Nishant", "");
				assertEquals(success, "false");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
}
