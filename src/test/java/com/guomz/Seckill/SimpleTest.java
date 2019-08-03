package com.guomz.Seckill;

import org.junit.Test;

public class SimpleTest extends SeckillApplicationTests{
	
	@Test
	public void test()
	{
		new T2();
	}

}

class T1{
	static int s1=1;
	static 
	{
		System.out.println("static block t1: "+T2.s2);
	}
	
	T1()
	{
		System.out.println("T1(): "+s1);
	}
}

class T2 extends T1{
	static int s2=2;
	static 
	{
		System.out.println("static block t2: "+T2.s2);
	}
	
	T2()
	{
		System.out.println("T2(): "+s2);
	}
}
