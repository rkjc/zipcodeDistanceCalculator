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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
		
		
		DB mass = mongoClient.getDB( "massive" );
		
		DBCollection stores = db.getCollection("stores");
		
		DBCursor cursor;
	
		Integer zip = 0;
		Double lat = 0.0;
		Double lon = 0.0;
		
		
		//load the zipcode lat-lon table into mongodb
//		File inFile = new File("zipLocs.csv");
//		if(inFile.isFile()){
//			coll.drop();
//			try {			
//				BufferedReader br = new BufferedReader(new FileReader(inFile));
//				StringBuilder sb = new StringBuilder();
//				String line = br.readLine();
//	
//				while (line != null) {
//				//for(int i = 0; i < 1000; i++){
//					zip = Integer.valueOf(line.substring(0, 5));
//					lat = Double.valueOf(line.substring(6, 15));
//					lon = Double.valueOf(line.substring(16, 26).trim());
//					
//					BasicDBObject doc = new BasicDBObject("zipcode", zip).
//			                append("latitude", lat).
//			                append("longitude", lon);
//			                
//					coll.insert(doc);
//					line = br.readLine();
//				}
//
//				br.close();
//	
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//		}
//		else {
//			System.out.println("could not find file");
//		}
		

//		BasicDBObject doc = new BasicDBObject("zipcode", zip).
//                append("latitude", lat).
//                append("longitude", lon);
//		
//		coll.insert(doc);

		DBObject myDoc;
		
		Integer thisZip = 63026;
		
		//myDoc = db.coll.find({ zipcode: thisZip});
		BasicDBObject query = new BasicDBObject("zipcode", 63025);
		//BasicDBObject query = new BasicDBObject();
		BasicDBObject field = new BasicDBObject();
		field.put("zipcode", 1);
		cursor = coll.find(query);
		ArrayList result = new ArrayList();
		
		//cursor = db.getCollection("zipcodeLocations").find(query,field);
		try {
			   while(cursor.hasNext()) {
				   BasicDBObject obj = (BasicDBObject) cursor.next();
				   zip = obj.getInt("zipcode");
				   lat = obj.getDouble("latitude");
				   lon = obj.getDouble("longitude");
					//result.add(obj.getString("zipcode"));
			       //System.out.println(cursor.next());
			   }
			} finally {
			   cursor.close();
			}
		
		//myDoc = coll.findOne({zipcode:  });
		//System.out.println(myDoc);
		System.out.println(zip);
		System.out.println(lat);
		System.out.println(lon);
		//System.out.println(result.get(0));
		System.out.println("");
		
		//print out the locations within distance collection
		HashMap<Integer, Double> zipDist = new HashMap<Integer, Double>();
		cursor = coll.find();
		try {
			//for(int i= 0; i < 50; i++){
			while(cursor.hasNext()) {
				BasicDBObject obj = (BasicDBObject) cursor.next();
				Double distance = ZipDistCalc.calcDistance(lat, lon, obj.getDouble("latitude"), obj.getDouble("longitude"));
			   if(distance  < 10.0){
				   //System.out.println(cursor.next());
				   zipDist.put(obj.getInt("zipcode"), distance);		   
			   }
		   }
		} finally {
		   cursor.close();
		}
		
		ArrayList storeDist = new ArrayList();
		
		System.out.println(zipDist);
		
		//DataManage.testForZipsCsv();
		//DataManage.parseZipsCsv();
		
		System.out.println("coll.getCount()" + coll.getCount());
		
		System.out.println("end of main");
		
		
		//double dist = ZipDistCalc.calcDistance(zip1, zip2);
		//System.out.println("distance is " + dist);
	}
	
	public static void storeDBcollectionZipcode(){
		
	}

}
