package sevenlakes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Rect {

	public static void main(String[] args) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("rect_u.in")))) {
			int cases = Integer.parseInt(reader.readLine());
			for(int i = 0; i<cases; i++){
				long[] rect = Arrays.asList(reader.readLine().split(" ")).stream().map(s -> Long.parseLong(s)).mapToLong(Long::longValue).toArray();
				System.out.println(calculateArea(rect[0], rect[1], rect[2], rect[3]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static long calculateArea(long x1, long y1, long x2, long y2) {
		return Math.abs(y2 - y1) * Math.abs(x2 - x1);
	}

}
