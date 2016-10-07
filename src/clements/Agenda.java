package clements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Agenda {
	
	public static void main(String args[]) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("agenda.dat")))) {
			int cases = Integer.parseInt(reader.readLine());
			for(int i = 0; i<cases; i++) {
				String []startInfo = reader.readLine().split(" ");
				double speed = Double.parseDouble(startInfo[0]);
				Time startTime = new Time(startInfo[1], startInfo[2]);
				String info = "";
				do {
					info = reader.readLine();
					String trip[] = info.split(" ");
					double number = Double.parseDouble(trip[trip.length - 2]);
					if(trip[trip.length - 1].equals("MILES")) {
						startTime.addMinutes(calculateMinutes(speed, number));
					} else {
						startTime.addMinutes(number);
					}
				} while(info.indexOf("HOME") == -1);
				System.out.println("Joe will arrive home at " + startTime);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int calculateMinutes(double speed, double distance) {
		return (int)(distance / speed * 60.0);
	}
	private static class Time {
		private int hours;
		private int minutes;
		public Time(String info, String side) {
			String []time = info.split(":");
			int beforeM = Integer.parseInt(time[0]);
			hours = side.equals("PM") ? beforeM + 12 : beforeM;
			minutes = Integer.parseInt(time[1]);
		}
		
		public void addMinutes(double minutes) {
			while(minutes > 60) {
				this.hours += 1;
				minutes -= 60;
			}
			this.minutes += minutes;
		}
		
		public String zeroString(int number) {
			return number >= 10 ? "" + number : "0" + number;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			String side = "";
			while(minutes > 60) {
				hours += 1;
				minutes -= 60;
			}
			int hours = this.hours;
			if(hours > 12) {
				side = "PM";
				hours -= 12;
			} else {
				side = "AM";
			}
			return zeroString(hours) + ":" + zeroString(minutes) + " " + side;
		}
		
	}
}
