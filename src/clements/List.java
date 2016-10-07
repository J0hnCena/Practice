package clements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class List {

	public static void main(String[] args) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("list.dat")))) {
			int cases = Integer.parseInt(reader.readLine());
			for(int i = 0; i<cases; i++) {
				int numbers[] = Arrays.asList(reader.readLine().split(" ")).stream().map(s-> Integer.parseInt(s)).mapToInt(Integer::intValue).toArray();
				Map<String, Item> store = new HashMap<>(numbers[i]);
				for(int j = 0; j<numbers[0]; j++) {
					String[] itemInfo = reader.readLine().split(", ");
					store.put(itemInfo[0], new Item(Integer.parseInt(itemInfo[1]), Double.parseDouble(itemInfo[2])));
				}
				double totalCost = 0;
				for(int c = 0; c<numbers[1]; c++) {
					String[] itemInfo = reader.readLine().split(", ");
					int quantity = 0;
					int desiredQuantity = Integer.parseInt(itemInfo[1]);
					Item item = store.get(itemInfo[0]);
					while(quantity < desiredQuantity) {
						quantity += item.quantity;
						totalCost += item.price;
					}
				}
				NumberFormat numberFormatter = NumberFormat.getCurrencyInstance();
				String checkout = numberFormatter.format(totalCost * 1.0625);
				System.out.println("Joe's trip to the store costs him " + checkout);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private static class Item {
		private int quantity;
		private double price;
		
		public Item(int quantity, double price) {
			this.quantity = quantity;
			this.price = price;
		}
	}
}
