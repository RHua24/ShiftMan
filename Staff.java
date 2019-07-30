package shiftman.server;


public class Staff {
	private String _givenName;
	private String _familyName;
	private boolean _isWorking;
	

	public Staff(String givenName, String familyName){
		this._givenName = givenName;
		this._familyName = familyName;
		this. _isWorking = false;
	}
	
	
	//This method is used to set working status of the current worker, either assigned or not assigned
	public void setWorking (int status) {
		if(status == 1) {
			_isWorking = true;
		}
		else {
			_isWorking = false;
		}
	}
	
	public boolean isWorking ( ){
		if (_isWorking) {
			return true;
		}
		else {
			return false;
		}
	}

	public String getGivenName (){
		return _givenName;
	}
	
	
	
	public String getFamilyName (){
		return _familyName;
	}

	/**
	 * Test whether the staff is the same staff as the input name;
	 */
	public boolean isSameStaff (String givenName, String familyName) {
		if (_givenName == givenName && _familyName == familyName) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String toString(){
		return this._givenName + " " + this._familyName; 
	}
	
	
	
	public String toStringFamilyNameFirst (){
		return this._familyName + ", " + this._givenName;
	}


	
}
