import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.UnknownHostException;
import java.util.Arrays;

public class ZipMain {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		
		// initialize a mongo instance, and handle any exceptions
		
		System.out.println("zipcode distance finder calculator test");
				
		MongoClient mongoClient = new MongoClient( "localhost" );
	

		DB db = mongoClient.getDB( "ziptest" );
		
		DBCollection coll = db.getCollection("zipcodeLocations");
		
		File inFile = new File("zipLocs.csv");
		
		Integer zip = 0;
		Double lat = 0.0;
		Double lon = 0.0;
		
		if(inFile.isFile()){
			try {
				BufferedReader br = new BufferedReader(new FileReader(inFile));
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();
	
				while (line != null) {
					zip = Integer.valueOf(line.substring(0, 5));
					lat = Double.valueOf(line.substring(6, 15));
					lon = Double.valueOf(line.substring(16, 26).trim());
					
					BasicDBObject doc = new BasicDBObject("zipcode", zip).
			                append("latitude", lat).
			                append("longitude", lon);
			                
					coll.insert(doc);
					line = br.readLine();
				}

				br.close();
	
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		else {
			System.out.println("could not find file");
		}
		

		BasicDBObject doc = new BasicDBObject("zipcode", zip).
                append("latitude", lat).
                append("longitude", lon);
                
		coll.insert(doc);

		System.out.println("coll.getCount()" + coll.getCount());
		
		DBObject myDoc = coll.findOne();
		System.out.println(myDoc);
		
		DBCursor cursor = coll.find();
		try {
		   while(cursor.hasNext()) {
		       System.out.println(cursor.next());
		   }
		} finally {
		   cursor.close();
		}
		
		//DataManage.testForZipsCsv();
		//DataManage.parseZipsCsv();
		
		System.out.println("end of main");
		
		
		//double dist = ZipDistCalc.calcDistance(zip1, zip2);
		//System.out.println("distance is " + dist);
	}
	
	public static void storeDBcollectionZipcode(){
	}

}
