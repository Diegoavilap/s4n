package com.diegoavilap.one;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.junit.Test;

public class LambdaAndFuntionalInterfaceTest {
	
	@Test 
	public void whenPassingLambdaToComputeIfAbsent_thenTheValueGetsComputed() {
		// Map de key tipo String and value tipo Integer
		Map<String, Integer> nameMap = new HashMap<>();
		// ComputeIfAbsent() busca la key pasada como primer parametro y devuelve su valor
		// si no encuentra la key en el map, calcula un valor para esta key de acuerdo a la funcion 
		// pasada como segundo parametro, que en este caso es un lambda
		Integer value = nameMap.computeIfAbsent("s4n", s -> s.length());
		assertEquals(3, value.intValue());
	}
	@Test 
	public void whenPassingMethodReferenceToComputeIfAbsent_thenTheValueGetsComputed() {
		Map<String, Integer> nameMap = new HashMap<>();
		// En este caso el segundo parametro es una referencia de un metodo 
		Integer value = nameMap.computeIfAbsent("s4n", String::length);
		assertEquals(3, value.intValue());
	}
	@Test
	public void whenComposingTwoFunctions_thenFunctionsExecuteSequentially(){
		//Function es una interfaz funcional que acepta dos parametros
		//el primero determina el tipo de dato que acepta
		//y el segundo determina el valor que va a retornar
		Function<Integer, String> intToString = Object::toString;
		Function<String, String> quote = s -> "'" + s + "'";
		// compose() permite combinar varias funciones y ejecutarlas de manera secuencial
		Function<Integer, String> quoteIntToString = quote.compose(intToString);
		 // apply() ejecuta la funcion con el parametro proporcionado
		assertEquals("'5'", quoteIntToString.apply(5));
	}
}
