package Tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.*;


public class Test123 {
	
	@Test
	public static void main(String[] args) {
		mock(Test123.class);
		assertTrue(Test123.class == Test123.class);
	}
}
