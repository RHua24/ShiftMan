package shiftman.server;

import java.util.ArrayList;
import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

/**
 * This class consider one single shift. This includes the time, worker and manager of each shift (worker).
 */

public class Shift {
	private String _dayOfWeek;
	private String _startTime;
	private String _endTime;
	private int _minimumWorkers;
	private ArrayList<Staff> _workerList;
	private ArrayList<Staff> _assignedList = new ArrayList<Staff> ();
	private Staff _manager;
	private boolean _hasManager;
	

	public Shift(String dayOfWeek, String startTime, String endTime, String minimumWorkers){
		_dayOfWeek = dayOfWeek;
		_startTime = startTime;
		_endTime = endTime;
		_minimumWorkers = Integer.parseInt(minimumWorkers);
		_workerList = new ArrayList<Staff> ();
		_hasManager = false;
	}

	public String getDayOfWeek () {
		return _dayOfWeek;
	}

	public String getStartTime (){
		return _startTime;
	}

	public String getEndTime (){
		return _endTime;
	}

	public int getMinimumWorkers (){
		return _minimumWorkers;
	}

	//This method is used to determine whether the input time is the same time as the current shift
	public boolean equals(WorkTime worktime){
		WorkTime time = new WorkTime(_dayOfWeek, _startTime, _endTime);
		if (time.time().equals(worktime.time())) {
			return true;
		}
		return false;
	}


	//This method is used to determine whether the input day is the same day as the current shift
	public boolean sameDayOfShift (String dayOfWeek) {
		if (dayOfWeek == _dayOfWeek) {
			return true;
		}
		else {
			return false;
		}
	}


	/**
	 * Store the information of staff into this shift either as a worker or manager. 
	 */
	public void setStaff(String givenName, String familyName, boolean isManager) {
		Staff staff = new Staff(givenName, familyName);
		if(isManager == true) {
			_manager = staff;
			_hasManager = true;
			//Change the working status of the staff into "working"
			_manager.setWorking(1);
			_assignedList.add(staff);
		}
		else {
			staff.setWorking(1);
			_workerList.add(staff);
			//Use the sort class to sort the worker by family name
			Collections.sort(_workerList, new SortByName());
			_assignedList.add(staff);
		
		}
	
		
	}



	public ArrayList<Staff> getAssignedList () {
		return _assignedList;
	}
	
	
	//This method is used to determine whether this shift's manager is the same as the input manager 
	public boolean hasManager (String managerName) {
		if (_hasManager == false) {
			return false;
		}
		if(managerName.equals(_manager.toString())) {
			return true;
		}
		return false;
	}
	
	
	public String returnManageName (){
		return _manager.toStringFamilyNameFirst();
	}
	
	
	//Determine whether this shift has a manager
	public boolean hasManagerOrNot (){
		if(_hasManager){
			return false;
		}
		else {
			return true;
		}
	}
  
	 
	/**
	 * This method is used to detect whether this shift contains the required worker. 
	 */
	public boolean hasWorker (String workerName) {
		if (_workerList.isEmpty()) {
			return false;
			
		}
		for (Staff worker: _workerList){
			if (worker.toString().equals(workerName)) {
				
				return true;
			}
		} return false;
	}
	
	
	public String getWorker (String workerName) {
		for (Staff worker: _workerList) {
			if (worker.toString().equals(workerName)) {
				return worker.toStringFamilyNameFirst();
			}
		}
		return "";
	}
	
	/**
	 * This method is used to detect whether this shift contains enough workers. 
	 */
	public boolean understaffedOrNot (){	
			if (_workerList.size() < _minimumWorkers) {
				return true;
			} else {
				return false;
			}
		
			
		}
	
	/**
	 * This method is used to detect whether this shift contains too much workers.
	 */
	public boolean overstaffedOrNot (){
		if (_workerList.size() > _minimumWorkers) {
			return true;
		} else {
			return false;
		}
	}





	public String TimeToString() {
		return (_dayOfWeek + "[" + _startTime + "-" + _endTime + "]" );
	}

	
	public String managerToString () {
		if (_hasManager == false) {
			return ("[No manager assigned]");
		}
		return ("Manager:" + _manager.getFamilyName() + ", " + _manager.getGivenName());
	}
	
	
	public String workerToString () {
		List<String> worker = new ArrayList<String> ();
		
		if (_workerList.isEmpty()) {
			return ("[No workers assigned]");
			
		} else {
			for (Staff staff: _workerList) {
				String singleWorker = staff.toString() ;	
				worker.add(singleWorker);
			}
			String workerInString = worker.toString();
			
			return workerInString;
	
		}

	}
	


}
