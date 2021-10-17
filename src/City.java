
public class City {

    private String cityName;
    private boolean testRequired;
    private int timeToTest;
    private float nightlyHotelCosts;
    
    public City(String cityName, boolean testRequired, int timeToTest, float nightlyHotelCosts) {
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

    public float getNightlyHotelCosts() {
	return nightlyHotelCosts;
    }
    
    
}
