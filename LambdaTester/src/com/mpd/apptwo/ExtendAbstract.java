package com.mpd.apptwo;

public class ExtendAbstract extends TestAbstract {
	void printSomething() {
		System.out.println("Print something");
	}
	
	void printSomethingElse() {
		System.out.println("Print something else");
	}

	public static void main(String[] args) {
		TestAbstract ta = new ExtendAbstract();
		ta.printSomething();
		ta.printSomethingElse();
	}
}
