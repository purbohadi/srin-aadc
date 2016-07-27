import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class FindingACircle {

    static int N, M;
    static DirectionalGraph graph;
    
    public static void main(String[] args) throws FileNotFoundException {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution.
        */
        Scanner sc = new Scanner(new FileInputStream("res/inputFindingACircle.txt"));
        N = sc.nextInt();
        M = sc.nextInt();
        graph = new DirectionalGraph(N);
        
        for (int i = 0; i < M; i++) {
           int u = sc.nextInt()-1;
           int v = sc.nextInt()-1;
           graph.addEdge(u, v);
        }
        
//        while (sc.hasNextLine()) {
//           int u = sc.nextInt()-1;
//           int v = sc.nextInt()-1;
//           graph.addEdge(u, v);
//        }

        if (isCyclic()) {
    		System.out.println("Yes");
	    }else{
    		System.out.println("No");
	    }
    }
    
    private static boolean isCyclic(){       
        for (int i = 0; i < N; i++) {
            boolean[] visited = new boolean[N];
            if (isCyclicUtil(i, visited)) 
                return true;
        }
        return false;
    }
    
    private static boolean isCyclicUtil(int v, boolean[] visited){
        if (!visited[v]) {
            visited[v]=true;
            for (int i = 0; i < N; ++i) {
                if (graph.isEdge(v, i)){
                    if (!visited[i]&&isCyclicUtil(i, visited)){ 
                        return true;
                    }else if(visited[i])
                        return true;
                }
            }
        }
        visited[v]=false;
        return false;
    }
    
}

class DirectionalGraph {
    private boolean adjacencyMatrix[][];
    private int vertexCount;

    public DirectionalGraph(int vCount) {
        this.vertexCount = vCount;
        this.adjacencyMatrix = new boolean[vertexCount][vertexCount];  
    }

    public void addEdge(int i, int j) {
        if (i >= 0 && i < vertexCount && j >= 0 && j < vertexCount) {
            this.adjacencyMatrix[i][j] = true;
        }
    }

    public boolean isEdge(int i, int j) {
        if (i >= 0 && i < vertexCount && j >= 0 && j < vertexCount)
            return adjacencyMatrix[i][j];
        else
            return false;
    }

}
