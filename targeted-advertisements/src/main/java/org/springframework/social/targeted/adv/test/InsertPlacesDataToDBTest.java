package org.springframework.social.targeted.adv.test;

import static org.junit.Assert.*;
import mongo.*;

import org.junit.Test;

public class InsertPlacesDataToDBTest {

	// File Path Value Null
		@Test
		public void FilePathNullTest() {
			try {
				AddDataSet adt = new AddDataSet();
				//boolean sucess = adt.insertPlacesDataToDB(null);
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
				//boolean sucess = adt.insertPlacesDataToDB("");
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
				//boolean sucess = adt.insertPlacesDataToDB("adads:\\daasds");
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
				//boolean sucess = adt.insertPlacesDataToDB("C:\\Users\\Abhi\\Desktop\\dataset\\places.csv");
				//assertEquals(sucess, false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

}
