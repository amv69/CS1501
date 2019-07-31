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
	 static int numVertex;
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
		numVertex = (int)sc.nextInt();
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
			System.out.println("4: Each Distinct Path ( Does Not Work )");
			System.out.println("5: Node down");
			System.out.println("6: Node Up");
			System.out.println("7: Change Weight");
			System.out.println("8: Quit");
			System.out.println("----EXTRA CREDIT----");
			System.out.println("9: Add Vertex(Not Yet)");
			int option = input.nextInt();
			switch(option){
				case 1:
					connected();
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
					System.out.println("Enter Starting vertex");
					int i2 = input.nextInt();
					System.out.println("Enter Ending vertex");
					int j2 = input.nextInt();
					System.out.println("Enter maximum weight vertex");
					int x = input.nextInt();
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
					System.out.println("Select a vertex to Change weight of(int)");
					int newV = input.nextInt();
					System.out.println("Select Ending Vertex to Change weight of(int)");
					int newW = input.nextInt();
					System.out.println("Select new weight(int)");
					int newWeight = input.nextInt();
					change(newV, newW, newWeight);
					break;
				case 8: System.exit(0);
					break;
				case 9:
					//addNewVertex();
					break;
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
	} /*
	public static void addNewVertex(){
		Scanner temp = new Scanner(System.in);
		//adjacencyList.add(++numVertex);
		// EdgeWeightedGraph adjacencyListTemp = new EdgeWeightedGraph(++numVertex);
		System.out.println("How many edges are you adding?");
			int amountEdges = temp.nextInt();
			for(int i = 0; i < amountEdges; i++){
				numVertex = numVertex + 1;
				adjacencyList.add(numVertex);
				System.out.println("Enter connecting vertex");
				int connect = temp.nextInt();
				System.out.println("Enter Weight");
				int newWeight = temp.nextInt();
				addEdge(numVertex, connect, newWeight);
			}
		} */
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

	public static void connected(){
		CC con = new CC(adjacencyList);
		if(numVertex == CC.count()){
			System.out.println("Network is fully connected");
		}
		else{
			System.out.println("Graph is not fully connected");
		}

		System.out.println("The following nodes are currently down");
		for(int i = 0; i < oldNodes.adj.size(); i++){
			System.out.println(oldNodes.adj[i].getV());
		}
		System.out.println(adjacencyList.toString());
	}
	/**
	 * Removes a Node from the Adjacency List
	 * @param v The first Vertex to search for
	 */
	public static void nodeDown(int v){
		for(Edge e: adjacencyList.edges()){
			if( e.getV() == v  || e.getW() == v){
				adjacencyList.remove(e);
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
				oldNodes.remove(e);
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
			System.out.println("Shortest Path is");
			for(Edge e: path){
				System.out.println(e);
			}
		}
		else{
			System.out.println("Path does not exist");
		}
	}
	/**
	 * Change the weight of the edge from edge.weight() to newWeight
	 * @param newV starting vertex
	 * @param newW ending vertex
	 * @param newWeight The weight to change the edge to
	 */
	public static void change(int newV, int newW, int newWeight){
		for(Edge e: adjacencyList.edges()){
			if((e.getV() == newV && e.getW() == newW) || (e.getV() == newW && e.getW() == newV) ) {
				e.changeWeight(newWeight);
			}
		}
	}

}