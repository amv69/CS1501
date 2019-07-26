import java.io.*;
import java.util.*;
	/**
	 * Assignment 5
	 * 
	 */

public class Assignment5{
	
	 
	 static int v;
	 static int w;
	 static int weight;
	 static Edge E;
	 static EdgeWeightedGraph adjacencyList;
	 static EdgeWeightedGraph oldNodes = new EdgeWeightedGraph(50);
	 /**
	 * Main Runs the program
	 * 
	 */
	public static void main(String args[]){
		File file = new File(args[0]);
		Scanner sc = new Scanner(System.in);
		try{
			sc = new Scanner(file);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		int numVertex = (int)sc.nextInt();
		int numEdge = (int)sc.nextInt();
		adjacencyList = new EdgeWeightedGraph(numVertex);
		while( sc.hasNextInt()){
			v = (int)sc.nextInt();
			w = (int)sc.nextInt();
			weight = (int)sc.nextInt();
			addEdge(v, w, weight);
		}
		Scanner input = new Scanner(System.in);
		while(true){
			System.out.println("Please enter an option");
			System.out.println("1: Report");
			System.out.println("2: MST");
			System.out.println("3: Shortest Path");
			System.out.println("4: Each Distinct Path");
			System.out.println("5: Node down");
			System.out.println("6: Node Up");
			System.out.println("7: Change Weight");
			System.out.println("8: Quit");
			int option = input.nextInt();
			switch(option){
				case 1:
					System.out.println(adjacencyList.toString());
					break;
				case 2:
					prims();
					break;
				case 3:
					System.out.println("Selecting a start vertex(int)");
					int i = input.nextInt();
					System.out.println("Enter vertex to path to(int)");
					int j = input.nextInt();
					dijkstra(i,j);
					break;
				case 4:
					break;
				case 5:
					System.out.println("Select a vertex to remove(int)");
					int down = input.nextInt();
					nodeDown(down);
					break;
				case 6:
					System.out.println("Select a vertex to Add(int)");
					int up = input.nextInt();
					nodeUp(up);
					break;
				case 7:
					break;
				case 8: System.exit(0);
			}


		}
	}
	/**
	 * Adds an Edge to the Adjacency List
	 * @param v The first Vertex
	 * @param w The connecting Vertex
	 * @param weight The weight of the edge
	 */
	public static void addEdge(int v, int w, int weight){
		Edge e = new Edge(v, w, weight);
		adjacencyList.addEdge(e);
	}
	/**
	 * Adds an Edge to the List of Old Nodes
	 * @param v The first Vertex
	 * @param w The connecting Vertex
	 * @param weight The weight of the edge
	 */
	public static void addOldEdge(int v, int w, int weight){
		Edge e = new Edge(v, w, weight);
		oldNodes.addEdge(e);
	}
	/**
	 * Removes a Node from the Adjacency List
	 * @param v The first Vertex to search for
	 */
	public static void nodeDown(int v){
		for(Edge e: adjacencyList.edges()){
			if( e.getV() == v  || e.getW() == v){
				//adjacencyList.remove(e);
				addOldEdge(e.getV(), e.getW(), (int)e.weight());
			}
		}
	}
	/**
	 * Adds the node back to the adjacency list if already removed
	 * @param v The first Vertex to search for
	 */
	public static void nodeUp(int v){
		for(Edge e: oldNodes.edges()){
			if(e.getV() == v || e.getW() == v){
				addEdge(e.getV(), e.getW(), (int)e.weight());
				//oldNodes.remove(e);
			}
		}
	}
	/**
	 * Runs Lazy Prim's Algorithm on the Adjacency list
	 * Displays the MST 
	 */
	public static void prims(){
		LazyPrimMSTTrace prim = new LazyPrimMSTTrace(adjacencyList);
		Iterable<Edge> primEdges = prim.edges();
		for(Edge e: primEdges){
			System.out.println(e);
		}
		System.out.println("with total weight " + prim.weight());
	}
	/**
	 * Runs Djikstra's Algorithm on the Adjacency list
	 * Displays the shortest path from vertex i to j
	 * @param i starting vertex
	 * @param j ending vertex
	 */
	public static void dijkstra(int i, int j){
		DijkstraSP sp = new DijkstraSP(adjacencyList, i);
		if(sp.hasPathTo(j)){
			Iterable<Edge> path = sp.pathTo(j);
			for(Edge e: path){
				System.out.println(e);
			}
		}
		else{
			System.out.println("Path does not exist");
		}
	}

}