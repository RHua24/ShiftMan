package shiftman.server;

import shiftman.server.TimeTable.WeekDays;

/**
 * This class is used as a manage of the working time for each shift and also used to detect the time overlapping of a shift. 
 */

public class WorkTime {
	private String _startTime;
	private String _endTime;
	private String _dayOfWeek;

	public WorkTime(String dayOfWeek, String startTime, String endTime) {
		_dayOfWeek = dayOfWeek;
		_startTime = startTime;
		_endTime = endTime;
	}
	
	public String toString (){
		return "[" + _startTime + "-" + _endTime + "]";
	}
	
	
	public String time(){
		return _dayOfWeek + _startTime + _endTime;
	}
	
	/**
	 * This method is used to check whether the time of added shift is overlapped with current shifts. 
	 */
	public boolean overlapOrNot (Shift shift) {
		String dayOfWeek = shift.getDayOfWeek();
		String StartTime = shift.getStartTime();
		String EndTime = shift.getEndTime();
		
		//extract out the hours and minutes of each time and convert them into integer for comparison. 
		int existStartTime = Integer.parseInt(StartTime.substring(0,2));
		int existEndTime = Integer.parseInt(EndTime.substring(0,2));
		int newStartTime = Integer.parseInt(_startTime.substring(0,2));
		int newEndTime = Integer.parseInt(_endTime.substring(0,2));	
		
		int existStartMinutes = Integer.parseInt(StartTime.substring(3,5));
		int existEndMinutes = Integer.parseInt(EndTime.substring(3,5));
		int newStartMinutes = Integer.parseInt(_startTime.substring(3,5));
		int newEndMinutes = Integer.parseInt(_endTime.substring(3,5));	
		
		//Use enum inside the TimeTable class to get the dayNumber that used to compare.
		WeekDays existday = WeekDays.valueOf(dayOfWeek);
		int existdayNumber = existday.getDayNumber();
		WeekDays day = WeekDays.valueOf(_dayOfWeek);
		int dayNumber = day.getDayNumber();

		//All the possible situations where the time is not overlapped
		if (existdayNumber != dayNumber) {
			return false;
		} 
		
		
		if (existStartTime < newStartTime && existEndTime < newStartTime) {		
			return false;
		}
		
		if (newStartTime < existStartTime && newEndTime < existStartTime) {
		
			return false;
		
		}
		
		if(existStartTime == newEndTime && existStartMinutes > newEndMinutes) {
			return false;
		}
		
		if(existEndTime == newStartTime && newStartMinutes > existEndMinutes) {
			return false;
		}
		
		return true;
		
	}
	
}




