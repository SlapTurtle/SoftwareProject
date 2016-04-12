package Tests;

import project.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestActivity {
	
	
	@Test
	public void Activity() {
		
	Project HearthStone = new Project();
	Activity create_cards = new Activity("Add more cards");
	
	HearthStone.addActivity(create_cards);
		
		
		}
}