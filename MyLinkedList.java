/**
 * Class containing implementation of a doubly linked list
 * Name: Shera Zhong
 * ID: A16871392
 * Email: s3zhong@ucsd.edu
 * Sources used: None
 * 
 * This class creates a MyLinkedList object, which stores data in the form
 * of nodes. Each node has a previous and next pointer, pointing to the 
 * previous and next data entries, respectively. There are two sentinel
 * nodes called head and tail that mark the beginning and end of the 
 * list, both with value null.
 */

import java.util.AbstractList;
import java.util.NoSuchElementException;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** 
 * MyLinkedList class that extends interface AbstractList. Each linked
 * list is comprised of nodes that can be manipulated by the user.
 * The user can search for, insert, remove, return nodes at a specified
 * index.   
 */

public class MyLinkedList<E> extends AbstractList<E> {

	int size;
	Node head;
	Node tail;

	static private final String NULL_POINTER_MSG = "Data to add can't be null";
	static private final String INDEX_BOUND_MSG = "Index out of bounds";
	static private final String ILLEGAL_STATE_MSG = "Illegal state";
	static private final String NO_SUCH_ELEMENT_MSG = "No such element";

	/**
	 * A Node class that holds data and references to previous and next Nodes.
	 */
	protected class Node {
		E data;
		Node next;
		Node prev;

		/** 
		 * Constructor to create singleton Node 
		 * @param element Element to add, can be null	
		 */
		public Node(E element) {
			// Initialize the instance variables
			this.data = element;
			this.next = null;
			this.prev = null;
		}

		/** 
		 * Set the parameter prev as the previous node
		 * @param prev - new previous node
		 */
		public void setPrev(Node prev) {
			this.prev = prev;		
		}

		/** 
		 * Set the parameter next as the next node
		 * @param next - new next node
		 */
		public void setNext(Node next) {
			this.next = next;
		}

		/** 
		 * Set the parameter element as the node's data
		 * @param element - new element 
		 */
		public void setElement(E element) {
			this.data = element;
		}

		/** 
		 * Accessor to get the next Node in the list 
		 * @return the next node
		 */
		public Node getNext() {
			return this.next;
		}

		/** 
		 * Accessor to get the prev Node in the list
		 * @return the previous node  
		 */
		public Node getPrev() {
			return this.prev;
		}

		/** 
		 * Accessor to get the Nodes Element 
		 * @return this node's data
		 */
		public E getElement() {
			return this.data;
		}
	}

	//  Implementation of the MyLinkedList Class
	/** Only 0-argument constructor is defined */
	public MyLinkedList() {
		head = new Node(null);
		tail = new Node(null);
		head.next = tail;
		tail.prev = head;
		size = 0;
	}

	/** 
	 * Getter that gets size of list
	 * @return size as an int 
	 */
	@Override
	public int size() {
		// need to implement the size method
		return size;
	}

	/** 
	 * Getter that gets data at an index of list
	 * @param index index to get value at
	 * @return data at index value
	 */
	@Override
	public E get(int index) {
		return getNth(index).data;  
	}

	/** 
	 * Adds an element into the list at a specified index
	 * @param index index to add element at
	 * @param data data inside element 
	 */	
	@Override
	public void add(int index, E data) {
		// check if user parameters are valid
		if (data == null)
		{
			throw new NullPointerException(NULL_POINTER_MSG);
		}
		if (index < 0 || index > size)
		{
			throw new IndexOutOfBoundsException(INDEX_BOUND_MSG);
		}
		Node newNode = new Node(data);
		if (index == 0) // add directly behind head
		{
			head.next = newNode;
			newNode.prev = head;
			if (size == 0) // add directly between head and tail
			{
				newNode.next = tail;
				tail.prev = newNode;
			}
			else
			{
				newNode.next = getNth(0);
				getNth(0).prev = newNode;
			}			
		}
		else if (index == size()) // add directly in front of tail
		{
			this.add(data);
			size--;
		}
		else // add anywhere else inside the list
		{
			newNode.setNext(getNth(index));
			getNth(index).setPrev(newNode);
			newNode.setPrev(getNth(index).getPrev());
			getNth(index).getPrev().setNext(newNode);
		}
		size++;
	}

