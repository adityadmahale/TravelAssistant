During the COVID times, traveling between cities/countries is not convenient. Depending on the traveler's priorities, an optimal path is required to reach the destination city. The travel assistant program allows the traveler to choose an optimal path based on their priorities of cost, travel time, and the number of hops.

# Implementation Overview:

I have built a graph for keeping track of connections between different cities. The City class acts as the vertex of the graph. The TravelHop class acts as the edge of the graph. I have used Dijkstra’s shortest path algorithm to find the shortest route between any two cities. Depending on whether an individual is vaccinated or not, the optimal path might change.

# Efficiency of the data Structures and algorithms/ Relationship to each other

A. Graph structure:
1. I have used a map to store the cities (vertices). The map has city name as the key and the city object as the value (Map<String, City>). With the map, the city object will be accessible in the constant time.
2. I have used a map to store the adjacency list. The map has the city object as the key and the list of travel hops (edges) as the value (Map<City, List<TravelHop>>). The map allows accessing the hops connected to a city in the constant time.
3. The first map allows accessing the city object using the name of the city in the constant time. In the second map, with the help of the first map the, the list of edges connected to the cities can be accessed in the constant time.
  
B. Data structures for holding information about the weights, modes and previous cities while following Dijkstra’s algorithm:
1. I have used three maps to store the information in a format similar to a table.
Map<City, Integer> for weights
Map<City, City> for previousCities
Map<City, String> for modes
2. The maps allow the information to be stored in a simple tabular format.
3. All three maps have the City class as the key. If a lookup is needed of which city is connected to the source city, it is possible to get the mode of travel from the previous city, previous city and the weight from the source city all at once in the constant time.
  
C. Keeping track of the visited cities:
I have used a set to keep track of already visited cities in the Dijkstra’s algorithm(Set<City>). The set only allows unique cities. Hence, an already visited city cannot be visited again.
D. A priority queue for breadth first traversal:
I am using a priority queue for processing cities based priority/weight. The city with the shortest weight gets visited first (Queue<CityWeight>). The priority queue removes the city with the smallest weight in order.
E. A stack for tracing the shortest path
I have used a stack to trace back the path from the destination city to the source city using the tables that I mentioned in the section B. The stack then presents the path in the correct order.
F. Planning trip:
1. The planning trip method does the calculation of finding the shortest distances between the start city and all other cities.
2. I have used the three maps to keep track of the weights to travel from source city, the mode of travel and the previously visited city.
3. A priority queue is used for doing the breadth first traversal.
4. The algorithm starts by adding the source city to the queue.
5. The city gets removed and all the adjacent city weight gets calculated.
6. There’s a check if see if it is possible to visit the city based on the conditions mentioned in the section E.
7. If it is not possible to visit the city, then that city gets skipped.
8. If it is possible to visit a city, then it checks whether a test is needed in the current city.
9. If the test is needed, then it adds the hotel cost to the weight of traveling between two cities.
10. The formula for calculating the weight is as follows:
weight = cost * costImportance + travelTime * timeImportance + hop * hopImportance
If the calculated weight is less than the current weight to reach from the source city, then the three tables get updated with the latest values.
If there’s no path, then the method returns null. Otherwise, it returns the list of paths.
11. The getPath method uses a stack to trace back the path to the source city.
12. The stack gets popped to find the correct path to the destination city.
  
# Key algorithms and design elements:

A. Adding city:
The addCity method allows adding a city to the graph. The method adds the city to the map as well as the adjacency list by initializing an empty list of travel hops. The method returns false if a city already exists in the system.
B. Adding flight/train:
I have used a new method called addTravelHop to add both flights and trains. The method checks if the flight/train already exists. If it doesn’t exist, then it adds it to the adjacency list.
C. Method to check if the travel hop is already present:
When a hop (train/flight) already exists between two cities, the system should not allow adding that hop. I have created a method called isTravelHopPresent to check if the hop is already present by iterating over the source city adjacency list.
D. Printing graph:
I have created a print method to show the connections between different cities. The output appears as follows.
E. Method to check if it is possible to visit the city:
The isVisitable method checks if it is possible to visit the city based on whether the traveler is vaccinated, negative report available, the test required criteria in the next city and the testing availability in the current city.
F. Method to check if the test is needed in the current city
The isTestNeeded method checks if a test is needed in the current city based on the status of the traveler (Vaccinated/Negative report) and the testing requirements (next city)/testing availability (current city).
G. Getting path:
The getPath method is used to get the shortest path between the start city and the destination city.
