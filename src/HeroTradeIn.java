

import java.util.ArrayList;
import java.util.Scanner;

public class HeroTradeIn {

	private static int Answer, N, countGroup;
	private static int[][] data;
	private static PhoneGroup[] phoneGroups;
	private static PhoneGroup phoneGroup;
	private static int[] countArea;
	private static ArrayList<Booth> visitedGroup;

	public static void main(String[] args) {
		/*
		 * Enter your code here. Read input from STDIN. Print output to STDOUT.
		 * Your class should be named Solution.
		 */

		Scanner sc = new Scanner(System.in);

		N = sc.nextInt();
		data = new int[N][N];
		phoneGroups = new PhoneGroup[6];
		countGroup = 0;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				data[i][j] = sc.nextInt();
			}
		}

		for (int i = 0; i < 6; i++) {
			countGroup += countAndSaveGroup(i);
		}

		findAndReplaceZero();

		// printResult();

		countResult();

	}

	public static void countResult() {
		int countResult = 0;
		countArea = new int[6];
		for (int i = 0; i < 6; i++) {
			countBooths(i);
			countResult += countArea[i];
		}
		System.out.println(countResult);
	}

	static void countBooths(int model) {
		// Make a bool array to mark visited cells.
		// Initially all cells are unvisited
		boolean[][] visited = new boolean[N][N];
		boolean[][] groupCheck = new boolean[N][N];

		// Initialize count as 0 and travese through the all cells of
		// given matrix
		int count = 0;
		for (int i = 0; i < N; ++i)
			for (int j = 0; j < N; ++j)
				if (data[i][j] == model && !visited[i][j]) // If a cell with
															// value 1 is not
				{ // visited yet, then new island found
					groupCheck[i][j] = true;
					DFS(data, i, j, visited, model, count, groupCheck); // Visit
																		// all
																		// cells
																		// in
																		// this
																		// island.
					++count; // and increment island count
				}

		countArea[model] = count;
	}

	public static void printResult() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(data[i][j]);
			}
			System.out.println();
		}
	}

	private static int countAndSaveGroup(int type) {

		boolean visited[][] = new boolean[N][N];
		boolean[][] groupCheck = new boolean[N][N];

		phoneGroup = new PhoneGroup();
		phoneGroup.setType(type);

		int count = 0;
		for (int row = 0; row < N; ++row) {
			for (int col = 0; col < N; ++col) {
				if (data[row][col] == type && !visited[row][col]) {
					groupCheck[row][col] = true;
					DFS(data, row, col, visited, type, count, groupCheck);
					saveGroup(groupCheck, type);
					groupCheck = new boolean[N][N];
					++count;
				}
			}
		}

		phoneGroups[type] = phoneGroup;
		return count;

	}

	private static PhoneGroup saveGroup(boolean[][] map, int model) {

		Phone phone = new Phone();
		phone.setType(model);

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (map[i][j]) {
					Booth point = new Booth();
					point.setPoint(i, j);
					phone.setType(model);
					phone.addMember(point);

				}
			}
		}

		phoneGroup.addComponent(phone);

		return phoneGroup;

	}

	private static void findAndReplaceZero() {

		for (int i = 0; i < phoneGroups[0].getComponentCount(); i++) {

			int countMembers = phoneGroups[0].getComponents()[i]
					.getMembersCount();

			countArea = new int[6];
			int changeToModel = 0;
			int countMax = 0;

			visitedGroup = new ArrayList<Booth>();

			for (int j = 0; j < countMembers; j++) {

				int y = phoneGroups[0].getComponents()[i].getMembers()[j]
						.getY();
				int x = phoneGroups[0].getComponents()[i].getMembers()[j]
						.getX();

				// up
				if (y - 1 >= 0) {
					if (data[y - 1][x] != 0) {
						setCountArea(y - 1, x);
					}
				}
				// right
				if (x + 1 < N - 1) {
					if (data[y][x + 1] != 0) {
						setCountArea(y, x + 1);
					}
				}
				// down
				if (y + 1 < N - 1) {
					if (data[y + 1][x] != 0) {
						setCountArea(y + 1, x);
					}
				}
				// left
				if (x - 1 >= 0) {
					if (data[y][x - 1] != 0) {
						setCountArea(y, x - 1);
					}
				}

				for (int model = 1; model < 6; model++) {
					if (countMax == countArea[model] && countArea[model] != 0) {
						changeToModel = Math.max(changeToModel, model);
					} else if (countMax < countArea[model]) {
						changeToModel = model;
						countMax = countArea[model];
					}
				}

			}

			for (int j = 0; j < countMembers; j++) {
				int y = phoneGroups[0].getComponents()[i].getMembers()[j]
						.getY();
				int x = phoneGroups[0].getComponents()[i].getMembers()[j]
						.getX();

				changeZeroTo(y, x, changeToModel);

			}
			for (int j = 0; j < visitedGroup.size(); j++) {
				phoneGroups[visitedGroup.get(j).getX()].getComponents()[visitedGroup
						.get(j).getY()].setVisited(false);
			}

		}
	}

	static void changeZeroTo(int i, int j, int model) {
		Phone[] phones = phoneGroups[data[i][j]].getComponents();
		for (int row = 0; row < phones.length; row++) {
			Booth[] booths = phones[row].getMembers();
			for (int col = 0; col < booths.length; col++) {
				if (booths[col].isPoint(i, j)) {
					for (int j2 = 0; j2 < booths.length; j2++) {
						data[booths[j2].getY()][booths[j2].getX()] = model;
					}
				}
			}
		}

	}

	static void setCountArea(int i, int j) {
		if (countArea[data[i][j]] != 0 && !isGroupVisited(i, j)) {
			countArea[data[i][j]] += getMembersFromPoint(i, j)
					.getMembersCount();
		} else if (isGroupVisited(i, j)) {
			return;
		} else {
			countArea[data[i][j]] = getMembersFromPoint(i, j).getMembersCount();
		}
		setGroupVisited(i, j, true);

	}

	static void setGroupVisited(int i, int j, boolean visited) {
		Phone[] phones = phoneGroups[data[i][j]].getComponents();
		for (int row = 0; row < phones.length; row++) {
			Booth[] booths = phones[row].getMembers();
			for (int col = 0; col < booths.length; col++) {
				if (booths[col].isPoint(i, j)) {
					phoneGroups[data[i][j]].getComponents()[row]
							.setVisited(visited);
					visitedGroup.add(new Booth(row, data[i][j]));

					return;
				}
			}
		}
	}

	static boolean isGroupVisited(int i, int j) {
		Phone[] phones = phoneGroups[data[i][j]].getComponents();
		for (int row = 0; row < phones.length; row++) {
			Booth[] booths = phones[row].getMembers();
			for (int col = 0; col < booths.length; col++) {
				if (booths[col].isPoint(i, j)) {
					return phones[row].isVisited();
				}
			}
		}
		return false;
	}

	static Phone getMembersFromPoint(int i, int j) {
		Phone[] phones = phoneGroups[data[i][j]].getComponents();
		for (int row = 0; row < phones.length; row++) {
			Booth[] booths = phones[row].getMembers();
			for (int col = 0; col < booths.length; col++) {
				if (booths[col].isPoint(i, j)) {
					return phones[row];
				}
			}
		}

		return phones[0];
	}

	static boolean isSafe(int M[][], int row, int col, boolean visited[][],
			int type) {
		return (row >= 0) && (row < N) && (col >= 0) && (col < N)
				&& (M[row][col] == type && !visited[row][col]);
	}

	static void DFS(int M[][], int row, int col, boolean visited[][], int type,
			int idx, boolean groups[][]) {
		int rowNbr[] = new int[] { -1, 0, 0, 1 };
		int colNbr[] = new int[] { 0, -1, 1, 0 };

		visited[row][col] = true;
		groups[row][col] = true;

		for (int k = 0; k < 4; ++k)
			if (isSafe(M, row + rowNbr[k], col + colNbr[k], visited, type))
				DFS(M, row + rowNbr[k], col + colNbr[k], visited, type, idx,
						groups);
	}
}

