package com.mpd.apptwo;
import java.util.function.Predicate;

import com.mpd.app.CheckPerson;
import com.mpd.app.Person;
import com.mpd.app.TestLambda;

public class TestExtender extends TestLambda implements CheckPerson {

	public static void main (String[] args) {
		TestLambda tl = new TestLambda();
		try {
			tl.testPrint("TestSTring"); // grabs protected String from TestLambda class
			tl.testPrint(123); // grabs protected in from TestLambda class
			tl.testPrintInt(); // grabs protected in from TestLambda class
			tl.testPrint();
			System.out.println(tl.getSum(7, 8));
		} finally {
			System.out.println("Hello"); // print a local hello from TestExtender
		}
		
		Predicate<String> predicateString = s -> {
			return s.equals("Hello");
		};
		/*System.out.println(predicateString.test("Hllo"));
		System.out.println(predicateString.test("Hllo"));
		System.out.println(predicateString.test("Hllo"));
		System.out.println(predicateString.test("Hllo"));
		System.out.println(predicateString.test("Hello"));*/
		
		Predicate<String> predicateAnd=predicateString.and(s->s.length()>4);
		System.out.println(predicateAnd.test("Hel"));
		
		
		Predicate<Integer> predicateInt = i -> {
			return i > 0;
		};
		
		/*System.out.println(predicateInt.test(1));
		System.out.println(predicateInt.test(-49));
		System.out.println(predicateInt.test(12));
		System.out.println(predicateInt.test(-59));
		System.out.println(predicateInt.test(0));
		System.out.println(predicateInt.test(7));*/
		System.out.print("test");
		System.out.print(testInit(5));
		System.out.print("\n");
		
		
		
		System.out.print("\n");
		int result = +1;
		System.out.print(result); // 1
		System.out.print("\n");
		result--;
		System.out.print(result); // 0
		System.out.print("\n");
		result++;
		System.out.print(result); // 1
		System.out.print("\n");
		result = -result;
		System.out.print(result); // -1
		System.out.print("\n");
		boolean success = false;
		System.out.print(success); // false
		System.out.print("\n");
		System.out.print(!success); // true
		System.out.print("\n");
		
		
		int x = 0;
		long y = 0;
		for(y = 0,  x = 4; x<10 && y < 10; x++ ,y++) {
			System.out.println(y + " ");
		}
		System.out.println(x);
	}
	
	public static int testInit(int i) {
		int blah = i;
		byte b = 100;
		int hexVal = 0x1a; //26 in hex
		int binVal = 0b11011;
		int withUnder = 1_00_00;
		return withUnder;
		
	}

	@Override
	public boolean test(Object t) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void test(Person p) {
		Predicate<Integer> male = num -> num == 1;
		Predicate<Integer> female = num -> num == 2;
	}
}
