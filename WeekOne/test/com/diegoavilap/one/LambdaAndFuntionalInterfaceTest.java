package com.diegoavilap.one;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	public byte[] transformArray(short[] array, ShortToByteFunction function) {
        byte[] transformedArray = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            transformedArray[i] = function.applyAsByte(array[i]);
        }
        return transformedArray;
	}
	
	@Test
    public void whenUsingCustomFunctionalInterfaceForPrimitives_thenCanUseItAsLambda() {

        short[] array = { (short) 1, (short) 2, (short) 3 };
        // Invoca a la funcion transformArray y le pasa como argumento el array 
        //  y una funcion lambda que castea a byte cada value del array y lo multiplica por 2
        byte[] transformedArray = transformArray(array, s -> (byte) (s * 2));

        byte[] expectedArray = { (byte) 2, (byte) 4, (byte) 6 };
        assertArrayEquals(expectedArray, transformedArray);
	}

    @Test
    public void whenUsingBiFunction_thenCanUseItToReplaceMapValues() {
        Map<String, Integer> salaries = new HashMap<>();
        salaries.put("Antonio", 700000);
        salaries.put("Beto", 600000);
        salaries.put("Cristian", 900000);
        // replaceAll es una Bifunction o sea una expresion lambda
        // que recibe dos parametros 
        salaries.replaceAll((name, oldValue) -> name.equals("Cristian") ? oldValue : oldValue + 100000);
        
        assertEquals(Integer.valueOf(800000), salaries.get("Antonio"));
        assertEquals(Integer.valueOf(700000), salaries.get("Beto"));
        assertEquals(Integer.valueOf(900000), salaries.get("Cristian"));
    }
    // las Supplier functional interface son expresiones que no reciben parametros
    // comunmente son usados para generacion tardia (lazy generation) de valores. 
    // generate() implementa una Supplir functional interface
    @Test
    public void whenUsingSupplierToGenerateNumbers_thenCanUseItInStreamGenerate() {

        int[] fibs = { 0, 1 };
        Stream<Integer> fibonacci = Stream.generate(() -> {
            int result = fibs[1];
            int fib3 = fibs[0] + fibs[1];
            fibs[0] = fibs[1];
            fibs[1] = fib3;
            return result;
        });
        List<Integer> fibonacci5 = fibonacci.limit(6).collect(Collectors.toList());
        
        assertEquals(Integer.valueOf(1), fibonacci5.get(0));
        assertEquals(Integer.valueOf(1), fibonacci5.get(1));
        assertEquals(Integer.valueOf(2), fibonacci5.get(2));
        assertEquals(Integer.valueOf(3), fibonacci5.get(3));
        assertEquals(Integer.valueOf(5), fibonacci5.get(4));
        assertEquals(Integer.valueOf(8), fibonacci5.get(5));
    }
    
    // 
    @Test
    public void whenUsingConsumerInForEach_thenConsumerExecutesForEachListElement() {
        List<String> names = Arrays.asList("Antonio", "Beto", "Cristian");
        names.forEach(name -> System.out.println("Hello, " + name));
    }
    
    @Test
    public void whenUsingBiConsumerInForEach_thenConsumerExecutesForEachMapElement() {
        Map<String, Integer> ages = new HashMap<>();
        ages.put("Antonio", 15);
        ages.put("Beto", 14);
        ages.put("Cristian", 20);

        ages.forEach((name, age) -> System.out.println(name + " is " + age + " years old"));
    }
    
    @Test
    public void whenUsingPredicateInFilter_thenListValuesAreFilteredOut() {
        List<String> names = Arrays.asList("Angela", "Aaron", "Bob", "Claire", "David");

        List<String> namesWithA = names.stream()
            .filter(name -> name.startsWith("A"))
            .collect(Collectors.toList());

        assertTrue(namesWithA.contains("Angela"));
        assertTrue(namesWithA.contains("Aaron"));
        assertEquals(2, namesWithA.size());
        
    }
    
    @Test
    public void whenUsingUnaryOperatorWithReplaceAll_thenAllValuesInTheListAreReplaced() {
        List<String> names = Arrays.asList("antonio", "beto", "cristian");

        names.replaceAll(String::toUpperCase);

        assertEquals("ANTONIO", names.get(0));
        assertEquals("BETO", names.get(1));
        assertEquals("CRISTIAN", names.get(2));
    }
    
    @Test
    public void whenUsingBinaryOperatorWithStreamReduce_thenResultIsSumOfValues() {

        List<Integer> values = Arrays.asList(3, 5, 8, 9, 12);

        int sum = values.stream()
        	//El primer parametro es el acumulador 
            .reduce(0, (i1, i2) -> i1 + i2);

        assertEquals(37, sum);

    }
	
}
