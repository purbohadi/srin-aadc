import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class S6TradeInHero {

	private static int maxN=105;
	private static int maxType=5+3;
	
	private static int[][] mainMap;
	private static boolean[][] visitedMap;
	private static boolean[][] visitedZero;
	
	private static int[] samsungCount = new int[maxType];
	
	private static int N, TC;
	
	public static void main(String[] args) throws FileNotFoundException{
		
		System.setIn(new FileInputStream("res/inputHero.txt"));
		
		Scanner sc = new Scanner(System.in);
		
		TC = sc.nextInt();
		
		for (int test = 0; test < TC; test++) {
			
			
			N=sc.nextInt();
			
			mainMap = new int[N+1][N+1];
			visitedMap = new boolean[N+1][N+1];
			visitedZero = new boolean[N+1][N+1];
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					mainMap[i][j]=sc.nextInt();
				}
			}
			
			
			//core logic
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					
					if((mainMap[i][j]==0)&&(!visitedMap[i][j])){
						
						
						clearSamsungCount();
						
						clearVisitedMap();
						
						traceZero(i, j);
						
						fillZero(i, j, maxSamsungCount());
						
						
						
					}
				}
			}
		}
		
		
		
//		printMap(N);
		
		paintFinalMap();
		clearSamsungCount();
		clearVisitedMap();
		
		int boothCount = 0;
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				
				if (!visitedMap[i][j] && mainMap[i][j]>0) {
					
					traceBooth(i, j, mainMap[i][j]);
					
					boothCount++;
					
				}
				
			}
		}
		
		
		System.out.println(boothCount);
		
	}
	
	//print Map
	public static void printMap(int n){
		System.out.println("Main Map with size");
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(mainMap[i][j]>=0)
					System.out.print(mainMap[i][j]+" ");
				else
					System.out.print(mainMap[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	
	public static void clearMap(){
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				mainMap[i][j]=0;
			}
		}
	}
	
	public static void clearVisitedMap(){
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				visitedMap[i][j]=false;
				visitedZero[i][j]=false;
			}
		}
	}
	
	public static void clearSamsungCount(){
		for (int i = 0; i < maxType; i++) {
			samsungCount[i]=0;
		}
	}
	
	
	public static int maxSamsungCount(){
		
		boolean locMaxFound=false;
		int locMaxCount=0;
		int locMaxType=0;
		
		
		for (int i = 0; i < maxType; i++) {
			if(samsungCount[i]>locMaxCount){
				locMaxFound=true;
				locMaxCount=samsungCount[i];
			}
		}
		
		
		if(locMaxFound){
			for (int i = 0; i < maxType; i++) {
				if(samsungCount[i]==locMaxCount) locMaxType=i;
			}
			
			return locMaxType*-1;
		}else
			return 0;
		
	}
	
	
	
	public static void traceZero(int locI, int locJ){
		
		if(locI<0 || locJ>=N || locJ<0 || locJ>=N) return;
		
		if(visitedMap[locI][locJ]) return;
		
		visitedMap[locI][locJ]=true;
		
		
		if(mainMap[locI+1][locJ]>0 && !visitedMap[locI+1][locJ]) traceNumber(locI+1, locJ, mainMap[locI+1][locJ]);
		if(mainMap[locI][locJ+1]>0 && !visitedMap[locI][locJ+1]) traceNumber(locI, locJ+1, mainMap[locI][locJ+1]);
		if(mainMap[locI-1][locJ]>0 && !visitedMap[locI-1][locJ]) traceNumber(locI-1, locJ, mainMap[locI-1][locJ]);
		if(mainMap[locI][locJ-1]>0 && !visitedMap[locI][locJ-1]) traceNumber(locI, locJ-1, mainMap[locI][locJ-1]);
		
		
		if(mainMap[locI+1][locJ]==0 && !visitedMap[locI+1][locJ]) traceZero(locI+1, locJ);
		if(mainMap[locI][locJ+1]==0 && !visitedMap[locI][locJ+1]) traceZero(locI, locJ+1);
		if(mainMap[locI-1][locJ]==0 && !visitedMap[locI-1][locJ]) traceZero(locI-1, locJ);
		if(mainMap[locI][locJ-1]==0 && !visitedMap[locI][locJ-1]) traceZero(locI, locJ-1);
		
		
	}
	
	
	// count zero number in one cluster
	static void traceNumber(int locI, int locJ, int value){
		
		if(locI<0 ||locI>=N || locJ<0 || locJ>=N) return;
		
		if(visitedMap[locI][locJ]) return;
		
		if(mainMap[locI][locJ]!=value) return;
		
		
		visitedMap[locI][locJ]=true;
		samsungCount[value]++;
		
		traceNumber(locI+1, locJ, value);
		traceNumber(locI, locJ+1, value);
		traceNumber(locI-1, locJ, value);
		traceNumber(locI, locJ-1, value);
		
	}
	
	//recursive call to paint all zero
	static void fillZero(int locI, int locJ, int maxType){
		if(locI<0 || locI>= N || locJ <0 || locJ>=N) return;
		
		if(visitedZero[locI][locJ]) return;
		
		mainMap[locI][locJ]= maxType;
		visitedZero[locI][locJ]=true;
		
		
		if(mainMap[locI+1][locJ]==0) fillZero(locI+1, locJ, maxType);
		if(mainMap[locI][locJ+1]==0) fillZero(locI, locJ+1, maxType);
		if(mainMap[locI-1][locJ]==0) fillZero(locI-1, locJ, maxType);
		if(mainMap[locI][locJ-1]==0) fillZero(locI, locJ-1, maxType);
		
	}
	
	// paint zero to maxType
	static void paintFinalMap(){
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (mainMap[i][j]<0) {
					mainMap[i][j] *=-1;
				}
			}
		}
	}
	
	// recursive to grouping
	static void traceBooth(int locI, int locJ, int value){
		
		if(locI<0 || locI>=N || locJ<0 || locJ>=N) return;
		
		
		if(visitedMap[locI][locJ]) return;
		
		if(mainMap[locI][locJ]!=value) return;
		
		
		visitedMap[locI][locJ]=true;
		
		
		
		traceNumber(locI+1, locJ, value);
		traceNumber(locI, locJ+1, value);
		traceNumber(locI-1, locJ, value);
		traceNumber(locI, locJ-1, value);
	}
	
	
	
}
