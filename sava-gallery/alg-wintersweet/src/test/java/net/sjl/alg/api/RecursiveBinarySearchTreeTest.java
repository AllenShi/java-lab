package net.sjl.alg.api;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;
import net.sjl.alg.api.SymbolTable;
import net.sjl.alg.impl.RecursiveBinarySearchTree;

public class RecursiveBinarySearchTreeTest {
	
	private static SymbolTable<Integer, String> bst;
	
	@BeforeClass
	public static void classSetup() {
		bst = new RecursiveBinarySearchTree<Integer, String>();
	}
	
	@AfterClass
	public static void classTearDown() {
		bst = null;
	}

	@Test
	public void testGetPut() {
		Integer key = 10;
		bst.put(key, "ten");		
		String value = bst.get(key);
		
		Assert.assertEquals("ten", value);
	}

}
