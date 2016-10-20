package katytaylor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Knight {

	public static void main(String[] args) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("knight.dat")))) {
			int cases = Integer.parseInt(reader.readLine());
			for(int i = 0; i<cases; i++) {
				int[] matInfo = Arrays.asList(reader.readLine().split(" ")).stream().map(s -> Integer.parseInt(s)).mapToInt(Integer::intValue).toArray();
				int rows = matInfo[0];
				int[][] mat = new int[rows][];
				for(int c = 0; c<rows; c++) {
					mat[c] = Arrays.asList(reader.readLine().trim().split("[ ]+")).stream().map(s -> Integer.parseInt(s)).mapToInt(Integer::intValue).toArray();
				}

				System.out.println(isValid(mat) ? "YAY" : "NAY");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isValid(int board[][]) {
		Pair start = locateStart(board);
		int xPos = start.x;
		int yPos = start.y;
		int xMove[] = {2, 1, -1, -2, -2, -1, 1, 2};
		int yMove[] = {1, 2, 2, 1, -1, -2, -2, -1};
		int move = 1;
		int prevMove = 0;
		while(move != board.length * board[0].length) {
			for(int i = 0; i<8; i++) {
				int evalX = xPos + xMove[i];
				int evalY = yPos + yMove[i];
				if(isSafe(evalX, evalY, board)) {
					if(board[evalX][evalY] == move) {
						xPos = evalX;
						yPos = evalY;
						move++;
						break;
					}
				}
			}
			prevMove++;
			if(move == prevMove)
				return false;
		}
		return true;
	}

	public static boolean isSafe(int x, int y, int board[][]) {
		return (x >= 0 && y >= 0 && x < board.length && y < board[0].length);
	}

	public static Pair locateStart(int board[][]) {
		for(int r = 0; r < board.length; r++) {
			for(int c = 0; c < board[0].length; c++) {
				if(board[r][c] == 0)
					return new Pair(r,c);
			}
		}
		throw new IllegalArgumentException("Board has no start location");
	}

	private static class Pair {
		private int x,y;
		Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}