	/** 
	 * Checks if element can be added or not
	 * @return true, always 
	 */
	public boolean add(E data) {
		// check if user parameters are valid
		if (data == null)
		{
			throw new NullPointerException(NULL_POINTER_MSG);
		}
		Node newNode = new Node(data);
		if (size == 0) // add directly behind head, in front of tail
		{
			head.next = newNode;
			newNode.prev = head;
		}
		else // add directly in front of tail
		{
			tail.getPrev().next = newNode;
			newNode.prev = tail.getPrev();
		}
		newNode.next = tail;
		tail.prev = newNode;
		size++;
		return true; 
	}

	/** 
	 * Sets data at a specified index
	 * @param index index to set value
	 * @param data data to set at index
	 * @return overridden node 
	 */
	public E set(int index, E data) {
		// check if user parameters are valid
		if (data == null)
		{
			throw new NullPointerException(NULL_POINTER_MSG);
		}
		if (index < 0 || index > size)
		{
			throw new IndexOutOfBoundsException(INDEX_BOUND_MSG);
		}
		Node oldNode = getNth(index); // node to store old data
		Node newNode = new Node(data);
		// change pointers of old node's neighbors and new node
		getNth(index).getNext().prev = newNode;
		getNth(index).getPrev().next = newNode;
		newNode.prev = getNth(index).getPrev();
		newNode.next = getNth(index).getNext();
		size++;
		return oldNode.data; 
	}

	/** 
	 * Removes node at index in list
	 * @param index index to remove
	 * @return removed element
	 */
	public E remove(int index) {
		// check if user parameters are valid
		if (index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException(INDEX_BOUND_MSG);
		}
		Node oldNode = getNth(index);
		if (size == 1) // remove sole element
		{
			tail.prev = head;
			head.next = tail;
		}
		else if (index == 0) // remove first element
		{
			getNth(index).getNext().prev = head;
			head.next = getNth(index).getNext();
		}
		else if (index == size - 1) // remove last element
		{
			tail.prev = getNth(index).getPrev();
			getNth(index).getPrev().next = tail;
		}
		else // remove element anywhere else in list
		{
			getNth(index).getNext().prev = getNth(index).getPrev();
			getNth(index).getPrev().next = getNth(index).getNext();
		}
		size--;
		return oldNode.data;
	}

	/** 
	 * Clears list 
	 */
	public void clear() {
		if (isEmpty()) return;
		for (int i = 0; i < size; i++)
		{
			remove(i); 
		}
		size = 0;
	}

	/** 
	 * Checks if list is empty or not
	 * @return false if not empty, true if it is 
	 */
	public boolean isEmpty() {
		if (size == 0) return true;
		return false;  
	}

	/** 
	 * Gets the node at a specified index
	 * @param index index to find the node
	 * @return node at specified index 
	 */
	protected Node getNth(int index) {
		// check if user parameters are valid
		if (index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException(INDEX_BOUND_MSG);
		}
		Node curr = head;
		for (int i = 0; i <= index; i++)
		{
			curr = curr.getNext();
		}
		return curr;  
	}

	/** 
	 * Constructs a listIterator
	 * @return newly created listIterator
	 */	
	@Override
	public ListIterator<E> listIterator()
	{
		MyListIterator iterator = new MyListIterator();
		return iterator;
	}

	/** 
	 * Constructs an iterator
	 * @return newly created listIterator
	 */
	@Override
	public Iterator<E> iterator()
	{
		MyListIterator iterator = new MyListIterator();
		return iterator;
	}
	/** 
	 * MyListIterator is the inner class inside MyLinkedList. Its 
	 * implementation allows users to traverse a linked list and 
	 * perform various manipulations of it.
	 */
	
