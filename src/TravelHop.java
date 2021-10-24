
public class TravelHop {

    private City startCity;
    private City destinationCity;
    private String mode;
    private int cost;
    private int duration;
    
    public TravelHop(City startCity, City destinationCity, String mode, int cost,
	    int duration) {
	this.startCity = startCity;
	this.destinationCity = destinationCity;
	this.mode = mode;
	this.cost = cost;
	this.duration = duration;
    }
    
    @Override
    public String toString() {
	return destinationCity + "(" + mode + ")";
    }

    public City getDestinationCity() {
        return destinationCity;
    }

    public String getMode() {
        return mode;
    }
    
    // Get total cost of traveling between two cities
    private int getTotalCost(boolean isVaccinated) {
	return !isVaccinated ? destinationCity.getTotalHotelCosts() + cost: cost;
    }
    
    // Calculate weight based on the formula: 
    // totalCost * costImportance + travelTime * travelTimeImportance + hop * travelHopImportance
    public int getHopWeight(int costImportance, int travelTimeImportance, 
	    int travelHopImportance, boolean isVaccinated) {
	return getTotalCost(isVaccinated) * costImportance + duration * travelTimeImportance +
		travelHopImportance;
    }
}
