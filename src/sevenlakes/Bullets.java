package sevenlakes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Bullets {

	public static void main(String[] args) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("bullets.in")))) {
			int cases = Integer.parseInt(reader.readLine());
			for(int i = 0; i<cases; i++) {
				int[] info = Arrays.asList(reader.readLine().split(" ")).stream().map(s -> Integer.parseInt(s)).mapToInt(Integer::intValue).toArray();
				System.out.println(info[0] * log2(info[0]) > info[1] ? "Sweatin' bullets" : "It's 32!");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static long log2(int value) {
		return (long)(Math.log(value) / Math.log(2));
	}

}
