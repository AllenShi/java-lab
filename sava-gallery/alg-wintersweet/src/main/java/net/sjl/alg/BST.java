package net.sjl.alg;

public class BST<K extends Comparable<K>, V> {
  protected Node root = null;
  
  public class Node {
    Node left;
    Node right;
    K key;
    V value;
 
    public Node(K key, V value) {
      this.key = key;
      this.value = value;
    }

    public void print() {
      Node node = this;
      do {
        System.out.println("Key := " + node.key + ", Value := " + node.value);
        node = node.right;
      } while (node != null);
    }

   public String toString() {
     return "Key := " + key + ", Value := " + value;
   }
    
  }

  public void put(K key, V value) {
  }

  public V get(K key) {
    return null;
  }

  public int height() {
    return height(root);
  }

  private int height(Node node) {
    if(node == null) return -1;

    int lheight = height(node.left);
    int rheight = height(node.right);
    return lheight > rheight ? lheight + 1 : rheight + 1;
  }

  public int level() {
    return 1;
  }
  
  private int level(Node node, int level, K key) { 
    if(node == null) return 0;

    if(node.key == key) return level;

    int dlevel = level(node.left, level + 1, key);
    if(dlevel != 0) return dlevel;

    return level(node.right, level + 1, key);

  }

  public int level(K key) {
    return level(root, 1, key);
  }

  public Node toDoublelyLinkedListFromRight() {
    if(root != null) {
      Holder<Node> headHolder = new Holder<>();
      toDoublelyLinkedList(root, headHolder); 
      return headHolder.getTarget();
    }
    return null;
  } 

  private void toDoublelyLinkedList(Node cur, Holder<Node> headHolder) {
    if(cur == null) return;
    toDoublelyLinkedList(cur.right, headHolder); 
     
    Node head = headHolder.getTarget();
    System.out.println("before assignment, head is " + head);
    cur.right = head;
    if(head != null) {
      head.left = cur;
    } 
 
    head = cur;
    System.out.println("after assignment, head is " + head);
    headHolder.setTarget(head); 
    
    toDoublelyLinkedList(cur.left, headHolder); 
  } 

  public Node toDoublelyLinkedListFromLeft() {
    if(root != null) {
      Holder<Node> headHolder = new Holder<>();
      toDoublelyLinkedList(root, headHolder); 
      return headHolder.getTarget();
    }
    return null;
  } 

  private void toDoublelyLinkedList(Node cur, Holder<Node> prevHolder, Holder<Node> headHolder) {
    if(cur == null) return;
    toDoublelyLinkedList(cur.left, prevHolder, headHolder); 
     
    Node prev = prevHolder.getTarget();
    Node head = headHolder.getTarget();
    System.out.println("before assignment, head is " + head);
    cur.left = prev;
    if(prev != null) {
      prev.right = cur;
    } else {
      head = cur; 
      headHolder.setTarget(head); 
    }
 
    prev = cur;
    prevHolder.setTarget(prev);
    toDoublelyLinkedList(cur.right, prevHolder, headHolder); 
  } 

  public static void main(String[] args) {
    BST<Integer, String> bst = new BST<>(); 
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
    System.out.println("The tree height is " + bst.height());
    System.out.println("The node key 5's level is " + bst.level(5));
    System.out.println("The node key 20's level is " + bst.level(20));
    // BST<Integer, String>.Node head = bst.toDoublelyLinkedListFromLeft();
    BST<Integer, String>.Node head = bst.toDoublelyLinkedListFromRight();
    head.print();
  }
}


