package katytaylor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import java.util.TreeMap;

public class Trick {

	public static void main(String[] args) throws CloneNotSupportedException {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("trick.dat")))) {
			int cases = Integer.parseInt(reader.readLine());
			for(int i = 0; i<cases; i++) {
				int numTypes = Integer.parseInt(reader.readLine());
				List<Candy> candyList = new ArrayList<>(numTypes);
				for(int c = 0; c<numTypes; c++) {
					String[] info = reader.readLine().split(", ");
					candyList.add(new Candy(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]), Integer.parseInt(info[3])));
				}
				int maxWeight = Integer.parseInt(reader.readLine());
				CandyCollection[] col = new CandyCollection[maxWeight + 1];
				Arrays.fill(col, new CandyCollection());
				for(int candyIndex = 0; candyIndex<candyList.size(); candyIndex++) {
					for(int weight = maxWeight; weight >= 0; weight--) {
						if(weight >= candyList.get(candyIndex).weight) {
							int quantity = Math.min(candyList.get(candyIndex).pieces, weight / candyList.get(candyIndex).weight);
							for(int numCandies = 1; numCandies<=quantity; numCandies++) {
								CandyCollection collect = col[weight - numCandies * candyList.get(candyIndex).weight];
								int test = collect.combinedTaste + numCandies * candyList.get(candyIndex).taste;
								if(test > col[weight].combinedTaste)
									(col[weight] = collect.clone()).addCandies(candyList.get(candyIndex), numCandies);
							}
						}
					}
				}
				
				CandyCollection maxValueCollection = col[maxWeight];
				for(Entry<String, Integer> ent : maxValueCollection.candies.entrySet()) {
					System.out.println(ent.getKey() + ": " + ent.getValue());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int calculateMax(int currWeight, Candy[] candies, int index) {
		return 0;
	}

	private static class Candy {
		private String name;
		private int taste, pieces, weight;
		public Candy(String name, int taste, int pieces, int weight) {
			this.name = name;
			this.taste = taste;
			this.pieces = pieces;
			this.weight = weight;
		}
	}

	private static class CandyCollection {
		private Map<String, Integer> candies;
		private int combinedTaste;
		public CandyCollection() {
			candies = new TreeMap<>();
			combinedTaste = 0;
		}

		public void addCandies(Candy candy, int number) {
			combinedTaste+=candy.taste*number;
			candies.put(candy.name, (candies.containsKey(candy.name) ? candies.get(candy.name) + number : number));
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#clone()
		 */
		@Override
		protected CandyCollection clone() throws CloneNotSupportedException {
			CandyCollection candies = new CandyCollection();
			candies.candies = new TreeMap<>(this.candies);
			candies.combinedTaste = this.combinedTaste;
			return candies;
		}
		
	}

}
