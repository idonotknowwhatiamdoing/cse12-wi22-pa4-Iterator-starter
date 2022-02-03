
/**
 * This file contains testers for the iterator inner class in 
 * MyLinkedList.
 * Name: Shera Zhong
 * ID: A16871392
 * Email: s3zhong@ucsd.edu
 * Sources used: None
 * 
 * Tests methods in MyLinkedList's inner class MyListIterator to
 * check for edge cases and if exceptions are thrown correctly. 
 * Creates several list objects on which different tests are run.
 */

import static org.junit.Assert.*;
import org.junit.*;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class containing testers for the inner class MyListIterator, 
 * inside MyLinkedList. Focuses on edge cases.
 * 
 * IMPORTANT: Do not change the method headers and points are awarded
 * only if your test cases cover cases that the public tester file
 * does not take into account.
 */
public class MyLinkedListCustomTester {

    private MyLinkedList testList, emptyList;
    private MyLinkedList.MyListIterator testIterate, emptyIterate;
    /**
     * This sets up the test fixture. JUnit invokes this method before
     * every testXXX method. The @Before tag tells JUnit to run this method
     * before each test.
     */
    @Before
    public void setUp() throws Exception {
        testList = new MyLinkedList();
        testList.add("0");
        testList.add("1");
        testList.add("2");
        testIterate = testList.new MyListIterator();
        
        emptyList = new MyLinkedList();
        emptyIterate = emptyList.new MyListIterator();
    }

    /**
     * Test the hasNext method when the list is empty
     */
    @Test
    public void testHasNext() {
        assertFalse("call hasNext on empty list", emptyIterate.hasNext());
    }

    /**
     * Test the next method when next is called at end
     */
    @Test
    public void testNext() {
        testIterate.left = testList.tail.getPrev();
        testIterate.right = testList.tail;
        testIterate.idx = 3;

        boolean caught = false;
        try 
        {
            testIterate.next();
        }
        catch(NoSuchElementException e) 
        {
            caught = true;
        }
        assertEquals("next() can't be called at end of list", true, caught);
    }

    /**
     * Test the hasPrevious method on empty list
     */
    @Test
    public void testHasPrevious() {
        assertFalse("call hasPrev on empty list", emptyIterate.hasPrevious());
    }

    /**
     * Test the previous method when previous is called at start
     */
    @Test
    public void testPrevious() {
        testIterate.left = testList.head;
        testIterate.right = testList.head.getNext();
        testIterate.idx = 0;

        boolean caught = false;
        try 
        {
            testIterate.previous();
        }
        catch(NoSuchElementException e) 
        {
            caught = true;
        }
        assertEquals("previous() can't be called at start of list", true, caught);
    }

    /**
     * Test the nextIndex method when at the end of list
     */
    @Test
    public void testNextIndex() {
        testIterate.left = testList.tail.getPrev();
        testIterate.right = testList.tail;
        testIterate.idx = 3;
        assertEquals("at end of list idx == size", 3, testIterate.nextIndex());
    }

    /**
     * TODO: test the previousIndex method when [...]
     */
    @Test
    public void testPreviousIndex() {
        testIterate.left = testList.head;
        testIterate.right = testList.head.getNext();
        testIterate.idx = 0;
        assertEquals("at beginning of list idx == -1", -1, testIterate.previousIndex());
    }

    /**
     * Test the set method when element is null
     */
    @Test
    public void testSet() {
        testIterate.left = testList.head.getNext();
        testIterate.right = testList.head.getNext().getNext();
        testIterate.idx = 1;
        testIterate.forward = true;
        testIterate.canRemoveOrSet = true;

        boolean caught = false;
        try 
        {
            testIterate.set(null);
        }
        catch(NullPointerException e) 
        {
            caught = true;
        }
        assertEquals("set element can't be null", true, caught);
    }

    /**
     * Test the remove method when remove was just called
     */
    @Test
    public void testRemoveTestOne() {
        testIterate.left = testList.head.getNext();
        testIterate.right = testList.head.getNext().getNext();
        testIterate.idx = 1;
        testIterate.forward = true;
        testIterate.canRemoveOrSet = true;

        testIterate.remove();
        boolean caught = false;
        try 
        {
            testIterate.remove();
        }
        catch(IllegalStateException e) 
        {
            caught = true;
        }
        assertEquals("can't call remove twice in a row", true, caught);
    }

    /**
     * Test the remove method when remove is called after add
     */
    @Test
    public void testRemoveTestTwo() {
        testIterate.left = testList.head.getNext();
        testIterate.right = testList.head.getNext().getNext();
        testIterate.idx = 1;
        testIterate.forward = true;
        testIterate.canRemoveOrSet = true;

        testIterate.add("9");
        boolean caught = false;
        try 
        {
            testIterate.remove();
        }
        catch(IllegalStateException e) 
        {
            caught = true;
        }
        assertEquals("can't call remove right after add", true, caught);
    }

    /**
     * Test the add method when element is null
     */
    @Test
    public void testAdd() {
        testIterate.left = testList.head.getNext();
        testIterate.right = testList.head.getNext().getNext();
        testIterate.idx = 1;
        testIterate.forward = true;
        testIterate.canRemoveOrSet = true;

        boolean caught = false;
        try 
        {
            testIterate.add(null);
        }
        catch(NullPointerException e) 
        {
            caught = true;
        }
        assertEquals("add element can't be null", true, caught);
    }

}