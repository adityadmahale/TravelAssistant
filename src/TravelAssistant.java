import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TravelAssistant {
    
    // Stores the city name and its corresponding City object.
    Map<String, City> cities = new HashMap<>();
    // Adjacency list
    Map<City, List<TravelHop>> adjacencyList = new HashMap<>();
    
    public boolean addCity( String cityName, boolean testRequired, int timeToTest,
	    int nightlyHotelCost) throws IllegalArgumentException {
	// If the city is already known by the TravelAssistant, then return false
	if (cities.containsKey(cityName)) return false;
	
	// Throw an exception when then the hotel cost is invalid
	if (nightlyHotelCost < 0) throw new IllegalArgumentException();
	
	// At this point, the passed arguments are correct.
	// Create a new City object and add it to the cities Map.
	var city = new City(cityName, testRequired, timeToTest, nightlyHotelCost);
	cities.put(cityName, city);
	
	// Add the city to the adjacency list.
	adjacencyList.put(city, new ArrayList<>());
	return true;
    }
    
    public boolean addFlight(String startCity, String destinationCity, int flightTime,
	    int flightCost) throws IllegalArgumentException {
	return addTravelHop(startCity, destinationCity, flightTime, flightCost, "flight");
    }
    
    public boolean addTrain(String startCity, String destinationCity, int trainTime, int trainCost)
	    throws IllegalArgumentException {
	return addTravelHop(startCity, destinationCity, trainTime, trainCost, "train");
    }
    
    private void validateCities(City fromCity, City toCity) throws IllegalArgumentException {
	// Throw an exception if:
	// The start and destination are the same city.
	// The start and destination cities are not present
	if (fromCity == null || toCity == null || fromCity == toCity) {
	    throw new IllegalArgumentException();
	}
    }
    
    private boolean addTravelHop(String startCity, String destinationCity, int duration,
	    int cost, String mode) throws IllegalArgumentException {
	// Check if the cost and duration are negative values
	if (duration < 0 || cost < 0) throw new IllegalArgumentException();
	
	// Get the start city and destination city objects
	var fromCity = cities.get(startCity);
	var toCity = cities.get(destinationCity);
	validateCities(fromCity, toCity);
	
	// Check if the travel mode is already present
	if (isTravelHopPresent(fromCity, toCity, mode)) return false;
	
	// At this point, the inputs are correct. So, a new edge can be added between the two cities.
	adjacencyList.get(fromCity).add(new TravelHop(fromCity, toCity, mode, cost, duration));
		
	return true;
    }
    
    private boolean isTravelHopPresent(City fromCity, City toCity, String mode) 
	    throws IllegalArgumentException {		
	// Handle the case when the edge is already present.
	String destinationCity;
	String modeOfTravel;
	for (var travelOption: adjacencyList.get(fromCity)) {
	    destinationCity = travelOption.getDestinationCity().getCityName();
	    modeOfTravel = travelOption.getMode();
	    if (destinationCity.equals(toCity.getCityName()) && modeOfTravel.equals(mode)) return true;
	}
	
	return false;
    }
    
    public void print() {
	for (City startCity: adjacencyList.keySet()) {
	    var destinations =  adjacencyList.get(startCity);
	    if (!destinations.isEmpty()) System.out.println(startCity + " -> " + destinations);
	}
    }
    
    
    public List<String> planTrip ( String startCity, String destinationCity, boolean isVaccinated, int
	    costImportance, int travelTimeImportance, int travelHopImportance ) throws
	    IllegalArgumentException {
	
	// Check if the importance parameters are non-negative integers
	if (costImportance < 0 || travelTimeImportance < 0 || travelHopImportance < 0)
	    throw new IllegalArgumentException();
	
	// Get the start city and destination city objects
	var fromCity = cities.get(startCity);
	var toCity = cities.get(destinationCity);
	validateCities(fromCity, toCity);
	
	// When an unvaccinated individual plans to travel to a destination city
	// where the testing is required, but the city does not have the covid test centre
	if (!isVaccinated && toCity.isTestRequired() && toCity.getTimeToTest() < 0) return null;
	
	// Set for storing visited cities
	Set<City> visited = new HashSet<>();
	
	// Stores the distance table from the "fromCity" node
	Map<City, Integer> distances = new HashMap<>();
	
	// Initialize the table with max value for all the nodes
	for (City city: cities.values()) {
	    distances.put(city, Integer.MAX_VALUE);
	}
	
	// Set fromCity distance to 0
	distances.put(fromCity, 0);
	
	return null;
    }
    
    private int getHopWeight(int costImportance, int travelTimeImportance, int travelHopImportance,
	    TravelHop hop, boolean isVaccinated) {
	return hop.getTotalCost(isVaccinated) * costImportance + hop.getDuration() * travelTimeImportance +
		travelHopImportance;
    }

}
