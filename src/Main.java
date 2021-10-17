
public class Main {

    public static void main(String[] args) {
	var travelAssistant = new TravelAssistant();
	travelAssistant.addCity("Halifax", false, 2, 5);
	travelAssistant.addCity("Toronto", false, 2, 5);
	travelAssistant.addCity("Montreal", false, 2, 5);
	
	travelAssistant.addFlight("Halifax", "Toronto", 300, 200);
	travelAssistant.addFlight("Montreal", "Halifax", 200, 200);
	travelAssistant.addTrain("Montreal", "Halifax", 200, 200);
	travelAssistant.print();
    }

}
