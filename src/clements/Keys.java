package clements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;

public class Keys {

	public static void main(String[] args) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("keys.dat")))) {
			int cases = Integer.parseInt(reader.readLine());
			for(int i = 0; i<cases; i++) {
				int number = Integer.parseInt(reader.readLine());
				NumberFormat formatter = NumberFormat.getPercentInstance();
				String num = formatter.format(1.0 / number);
				System.out.printf("%.2f",Double.parseDouble(num.substring(0,num.length()-1)));
				System.out.println("%");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