class PhoneGroup {
	private int model;
	private ArrayList<Phone> components = new ArrayList<Phone>();

	public PhoneGroup() {
	}

	public void setType(int type) {
		model = type;
	}

	public void addComponent(Phone phone) {
		components.add(phone);
	}

	public int getComponentCount() {
		return components.size();
	}

	public Phone[] getComponents() {
		Phone[] phones = new Phone[components.size()];
		for (int i = 0; i < phones.length; i++) {
			phones[i] = components.get(i);
		}
		return phones;

	}
}

class Phone {
	private int type;
	private ArrayList<Booth> members = new ArrayList<Booth>();
	private boolean isVisited;

	public Phone() {

	}

	public void setType(int model) {
		type = model;
	}

	public int getType() {
		return type;
	}

	public void addMember(Booth booth) {
		members.add(booth);
	}

	public Booth[] getMembers() {
		Booth[] booths = new Booth[members.size()];
		for (int i = 0; i < booths.length; i++) {
			booths[i] = members.get(i);
		}
		return booths;
	}

	public int getMembersCount() {
		return members.size();
	}

	public void setVisited(boolean visited) {
		isVisited = visited;
	}

	public boolean isVisited() {
		return isVisited;
	}

}

class Booth {
	private int x, y;

	public Booth() {
		// TODO Auto-generated constructor stub
	}

	public Booth(int i, int j) {
		x = j;
		y = i;
	}

	public void setPoint(int i, int j) {
		x = j;
		y = i;
	}

	public boolean isPoint(int i, int j) {
		return (y == i && x == j);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
