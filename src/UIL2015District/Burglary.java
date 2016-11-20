package UIL2015District;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;

public class Burglary {

	public static void main(String[] args) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("burglary.dat")))) {
			int cases = Integer.parseInt(reader.readLine());
			for(int i = 0; i<cases; i++) {
				int doors = Integer.parseInt(reader.readLine());
				String moneyz[] = reader.readLine().split(" ");
				double max = 0;
				double maxDoors = 0;
				double curr = 0;
				for(int j = 1; j<=doors; j++) {
					double money = Double.parseDouble(moneyz[j - 1]);
					curr+=money;
					double ex = calculateExpected(j, curr);
					if(ex > max) {
						max = ex;
						maxDoors = j - 1;
					}
				}
				NumberFormat formatter = NumberFormat.getCurrencyInstance();
				String maxAmount = formatter.format(max);
				System.out.println(maxDoors + " " + maxAmount.substring(1));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static double calculateExpected(int doors, double money) {
		return (1 - (doors - 1) * 0.05) * money;
	}

}
