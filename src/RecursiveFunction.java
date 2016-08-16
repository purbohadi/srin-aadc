

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RecursiveFunction {

	static int[] data;
	static boolean[] visited;
	static int n;
	static int[][] map;
	static boolean[][] visitedMap;
	static int N, C, startX, startY, Answer, recGold;

	static int[] distance;

	public static void main(String[] args) throws FileNotFoundException {

		System.setIn(new FileInputStream("res/inputRecursive.txt"));
		Scanner sc = new Scanner(System.in);

		int T = sc.nextInt();

		for (int test_case = 1; test_case <= T; test_case++) {

			N = sc.nextInt();
			n = 3;

			map = new int[3][2];
			distance = new int[N];

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 2; j++) {
					map[i][j] = sc.nextInt();
				}
			}

			Answer = Integer.MAX_VALUE;

			data = new int[n];
			visited = new boolean[n + 1];
			for (int i = 0; i < visited.length; i++) {
				visited[i] = false;
			}

			// gateLeft(1);
			// gateLeft(0);
			// gateLeft(2);
			//
			// countTotal();

			// countTotal();
			combination(0);
			System.out.println("#"+(test_case)+" "+Answer);
		}

		// int x = sc.nextInt();
		// int y = sc.nextInt();
		// System.out.println(countFibonacci(n));
		// System.out.println(countPower(x, y));

		// Answer = 0;
		// recGold = 0;
		//
		// N = sc.nextInt();
		// C = sc.nextInt();
		//
		// map = new int[N][C];
		// visitedMap = new boolean[N][C];
		//
		// startY = sc.nextInt();
		// startX = sc.nextInt();
		//
		// for (int i = 0; i < N; i++) {
		// for (int j = 0; j < C; j++) {
		// map[i][j]=sc.nextInt();
		// }
		// }

		// traverseMap(startX, startY);

		// System.out.println(recGold);

		// permutation(0);

		sc.close();
	}

	static void gateLeft(int idx) {

		int fishingSpot = map[idx][0] - 1;
		int fisherNum = map[idx][1];

		int count = 0;
		int index = 0;
		if (distance[fishingSpot] == 0) {
			distance[fishingSpot] = 1;
			count++;
			index++;
		}

		while (true) {

			if (count >= fisherNum)
				break;
			
			// fill left
			if (fishingSpot - index >= 0) {
				if (distance[fishingSpot - index] == 0) {
					distance[fishingSpot - index] = index + 1;
					count++;
				}
			}

			if (count >= fisherNum)
				break;
			
			// fill right
			if (fishingSpot + index < N) {
				if (distance[fishingSpot + index] == 0) {
					distance[fishingSpot + index] = index + 1;
					count++;
				}
			}
			
			index++;

		}

	}

	static void gateRight(int idx) {

		int fishingSpot = map[idx][0] - 1;
		int fisherNum = map[idx][1];

		int count = 0;
		int index = 0;
		
		if (distance[fishingSpot] == 0) {
			distance[fishingSpot] = 1;
			count++;
			index++;
		}

		while (true) {

			if (count >= fisherNum)
				break;
			
			// fill right
			if (fishingSpot + index < N) {
				if (distance[fishingSpot + index] == 0) {
					distance[fishingSpot + index] = index + 1;
					count++;
				}
			}

			if (count >= fisherNum)
				break;
			
			// fill left
			if (fishingSpot - index >= 0) {
				if (distance[fishingSpot - index] == 0) {
					distance[fishingSpot - index] = index + 1;
					count++;
				}
			}

			
			index++;

		}

	}

	static void combination(int idx) {
		if (idx == n) {

//			printData();

			for (int i = 0; i < 3; i++) {
				gateLeft(data[i] - 1);
			}
			
			resetData();
			

			for (int i = 0; i < 3; i++) {
				gateRight(data[i] - 1);
			}
			countTotal();

			resetData();

			return;
		} else {
			for (int i = 1; i <= n; i++) {
				if (!visited[i]) {
					visited[i] = true;
					data[idx] = i;
					combination(idx + 1);
					visited[i] = false;
				}
			}
		}
	}

	static void countTotal() {
		int total = 0;
		for (int i = 0; i < N; i++) {
			total += distance[i];
		}

		if (total < Answer)
			Answer = total;

	}

	static void traverseMap(int x, int y) {
		if (y < 0 || y >= N || x < 0 || x >= C)
			return;

		if (!visitedMap[y][x] && map[y][x] == 1) {
			visitedMap[y][x] = true;
			recGold++;
			traverseMap(x + 1, y);
			traverseMap(x, y + 1);
			traverseMap(x - 1, y);
			traverseMap(x, y - 1);
			// visitedMap[y][x] = false;
		}

	}

	static void permutation(int idx) {
		if (idx == n) {
			printData();
			return;
		} else {
			for (int i = 1; i <= n; i++) {
				data[idx] = i;
				permutation(idx + 1);
			}
		}

	}

	static void resetData() {
		for (int i = 0; i < N; i++) {
			distance[i] = 0;
		}
	}

	static void printData() {
		for (int i = 0; i < data.length; i++) {
			System.out.print(data[i] + " ");
		}
		System.out.println();
	}

	static int countFibonacci(int n) {
		if (n <= 2)
			return 1;
		else
			return countFibonacci(n - 2) + countFibonacci(n - 1);
	}

	static int countPower(int x, int y) {
		if (y == 0)
			return 1;
		else
			return x * countPower(x, y - 1);
	}

}
