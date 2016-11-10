package katytaylor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class Jack {

	public static void main(String[] args) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("jack.dat")))) {
			int[] sizes = Arrays.asList(reader.readLine().split(" ")).stream().map(s -> Integer.parseInt(s)).mapToInt(Integer::intValue).toArray();
			int[][] mat = new int[sizes[0]][sizes[1]];
			for(int[] arr : mat) {
				Arrays.fill(arr, 0);
			}
			String line;
			while((line = reader.readLine()) != null) {
				int testmat[][] = new int[sizes[0]][sizes[1]];
				for(int [] arr : testmat) {
					Arrays.fill(arr, 0);
				}
				int[] info = Arrays.asList(line.split(" ")).stream().map(s -> Integer.parseInt(s)).mapToInt(Integer::intValue).toArray();
				Lantern lant = new Lantern(info[0], info[1], info[2], info[3]);
				int initX = lant.row;
				int initY = lant.length;
				testmat[initX][initY] = lant.brightness;
				int[] xtravel = {0, 1, 1, 1, 0, -1, -1, -1};
				int[] ytravel = {1, 1, 0, -1, -1, -1, 0, 1};
				boolean [][] visited = new boolean[mat.length][mat[0].length];
				for(boolean[] arr : visited) {
					Arrays.fill(arr, false);
				}
				visited[initX][initY] = true;
				LinkedList<Point> points = new LinkedList<>();
				points.add(new Point(initX, initY));
				while(!points.isEmpty()) {
					Point test = points.removeFirst();
					for(int i = 0; i<xtravel.length; i++) {
						int newX = test.x + xtravel[i];
						int newY = test.y + ytravel[i];
						if(newX >= 0 && newY >= 0 && newX < mat.length && newY < mat[0].length && !visited[newX][newY]) {
							visited[newX][newY] = true;
							int testVal = testmat[test.x][test.y] - 1;
							testmat[newX][newY] = testVal > 0 ? testVal : 0;
							points.add(new Point(newX, newY));
						}
					}
				}
				testmat[initX][initY] = lant.isOnTheFloor ? 0 : lant.brightness;
				for(int r = 0; r<mat.length; r++) {
					for(int c = 0; c<mat[0].length; c++) {
						mat[r][c] += testmat[r][c];
					}
				}
			}
			for(int[] arr : mat) {
				System.out.println(Arrays.toString(arr));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class Lantern {
		private int row, length, brightness;
		private boolean isOnTheFloor;
		public Lantern(int row, int length, int intensity, int height) {
			this.row = row;
			this.length = length;
			this.brightness = intensity - height;
			isOnTheFloor = height == 0;
		}
	}

	private static class Point {
		private int x, y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}
