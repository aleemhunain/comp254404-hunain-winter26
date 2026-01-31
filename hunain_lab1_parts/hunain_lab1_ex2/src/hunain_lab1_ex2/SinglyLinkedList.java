/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package hunain_lab1_ex2;
import java.util.ArrayList;
/**
 * A basic singly linked list implementation.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class SinglyLinkedList<E> implements Cloneable {
  //---------------- nested Node class ----------------
  /**
   * Node of a singly linked list, which stores a reference to its
   * element and to the subsequent node in the list (or null if this
   * is the last node).
   */
  private static class Node<E> {

    /** The element stored at this node */
    private E element;            // reference to the element stored at this node

    /** A reference to the subsequent node in the list */
    private Node<E> next;         // reference to the subsequent node in the list

    /**
     * Creates a node with the given element and next node.
     *
     * @param e  the element to be stored
     * @param n  reference to a node that should follow the new node
     */
    public Node(E e, Node<E> n) {
      element = e;
      next = n;
    }

    // Accessor methods
    /**
     * Returns the element stored at the node.
     * @return the element stored at the node
     */
    public E getElement() { return element; }

    /**
     * Returns the node that follows this one (or null if no such node).
     * @return the following node
     */
    public Node<E> getNext() { return next; }

    // Modifier methods
    /**
     * Sets the node's next reference to point to Node n.
     * @param n    the node that should follow this one
     */
    public void setNext(Node<E> n) { next = n; }
    
  } //----------- end of nested Node class -----------

  // instance variables of the SinglyLinkedList
  /** The head node of the list */
  private Node<E> head = null;               // head node of the list (or null if empty)

  /** The last node of the list */
  private Node<E> tail = null;               // last node of the list (or null if empty)

  /** Number of nodes in the list */
  private int size = 0;                      // number of nodes in the list

  /** Constructs an initially empty list. */
  public SinglyLinkedList() { }              // constructs an initially empty list

  // access methods
  /**
   * Returns the number of elements in the linked list.
   * @return number of elements in the linked list
   */
  public int size() { return size; }

  /**
   * Tests whether the linked list is empty.
   * @return true if the linked list is empty, false otherwise
   */
  public boolean isEmpty() { return size == 0; }

  /**
   * Returns (but does not remove) the first element of the list
   * @return element at the front of the list (or null if empty)
   */
  public E first() {             // returns (but does not remove) the first element
    if (isEmpty()) return null;
    return head.getElement();
  }

  /**
   * Returns (but does not remove) the last element of the list.
   * @return element at the end of the list (or null if empty)
   */
  public E last() {              // returns (but does not remove) the last element
    if (isEmpty()) return null;
    return tail.getElement();
  }

  // update methods
  /**
   * Adds an element to the front of the list.
   * @param e  the new element to add
   */
  public void addFirst(E e) {                // adds element e to the front of the list
    head = new Node<>(e, head);              // create and link a new node
    if (size == 0)
      tail = head;                           // special case: new node becomes tail also
    size++;
  }

  /**
   * Adds an element to the end of the list.
   * @param e  the new element to add
   */
  public void addLast(E e) {                 // adds element e to the end of the list
    Node<E> newest = new Node<>(e, null);    // node will eventually be the tail
    if (isEmpty())
      head = newest;                         // special case: previously empty list
    else
      tail.setNext(newest);                  // new node after existing tail
    tail = newest;                           // new node becomes the tail
    size++;
  }

  /**
   * Removes and returns the first element of the list.
   * @return the removed element (or null if empty)
   */
  public E removeFirst() {                   // removes and returns the first element
    if (isEmpty()) return null;              // nothing to remove
    E answer = head.getElement();
    head = head.getNext();                   // will become null if list had only one node
    size--;
    if (size == 0)
      tail = null;                           // special case as list is now empty
    return answer;
  }

  @SuppressWarnings({"unchecked"})
  public boolean equals(Object o) {
    if (o == null) return false;
    if (getClass() != o.getClass()) return false;
    SinglyLinkedList other = (SinglyLinkedList) o;   // use nonparameterized type
    if (size != other.size) return false;
    Node walkA = head;                               // traverse the primary list
    Node walkB = other.head;                         // traverse the secondary list
    while (walkA != null) {
      if (!walkA.getElement().equals(walkB.getElement())) return false; //mismatch
      walkA = walkA.getNext();
      walkB = walkB.getNext();
    }
    return true;   // if we reach this, everything matched successfully
  }

  @SuppressWarnings({"unchecked"})
  public SinglyLinkedList<E> clone() throws CloneNotSupportedException {
    // always use inherited Object.clone() to create the initial copy
    SinglyLinkedList<E> other = (SinglyLinkedList<E>) super.clone(); // safe cast
    if (size > 0) {                    // we need independent chain of nodes
      other.head = new Node<>(head.getElement(), null);
      Node<E> walk = head.getNext();      // walk through remainder of original list
      Node<E> otherTail = other.head;     // remember most recently created node
      while (walk != null) {              // make a new node storing same element
        Node<E> newest = new Node<>(walk.getElement(), null);
        otherTail.setNext(newest);     // link previous node to this one
        otherTail = newest;
        walk = walk.getNext();
      }
    }
    return other;
  }

  public int hashCode() {
    int h = 0;
    for (Node walk=head; walk != null; walk = walk.getNext()) {
      h ^= walk.getElement().hashCode();      // bitwise exclusive-or with element's code
      h = (h << 5) | (h >>> 27);              // 5-bit cyclic shift of composite code
    }
    return h;
  }

  /**
   * Produces a string representation of the contents of the list.
   * This exists for debugging purposes only.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("(");
    Node<E> walk = head;
    while (walk != null) {
      sb.append(walk.getElement());
      if (walk != tail)
        sb.append(", ");
      walk = walk.getNext();
    }
    sb.append(")");
    return sb.toString();
  }
  
  /* swapNodes method*/
  public String swapNodes(Node<E> n1, Node<E> n2) {
	  boolean n1_occur = false;
	  boolean n2_occur = false;
	  
	  Node<E> walk = head;
	    while (walk != null) {
	    	if (walk == n1) {
	    		n1_occur = true;
	    	}
	    	if (walk == n2) {
	    		n2_occur = true;
	    	}
	    	walk = walk.getNext();
	    }  
	  if ((n1_occur && n2_occur) && (n1 != n2)) {
		  swapNodesHandler(n1,n2);
		  return "swapped";
	  } else if ((n1_occur && n2_occur) && (n1.equals(n2))){     
		   return "same";
	  }
	  return "swap error";
//		   else if (!(n1_occur && n2_occur) && (n1_occur || n2_occur)) { // remains of testing for whether 2 nodes exist in the first place in LL
//		  // "just one";
//	  } else {
//		  // "none";
//	  }
  }
  
  private void swapNodesHandler(Node<E> n1, Node<E> n2) {
	  Node<E> bef_first = null;
	  Node<E> bef_sec = null;
	  Node<E> first = null;
	  Node<E> second = null;

	  Node<E> walk = head;
	  Node<E> prev = null;

	  while (walk != null) {

		    if (walk == n1 || walk == n2) {

		        if (first == null) {
		            first = walk;
		            bef_first = prev;
		        } else {
		            second = walk;
		            bef_sec = prev;
		            break;
		        }
		    }

		    prev = walk;
		    walk = walk.getNext();
	  }
	  
	  Node<E> after_first = first.getNext();
	  Node<E> after_second = second.getNext();
	  
	  if (bef_sec == first) { //catch this case as it doesnt mix with base implementation

		    if (bef_first != null) {
		        bef_first.setNext(second);
		    } else {
		        head = second; // first was head
		    }

		    first.setNext(after_second);
		    second.setNext(first);
	  } else { //main case of swapping atleast a 4 node LL with no overlap


		    if (bef_first != null) {
		        bef_first.setNext(second); //there is duplication here, but since it works i won't mess with it
		    } else {
		        head = second;
		    }
		    bef_sec.setNext(first);

		    first.setNext(after_second);
		    second.setNext(after_first);
	  }
	  
	  // updated tail case 
	  if (tail == second) { //just in-case second happened to be tail, caught during testing
	      tail = first;
	  }
	  
	  	  
  }
  
  
  //main method
  public static void main(String[] args)
  {
	  
	  SinglyLinkedList<String> list = new SinglyLinkedList<String>();
	  list.addFirst("MSP");
	  list.addLast("ATL");
	  list.addLast("BOS");
	  //
	  list.addFirst("LAX");
	  System.out.println(list);
	  
	  Node<String> n1 = list.head; // you can change these to any nodes in the list
	  System.out.println(n1.getElement());
	  Node<String> n2 = list.tail;
	  System.out.println(n2.getElement());
	  
	  int n1_index = -1;
	  int n2_index = -1;
	  int index = 0;
	  
	  
	// capture BEFORE swap
	  Node<String> walk = list.head;
	  while (walk != null) {
		  if (walk == n1) {n1_index = index;}
		  if (walk == n2) {n2_index = index;}
		  walk = walk.getNext();
		  index++;
	  }
	  

	  // SWAP	  
	  String result = list.swapNodes(n1, n2);
	  System.out.println(list);
	  
	  
	  // SWAP confirmation
	  if (result.equals("swap error")) {
		  System.out.println("nodes submitted were either partially/fully not in list, no swap was made");
	  } else if (result.equals("same")) {
		  System.out.println("nodes submitted were the same node, no swap was made");
	  } else if (result.equals("swapped")) {
		  System.out.println("nodes were swapped");
		  
		  //check AFTER swap
		  walk = list.head;
		  index = 0;
		  while (walk != null) {
			  if (walk == n1) {if (index == n2_index) {System.out.println("node 1 was swapped successfully");}}
			  if (walk == n2) {if (index == n1_index) {System.out.println("node 2 was swapped successfully");}}
			  walk = walk.getNext();
			  index++;
		  }
		  if (list.tail.getNext() == null) {System.out.println("Tail is secure.");}
		  if (walk == null) {System.out.println("Check complete.");}
		  
	  }
	  
	  
	  
  }
}
