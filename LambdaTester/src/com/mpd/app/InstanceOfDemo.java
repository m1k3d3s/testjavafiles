package com.mpd.app;

public class InstanceOfDemo {
	public static void main(String[] args) {
		Parent obj1 = new Parent();
		Parent obj2 = new Child();
		
		System.out.println("obj instanceof Parent: " + (obj1 instanceof Parent));
		System.out.println("obj instanceof Child: " + (obj2 instanceof Child));
		System.out.println("obj2 instanceof Parent: " + (obj2 instanceof Parent));
		System.out.println("obj1 instanceof Child: " + (obj1 instanceof Child));
		System.out.println("obj instanceof MyInterface: " + (obj1 instanceof MyInterface));
		System.out.println("null instanceof Object: " + (null instanceof Object));
	}
	
}

class Parent {}
class Child extends Parent implements MyInterface {}
