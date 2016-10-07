package clements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Schedule {

	public static void main(String[] args) {
		Time start = new Time("03:00" , "AM");
		Time end = new Time("09:00", "PM");
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("schedule.dat")))) {
			int cases = Integer.parseInt(reader.readLine());
			for(int i = 0; i<cases; i++) {
				int maxJobs = Integer.parseInt(reader.readLine());
				List<TimeLap> times = new ArrayList<>();
				for(int j = 0; j<maxJobs; j++) {
					String [] jobInfo = reader.readLine().split(" ");
					Time timeStart = new Time(jobInfo[0], jobInfo[1]);
					Time timeEnd = new Time(jobInfo[2], jobInfo[3]);
					if(!start.after(timeStart) && ! end.before(timeEnd)) {
						times.add(new TimeLap(timeStart, timeEnd));
					}
				}
				int max = 0;
				ListIterator<TimeLap> it = times.listIterator();
				while(it.hasNext()) {
					TimeLap test = it.next();
					int testMax = 0;
					for(int c = 0; c<times.size(); c++) {
						if(!test.overlaps(times.get(c)))
							testMax++;
					}
					if(testMax > max)
						max = testMax;
				}
				System.out.println(max);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class TimeLap {
		private Time start;
		private Time end;

		public TimeLap(Time start, Time end) {
			this.start = start;
			this.end = end;
		}

		public boolean overlaps(TimeLap other) {
			return !(other.start.after(end) || start.after(other.end)) || (start.equals(other.start) && end.equals(other.end));
		}
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

		public boolean after(Time time) {
			return time.getHours() < hours ? true : time.getMinutes() < minutes; 
		}

		public boolean before(Time time) {
			return time.getHours() > hours ? true : time.getMinutes() > minutes;
		}
		
		public boolean equals(Time time) {
			return time.getHours() == hours && time.getMinutes() == minutes;
		}

		/**
		 * @return the hours
		 */
		public int getHours() {
			return hours;
		}
		/**
		 * @return the minutes
		 */
		public int getMinutes() {
			return minutes;
		}
	}
}
