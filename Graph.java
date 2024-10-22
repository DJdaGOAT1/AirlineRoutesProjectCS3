// Devansh Joshi
// Period: 5
// Graph.java
// Purpose of this file: Provides the foundation for the AirlineRoutes lab

import java.util.*;
import java.awt.*;
import java.io.*;
public class Graph implements AirlineGraph{
	// instance variables that implement the file
	private static int[][] graph;
	public static Stack<Integer> stack;
	
	public Graph() {
		// initializing instance variables
		graph = new int[SIZE][SIZE];
		stack = new Stack<Integer>();
		
		
		// using try/catch to find the file that we need to use
		try {
			Scanner sc = new Scanner(new File("connections.dat"));
			while(sc.hasNext()) {
				Scanner line = new Scanner(sc.nextLine()).useDelimiter(",");
				graph[line.nextInt()][line.nextInt()] = line.nextInt();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	private boolean findPath(int length, Point p) {
		if(length == 1) {
			if(this.adjacent(p)) {
				// push in the start and the end nodes to the stack
				stack.push(p.y);
				stack.push(p.x);
				// return true as path is available
				return true;
			}
		} 
		else {
			// traversing through the cities to find any available paths
			for(int city = 0; city < 10; city++) {
				if(this.adjacent(new Point(p.x, city)) && this.findPath(length - 1, new Point(city, p.y))) {
					// added to stack if there is a path available
					stack.push(p.x);
					// return true as path is available
					return true;
				}
			}
		}
		// no path available 
		return false;
	}
	
	private int findAirportCode(String code) {
		// traversing through airportCode array of airport nodes
		for(int i = 0; i < SIZE; i++) {
			if(airportCode[i].equals(code)) { // whenever you find the airport node you are looking for return it
				return i;
			}
		}	
		return -1; // when you cannot find it
	}

	
	public String findRoute(int length, String start, String end) {
		String str = "";
		int cost = 0;
		
		// if there is a route from a certain point to another and if there is, calculate its cost and print out its order
		if(this.findPath(length, new Point(this.findAirportCode(start), this.findAirportCode(end)))) {
			while(stack.size() != 1) {
				int num = stack.pop(); 
				cost += graph[num][stack.peek()];
				str = str + city[num] + " -> ";
			}
			// putting this outside of while loop to keep track of  " -> " format
			cost += graph[stack.peek()][this.findAirportCode(end)];
			str = str + city[stack.pop()];
		}else { // when you have no connection
			return "There is no such connection!";
		}
		
		// returns the cost of the entire route in a string format
		return str + "  $" + cost + ".00";
	}
	
	public boolean adjacent(Point edge) {
		// checking if there are no connected, false otherwise
		return edge.x != edge.y ? graph[edge.x][edge.y] != 0 || graph[edge.y][edge.x] != 0 : false;
	}
	
	
	public String cheapestRoute(String start, String end) {
		// having an array cheapest to find cheapest route
		int[] cheapest = new int[SIZE]; 
		int[] source = new int[SIZE]; 
		boolean[] visited = new boolean[SIZE]; // keeping track of visited nodes
		String string = ""; 
		String temp = start; // using temp variable to go through the nodes
		// default set cheapest and source 
		Arrays.fill(cheapest, Integer.MAX_VALUE); 
		Arrays.fill(source, -1);
		
		
		cheapest[this.findAirportCode(temp)] = 0;  
		visited[this.findAirportCode(temp)] = true; // setting it true in visited
		source[this.findAirportCode(start)] = this.findAirportCode(start);
		
		
		// traversing through the matrix
		for(int i = 1; i < SIZE; i++) {
			for(int num = 0; num < SIZE; num++) {
				
				// logic for checking if it is cheaper than other nodes
				if(this.adjacent(new Point(this.findAirportCode(temp), num)) && cheapest[this.findAirportCode(temp)] + graph[this.findAirportCode(temp)][num] < cheapest[num] && num != this.findAirportCode(temp)) 
				{
					cheapest[num] = cheapest[this.findAirportCode(temp)] + graph[this.findAirportCode(temp)][num];
					source[num] = this.findAirportCode(temp);
				}
			}
			temp = airportCode[this.smallest(cheapest, visited)];
			visited[this.findAirportCode(temp)] = true;
		}
		
		
		
		// adding the last node to the stack
		int lastVisited = this.findAirportCode(end);
		stack.push(lastVisited);
		
		// making sure you go through all the nodes
		while(this.findAirportCode(end) != this.findAirportCode(start))
		{
			// if there is no connection
			if(source[this.findAirportCode(end)] == -1) {
				return "There is no such connection!";
			}
			
			// else you add it to the stack in which the order will be
			stack.push(source[this.findAirportCode(end)]);
			end = airportCode[source[this.findAirportCode(end)]];
		}
		
		// use a string variable to output order from stack
		while(stack.size() != 1) {
			string = string + city[stack.pop()] + " -> "; // prints out the cheapestRoute until the stack becomes empty
		}
		string = string + city[stack.pop()]; 
		return string + " $" + cheapest[lastVisited]; // printing it out in monetary value format
	}
	
	public int[] shortestPath(String source) {
		boolean[] visited = new boolean[SIZE]; // used for DFS or SP for checking unvisited nodes
		int[] prevNodes = new int[SIZE]; // stores the previous nodes which can be used to rebuild the path
		int[] dist = new int[SIZE]; // array with the shortest distances from the source node

        int index = findAirportCode(source); // the location of the source node
        Arrays.fill(dist, Integer.MAX_VALUE); // all set to max value
        Arrays.fill(visited, false); // setting a default of no nodes visited
        Arrays.fill(prevNodes, -1); // no operations were done, so all nodes got no previous nodes
        dist[index] = 0; // set source node to weight 0

        for (int i = 0; i < 10; i++) {
            int next = smallDistance(dist, visited); // checks the minimal unchecked node
            visited[next] = true; // marks that as checked

            // we check the graph's row to see if there are any connections
            for (int j = 0; j < 10; j++) {
                //  checks if it is an unvisited connected node, and has a sum that is lesser than the current minimum distance for that node
                if (!visited[j] && graph[next][j] != 0 && dist[next] != Integer.MAX_VALUE && dist[next] + graph[next][j] < dist[j]) {
                	dist[j] = dist[next] + graph[next][j]; // we update the index with the smaller sum
                	prevNodes[j] = next; // updates the previous node that got us here
                }
            }
        }
        
        return dist; // returns the distances from the source node
    }


    private int smallDistance(int[] dist, boolean[] visited) {
        int min = Integer.MAX_VALUE, minIndex = -1; // checker variables
        for (int i = 0; i < 10; i++) {
            // checks for the least unvisited node
            if (!visited[i] && dist[i] <= min) {
                min = dist[i];
                minIndex = i;
            }
        }
        return minIndex; // returns the min index
    }



	
	private int smallest(int[] arr, boolean[] visited) {
		// private helper method to find smaller node
		int smallestLengthvalue = 0;
		for(int len = 0; len < SIZE; len++) {
			if(!visited[len] && arr[len] < arr[smallestLengthvalue]) {
				// checking if it is not visited and that it is smaller
				smallestLengthvalue = len;
			}
		}
		// returns smallest
		return smallestLengthvalue;
	}
	
	public String toString() {
		// Printing it all out in a matrix method
		System.out.print("     ");
		for(int i = 0; i < Graph.airportCode.length; i++) {
			System.out.print(Graph.airportCode[i] + "  "); // printing out the "columns" of matrix
		}
		System.out.println();
		String str = "";
		for(int r = 0; r < 10; r++) {
			str = str + Graph.airportCode[r] + "   "; // which flight to connect to
			for(int c = 0; c < 10; c++) {
				if(graph[r][c] == 0) {
					str = str + "-    "; // keep dashes if there are no paths available
				}
				else {
					str = str + graph[r][c] + "  "; 
				}
			}
			str = str + "\n"; // outputting in new line
		}
		
		return str;
	}
	

}


