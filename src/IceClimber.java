import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IceClimber {
	
	static int[][] map;
	static boolean[][] visitedMap;
	static int M, N, startX, startY, finishX, finishY;
	static boolean minLevel;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner sc = new Scanner(new FileInputStream("res/inputIceClimber.txt"));
		
		int T = sc.nextInt();
		
		for (int test_case = 0; test_case < T; test_case++) {
			N=sc.nextInt();
			M=sc.nextInt();
			
			map = new int[M][N];
			visitedMap = new boolean[M][N];
			
			for (int i = 0; i < M; i++) {
				for (int j = 0; j < N; j++) {
					map[i][j]=sc.nextInt();
					if (map[i][j]==2) {
						startX=i;
						startY=j;
					}
					
					if (map[i][j]==3) {
						finishX=i;
						finishY=j;
					}
					
				}
			}
			
			for (int i = 0; i < M; i++) {
				clearVisitedMap();
				findPath(startX, startY, i);
				if (minLevel) {
					System.out.println(i);
					break;
				}
			}
			
		}
	}
	
	
	private static void clearVisitedMap(){
		visitedMap = new boolean[M][N];
	}
	
	private static boolean isValidPos(int x, int y){
		if((x>=0&&y>=0)&&(x<M&&y<N))
			return true;
		else
			return false;
	}
	
	private static void findPath(int posX, int posY, int maxLevel){
		if(!isValidPos(posX, posY))
			return;
		
		if(map[posX][posY]<1||map[posX][posY]>3)
			return;
		
		if(posX==finishX && posY==finishY){
			minLevel=true;
			return;
		}
		
		if (!minLevel && !visitedMap[posX][posY]) {
			visitedMap[posX][posY]=true;
			
			for (int currLevel = 0; currLevel <= maxLevel; currLevel++)
				findPath(posX+currLevel, posY, maxLevel);
			
			for (int currentLevel = 0; currentLevel <= maxLevel; currentLevel++)
				findPath(posX-currentLevel, posY, maxLevel);
			
			findPath(posX, posY+1, maxLevel);
			findPath(posX, posY-1, maxLevel);
			
			visitedMap[posX][posY]=false;
			
		}
	}
	
	

}
