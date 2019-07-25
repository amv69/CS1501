import java.io.*;
import java.util.*;

public class Assignment5{
	
	 
	 static int v;
	 static int w;
	 static int weight;
	 static Edge E;
	 static EdgeWeightedGraph adjacencyList;

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
					

					break;
				case 3:
					System.out.println("Selecting a start vertex(int)");
					int i = input.nextInt();
					DijkstraSP sp = new DijkstraSP(adjacencyList, i);
					System.out.println("Enter vertex to path to(int)");
					int j = input.nextInt();
					if(sp.hasPathTo(j)){
						Iterable<Edge> path = sp.pathTo(j);
						for(Edge e: path){
							System.out.println(e);
						}
					}
					else{
						System.out.print("Path does not exist");
					}
					
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					break;
				case 8: System.exit(0);
			}


		}
	}

	public static void addEdge(int v, int w, int weight){
		Edge e = new Edge(v, w, weight);
		adjacencyList.addEdge(e); //Since it is undirected
	}


}