package shiftman.server;

import java.util.Comparator;

import shiftman.server.TimeTable.WeekDays;

/**
 * This class is used as a sorting class that sort the shift in order of days and time by implementing Comparator interface.
 */

public class SortByDay implements Comparator<Shift> {
	boolean before = false;
	boolean after = false;

	/**
	 * This method is used to compare the shift firstly in weekday, then starting hours, and lastly starting minutes;
	 */
	public int compare(Shift firstShift, Shift secondShift) {
		String firstDay = firstShift.getDayOfWeek();
		String secondDay = secondShift.getDayOfWeek();
		WeekDays firstWork = WeekDays.valueOf(firstDay);
		WeekDays secondWork = WeekDays.valueOf(secondDay);
		int firstTime = Integer.parseInt(firstShift.getStartTime().substring(0,2));
		int secondTime = Integer.parseInt(secondShift.getStartTime().substring(0,2));
		int firstMinutes = Integer.parseInt(firstShift.getStartTime().substring(3,5));
		int secondMinutes = Integer.parseInt(secondShift.getStartTime().substring(3,5));
		
		//If the two shift are at the same time then compare their shift hours.
		if (firstWork.getDayNumber() - secondWork.getDayNumber() == 0) {
			//If the two shift hours are the same then compare their shift minutes. 
			if (firstTime == secondTime) {
				return (firstMinutes - secondMinutes);
			}
			return (firstTime - secondTime);
		}
		else {
			return firstWork.getDayNumber() - secondWork.getDayNumber();
		}
	}
}
