package sevenlakes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

public class Phonebook {

	public static void main(String[] args) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("phonebook.in")))) {
			int numNames = Integer.parseInt(reader.readLine());
			TreeMap<String, String> names = new TreeMap<>();
			for(int i = 0; i<numNames; i++) {
				String[] name = reader.readLine().split(" ");
				names.put(name[1], name[0]);
			}
			names.forEach((k,v) -> System.out.println(k + ", " + v));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
