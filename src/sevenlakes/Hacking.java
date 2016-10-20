package sevenlakes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Hacking {

	public static void main(String[] args) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("hacking.in")))) {
			int addresses = Integer.parseInt(reader.readLine());
			Map<Integer, Integer> original = new HashMap<>(addresses);
			Map<Integer, Integer> current = new HashMap<>(addresses);
			for(int i = 0; i<addresses; i++) {
				String []address = reader.readLine().split(" ");
				original.put(Integer.parseInt(address[0]), Integer.parseInt(address[1], 16));
			}
			
			for(int c = 0; c<addresses; c++) {
				String []address = reader.readLine().split(" ");
				current.put(Integer.parseInt(address[0]), Integer.parseInt(address[1], 16));
			}
			
			StringBuilder corrupted = new StringBuilder();
			for(int k = 0; k<addresses; k++) {
				if(!original.get(k).equals(current.get(k))) {
					corrupted.append(k + ", ");
				}
			}
			System.out.println(corrupted.substring(0, corrupted.length() - 1));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
