package com.mpd.app;

public class TestLargerLambda extends TestLambda {
	
	public static String teststring = "test string";

	TestLargerLambda(){System.out.println("Test");}
	
	public static void testClassMethod() {
		System.out.println("The static method in TestLargerLambda");
	}
	
	public void testInstanceMethod() {
		System.out.println("The instance method in TestLargerLambda");
	}
	
	public void printDescription() {
		super.printDescription();
		System.out.println("Adding to what super class is printing");
	}
	
	public static boolean hasFeathers() {return true;}
	
	public static void main(String[] args){
		TestLargerLambda tll = new TestLargerLambda();
		TestLambda testl = new TestLambda();
		tll.testPrint(9);
		testClassMethod();
		tll.testInstanceMethod();
		testl.testInstanceMethod();
		testl.testClassMethod();
		System.out.println(tll.hasFeathers());
		tll.setColor("Blue");
		System.out.println(tll.getColor());
		tll.printDescription();
		System.out.println(testl.teststring);
		System.out.println(tll.teststring);
		
	}
	
}
