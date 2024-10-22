import java.util.Arrays;
import java.io.IOException;

public class AirlineRoutes
{

	public static void main(String args[]) throws IOException{
    	Graph map = new Graph();
    	System.out.println(map);

    	System.out.println("FindRoute Tests:");
    	System.out.println("\tSFO to JFK, 1 hop");
    	String route = map.findRoute(1, "SFO", "JFK");
		System.out.println("\t"+route);

    	System.out.println("\n\tPHX to MIA, 2 hops");
    	route = map.findRoute(2, "PHX", "MIA");
		System.out.println("\t"+route);

    	System.out.println("\n\tSFO to JFK, 4 hops");
    	route = map.findRoute(4, "SFO", "JFK");
		System.out.println("\t"+route);

    	System.out.println("\n\tHNL to BOS, 5 hops");
    	route = map.findRoute(5, "HNL", "BOS");
		System.out.println("\t"+route);


		System.out.println("CheapestRoute Tests:");

		System.out.println("\tCheapest Route From SFO to JFK");
    	route = map.cheapestRoute("SFO", "JFK");
		System.out.println("\t"+route);

		System.out.println("\n\tCheapest Route From PHX to MIA");
    	route = map.cheapestRoute("PHX", "MIA");
		System.out.println("\t"+route);

		System.out.println("\n\tCheapest Route From HNL to BOS");
    	route = map.cheapestRoute("HNL", "BOS");
		System.out.println("\t"+route);

		//Optional shortestPath Tests

			//Make sure you have the shortestPathCheck.class file in your project folder
	/*
	 	shortestPathCheck checker = new shortestPathCheck();
		int[] yourCheapestCostList;
		int[] actualCheapestCostList;
		int successCount = 0;

		for(int v = 0; v <map.airportCode.length; v++)
		{
			System.out.print("\nShortest Path From "+map.airportCode[v]+": ");

			yourCheapestCostList = map.shortestPath(map.airportCode[v]);
			actualCheapestCostList = checker.dijkstra(map.airportCode[v]);
			if(Arrays.equals(yourCheapestCostList, actualCheapestCostList))
			{
				successCount++;
				System.out.println("PASSED");
			}
			else
			{
				System.out.println("FAILED");
				System.out.println("\tYour Smallest Costs: "+Arrays.toString(yourCheapestCostList));
				System.out.println("\tActual Smallest Costs: "+Arrays.toString(actualCheapestCostList));
			}

		}
	*/

    }
}