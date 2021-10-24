
public class CityWeight implements Comparable<CityWeight> {
	City city;
	int distance;
	
	public CityWeight(City city, int distance) {
	    this.city = city;
	    this.distance = distance;
	}

	@Override
	public int compareTo(CityWeight o) {
	    return Integer.compare(distance, o.distance);
	}
}
