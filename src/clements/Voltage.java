package clements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Voltage {
	private HashMap<String, Double> data;
	public Voltage(String type1, String type2, double arg1, double arg2) {
		data = new HashMap<>();
		data.put(type1, arg1);
		data.put(type2, arg2);
		while(data.entrySet().size() != 4) {
			calculateCurrent();
			calculateVoltage();
			calculatePower();
			calculateResistance();
		}
	}

	public static void main(String[] args) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("voltage.dat")))) {
			int cases = Integer.parseInt(reader.readLine());
			for(int i = 0; i<cases; i++) {
				String data1[] = reader.readLine().split(" = ");
				String data2[] = reader.readLine().split(" = ");
				Voltage volt = new Voltage(data1[0], data2[0], Double.parseDouble(data1[1]), Double.parseDouble(data2[1]));
				System.out.println(volt);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void calculateVoltage() {
		if(!data.containsKey("V"))
			data.put("V", data.containsKey("P") ? data.get("P") / data.get("I") : data.get("I") * data.get("R"));
	}

	public void calculateCurrent() {
		if(!data.containsKey("I"))
			data.put("I", data.containsKey("P") ? data.get("P") / data.get("V") : data.get("V") / data.get("R"));
	}

	public void calculateResistance() {
		if(!data.containsKey("R"))
			data.put("R", data.get("V") / data.get("I"));
	}

	public void calculatePower() {
		if(!data.containsKey("P"))
			data.put("P", data.get("I") * data.get("V"));
	}
	
	public String rounded(double doub) {
		return "" + String.format("%.3f", doub);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "V = " + rounded(data.get("V")) + ", " + "I = " + rounded(data.get("I")) + ", " + "R = " + rounded(data.get("R")) + ", " + "P = " + rounded(data.get("P"));
	}

	

}
