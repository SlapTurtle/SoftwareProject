package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.*;

import Classes.*;

public class Test123 {
	
	@Test
	public static void main(String[] args) {
		mock(Test123.class);
		int hejmock = 3;
		when(Test123.hej()).thenReturn(hejmock);
		assertTrue(Test123.class == Test123.class);
	}
	
	private static int hej(){
		return 1;
	}
}
