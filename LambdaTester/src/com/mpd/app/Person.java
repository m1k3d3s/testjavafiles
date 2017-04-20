package com.mpd.app;

import java.time.LocalDate;

public class Person {
	
	public enum MaleFemale {
		MALE(1),
		FEMALE(2);
		
		private final int value;
		
		private MaleFemale(int code) {
			this.value = code;
		}
		
		private int getValue() {
			return this.value;
		}
	}
}
