package com.diegoavilap.one;

import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Map<String, Integer> nameMap = new HashMap<>();
		Integer value = nameMap.computeIfAbsent("s4n", String::length);
		System.out.println(value);
		
		int[] total = new int[1];
		Runnable r = () -> total[0]++;
		r.run();
		System.out.println(total[0]);
	}

}
