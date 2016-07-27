

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.Scanner;

public class TownHall {

	private static int[][] goldMines, map, visitedMap;
	private static int[][][] distMap;
	private static int Answer, N, G;

	public static void main(String[] args) throws FileNotFoundException {

		Scanner sc = new Scanner(new FileInputStream("res/inputTownHall.txt"));

		N = sc.nextInt();
		G = sc.nextInt();

		Answer = Integer.MAX_VALUE;

		goldMines = new int[G][2];

		for (int i = 0; i < G; i++) {
			for (int j = 0; j < 2; j++) {
				goldMines[i][j] = sc.nextInt() - 1;
			}
		}

		map = new int[N][N];
		visitedMap = new int[N][N];
		distMap = new int[N][N][G];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] = sc.nextInt();
				visitedMap[i][j]=0;
			}
		}

		for (int i = 0; i < G; i++) {
			map[goldMines[i][0]][goldMines[i][1]] = -1;
			for (int j = 0; j < N; j++) {
				for (int k = 0; k < N; k++) {
					distMap[j][k][i] = -1;
				}
			}

		}

		for (int i = 0; i < G; i++) {

			for (int k = 0; k < N; k++) {
				for (int l = 0; l < N; l++) {
					visitedMap[k][l]=0;
				}
			}
			
			findPathBFS(goldMines[i][0], goldMines[i][1], i);
			printDistMap(i);
			
//			findPath(goldMines[i][0], goldMines[i][1], 0, i);
//			printDistMap(i);

		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] != -1 && map[i][j] != 0) {
					int max = compareMax(i, j);
					if (Answer > max)
						Answer = max;
				}
			}
		}

		System.out.println();
		System.out.println(Answer);

		// printMap();
	}

	private static int compareMax(int i, int j) {
		int max = 0;
		for (int k = 0; k < G; k++) {

			if (distMap[i][j][k] > max)
				max = distMap[i][j][k];
		}
		return max;
	}

	private static void printDistMap(int idx) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(distMap[i][j][idx] + " ");
			}
			System.out.println();
		}

		System.out.println();
	}

	private static void printMap() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}

	private static void findPath(int i, int j, int dist, int mineIdx) {

		if (i < 0 || i >= N || j < 0 || j >= N)
			return;

		if (map[i][j] == 0)
			return;

		if ((distMap[i][j][mineIdx] == -1) || distMap[i][j][mineIdx] > dist) {
			distMap[i][j][mineIdx] = dist;

			findPath(i, j - 1, dist + 1, mineIdx);
			findPath(i, j + 1, dist + 1, mineIdx);
			findPath(i - 1, j, dist + 1, mineIdx);
			findPath(i + 1, j, dist + 1, mineIdx);

		}

	}
	
	private static void findPathBFS(int i, int j, int mineIdx){

		int dist=0;
		visitedMap[i][j]=1;
		
//		Queue queueX = new LinkedList<Integer>();
//		Queue queueY = new LinkedList<Integer>();
//		queueX.add(j);
//		queueY.add(i);
		
		MyQueue queueX = new MyQueue();
		MyQueue queueY = new MyQueue();
		queueX.add(j);
		queueY.add(i);
		
		int x=0;
		int y=0;
		
		int[] dx={1,0,-1,0};
		int[] dy={0,1,0,-1};
		
		distMap[i][j][mineIdx]=0;
		
		while (!queueX.isEmpty()) {
			
//			y=(Integer) queueY.poll();
//			x=(Integer) queueX.poll();
			
			y= queueY.poll();
			x= queueX.poll();
			
			for (int k = 0; k < 4; k++) {
				i=y+dy[k];
				j=x+dx[k];
				
				dist=distMap[y][x][mineIdx];
				
				if (i < 0 || i >= N || j < 0 || j >= N)
					continue;
				
				if(visitedMap[i][j]==0 && map[i][j]==1){
					visitedMap[i][j]=1;
					queueX.add(j);
					queueY.add(i);
					dist++;
					distMap[i][j][mineIdx]=dist;
				}
			}
		}
		
//		printDistMap(mineIdx);
	}

}


class MyQueue{
	
	MyLinkedList ll =  new MyLinkedList();
	int itemCount = 0;
	
	
	public void add(int item){
		ll.insertLast(item);
		itemCount++;
	}
	
	public Node remove(){
		itemCount--;
		return ll.deleteFirst();
	}
	
	public int poll(){//remove and dequeue
		
		Node node = remove();
		
		return node.data;
	}
	
	public boolean isEmpty(){
		if(itemCount>0)
			return false;
		else
			return true;
	}
	
}


class Node{
	
	public int data;
	public Node next;
	
	public Node(int data) {
		// TODO Auto-generated constructor stub
		this.data = data;
	}
	
}


class MyLinkedList{
	
	private Node head;
	
	public MyLinkedList() {
		// TODO Auto-generated constructor stub
		head=null;
	}
	
	public void insertLast(int data){
		Node newNode = new Node(data);
		
		if (head==null) {
			head=newNode;
			return;
		}
		
		Node tempNode = head;
		while (tempNode.next!=null) {
			tempNode=tempNode.next;
		}
		
		tempNode.next=newNode;
	}
	
	public Node deleteFirst(){
		if(head==null)
			System.out.println("is Empty");
		
		Node tempNode = head;
		
		head = head.next;
		return tempNode;
	}
	
}



