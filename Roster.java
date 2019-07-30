package shiftman.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import shiftman.server.TimeTable.WeekDays;


public class Roster {
	private String _shopName;
	private ArrayList <Shift> _shiftList = new ArrayList <Shift> ();
	private ArrayList <Staff> _staffList = new ArrayList <Staff> ();


	public Roster (String shopName){
		_shopName = shopName;
	}


	
	public List<String> understaffedShifts (){	
		List<String> understaffedShifts = new ArrayList<String> ();
		for (Shift shift: _shiftList ) {
			if (shift.understaffedOrNot()) {
				understaffedShifts.add(shift.TimeToString());
			} 
		}
		
		return understaffedShifts;
	}


	public List<String> overstaffedShifts (){
		List<String> overstaffedShifts = new ArrayList<String> ();
		for (Shift shift: _shiftList ) {
			if (shift.overstaffedOrNot()) {
				overstaffedShifts.add(shift.TimeToString());
			}
		}
		return overstaffedShifts;
	}
	


	public List<String> shiftsWithoutManagers() {
		List<String> shiftWithoutManagers = new ArrayList<String> ();
		for (Shift shift: _shiftList) {
			if(shift.hasManagerOrNot()) {			
				shiftWithoutManagers.add(shift.TimeToString());
			}
		}
		return shiftWithoutManagers;
	}



	public String addShift(String dayOfWeek, String startTime, String endTime, String minimumWorkers){
		WorkTime time = new WorkTime(dayOfWeek, startTime, endTime);
		for (Shift shift: _shiftList) {			
			if (time.overlapOrNot(shift)) {				
				return ("ERROR: Time given is overlapped with current shifts");
		
			}
		}
		
		_shiftList.add(new Shift(dayOfWeek, startTime, endTime, minimumWorkers));
		//Use the SortByDay class to sort the shift in the order of time
		Collections.sort(_shiftList, new SortByDay());
		
		return "";
	}


	public List<String> getRosterForDay (String dayOfWeek) {
		List<String> RosterForDay = new ArrayList<String> ();
		if (_shiftList.isEmpty()){
			 RosterForDay.add("");
			 return RosterForDay;
		}
		WeekDays day = WeekDays.valueOf(dayOfWeek);
		RosterForDay.add(_shopName );
		String temp = dayOfWeek + " " + day.getWorkingHours();
		RosterForDay.add(temp);
		for (Shift shift: _shiftList) {
			if (shift.sameDayOfShift(dayOfWeek)) {
				RosterForDay.add(shift.TimeToString() + " " + shift.managerToString() + " " + shift.workerToString());
			}
		}
		return RosterForDay;
	}


	
	
	public String assignStaff(String dayOfWeek, String startTime, String endTime, String givenName, String familyName, boolean isManager) throws NonExistShiftException{
		String fullname = givenName + " " + familyName;
		WorkTime worktime = new WorkTime(dayOfWeek, startTime, endTime);
		
		for(Shift shift:_shiftList) {
			if(shift.equals(worktime)) {
				//If the shift already has the staff as a manger or a worker then return error message
				if (shift.hasWorker(fullname) || shift.hasManager(fullname)) {
					return ("ERROR: The person is already assigned as a worker or manager.");
				} else {
				shift.setStaff(givenName, familyName, isManager);
				return "";
				}
			} else {
				continue;
			}
		}
		throw new NonExistShiftException(dayOfWeek + " " + startTime + " " + endTime);
	
	}
		
	

	public List<String> getRosterForWorker (String workerName){
		List<String> rosterForWorkerList = new ArrayList<String> ();
		ArrayList<Shift> rosterForWorker = new ArrayList<Shift> ();
		boolean nameAdded = false;
		for (Shift shift: _shiftList) {
			
			if (shift.hasWorker(workerName)){
				if (nameAdded == false) {
					rosterForWorkerList.add(shift.getWorker(workerName));
					nameAdded = true;
				}
				rosterForWorker.add(shift);
			
			}
		}

		for (Shift shift: rosterForWorker) {
			rosterForWorkerList.add(shift.TimeToString());
		}
		return rosterForWorkerList;
	}
	
	
	


	public List<String> getShiftsManagedBy (String managerName){
		List<String> shiftsManageBy = new ArrayList<String> ();
		boolean nameAdded = false;
		for(Shift shift: _shiftList) {
			if(shift.hasManager(managerName)) {
				//This boolean sentence is used to ensure that the name is added only once
				if (nameAdded == false) { 
				shiftsManageBy.add(shift.returnManageName ());
				nameAdded = true;
				}
				shiftsManageBy.add(shift.TimeToString());
			}
		}
		return shiftsManageBy;
	}


	
	public List<String> getUnassignedStaff (){
		List<String>_unassignedStaff = new ArrayList<String>();
		ArrayList<Staff> collection = new ArrayList<Staff> ();
		for(Shift shift: _shiftList) {
			//Add all the assigned staff from all the shifts into a single array list. 
			collection.addAll(shift.getAssignedList());
		}
		
		//Loop the staff list and assigned staff list
		for (Staff staff: _staffList) {
			boolean assigned = false;
			for (Staff assignedstaff: collection) {
				//If there is same staff present in two list then this staff is already assigned, then avoid this staff
				if (assignedstaff.toString().equals(staff.toString())) {
					assigned = true;
					break;
				}
			} 
			//If this staff is not equal to any staff in the staff list then this staff have not been assigned.
			if (assigned == false) {
				_unassignedStaff.add(staff.toString());
			}
			
		}
		
		return _unassignedStaff;
	}
		

	public String registerStaff (String givenName, String familyName) {
		
		try {
			if (givenName.equals("")|| familyName.equals("")) {
				throw new IllegalArgumentException();
			}
			if (registerOrNot(givenName, familyName)) {
				return ("ERROR: The staff has already registered");
			}
		
		Staff staff = new Staff(givenName, familyName);
		staff.setWorking(2);
		_staffList.add(staff);
		//Use the SortByName class to sort the staff inside the staffList by Name;
		Collections.sort(_staffList, new SortByName());
		return "";
		}
		catch (IllegalArgumentException e){
			String message = ("ERROR: The givenname or familyname entered is empty.");
			return message;
		}
	}
	
	
	public boolean registerOrNot (String givenName, String familyName) {
	
	String fullname = givenName + " " + familyName;
	for (Staff registeredStaff: _staffList) {
		if(registeredStaff.toString().toLowerCase().equals(fullname.toLowerCase())) {
			return true;
		}
	}
	return false;
	}



	public List<String> getRegisteredStaff(){
		List<String> registeredStaff = new ArrayList<String> ();
		for (Staff staff: _staffList) {
			registeredStaff.add(staff.toString());
		}
		return registeredStaff;		
	}



}


