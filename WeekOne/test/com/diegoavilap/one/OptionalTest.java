package com.diegoavilap.one;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class OptionalTest {
	
	// **Empty()**
	@Test
	public void whenCreatesEmptyOptional_thenCorrect() {
		//Creando un Optional vacio
	    Optional<String> empty = Optional.empty();
	    // isPresent() valida si hay algun valor en un Optional
	    assertFalse(empty.isPresent());
	}
	
	// **of()**
	@Test
	public void givenNonNull_whenCreatesNonNullable_thenCorrect() {
	    String name = "baeldung";
	    Optional.of(name);
	}
	
	@Test
	public void givenNonNull_whenCreatesOptional_thenCorrect() {
	    String name = "s4n";
	    //Creando un Optional con el metodo estatico of
	    Optional<String> opt = Optional.of(name);
	    assertEquals("Optional[s4n]", opt.toString());
	}
		
	@Test
	public void givenNull_whenThrowsErrorOnCreate_thenCorrect() {
	    String name = null;
	    // Creando un Optional con el metodo estatico of
	    // que retorna un NullPointerException
	    // si se pasa como argumento un null
		assertThrows(NullPointerException.class, () -> {
	    	Optional<String> opt = Optional.of(name);
	    });  
	}
	
	
	// **ofNullable()**
	@Test
	public void givenNonNull_whenCreatesNullable_thenCorrect() {
	    String name = "s4n";
	    //Si se espera un valor null como argumento para crear el Optional
	    //se debe utilizar el metodo ofNullable()
	    //de esa forma se evita el NullPointerException
	    Optional<String> opt = Optional.ofNullable(name);
	    assertEquals("Optional[s4n]", opt.toString());
	}
	
	@Test
	public void givenNull_whenCreatesNullable_thenCorrect() {
	    String name = null;
	    //Si se pasa como parametro un null se evita el NullPointerException
	    //A cambio se obitene un Optional vacio
	    Optional<String> opt = Optional.ofNullable(name);
	    assertEquals("Optional.empty", opt.toString());
	}
	
	// **isPresent()**
	// El metodo isPresent() sirve para validar si el Optional tiene un valor o no
	// retorna true solo si el valor no es null
	@Test
	public void givenOptionalNonNull_whenIsPresentWorks_thenCorrect() {
	    Optional<String> opt = Optional.of("s4n");
	    assertTrue(opt.isPresent());
	}
	
	@Test
	public void givenOptionalNull_whenIsPresentWorks_thenCorrect() {
	    Optional<String> opt =  Optional.ofNullable(null);
	    assertFalse(opt.isPresent());
	}
	
	// **ifPresent()**
	// El memtodo ifPresent() valida si el Optional no es null y ejecuta el codigo
	// pasado como argumento
	@Test
	public void givenOptionalNonNull_whenIfPresentWorks_thenCorrect() {
	    Optional<String> opt = Optional.of("s4n");
	    opt.ifPresent(name -> System.out.println(name.length()));
	}
	
	// Si el valor existe y se pasa null como argumento, 
	// ifPresent() lanza un NullPointerException
	@Test
	public void givenOptionalNull_whenIfPresentWorks_thenCorrect() {
	    Optional<String> opt = Optional.of("s4n");
	    assertThrows(NullPointerException.class, () -> {
	    	opt.ifPresent(null);
	    }); 
	}
	
	// **orElse()**
	// Si el Optional tiene algun valor el metodo orElse() retorna ese valor
	// de lo contrario retorna el valor pasado como argumento 
	@Test
	public void whenOrElseWorks_thenCorrect() {
	    String nullName = null;
	    String name = Optional.ofNullable(nullName).orElse("s4n");
	    assertEquals("s4n", name);
	}
	
	// **orElseGet()**
	//Si el Otional tiene algun valor el metodo orElseGet() retorna ese valor
	// de lo contrario devuelve el valor del retorno del closure "Suplier"
	@Test
	public void whenOrElseGetWorks_thenCorrect() {
	    String nullName = null;
	    String name = Optional.ofNullable(nullName).orElseGet(() -> "s4n");
	    assertEquals("s4n", name);
	}
	
	
	//Diferences Between orElse and orElseGet
	public String getMyDefault() {
	    System.out.println("Getting Default Value");
	    return "Default Value";
	}
	
	@Test
	public void whenOrElseGetAndOrElseOverlap_thenCorrect() {
	    String text = null;
	 
	    System.out.println("Using orElseGet:");
	    String defaultText = Optional.ofNullable(text).orElseGet(this::getMyDefault);
	    
	    assertEquals("Default Value", defaultText);
	 
	    System.out.println("Using orElse:");
	    defaultText = Optional.ofNullable(text).orElse(getMyDefault());
	    assertEquals("Default Value", defaultText);
	}

	// La diferencia es que cuando el value existe, el metodo orElseGet()  
	// previene la creacion del objeto que se encuentra como default 
	@Test
	public void whenOrElseGetAndOrElseDiffer_thenCorrect() {
	    String text = "Text present";
	 
	    System.out.println("Using orElseGet:");
	    String defaultText = Optional.ofNullable(text).orElseGet(this::getMyDefault);
	    assertEquals("Text present", defaultText);
	 
	    System.out.println("Using orElse:");
	    defaultText = Optional.ofNullable(text).orElse(getMyDefault());
	    assertEquals("Text present", defaultText);
	}
	
	// ** orElseThrow()**
	// Retorna el value si es dierente a null o cuando este value es null
	// lanza una excepcion del tipo que se le haya pasado como parametro
	// si se le pasa null como parametro, lanza una excepcion del tipo
	// NullPointerException
	@Test
	public void whenOrElseThrowWorks_thenCorrect() {
	    String nullName = null;
	    assertThrows(IllegalArgumentException.class, () -> {
	    	String name = Optional.ofNullable(nullName).orElseThrow(IllegalArgumentException::new);
	    }); 
	}
	
	// **get()**
	// Retorna el valor del Optional
	@Test
	public void givenOptional_whenGetsValue_thenCorrect() {
	    Optional<String> opt = Optional.of("s4n");
	    String name = opt.get();
	 
	    assertEquals("s4n", name);
	}
	
	//Si el valor es null lanza una excepcion del tipo NoSuchElementException
	@Test
	public void givenOptionalWithNull_whenGetThrowsException_thenCorrect() {
	    Optional<String> opt = Optional.ofNullable(null);
	    assertThrows(NoSuchElementException.class, () -> {
	    	String name = opt.get();
	    });
	}
	
	// **filter()**
	// Si el predicado pasado como parametro se cumple, retorna el Optional con su value
	// Si no se cumple el predicado retorna un Optinal vacio
	@Test
	public void whenOptionalFilterWorks_thenCorrect() {
	    Integer year = 2016;
	    Optional<Integer> yearOptional = Optional.of(year);
	    boolean is2016 = yearOptional.filter(y -> y == 2016).isPresent();
	    assertTrue(is2016);
	    boolean is2017 = yearOptional.filter(y -> y == 2017).isPresent();
	    assertFalse(is2017);
	}
	// The filter API is normally used this way to reject wrapped values based on a predefined rule. 
	// You could use it to reject a wrong email format or a password that is not strong enough.

	//---------//
	public boolean priceIsInRange1(Modem modem) {
	    boolean isInRange = false;
	 
	    if (modem != null && modem.getPrice() != null && (modem.getPrice() >= 10 && modem.getPrice() <= 15)) {
	        isInRange = true;
	    }
	    return isInRange;
	}
	
	@Test
	public void whenFiltersWithoutOptional_thenCorrect() {
	    assertTrue(priceIsInRange1(new Modem(10.0)));
	    assertFalse(priceIsInRange1(new Modem(9.9)));
	    assertFalse(priceIsInRange1(new Modem(null)));
	    assertFalse(priceIsInRange1(new Modem(15.5)));
	    assertFalse(priceIsInRange1(null));
	}
	
	// La principal ventaja de usar las propiedades del Optional es que de esta forma
	// se evita el codigo preventivo y la funcion se enfoca en la verdadero objetivo 
	// que es verificar si el precio esta en el rango entre 10 a 15
	public boolean priceIsInRange2(Modem modem2) {
	     return Optional.ofNullable(modem2)
	       .map(Modem::getPrice)
	       //El metodo filter se usa para evitar valores indeseados sin la necesidad de utilizar
	       //condicionales
	       .filter(p -> p >= 10)
	       .filter(p -> p <= 15)
	       .isPresent();
	 }
	
	@Test
	public void whenFiltersWithOptional_thenCorrect() {
	    assertTrue(priceIsInRange2(new Modem(10.0)));
	    assertFalse(priceIsInRange2(new Modem(9.9)));
	    assertFalse(priceIsInRange2(new Modem(null)));
	    assertFalse(priceIsInRange2(new Modem(15.5)));
	    assertFalse(priceIsInRange2(null));
	}
	
	// ** map() **
	// La funcion map() si existe un valor en el optional, aplica la funcion que se le pasa como parametro
	// Si el resultado de esta funcion no es null retorna un optional con el valor obtenido
	// de lo contrario retorna un optional vacio
	
	@Test
	public void givenOptional_whenMapWorks_thenCorrect() {
	    List<String> companyNames = Arrays.asList("paypal", "oracle", "", "microsoft", "", "apple");
	    Optional<List<String>> listOptional = Optional.of(companyNames);
	 
	    int size = listOptional
	      .map(List::size)
	      .orElse(0);
	    assertEquals(6, size);
	}
	
	@Test
	public void givenOptional_whenMapWorksWithFilter_thenCorrect() {
	    String password = " password ";
	    Optional<String> passOpt = Optional.of(password);
	    boolean correctPassword = passOpt.filter(
	      pass -> pass.equals("password")).isPresent();
	    assertFalse(correctPassword);
	 
	    correctPassword = passOpt
	    // map() es utlizado para limpiar la contraseña de espacios al pricipio
	    // y al final utilizando String::trim
	      .map(String::trim)
	      .filter(pass -> pass.equals("password"))
	      .isPresent();
	    assertTrue(correctPassword);
	}
	// ** flatMap() **
	// El metodo flaMap() es similar al metodo Map() pero solo acepta como parametro
	// una funcion que retorna un objeto envuelto como Optional
	@Test
	public void givenOptional_whenFlatMapWorks_thenCorrect2() {
	    Person person = new Person("john", 26);
	    Optional<Person> personOptional = Optional.of(person);
	    
	    // Al usar map() obtengo un Optional y a su vez el metodo getName de la
	    // clase Person retorna un Optional es por esto que en la siguiente linea
	    // se obtiene un String envuelto (wrapper) dos veces como Optional
	    Optional<Optional<String>> nameOptionalWrapper  
	      = personOptional.map(Person::getName);
	    Optional<String> nameOptional  
	      = nameOptionalWrapper.orElseThrow(IllegalArgumentException::new);
	    String name1 = nameOptional.orElse("");
	    assertEquals("john", name1);
	 
	    // El Metodo flatMap() Permite acceder al valor directamente, evitando esa
	    // doble envoltura (wrapper) 
	    String name = personOptional
	      .flatMap(Person::getName)
	      .orElse("");
	    assertEquals("john", name);
	}
	
	@Test
	public void givenOptional_whenFlatMapWorksWithFilter_thenCorrect() {
        Person person = new Person("john", 26);
        person.setPassword("password");
        Optional<Person> personOptional = Optional.of(person);

        String password = personOptional.flatMap(Person::getPassword)
            .filter(cleanPass -> cleanPass.equals("password"))
            .orElseThrow(IllegalArgumentException::new);
        assertEquals("password", password);
    }
	
	// ** Equals() **
	// Retorna true si ambas instancias de Optional estan vacias o 
	// si tienen el mismo value de lo contrario retorna false
	@Test
	public void givenOptionalNull_whenEqualsWorks_thenCorrect() {
		String value = null;
		Optional<String> optional1 = Optional.ofNullable(value);
		Optional<String> optional2 = Optional.ofNullable(value);
		assertTrue(optional1.equals( optional2));
	}
	
	@Test 
	public void givenOptionalNoNull_whenEqualsWorks_thenCorrect() {
		String value = "s4n";
		String value2 = "s4n";
		Optional<String> optional1 = Optional.ofNullable(value);
		Optional<String> optional2 = Optional.ofNullable(value2);
		assertTrue(optional1.equals( optional2));
	}
}
