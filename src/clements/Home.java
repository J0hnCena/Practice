package clements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Home {

	public static void main(String[] args) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("home.dat")))) {
			int cases = Integer.parseInt(reader.readLine());
			for(int i = 0; i<cases; i++) {
				int size = Integer.parseInt(reader.readLine());
				String [] path = reader.readLine().split(" ");
				for(int c = size-1; c>=0; c--) {
					System.out.print(path[c].equals("right") ? "left " : "right ");
				}
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
