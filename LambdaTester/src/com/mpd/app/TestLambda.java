package com.mpd.app;

public class TestLambda extends java.lang.Object {
	
	static final int TEST_INT = 42;
	public static String teststring = "Private string from TestLambda class";
	private int testinteger = 123;
	private static String color;
	
	


	public static void main(String[] args) {
		
		
		TestLambda ltester = new TestLambda();
		
		MathOperation addition = (int a, int b) -> a+b;
		MathOperation subtraction = (int a, int b) -> a-b;
		MathOperation multiplication = (int a, int b) -> a*b;
		MathOperation division = (int a, int b) -> a/b;
		
		GreetingService greet = message -> System.out.println("Hello " + message);
		greet.sayMessage("Test");
		
		System.out.println("10 + 5 = " + ltester.operate(10, 5, addition));
		System.out.println("10 - 5 = " + ltester.operate(10, 5, subtraction));
		System.out.println("10 * 5 = " + ltester.operate(10, 5, multiplication));
		System.out.println("10 / 5 = " + ltester.operate(10, 5, division));
	
		ltester.testPrint();
		ltester.testPrintInt();
		
		int num = 4;
		newNumber(num); // method recieves a copy of the variable num = 4
		System.out.println(num); // 4
		
		String name = "webby";
		System.out.println(name);
		System.out.println(speak("graeme"));
		StringBuilder sb = new StringBuilder();
		strspeak(sb);
		System.out.println(sb); // Webby becaus strspeak calls method on parameter and point to the same string builder
		
	}
	
	public static boolean hasFeathers() {return false;}
	
	public static void testClassMethod() {
		System.out.println("The static method in TestLambda");
	}
	
	public void testInstanceMethod() {
		System.out.println("The instance method in TestLambda");
	}
	
	
	public static void strspeak(StringBuilder s) {
		s.append("Webby");
	}
	
	public static String speak(String name) {
		return name = "sparky";
	}
	
	private int sum(int a, int b) {
		return a + b;
	}
	
	public int getSum(int a, int b) {
		return sum(a,b);
	}
	
	
	public void testPrint() {
		System.out.println(teststring);
	}

	public void testPrint(String s) {
		System.out.println(s);
	}
	
	public void testPrint(int i){
		System.out.println(i);
	}
	
	public void testPrintInt() {
		System.out.println(testinteger);
	}
	
	interface MathOperation {
		int operation(int a, int b);
	}
		
	interface GreetingService {
		void sayMessage(String message);
	}
		
	private int operate(int a, int b, MathOperation mathOperation){
		return mathOperation.operation(a, b);
	}
	
	private String saysomething(String message) {
		return message;
	}
	
	public static void newNumber(int num) {
		num = 8;
	}
	
	
	public String getColor() {
		return this.color;
	}

	public void setColor(String clr) {
		this.color = clr;
	}
	
	public void printDescription() {
		System.out.println("Testing this print method");
	}
}
