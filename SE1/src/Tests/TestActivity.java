package Tests;

import project.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestActivity extends TestBasis{
	
	
	@Test
	public void Activity() throws IllegalOperationException {
		
		// test that getName works:
		
		// ********* METODEN FINDES IKKE String n = a1.getName();
		
		// ********* METODEN FINDES IKKE assertTrue(n=="01xxx");
		
		//test that setEndWeek works
		
		Week ew = new Week(16, 2016);
		
		a1.setEndWeek(ew);
		
		assertTrue(a1.getEndWeek() == ew);
		
		// test for assign employee, and that he can't be assigned twice, and that you can't assign nothing
		
		assertTrue(a1.assignEmployee(e1));
		assertFalse(a1.assignEmployee(e1));
		assertFalse(a1.assignEmployee(null));
		
		// test assign to project. and same can't be assigned twice, and that you can't assign nothing
		
		assertTrue(a1.assignProject(p1));
		assertFalse(a1.assignProject(p1));
		assertFalse(a1.assignProject(null));
		
		// test that get employee hours work
		
		e1.setHours(a1, 5.5, week1, 3);
		double test = a1.getEmployeeHours(e1, week1);
		
		assertTrue(test == 5.5);
		
		
		}
}
