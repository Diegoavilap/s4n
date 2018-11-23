package com.diegoavilap.one;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamTest {
	
		@Test
		public void when_streamWorks_thenCorrect(){
			String firstValue = Arrays.asList("a1", "a2", "a3")
		    .stream()
		    .findFirst()
		    .get(); 
			
			assertEquals("a1", firstValue);
		}
		
		@Test
		public void givenList_when_ofWorks_thenCorrect(){
			String firstValue = Stream.of("a1", "a2", "a3")
		    .findFirst()
		    .get(); 
			
			assertEquals("a1", firstValue);
		}
		
		@Test
	    public void givenAnArrayOfIntegers_whenAvgIsCalled_thenCorrectAvgIsReturned() {
	        int [] datos = {20, 98, 12, 7, 35};
			double avg = IntStream.of(datos).average().getAsDouble();

	        assertTrue(34.4 == avg);
		}
		
		@Test
	    public void givenAnArray_whenSumIsCalled_thenTheCorrectSumIsReturned() {
			// mapToInt transforma el stream en un IntStrem el cual
			// cuenta con metodos como sum()
	        int sum = Stream.of(2, 5).mapToInt(i -> i).sum();
	        assertEquals(7, sum);
		}
		
		public static List<String> allToUpperCase(List<String> words) {
		    return words.stream()
		                .map(string -> string.toUpperCase())
		                .collect(Collectors.toList());
		}
		
		@Test
		public void givenmultipleWords_thenCorrectUppercaseIsReturned() {
		    List<String> input = Arrays.asList("alberto", "bienvenido", "hello");
		    List<String> result = allToUpperCase(input);
		    assertEquals(Arrays.asList("ALBERTO", "BIENVENIDO", "HELLO"), result);
		}
		
		public static List<Person> persons() {
		    return Arrays.asList(
	    	        new Person("Max", 18),
	    	        new Person("Peter", 23),
	    	        new Person("Pamela", 23),
	    	        new Person("David", 12));
		}
				
		@Test
		public void givenListOfPersons_thenCorrectFilteredStartsWithPIsReturned() {
			List<Person> persons = persons();
			List<Person> filtered =
				    persons
				        .stream()
				        .filter(p -> p.name.startsWith("P"))
				        .collect(Collectors.toList());

			String expectResult = "[Peter, Pamela]";
			assertEquals(expectResult, filtered.toString());
		}
		
		@Test
		public void givenLisOfPersons_thenCorrectJoiningStringIsReturned() {
			List<Person> persons = persons();
			String phrase = persons
				    .stream()
				    .filter(p -> p.age >= 18)
				    .map(p -> p.name)
				    .collect(Collectors.joining(" y ", "En Colombia ", " son mayores de edad."));
			String phraseResult = "En Colombia Max y Peter y Pamela son mayores de edad.";
			assertEquals(phraseResult, phrase);
		}
		
		@Test
		public void givenListOfPerson_thenAvgIsReturned() {
			List<Person> persons = persons();
			
			Double averageAge = persons.stream().collect(Collectors.averagingInt(p -> p.age));
			assertEquals(Double.valueOf(19.0), averageAge);
		}
		
		@Test
		public void givenListOfPerson_whenCreateACustomCollectorthenCorrect() {
			Collector<Person, StringJoiner, String> personNameCollector =
				    Collector.of(
				        () -> new StringJoiner(" | "),          // supplier
				        (j, p) -> j.add(p.name.toUpperCase()),  // accumulator
				        (j1, j2) -> j1.merge(j2),               // combiner
				        StringJoiner::toString);                // finisher
			List<Person> persons = persons();
			String names = persons
			    .stream()
			    .collect(personNameCollector);

			String expectNames = "MAX | PETER | PAMELA | DAVID";
			assertEquals(expectNames, names);
		}
		@Test
		public void given() {
			List<Person> persons = persons();
			Integer ageSum = persons
				    .stream()
				    .reduce(0, (sum, p) -> sum += p.age, (sum1, sum2) -> sum1 + sum2);

			assertEquals(Integer.valueOf(76), ageSum);
				
		}
}

