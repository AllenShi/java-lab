package net.sjl.alg.api;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import net.sjl.alg.api.Stack;
import net.sjl.alg.impl.ArrayStack;

public class ArrayStackTest {

	@Test
	public void testPushString() {
		Stack<String> stack = new ArrayStack<String>();
		
		System.out.println("stack | .pop = " + stack.pop());
		stack.push("a");
		stack.push("b");
		
		assertEquals(stack.pop(), "b");
		assertEquals(stack.pop(), "a");
		
	}

}
