import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;



public class DataManage {

	public static void makeZipCollection(){
		//parses zipcode list and loads it to the collection zipLoc	
	}
	
	public static void testForZipsCsv(){
		File file = new File("zips.csv");
		boolean test = file.isFile();
		System.out.println("zips.csv exists = " + test);
	}
	
	public static void parseZipsCsv(){
		File inFile = new File("zips.csv");
		File outFile = new File("zipLocs.csv");
		
		if(inFile.isFile()){
			try {
				BufferedReader br = new BufferedReader(new FileReader(inFile));
				BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
				
				
				String line = br.readLine(); //first one gets rid of the header
				line = br.readLine();
	
				String zip = "";
				String lon = "";
				String lat = "";
				
				while (line != null) {	
				//for(int i=0; i < 65; i++){	
					zip = line.substring(1, 6);
					lat = line.substring(17, 26);
					lon = line.substring(30, 40);
					
					if(isInteger(zip)){
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
	
	public static String readFileInput(File file){
		String everything = "";		
		if(file.isFile()){
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
		}
		else {
			System.out.println("could not find file");
		}
		return everything;
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }	    
	    return true; // only got here if we didn't return false
	}
}
