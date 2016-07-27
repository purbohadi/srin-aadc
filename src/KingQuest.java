

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class KingQuest {

	private static int Answer, N;
	
	private static final int PAY = 0;
	private static final int HIRE = 1;
	private static final int BATTLE = 2;
	private static int[] orc, gold, actionArr;

	public static void main(String[] args) throws FileNotFoundException {

		Scanner sc = new Scanner(new FileInputStream("res/inputKingQuest.txt"));

		N = sc.nextInt();

		orc = new int[N];
		gold = new int[N];
		actionArr = new int[N];
		Answer = 0;
		
		for (int i = 0; i < N; i++) {
			orc[i]=0;
			gold[i]=0;
		}

		for (int i = 0; i < N; i++) {
			orc[i] = sc.nextInt();
			gold[i] = sc.nextInt();
			Answer += gold[i] * 2;
			sc.nextLine();
		}
		
		int[] curOrc = new int[3];
		
		for (int i = 0; i < 3; i++) {
			curOrc[i]=0;
		}
		
		findMinimumGold(0, PAY, new Orc(0,0,0),  0);
		System.out.print(Answer);
	}
	
	
	private static void printStep(int level){
		for (int i = 0; i < level; i++) {
			printAction(actionArr[i]);
		}
	}
	
	private static void printAction(int action){
		if (action==PAY) {
			System.out.print("Pass ");
		}else if(action==HIRE){
			System.out.print("Hire ");
		}else{
			System.out.print("Battle ");
		}
	}

	private static void findMinimumGold(int enc, int action, Orc orcs, int currGold) {
		
		if(currGold>Answer)
			return;
		
		if(enc == N){
			if(Answer>currGold)
				Answer=currGold;
//			printStep(enc);
			return;
		}
			
		actionArr[enc]=action;
		
		findMinimumGold(enc+1, PAY, orcs, currGold+gold[enc]);
		
		findMinimumGold(enc+1, HIRE, new Orc(orcs.currOrc1+orc[enc],0,0), currGold+(2*gold[enc]));
		
		int myArmy = orcs.currOrc1+orcs.currOrc2+orcs.currOrc3;
		
		if (myArmy>=orc[enc]) {
			
			int enemyArmy = orc[enc];
			enemyArmy=enemyArmy-orcs.currOrc3;
			
			if (enemyArmy<0) {
				enemyArmy=0;
			}
			
			int tempArmy3 = 0;
			if (enemyArmy>0) {
				if (enemyArmy>orcs.currOrc2) {
					enemyArmy-=orcs.currOrc2;
				} else {
					tempArmy3=orcs.currOrc2-enemyArmy;
					enemyArmy=0;
				}
			}else
				tempArmy3=orcs.currOrc2;
			
			int tempArmy2=0;
			if (enemyArmy>0) {
				tempArmy2=orcs.currOrc1-enemyArmy;
				enemyArmy=0;
			}else{
				tempArmy2=orcs.currOrc1;
			}
			
			findMinimumGold(enc+1, BATTLE, new Orc(0,tempArmy2,tempArmy3), currGold);
		}

	}

}

class Orc{
	int currOrc1;
	int currOrc2;
	int currOrc3;
	
	public Orc(int orc1, int orc2, int orc3) {
		this.currOrc1=orc1;
		this.currOrc2=orc2;
		this.currOrc3=orc3;
	}
	
	public void setValue(int orc1, int orc2, int orc3){
		this.currOrc1=orc1;
		this.currOrc2=orc2;
		this.currOrc3=orc3;
	}
	
}


