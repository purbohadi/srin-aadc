import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class InterstellarCargo {
    
    public static int AnswerN, N, limit;
    public static WeightedGraph graph;
    public static int[][] nodes;
    
    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new FileInputStream("res/inputInterstellarCargo.txt"));
//        int T = sc.nextInt();

//        for (int tes_case = 0; tes_case < T; tes_case++) {

            AnswerN = 0;


            int x1 = sc.nextInt();
            int y1 = sc.nextInt();

            int x2 = sc.nextInt();
            int y2 = sc.nextInt();

            N = sc.nextInt();
        
            limit = 2 * N + 2;

            graph = new WeightedGraph(limit);

            nodes = new int[limit][2];

            int idx = 0;

            nodes[idx][0] = x1;
            nodes[idx++][1] = y1;
            nodes[idx][0] = x2;
            nodes[idx++][1] = y2;

            for (int i = 0; i < limit; i++) {
                for (int j = 0; j < limit; j++) {
                    graph.addEdge(i, j, Integer.MAX_VALUE);
                }
            }

            int weight = countWeight(x1, y1, x2, y2);

            graph.addEdge(0, 1, weight);
            for (int i = 0; i < N; i++) {
                x1 = sc.nextInt();
                y1 = sc.nextInt();
                x2 = sc.nextInt();
                y2 = sc.nextInt();
                nodes[idx][0] = x1;
                nodes[idx++][1] = y1;
                nodes[idx][0] = x2;
                nodes[idx++][1] = y2;
                graph.addEdge(idx - 2, idx - 1, 0);
            }

            for (int i = 0; i < limit; i++) {
                for (int j = 0; j < limit; j++) {
                    weight = countWeight(nodes[i][0], nodes[i][1], nodes[j][0], nodes[j][1]);
                    if (graph.getWeight(i, j)>0) {
                         graph.addEdge(i, j, weight);
                    }
                }
            }

            System.out.println(countMSWithDjikstra(0, 1));
//        }
	}

    public static int countMSWithDjikstra(int src, int dest) {
        int[] distance = new int[limit];
        boolean[] shortPath = new boolean[limit];

        for (int i = 0; i < limit; i++) {
            distance[i] = Integer.MAX_VALUE;
            shortPath[i] = false;
        }

        distance[src] = 0;

        for (int count = 0; count < limit - 1; count++) {
            int u = minDistance(distance, shortPath);
            if (u == dest) {
            break;
            }

            shortPath[u] = true;
            for (int v = 0; v < limit; v++) {
                if (!shortPath[v] //&& graph.getWeight(u, v) > 0
                    && distance[u] != Integer.MAX_VALUE
                    && (distance[u] + graph.getWeight(u, v)) < distance[v]) {

                    distance[v] = distance[u] + graph.getWeight(u, v);
                }
            }
        }

        return distance[dest];
    }

    public static int minDistance(int[] dist, boolean[] shortPath) {
        int min = Integer.MAX_VALUE;
        int min_index = 0;

        for (int v = 0; v < limit; v++) {
            if (!shortPath[v] && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        }
        return min_index;
    }

    public static int countWeight(int x1, int y1, int x2, int y2) {
        int a = Math.abs(x2 - x1);
        int b = Math.abs(y2 - y1);
        return a + b;
    }
        
        
}
    
class WeightedGraph {
    private int adjacencyMatrix[][];
    private int vertexCount;

    public WeightedGraph(int vCount) {
	this.vertexCount = vCount;
	this.adjacencyMatrix = new int[vertexCount][vertexCount];
    }

    public void addEdge(int i, int j, int weight) {
	if (i >= 0 && i < vertexCount && j >= 0 && j < vertexCount) {
	    adjacencyMatrix[i][j] = weight;
	    adjacencyMatrix[j][i] = weight;
	}
    }

    public void removeEdge(int i, int j) {
	if (i >= 0 && i < vertexCount && j >= 0 && j < vertexCount) {
	    // this.adjacencyMatrix[i][j] = weight;
	    // adjacencyMatrix[j][i] = false;
	}
    }

    public int getWeight(int i, int j) {
	return adjacencyMatrix[i][j];
    }

}
    