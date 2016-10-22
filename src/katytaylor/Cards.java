package katytaylor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Cards {
	private List<Card> list;
	private Map<Integer, Integer> cardSorter;
	public Cards(List<Card> list) {
		this.list = list;
		Collections.sort(list);
		cardSorter = new HashMap<>();
		for(Card card : list) {
			cardSorter.put(card.rank, cardSorter.containsKey(card.rank) ? cardSorter.get(card.rank) + 1 :  1);
		}
	}

	public static void main(String[] args) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("cards.dat")))) {
			String line;
			while((line = reader.readLine()) != null) {
				Cards cards = new Cards(Arrays.asList(line.split(" ")).stream().map(s -> new Card(s)).collect(Collectors.toList()));
				if(cards.isRoyalFlush()) {
					System.out.println("Royal Flush");
				} else if(cards.isStraightFlush()) {
					System.out.println("Straight Flush");
				} else if(cards.howManyOfAKind() == 4) {
					System.out.println("Four of a kind");
				} else if(cards.isFullHouse()) {
					System.out.println("Full house");
				} else if(cards.isFlush()) {
					System.out.println("Flush");
				} else if(cards.isStraight()) {
					System.out.println("Straight");
				} else if(cards.howManyOfAKind() == 3) {
					System.out.println("Three of a kind");
				} else if(cards.howManyPairs() == 2) {
					System.out.println("Two pair");
				} else if(cards.howManyPairs() == 1) {
					System.out.println("One pair");
				} else {
					System.out.println("No pair");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Card cardFromName(String name) {
		return new Card(name);
	}
	
	public int howManyOfAKind() {
		int max = 0;
		for(Entry<Integer, Integer> entry : cardSorter.entrySet()) {
			if(entry.getValue() > max)
				max = entry.getValue();
		}
		return max;
	}
	
	public boolean isFullHouse() {
		return howManyOfAKind() == 3 && howManyPairs() == 1;
	}
	
	public boolean isRoyalFlush() {
		if(!isFlush())
			return false;
		int[] royalRanks = {1, 10, 11, 12, 13};
		for(int i = 0; i<list.size(); i++) {
			if(list.get(i).rank != royalRanks[i])
				return false;
		}
		return true;
	}
	
	public boolean isStraightFlush() {
		return isFlush() && isStraight();
	}
	
	public boolean isFlush() {
		String suit = list.get(0).suit;
		for(int i = 1; i<list.size(); i++) {
			if(!list.get(i).suit.equals(suit)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isStraight() {
		for(int i = 0; i<list.size() - 1; i++) {
			if(!list.get(i).isAdjacent(list.get(i + 1))) {
				return false;
			}
		}
		return true;
	}
	
	public int howManyPairs() {
		int numPairs = 0;
		for(Entry<Integer, Integer> entry : cardSorter.entrySet()) {
			if(entry.getValue() == 2)
				numPairs++;
		}
		return numPairs;
	}
	
	private static class Card implements Comparable<Card>{
		private String suit;
		private int rank;
		public Card(String type) {
			suit = type.substring(type.length() - 1, type.length());
			String theRank = type.substring(0, type.length() - 1);
			if(theRank.equals("J")) {
				rank = 11;
			} else if(theRank.equals("Q")) {
				rank = 12;
			} else if(theRank.equals("K")) {
				rank = 13;
			} else if(theRank.equals("A")) {
				rank = 1;
			} else {
				rank = Integer.parseInt(theRank);
			}
		}
		
		public boolean isAdjacent(Card card) {
			if(this.rank == 1 && card.rank == 10) 
				return true;
			else
				return card.rank - this.rank == 1;
		}

		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(Card other) {
			if(this.rank < other.rank)
				return -1;
			else if(this.rank > other.rank)
				return 1;
			return 0;
		}
		
		
	}

}
