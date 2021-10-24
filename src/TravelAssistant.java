import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

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
    
    // Adds flight between two cities
    public boolean addFlight(String startCity, String destinationCity, int flightTime,
	    int flightCost) throws IllegalArgumentException {
	return addTravelHop(startCity, destinationCity, flightTime, flightCost, "fly");
    }
    
    // Add train between two cities
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
	Map<City, Integer> weights = new HashMap<>();
	
	// Initialize the table with max value for all the visitable cities
	for (City city: cities.values()) {
	    if (city.isVisitable(isVaccinated)) {
		weights.put(city, Integer.MAX_VALUE);
	    }
	}
	
	// Table for storing previous cities
	Map<City, City> previousCities = new HashMap<>();
	
	// Table for storing mode of travel
	Map<City, String> modes = new HashMap<>();
	modes.put(fromCity, "start");
	
	// Set fromCity distance to 0
	weights.put(fromCity, 0);
	
	// Process cities in priority
	PriorityQueue<CityWeight> queue = new PriorityQueue<>();
	
	// Add the start city to the queue
	queue.add(new CityWeight(fromCity, 0));
	
	while(!queue.isEmpty()) {
	    // Remove the city with priority
	    City current = queue.remove().city;
	    
	    // Add to the city to the visited set
	    visited.add(current);
	    
	    // Loop for all the neighboring cities
	    for (var hop: adjacencyList.get(current)) {
		City neighborCity = hop.destinationCity;
		
		// If the city is already visited, then skip
		if (visited.contains(neighborCity)) continue;
		
		// If not vaccinated and the test cannot be taken at the city, then skip
		if (!neighborCity.isVisitable(isVaccinated)) continue;
		
		// Calculate new weight
		var newWeight = weights.get(current) + hop.getHopWeight(costImportance, travelTimeImportance,
			travelHopImportance, isVaccinated);
		
		// If the new weight is less, then update the weight
		if (newWeight < weights.get(neighborCity)) {
		    weights.put(neighborCity, newWeight);
		    
		    // Update the previousCities table
		    previousCities.put(neighborCity, current);
		    
		    // Update the mode of travel table
		    modes.put(neighborCity, hop.getMode());
		    
		    // Add it to the queue
		    queue.add(new CityWeight(neighborCity, newWeight));
		}
	    } 
	}
	
	// If there's no route, then return null
	if (weights.get(toCity) == Integer.MAX_VALUE) return null;
	
	return getPath(toCity, modes, previousCities);
    }
    
    // Returns the list of paths between two cities
    private List<String> getPath(City toCity, Map<City, String> modes, Map<City, City> previousCities) {
	
	// A stack to retrieve the information in the reverse order from the previousCities table
	Stack<City> stack = new Stack<>();
	
	// Push the destination city to the stack
	stack.push(toCity);
	
	// Push all the cities to by getting the previously visted cities
	City previousCity = previousCities.get(toCity);
	while(previousCity != null) {
	    stack.push(previousCity);
	    previousCity = previousCities.get(previousCity);
	}
	
	// Remove all items from the stack to get the paths in order
	List<String> path = new ArrayList<>();
	City city;
	while (!stack.isEmpty()) {
	    city = stack.pop();
	    path.add(modes.get(city) + " " + city.getCityName());
	}
	
	return path;
    }
}
