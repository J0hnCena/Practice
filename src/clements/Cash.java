package clements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Cash {

	public static void main(String[] args) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("cash.dat")))) {
			int cases = Integer.parseInt(reader.readLine());
			for(int i = 0; i<cases; i++) {
				String info[] = reader.readLine().split(" ");
				int size = Integer.parseInt(info[0]);
				int change = Integer.parseInt(info[1]);
				String coins[] = reader.readLine().split(" ");
				int input[] = new int[size];
				for(int j = 0; j<size; j++) {
					input[j] = Integer.parseInt(coins[j]);
				}
				String possible = isPossible(input, 0, 0, change) ? "possible" : "not possible";
				System.out.println(change + " is " + possible);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isPossible(int input[], int currSum, int index, int size) {
		if(currSum == size)
			return true;
		else if(index == input.length)
			return false;
		else {
			currSum += input[index];
			if(!isPossible(input, currSum, index + 1, size)) {
				currSum -= input[index];
				return isPossible(input, currSum, index + 1, size);
			}
			return true;
		}
	}

}
