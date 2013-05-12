

//import com.google.code.morphia.annotations.Entity;
//import com.google.code.morphia.annotations.Id;
//import com.google.code.morphia.annotations.Property;
//import com.google.code.morphia.annotations.Reference;
//import com.google.code.morphia.annotations.Embedded;
//import com.google.gson.annotations.Expose;
//import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

//@Entity("stores")
public class Store {

	//@Id
	//private ObjectId id;

	//@Expose
	private String name;

	//@Expose
	private String companyName;
	
	//@Expose
	private String storeID;

	//@Expose
	private String description;

	//@Expose
	private String storeFrontImageURI;

	//@Expose
	private String runningLightsMessage;

	//@Expose
	private String address1;

	//@Expose
	private String address2;

	//@Expose
	private String geoLocation;

	//@Expose
	private String zipcode;

	//@Expose
	private String permitInfo;

	//@Expose
	private String ein;

	//@Expose
	private String inventoryVolume;

	//@Expose
	private String industry;

	//@Expose
	private Integer numberOfRacks;

	//@Expose
	private Integer ranking;

	//@Expose
	private Integer currentViewingStore;

	//@Expose
	private Integer totalViewedStore;

	// for testing purpose now
//	@Expose
//	@Embedded
//	private Rack[] racks;

//	@Expose
//	@Reference
//	private List<Product> inventory;
	
	// getter/setters
	public String getName() { return name; }
	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyName() { return companyName; }
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getStoreID() { return storeID; }
	public void setStoreID(String storeID) {
		this.storeID = storeID;
	}

	public String getDescription() { return description; }
	public void setDescription(String description) {
		this.description = description;
	}

	public String getStoreFrontImageURI() { return storeFrontImageURI; }
	public void setStoreFrontImageURI(String storeFrontImageURI) {
		this.storeFrontImageURI = storeFrontImageURI;
	}

	public String getRunningLightsMessage() { return runningLightsMessage; }
	public void setRunningLightsMessage(String runningLightsMessage) {
		this.runningLightsMessage = runningLightsMessage;
	}

	public String getAddress1() { return address1; }
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() { return address2; }
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getGeoLocation() { return geoLocation; }
	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}

	public String getZipcode() { return zipcode; }
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getPermitInfo() { return permitInfo; }
	public void setPermitInfo(String permitInfo) {
		this.permitInfo = permitInfo;
	}

	public String getEin() { return ein; }
	public void setEin(String ein) {
		this.ein = ein;
	}

	public String getInventoryVolume() { return inventoryVolume; }
	public void setInventoryVolume(String inventoryVolume) {
		this.inventoryVolume = inventoryVolume;
	}

	public String getIndustry() { return industry; }
	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public Integer getNumberOfRacks() { return numberOfRacks; }
	public void setNumberOfRacks(Integer numberOfRacks) {
		this.numberOfRacks = numberOfRacks;
	}

	public Integer getRanking() { return ranking; }
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public Integer getCurrentViewingStore() { return currentViewingStore; }
	public void setCurrentViewingStore(Integer currentViewingStore) {
		this.currentViewingStore = currentViewingStore;
	}

	public Integer getTotalViewedStore() { return totalViewedStore; }
	public void setTotalViewedStore(Integer totalViewedStore) {
		this.totalViewedStore = totalViewedStore;
	}

//	public List<Product> getInventory() { return inventory; }
//	public void setInventory(List<Product> inventory) {
//		this.inventory = inventory;
//	}
//
//	public void addProduct(Product product) {
//		this.inventory.add(product);
//	}
//
//	public Rack[] getRacks() { return racks; }
//	public void setRacks(Rack[] racks) {
//		this.racks = racks;
//	}

}