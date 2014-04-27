package org.springframework.social.targeted.adv.test;

import static org.junit.Assert.*;

import org.junit.Test;

import mongo.*;
public class InsertProductDataToDBTest {

	// File Path Value Null
	@Test
	public void FilePathNullTest() {
		try {
			AddDataSet adt = new AddDataSet();
			//boolean sucess = adt.insertProductDataToDB(null);
			//assertEquals(sucess, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	// File Path Value Blank
		@Test
		public void FilePathValueBlank() {
			try {
				AddDataSet adt = new AddDataSet();
				//boolean sucess = adt.insertProductDataToDB("");
				//assertEquals(sucess, false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
		
		// Invalid File Path
		@Test
		public void InvalidFilePathTest() {
			try {
				AddDataSet adt = new AddDataSet();
				//boolean sucess = adt.insertProductDataToDB("adads:\\daasds");
				//assertEquals(sucess, false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		// Valid Path Value Null
		@Test
		public void ValidPathValueTest() {
			try {
				AddDataSet adt = new AddDataSet();
				//boolean sucess = adt.insertProductDataToDB("C:\\Users\\Abhi\\Desktop\\dataset\\products.csv");
				//assertEquals(sucess, false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
