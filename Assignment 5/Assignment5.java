import java.io.*;
import java.util.*;

public class Assignment5{
	
	 
	 int v;
	 int w;
	 int weight;
	 Edge E;
	 LinkedList<Integer> adjacencyList[];

	public static void main(String args[]){
		File file = new File(args[0]);
		Scanner sc = new Scanner(file);
		int numVertex = sc.nextLine();
		int numEdge = sc.nextLine();
		adjacencyList[] = new LinkedList[numVertex];
		for(int i = 0; i < numVertex; i++){
			adjacencyList[i] = new LinkedList<>();
		}
		while( sc.hasNextLine() ){
			v = sc.nextInt();
			w = sc.nextInt();
			weight = sc.nextInt();
			addEdge(v, w, weight);
		}
	}

	public void addEdge(int v, int w, int weight){
		Edge e = new Edge(v, w, weight);
		adjacencyList[v].addFirst(e);
		adjacencyList[w].addFirst(e); //Since it is undirected
	}
}