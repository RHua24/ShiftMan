package shiftman.server;

import java.util.Comparator;
/**
 * This class is a sorting class that is used to sort a list of staff by family name in alphabetical order. 
 */
public class SortByName implements Comparator<Staff> {

	@Override
	public int compare(Staff first, Staff second) {
		return first.getFamilyName().compareTo(second.getFamilyName());
	}
 
	
}