	protected class MyListIterator implements ListIterator<E> {
        // class variables here
		Node left, right; 
		int idx;
		boolean forward;
		boolean canRemoveOrSet;

		/** 
		 * Constructs a listIterator
		 */
		public MyListIterator()
		{
			// Initialize the instance variables
			left = head;
			right = head.next;
			idx = 0;
			forward = true;
			canRemoveOrSet = false;

		}

		/** 
		 * Checks if next() can be called
		 * @return true if next is valid and not tail, false otherwise
		 */
        public boolean hasNext() 
		{
			if (right != null && right != tail) return true;
			return false;
        }

		/** 
		 * Shifts the iterator to the right by 1 index
		 * @return old element at the right
		 */
		public E next()
		{
			if (right == null || right == tail)
			{
				throw new NoSuchElementException(NO_SUCH_ELEMENT_MSG);
			}
			left = left.getNext();
			right = right.getNext();
			idx++;
			canRemoveOrSet = true;
			forward = true;
			return (E) left.data;
		}

		/** 
		 * Checks if previous() can be called
		 * @return true if prev is valid and not tail, false otherwise
		 */
		public boolean hasPrevious()
		{
			if (left != null && left != head) return true;
			return false;
		}

		/** 
		 * Shifts the iterator to the left by 1 index
		 * @return old element at the left
		 */
		public E previous()
		{
			if (left == null || left == head)
			{
				throw new NoSuchElementException(NO_SUCH_ELEMENT_MSG);
			}
			left = left.prev;
			right = right.prev;
			idx--;
			canRemoveOrSet = true;
			forward = false;
			return (E) right.data;
		}

		/** 
		 * Returns next index
		 * @return what the index would be after calling next
		 */
		public int nextIndex()
		{
			if (idx == size) return size;
			return idx++;
		}

		/** 
		 * Returns previous index
		 * @return what the index would be after calling previous
		 */
		public int previousIndex()
		{
			if (idx == 0) return -1;
			return --idx;
		}

		/** 
		 * Adds element into list before what would be returned by next()
		 * @param element element to add
		 */		
		public void add(E element)
		{
			// check if user parameters are valid
			if (element == null)
			{
				throw new NullPointerException(NULL_POINTER_MSG);
			}
			Node newNode = new Node(element);
			right.prev = newNode;
			newNode.next = right;
			left.next = newNode;
			newNode.prev = left;
			idx++;
			canRemoveOrSet = false;
			left = left.next;
		}

		/** 
		 * Replaces element called by last next() or previous() call
		 * and replaces it with new element
		 * @param element element to add
		 */	
		public void set(E element)
		{
			// check if user parameters are valid
			if (element == null)
			{
				throw new NullPointerException(NULL_POINTER_MSG);
			}
			if (canRemoveOrSet == false)
			{
				throw new IllegalStateException(ILLEGAL_STATE_MSG);
			}
			Node newNode = new Node(element);
			if (forward == false)
			{
				right.next.prev = newNode;
				newNode.next = right.next;
				left.next = newNode;
				newNode.prev = left;
				right = newNode;
			}
			else if (forward == true)
			{
				left.prev.next = newNode;
				newNode.prev = left.prev;
				right.prev = newNode;
				newNode.next = right;
				left = newNode;
			}
		}

		/** 
		 * Removes element called by last next() or previous() call
		 * and replaces it with new element
		 */	
		public void remove()
		{
			if (canRemoveOrSet == false)
			{
				throw new IllegalStateException(ILLEGAL_STATE_MSG);
			}
			if (forward == false)
			{
				right = right.next;

				right.prev = left;
				left.next = right;
			}
			else if (forward == true)
			{
				left = left.prev;

				left.next = right;
				right.prev = left;

				idx--;
			}
			canRemoveOrSet = false;
		}
	}
}