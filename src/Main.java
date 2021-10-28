
public class Main {

    public static void main(String[] args) {
	var travelAssistant = new TravelAssistant();
	travelAssistant.addCity("S", true, 1, 100);
	travelAssistant.addCity("L11", false, 1, 150);
	travelAssistant.addCity("L12", true, 1, 100);
	travelAssistant.addCity("L2", true, 1, 120);
	travelAssistant.addCity("D", true, 2, 50);
	
	travelAssistant.addFlight("S", "L11", 200, 100);
	travelAssistant.addFlight("S", "L12", 170, 90);
	travelAssistant.addFlight("L11", "L2", 180, 50);
	travelAssistant.addFlight("L12", "L2", 160, 40);
	travelAssistant.addFlight("L2", "D", 100, 40);
	
	travelAssistant.print();
	
	var list = travelAssistant.planTrip("S", "D", false, 2, 1, 0);
	
	for (var item: list) {
	    System.out.println(item);
	}
    }

}
