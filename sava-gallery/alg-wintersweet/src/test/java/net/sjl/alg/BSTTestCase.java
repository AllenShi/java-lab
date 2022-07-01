package net.sjl.alg;

import org.junit.*;
import static org.junit.Assert.*;

public class BSTTestCase {

  private BST<Integer, String> bst = null;

  @Before
  public void populate() {
    bst = new BST<>();
    BST<Integer, String>.Node root = bst.new Node(10, "ten");
    BST<Integer, String>.Node left = bst.new Node(5, "five");
    BST<Integer, String>.Node right = bst.new Node(15, "fifteen");
    BST<Integer, String>.Node lleft = bst.new Node(2, "two");
    BST<Integer, String>.Node lright = bst.new Node(8, "eight");
    BST<Integer, String>.Node rleft = bst.new Node(13, "thirteen");
    BST<Integer, String>.Node rright = bst.new Node(20, "twenty");
    BST<Integer, String>.Node rrright = bst.new Node(25, "twenty-five");

    root.left = left;
    root.right = right;
    left.left = lleft;
    left.right = lright;
    right.left = rleft;
    right.right = rright;
    rright.right = rrright;
    
    bst.root = root;
  }

  @After
  public void drain() {
    bst = null;
  }

  @Test
  public void testBSTHeight() {
    assertEquals(3, bst.height()); 
  }

  @Test
  public void testBSTLevel() {
    assertEquals(2, bst.level(5));
    assertEquals(3, bst.level(20));
  }

  @Test
  public void testToDLLFromLeft() {
    BST<Integer, String>.Node head = bst.toDoublelyLinkedListFromLeft(); 
    head.print();
  }

  @Test
  public void testToDLLFromRight() {
    BST<Integer, String>.Node head = bst.toDoublelyLinkedListFromRight();
    head.print();
  }

}
