package clements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Lights {
	byte[] lights;

	public Lights(byte[]arr) {
		lights = arr;
	}

	public static void main(String[] args) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("lights.dat")))) {
			int cases = Integer.parseInt(reader.readLine());
			for(int i = 0; i<cases; i++) {
				String[] info = reader.readLine().split(" ");
				byte[] arr = new byte[info[0].length()];
				for(int j = 0; j<arr.length; j++) {
					arr[j] = Byte.parseByte(info[0].substring(j, j+1));
				}
				Lights lights = new Lights(arr);
				int commandLength = Integer.parseInt(info[1]);
				for(int c = 0; c<commandLength; c++) {
					lights.execute(reader.readLine().split(" "));
				}
				System.out.println(lights);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void execute(String[] command) {
			switch(command[0]) {
			case "FLIP":
				if(command.length == 3)
					flip(command[1], command[2]);
				else
					flip();
				break;
			case "ON":
				if(command.length == 3)
					on(command[1], command[2]);
				else
					on();
				break;
			case "OFF":
				if(command.length == 3)
					off(command[1], command[2]);
				else
					off();
				break;
			}
	}
	
	public void on() {
		for(int i = 0; i<lights.length; i++) {
			lights[i] = 1;
		}
	}
	
	public void on(String start, String stop) {
		int startPos = Integer.parseInt(start);
		int endPos = Integer.parseInt(stop);
		for(int i = startPos; i<endPos; i++) {
			lights[i] = 1;
		}
	}
	
	public void off() {
		for(int i = 0; i<lights.length; i++) {
			lights[i] = 0;
		}
	}
	
	public void off(String start, String stop) {
		int startPos = Integer.parseInt(start);
		int endPos = Integer.parseInt(stop);
		for(int i = startPos; i<endPos; i++) {
			lights[i] = 0;
		}
	}
	
	public void flip() {
		for(int i = 0; i<lights.length; i++) {
			lights[i] ^= 1;
		}
	}
	
	public void flip(String start, String stop) {
		int startPos = Integer.parseInt(start);
		int endPos = Integer.parseInt(stop);
		for(int i = startPos; i<endPos; i++) {
			lights[i] ^= 1;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i<lights.length; i++) {
			builder.append(lights[i]);
		}
		return builder.toString();
	}
	
}
