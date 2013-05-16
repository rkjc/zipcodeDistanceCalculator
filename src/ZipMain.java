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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ZipMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		// make sure to run mongo first or will throw many exceptions

		System.out.println("zipcode distance finder calculator test");

		MongoClient mongoClient = new MongoClient("localhost");

		DB db = mongoClient.getDB("ziptest");

		DBCollection coll = db.getCollection("zipcodeLocations");

		DB mass = mongoClient.getDB("massive");

		DBCollection storesCollection = mass.getCollection("stores");

		System.out.println("storesCollection.getCount() in Main "
				+ storesCollection.getCount());

		System.out.println("coll.getCount() in Main " + coll.getCount());

		// run this once at the beginning of development session
		// DataManage.loadZipFileIntoDB();
		DataManage.loadZipFileIntoMassive();
		//DataManage.makeCAzips();
		
		// make an arraylist of all the stores
		DBCursor myCursor;
		myCursor = storesCollection.find();
		List<DBObject> stores = new ArrayList<DBObject>();
		stores = myCursor.toArray();
		myCursor.close();


		//get hashMap of zipcodes and distances within distance dist from zipcode zip (zip, dist)
		HashMap<Integer, Double> zipDist = getZipcodeDistanceHahMap(90032, 12.0);
		
		System.out.println("zipDist hashMap = " + zipDist);
		System.out.println("zipDist hashMap size = " + zipDist.size());

		//HashMap<Double, DBObject> distanceByStore = new HashMap<Double, DBObject>();

		//String storeZipS = "";
		Integer storeZip = 0;
		//Double storeLat = 0.0;
		//Double storeLon = 0.0;

		//List<DBObject> stores = new ArrayList<DBObject>(); filled with all stores
		Iterator<DBObject> iterator = stores.iterator();
		System.out.println("size of stores list = " + stores.size());
		Map<Double, DBObject> distanceByStore = new TreeMap<Double, DBObject>();

		DBObject obj;
		while (iterator.hasNext()) {
			obj = iterator.next();
			storeZip = Integer.parseInt((String) (obj).get("zipcode"));
			System.out.println("zipDist contains " + storeZip + "  "
					+ zipDist.containsKey(storeZip));
			
			if (zipDist.containsKey(storeZip)) {
				System.out.println("distance to store = "
						+ zipDist.get(storeZip));
				distanceByStore.put(zipDist.get(storeZip), obj);
			}
			
			iterator.remove();
		}

		ArrayList<DBObject> returnStores = new ArrayList<DBObject>();

		for (Map.Entry<Double, DBObject> entry : distanceByStore.entrySet()) {
			Double key = entry.getKey();
			System.out.println("\ndistance to store = " + key);
			DBObject value = entry.getValue();
			System.out.println(" store ID = " + value.get("_id"));
			returnStores.add(value);
		}

		//System.out.println("\nreturnStores = " + returnStores);
		
		// iterate through stores list
		// check if hashmap zipDist contains that zip as key
		// if yes add that store object and distance to ArrayList storeDist
		// order storeDist list by distance small->largest
		// copy to list containing just the stores in order
		// return list to client

		// System.out.println(zipDist);

		System.out.println("end of main");
	}

	public static HashMap<Integer, Double> getZipcodeDistanceHahMap(Integer thisZip, Double dist) throws Exception{
		MongoClient mongoClient = new MongoClient("localhost");
		DB db = mongoClient.getDB("ziptest");
		DBCollection coll = db.getCollection("zipcodeLocations");
		
		// gets lat and lon for zipcode thisZip
				Integer zip = 0;
				Double lat = 0.0;
				Double lon = 0.0;
				DBCursor cursor;
				//Integer thisZip = 90032; // csula zipcode
				BasicDBObject query = new BasicDBObject("zipcode", thisZip);
				cursor = coll.find(query);
				try {
					while (cursor.hasNext()) {
						BasicDBObject obj = (BasicDBObject) cursor.next();
						zip = obj.getInt("zipcode");
						lat = obj.getDouble("latitude");
						lon = obj.getDouble("longitude");
						// result.add(obj.getString("zipcode"));
						// System.out.println(cursor.next());
					}
				} finally {
					cursor.close();
				}

				System.out.println(zip);
				System.out.println(lat);
				System.out.println(lon);
				System.out.println("");

				// build a hashmap of zipcodes that meet the criteria
				HashMap<Integer, Double> zipDist = new HashMap<Integer, Double>();
				DBCursor cursor2 = coll.find();
				Double maxDistance = 12.0;
				try {
					// for(int i= 0; i < 50; i++){
					while (cursor2.hasNext()) {
						DBObject obj = (DBObject) cursor2.next();
						Double distance = ZipDistCalc.calcDistance(lat, lon,
								(Double)obj.get("latitude"), (Double)(obj.get("longitude")));
						if (distance < maxDistance) {
							System.out.println("(Integer)obj.get(\"zipcode\")   "
									+ (Integer)(obj.get("zipcode")));
							zipDist.put((Integer)(obj.get("zipcode")), distance);
						}
					}
				} finally {
					cursor2.close();
				}
				
				return zipDist;
	}

}
