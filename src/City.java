// Vertex of the graph
// Holds city related information
public class City {
    
    // City name
    private String cityName;
    // Stores if the test is required in this city
    private boolean testRequired;
    // Stores the time to test in days
    private int timeToTest;
    // Stores the nightly hotel cost in this city
    private int nightlyHotelCosts;
    
    public City(String cityName, boolean testRequired, int timeToTest, int nightlyHotelCosts) {
	this.cityName = cityName;
	this.testRequired = testRequired;
	this.timeToTest = timeToTest;
	this.nightlyHotelCosts = nightlyHotelCosts;
    }
    
    @Override
    public String toString() {
	return cityName;
    }
    
    // Returns the name of the city
    public String getCityName() {
	return cityName;
    }
    
    // Returns if the test is required in the city
    public boolean isTestRequired() {
	return testRequired;
    }
    
    // Returns time to test
    public int getTimeToTest() {
	return timeToTest;
    }
    
    // Returns total hotel cost of staying in the city
    public int getTotalHotelCosts() {
	return timeToTest * nightlyHotelCosts;
    }
    
    // Checks if the test is possible in this city
    public boolean isTestPossible() {
	return timeToTest >= 0;
    }
    
}
