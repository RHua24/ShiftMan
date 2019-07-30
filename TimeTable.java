package shiftman.server;

/**
 * This class is used to manage and store the working hour for each day in the week. 
 */


public class TimeTable {

	public TimeTable () {

	}


	/**
	 * This enum considers constants of weekdays where field of working hours is stored for each day. So every time when working hours of 
	 * a day is needed this enum is invoked. 
	 */
	enum WeekDays {
		Monday(1),
		Tuesday(2),
		Wednesday(3),
		Thursday(4),
		Friday(5),
		Saturday(6),
		Sunday(7);	
	
	private final int dayNumber;

	private String workHours;
	private WeekDays (int dayNumber) {
		this.dayNumber = dayNumber;
	}
	
	
	public int getDayNumber () {
		return dayNumber;
	}
	/**
	 * This method stores the working hours as a field for a particular weekday.
	 */
	public String setWorkingHours (String startTime, String endTime) {
		try {
		workHours = startTime + "-" + endTime;
		return "";
		}
		catch (RuntimeException e){
			return ("ERROR: given data" + startTime + "or" +endTime + "is invalid");
		}
	}
	
	public String getWorkingHours () {
		return workHours;
	
	}
	
	
	}
}

