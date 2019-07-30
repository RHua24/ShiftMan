package shiftman.server;


import java.util.List;

import shiftman.server.TimeTable.WeekDays;

public class ShiftManServer implements ShiftMan {

	TimeTable timetable = new TimeTable();
	Roster _roster;


	public String newRoster(String shopName) {
		_roster = new Roster(shopName);
		return "";
	}


	public String setWorkingHours(String dayOfWeek, String startTime, String endTime) {

		if (dayOfWeek == null || startTime.equals("") || endTime == null) {
			return ("ERROR: given data is invalid");
		}
		try {
			//Use enum inside the TimeTable class to get the dayNumber.
			WeekDays day = WeekDays.valueOf(dayOfWeek);
			return day.setWorkingHours(startTime, endTime);	
		}
		catch (IllegalArgumentException e){
			return ("ERROR: given" + dayOfWeek +  startTime + endTime + "is invalid");
		}
	}


	public String addShift(String dayOfWeek, String startTime, String endTime, String minimumWorkers) {
		try{

			return _roster.addShift(dayOfWeek, startTime, endTime, minimumWorkers);
		}
		catch(IllegalArgumentException e){
			return ("ERROR: given data is invalid");
		}
	}


	public String registerStaff(String givenname, String familyName) {

		return _roster.registerStaff (givenname, familyName);

	}



	public String assignStaff(String dayOfWeek, String startTime, String endTime, String givenName, String familyName,
			boolean isManager) {	
		if (_roster.registerOrNot(givenName, familyName) == false) {
			return ("ERROR: The staff is not registered");
		}

		try {
			return _roster.assignStaff(dayOfWeek, startTime, endTime, givenName, familyName, isManager);
		} catch (NonExistShiftException e) {
			return e.toString();
		}
	}



	public List<String> getRegisteredStaff() {
		return _roster.getRegisteredStaff();


	}


	public List<String> getUnassignedStaff() {
		return _roster.getUnassignedStaff();

	}



	public List<String> shiftsWithoutManagers() {
		return _roster.shiftsWithoutManagers();

	}


	public List<String> understaffedShifts() {
		return _roster.understaffedShifts ();

	}


	public List<String> overstaffedShifts() {
		return _roster.overstaffedShifts ();

	}


	public List<String> getRosterForDay(String dayOfWeek) {
		return _roster.getRosterForDay (dayOfWeek);

	}


	public List<String> getRosterForWorker(String workerName) {
		return _roster.getRosterForWorker (workerName);

	}


	public List<String> getShiftsManagedBy(String managerName) {
		return _roster. getShiftsManagedBy (managerName);

	}

	@Override
	public String reportRosterIssues() {

		return null;
	}

	@Override
	public String displayRoster() {
		// TODO Auto-generated method stub
		return null;
	}

}
