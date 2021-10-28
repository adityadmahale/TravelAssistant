// Edge of the graph
// Holds travel related information
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
    
    // Calculate weight based on the formula: 
    // totalCost * costImportance + travelTime * travelTimeImportance + hop * travelHopImportance
    public int getHopWeight(int costImportance, int travelTimeImportance, 
	    int travelHopImportance) {
	return cost * costImportance + duration * travelTimeImportance + travelHopImportance;
    }
}
