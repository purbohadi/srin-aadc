

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RaidenArcade {

	private static int[][] grid, bombGrid;
	private static int Answer, N, M, recGold;
	private static boolean gameOver;

	public static void main(String[] args) throws FileNotFoundException {

		Scanner sc = new Scanner(new FileInputStream(
				"res/inputRaidenArcade.txt"));

		int T = sc.nextInt();

		for (int test_case = 1; test_case <= T; test_case++) {
			M = 5;
			N = sc.nextInt();
			grid = new int[N][M];
			bombGrid = new int[N][M];

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					grid[i][j] = sc.nextInt();
				}
			}

			Answer=0;
			
			for (int i = 0; i < N - M + 1; i++) {
				recGold=0;
				gameOver=true;
				bomb(i);
				countMax(N, 2, 0);

				if (recGold > Answer) {
					Answer = recGold;
				}
				
				if (gameOver)
					Answer = -1;
				
			}

			System.out.println(Answer);

		}

	}

	private static void countMax(int i, int j, int gold) {

		if (j < 0 || j >= M)
			return;

		if (gold < 0) {
			return;
		}

		if (i < 0) {
			if (gold > recGold) {
				recGold = gold;
			}
			gameOver = false;
			return;
		}

		if (i < N) {

			if (bombGrid[i][j] == 1)
				gold++;
			else if (bombGrid[i][j] == 2)
				gold--;
		}

		countMax(i - 1, j - 1, gold);// move left

		countMax(i - 1, j + 1, gold);// move right

		countMax(i - 1, j, gold);// stay

		return;
	}

	public static void bomb(int idx) {

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				bombGrid[i][j] = grid[i][j];
			}
		}

		for (int i = idx; i < idx + M; i++) {
			for (int j = 0; j < M; j++) {
				if (grid[i][j] == 2)
					bombGrid[i][j] = 0;
			}
		}

	}

}
