package clements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.List;

public class Fixing {

	public static void main(String[] args) {
		char []locationNames = {'J', 'M', 'W', 'D', 'A'};
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("fixing.dat")))) {
			int cases = Integer.parseInt(reader.readLine());
			for(int i = 0; i<cases; i++) {
				int length = Integer.parseInt(reader.readLine());
				Map<Character, Point> locations = new HashMap<>(5);
				Map<Character, Edges> edges = new HashMap<>(5);
				char[][] leMap = new char[length][];
				for(int c = 0; c<leMap.length; c++) {
					leMap[c] = reader.readLine().toCharArray();
				}
				populateMapWithLocations(locations, leMap);
				for(Entry<Character, Point> entry : locations.entrySet()) {
					int [][]dist = getDistances(entry.getValue(), leMap);
					Edges edge = new Edges(entry.getKey());
					for(int r = 0; r<leMap.length; r++) {
						for(int c = 0; c<leMap[0].length; c++) {
							if(leMap[r][c] != edge.character && leMap[r][c] != '.' && leMap[r][c] != '#') {
								edge.addEdge(leMap[r][c], dist[r][c]);
							}
						}
					}
					edges.put(edge.character, edge);
				}
				int minTime = Integer.MAX_VALUE;
				Permutations permutations = new Permutations();
				permutations.permute(locationNames, 1);
				for(char []permutation : permutations.permutations) {
					int test = 0;
					for(int x = 0; x<permutation.length - 1; x++) {
						test += edges.get(permutation[x]).edges.get(permutation[x + 1]);
					}
					if(test < minTime) {
						minTime = test;
					}
				}
				minTime +=40;
				System.out.println(minTime);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int[][] getDistances(Point start, char[][] map) {
		LinkedList<Point> points = new LinkedList<>();
		int[][] distances = new int[map.length][map[0].length];
		boolean[][] visited = new boolean[map.length][map[0].length];
		for(boolean[] arr : visited) {
			Arrays.fill(arr, false);
		}
		for(int[] arr:distances) {
			Arrays.fill(arr, 0);
		}
		int[] xMov = {0, 0, 1, -1};
		int[] yMov = {1, -1, 0, 0};
		points.add(start);
		while(!points.isEmpty()) {
			Point examine = points.removeFirst();
			visited[examine.row][examine.column] = true;
			for(int i = 0; i<4; i++) {
				int newX = examine.row + xMov[i];
				int newY = examine.column + yMov[i];
				if(newX >= 0 && newY >= 0 && newX < map.length && newY < map[0].length && map[newX][newY] != '#' && !visited[newX][newY]) {
					distances[newX][newY] = distances[examine.row][examine.column] + 1;
					points.add(new Point(newX, newY));
				}
			}
		}
		return distances;
	}

	private static class Permutations {
		private List<char []> permutations;
		
		public Permutations() {
			permutations = new ArrayList<>();
		}
		
		public void permute(char[] perm, int location) {
			for(int i = location; i<perm.length; i++) {
				char [] copy = Arrays.copyOf(perm, perm.length);
				swap(copy, i, location);
				permute(copy, location + 1);
			}
			if(location == perm.length - 1) {
				permutations.add(perm);
			}
		}
	}

	public static void populateMapWithLocations(Map<Character, Point> locations, char [][]map) {
		for(int r = 0; r<map.length; r++) {
			for(int c = 0; c<map[0].length; c++) {
				if(map[r][c] != '#' && map[r][c] != '.') {
					locations.put(map[r][c], new Point(r,c));
				}
			}
		}
	}

	public static Point findLocation(char character, char [][]map) {
		for(int r = 0; r<map.length; r++) {
			for(int c = 0; c<map[0].length; c++) {
				if(map[r][c] == character)
					return new Point(r,c);
			}
		}
		return null;
	}

	public static void swap(char []arr, int swap1, int swap2) {
		char temp = arr[swap1];
		arr[swap1] = arr[swap2];
		arr[swap2] = temp;
	}

	private static class Edges {
		private char character;
		private Map<Character, Integer> edges;
		public Edges(char character) {
			edges = new HashMap<>();
			this.character = character;
		}

		public void addEdge(char charName, int distance) {
			edges.put(charName, distance);
		}
	}

	private static class Point {
		private int row,column;
		public Point(int row, int column) {
			this.row = row;
			this.column = column;
		}
	}
}
