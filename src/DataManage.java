import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DataManage {

	public static void makeZipCollection() {
		// parses zipcode list and loads it to the collection zipLoc
	}

	public static void testForZipsCsv() {
		File file = new File("zips.csv");
		boolean test = file.isFile();
		System.out.println("zips.csv exists = " + test);
	}

	public static void parseZipsCsv() {
		File inFile = new File("zips.csv");
		File outFile = new File("zipLocs.csv");

		if (inFile.isFile()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(inFile));
				BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));

				String line = br.readLine(); // first one gets rid of the header
				line = br.readLine();

				String zip = "";
				String lon = "";
				String lat = "";

				while (line != null) {
					// for(int i=0; i < 65; i++){
					zip = line.substring(1, 6);
					lat = line.substring(17, 26);
					lon = line.substring(30, 40);

					if (isInteger(zip)) {
						StringBuilder sb = new StringBuilder();
						sb.append(zip);
						sb.append(",");
						sb.append(lat);
						sb.append(",");
						sb.append(lon);
						sb.append("\n");
						bw.append(sb);
					}

					line = br.readLine();
				}

				br.close();
				bw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	public static void makeCAzips() {
		File inFile = new File("zipLocs.csv");
		File outFile = new File("CAzipLocs.csv");

		if (inFile.isFile()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(inFile));
				BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));

				String line = br.readLine(); // prime the reader with the first line
				
				while (line != null) {
	
					if (line.startsWith("9")) {
						bw.append(line);
						bw.append("\n");
					}

					line = br.readLine();
				}

				br.close();
				bw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static String readFileInput(File file) {
		String everything = "";
		if (file.isFile()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append("\n");
					line = br.readLine();
				}
				everything = sb.toString();
				br.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("could not find file");
		}
		return everything;
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true; // only got here if we didn't return false
	}

	public static void printSelectZipcodes() throws Exception {

		System.out.println("printing select zipcodes");

		MongoClient mongoClient = new MongoClient("localhost");

		DB db = mongoClient.getDB("ziptest");

		DBCollection coll = db.getCollection("zipcodeLocations");

		DBCursor cursor;

		// gets lat and lon for zipcode thisZip
		Integer thisZip = 90032;
		Integer zip = 0;
		Double lat = 0.0;
		Double lon = 0.0;
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

		// print out the locations within distance collection
		HashMap<Integer, Double> zipDist = new HashMap<Integer, Double>();
		cursor = coll.find();

		BasicDBObject obj;
		Double distance = 0.0;

		try {
			System.out.println("zipcodes within 5 miles");
			while (cursor.hasNext()) {
				obj = (BasicDBObject) cursor.next();
				distance = ZipDistCalc.calcDistance(lat, lon,
						obj.getDouble("latitude"), obj.getDouble("longitude"));
				if (distance <= 5) {
					// System.out.println(cursor.next());
					zipDist.put(obj.getInt("zipcode"), distance);
				}
			}
			System.out.println("number of zipcodes = " + zipDist.size());
			System.out.println(zipDist);

			zipDist = new HashMap<Integer, Double>();
			System.out.println("\nzipcodes between 5 and 10 miles");
			cursor = coll.find();
			while (cursor.hasNext()) {
				obj = (BasicDBObject) cursor.next();
				distance = ZipDistCalc.calcDistance(lat, lon,
						obj.getDouble("latitude"), obj.getDouble("longitude"));
				if (distance > 5 && distance <= 10) {
					// System.out.println(cursor.next());
					zipDist.put(obj.getInt("zipcode"), distance);
				}
			}
			System.out.println("number of zipcodes = " + zipDist.size());
			System.out.println(zipDist);

			zipDist = new HashMap<Integer, Double>();
			System.out.println("\nzipcodes between 10 and 20 miles");
			cursor = coll.find();
			while (cursor.hasNext()) {
				obj = (BasicDBObject) cursor.next();
				distance = ZipDistCalc.calcDistance(lat, lon,
						obj.getDouble("latitude"), obj.getDouble("longitude"));
				if (distance > 10 && distance <= 20) {
					// System.out.println(cursor.next());
					zipDist.put(obj.getInt("zipcode"), distance);
				}
			}
			System.out.println("number of zipcodes = " + zipDist.size());
			System.out.println(zipDist);
			
			zipDist = new HashMap<Integer, Double>();
			System.out.println("\nzipcodes between 20 and 30 miles");
			cursor = coll.find();
			while (cursor.hasNext()) {
				obj = (BasicDBObject) cursor.next();
				distance = ZipDistCalc.calcDistance(lat, lon,
						obj.getDouble("latitude"), obj.getDouble("longitude"));
				if (distance > 20 && distance <= 30) {
					// System.out.println(cursor.next());
					zipDist.put(obj.getInt("zipcode"), distance);
				}
			}
			System.out.println("number of zipcodes = " + zipDist.size());
			System.out.println(zipDist);

		} finally {
			cursor.close();
		}

	}

	public static void loadZipFileIntoDB() throws Exception {
		System.out.println("loading zipdata to DB");

		MongoClient mongoClient = new MongoClient("localhost");

		DB db = mongoClient.getDB("ziptest");

		DBCollection coll = db.getCollection("zipcodeLocations");

		Integer zip = 0;
		Double lat = 0.0;
		Double lon = 0.0;

		// load the zipcode lat-lon table into mongodb database ziptest
		File inFile = new File("zipLocs.csv");
		if (inFile.isFile()) {
			coll.drop();
			try {
				BufferedReader br = new BufferedReader(new FileReader(inFile));
				String line = br.readLine();

				while (line != null) {
					// for(int i = 0; i < 1000; i++){
					zip = Integer.valueOf(line.substring(0, 5));
					lat = Double.valueOf(line.substring(6, 15));
					lon = Double.valueOf(line.substring(16, 26).trim());

					BasicDBObject doc = new BasicDBObject("zipcode", zip)
							.append("latitude", lat).append("longitude", lon);

					coll.insert(doc);
					line = br.readLine();
				}

				br.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("could not find file");
		}

	}
	
	public static void loadZipFileIntoMassive() throws Exception {
		System.out.println("loading zipdata to DB");

		MongoClient mongoClient = new MongoClient("localhost");

		DB db = mongoClient.getDB("massive");

		DBCollection coll = db.getCollection("zipcodeLocations");

		Integer zip = 0;
		Double lat = 0.0;
		Double lon = 0.0;

		// load the zipcode lat-lon table into mongodb database ziptest
		File inFile = new File("zipLocs.csv");
		if (inFile.isFile()) {
			coll.drop();
			try {
				BufferedReader br = new BufferedReader(new FileReader(inFile));
				String line = br.readLine();

				while (line != null) {
					// for(int i = 0; i < 1000; i++){
					zip = Integer.valueOf(line.substring(0, 5));
					lat = Double.valueOf(line.substring(6, 15));
					lon = Double.valueOf(line.substring(16, 26).trim());

					BasicDBObject doc = new BasicDBObject("zipcode", zip)
							.append("latitude", lat).append("longitude", lon);

					coll.insert(doc);
					line = br.readLine();
				}

				br.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("could not find file");
		}

	}
	
	// inserts a single document
	// BasicDBObject doc = new BasicDBObject("zipcode", zip).
	// append("latitude", lat).
	// append("longitude", lon);
	//
	// coll.insert(doc);
}
