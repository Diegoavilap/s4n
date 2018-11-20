package com.diegoavilap.one;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class OptionalTest {
	
	
	@Test
	public void optionalVacio() {
		//Creando un Optional vacio
	    Optional<String> empty = Optional.empty();
	    // isPresent() valida si hay algun valor en un Optional
	    assertFalse(empty.isPresent());
	}
	
	
}
