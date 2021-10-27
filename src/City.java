// Vertex of the graph
// Holds city related information
public class City {

    private String cityName;
    private boolean testRequired;
    private int timeToTest;
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

    public String getCityName() {
	return cityName;
    }

    public boolean isTestRequired() {
	return testRequired;
    }

    public int getTimeToTest() {
	return timeToTest;
    }
    
    // Returns total hotel cost of staying in the city
    public int getTotalHotelCosts() {
	return timeToTest > 0 ? timeToTest * nightlyHotelCosts : 0;
    }
    
    // Method to check if it is possible to visit the city
    public boolean isVisitable(boolean isVaccinated, boolean isReportNegative) {
	if (isVaccinated || isReportNegative) return true;
	
	return !testRequired;
    }
    
}
