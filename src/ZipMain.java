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

		// make an arraylist of all the stores
		DBCursor myCursor;
		myCursor = storesCollection.find();
		List<DBObject> stores = new ArrayList<DBObject>();
		stores = myCursor.toArray();
		myCursor.close();

		// gets lat and lon for zipcode thisZip
		Integer zip = 0;
		Double lat = 0.0;
		Double lon = 0.0;
		DBCursor cursor;
		Integer thisZip = 90032; // csula zipcode
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
		cursor = coll.find();
		Double maxDistance = 12.0;
		try {
			// for(int i= 0; i < 50; i++){
			while (cursor.hasNext()) {
				BasicDBObject obj = (BasicDBObject) cursor.next();
				Double distance = ZipDistCalc.calcDistance(lat, lon,
						obj.getDouble("latitude"), obj.getDouble("longitude"));
				if (distance < maxDistance) {
					System.out.println("(Integer)obj.getInt(\"zipcode\")   "
							+ (Integer) obj.getInt("zipcode"));
					zipDist.put((Integer) obj.getInt("zipcode"), distance);
				}
			}
		} finally {
			cursor.close();
		}

		System.out.println("zipDist hashMap = " + zipDist);
		System.out.println("zipDist hashMap size = " + zipDist.size());

		HashMap<Double, DBObject> distanceByStore = new HashMap<Double, DBObject>();

		String storeZipS = "";
		Integer storeZip = 0;
		Double storeLat = 0.0;
		Double storeLon = 0.0;

		Iterator<DBObject> iterator = stores.iterator();
		System.out.println("size of stores list = " + stores.size());
		int i = 0;
		DBObject obj;
		while (iterator.hasNext()) {
			System.out.println(" i = " + i++);
			// storeZip =
			// Integer.getInteger(((iterator.next()).get("zipcode")).toString());
			obj = iterator.next();
			storeZipS = (String) (obj).get("zipcode");
			storeZip = Integer.parseInt(storeZipS);
			System.out.println("storeZipS var = " + storeZipS);
			System.out.println("zipDist contains " + storeZip + "  "
					+ zipDist.containsKey(storeZip));
			if (zipDist.containsKey(storeZip)) {
				System.out.println("distance to store = "
						+ zipDist.get(storeZip));
				distanceByStore.put(zipDist.get(storeZip), obj);
			}

			System.out.println(storeZipS);
			iterator.remove();
		}

		Map<Double, DBObject> treeMap = new TreeMap<Double, DBObject>(
				distanceByStore);

		ArrayList<DBObject> returnStores = new ArrayList<DBObject>();

		for (Map.Entry<Double, DBObject> entry : treeMap.entrySet()) {
			Double key = entry.getKey();
			System.out.println("\ndistance to store = " + key);
			DBObject value = entry.getValue();
			System.out.println(" store ID = " + value.toString());
			returnStores.add(value);
		}

		System.out.println("\nreturnStores = " + returnStores);
		
		// iterate through stores list
		// check if hashmap zipDist contains that zip as key
		// if yes add that store object and distance to ArrayList storeDist
		// order storeDist list by distance small->largest
		// copy to list containing just the stores in order
		// return list to client

		// System.out.println(zipDist);

		System.out.println("end of main");

		// double dist = ZipDistCalc.calcDistance(zip1, zip2);
		// System.out.println("distance is " + dist);
	}

	public static void storeDBcollectionZipcode() {

	}

}
