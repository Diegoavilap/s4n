package com.diegoavilap.one;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Person {
	public String name;
    public int age;
    private String password;
 
    
    public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
    

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toString(){
		return name;
	}


	public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
 
    public Optional<Integer> getAge() {
        return Optional.ofNullable(age);
    }
 
    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }
    
    static List<Person> persons() {
    	   return Arrays.asList(
    	        new Person("Max", 18),
    	        new Person("Peter", 23),
    	        new Person("Pamela", 23),
    	        new Person("David", 12));
    
    }
    
}
