package tsp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DoublyLinkedListTest {
    private DoublyLinkedList emptyList;
    private DoublyLinkedList notEmptyList;
    private DoublyLinkedList twoOptList;

    @Before
    public void setUp() {
        emptyList = new DoublyLinkedList();

        notEmptyList = new DoublyLinkedList();
        notEmptyList.addEnd(4);
        notEmptyList.addEnd(6);
        notEmptyList.addEnd(7);
        notEmptyList.addEnd(8);
        notEmptyList.addEnd(9);

        twoOptList = new DoublyLinkedList();
        twoOptList.addEnd(1);
        twoOptList.addEnd(2);
        twoOptList.addEnd(3);
        twoOptList.addEnd(4);
        twoOptList.addEnd(5);
        twoOptList.addEnd(6);
        twoOptList.addEnd(7);
        twoOptList.addEnd(8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalAddEmptyList() {
        emptyList.add(2, 3);
    }

    @Test
    public void testEmptyList() {
        emptyList.addEnd(4);
        assertEquals(emptyList.getItem(0), 4);
        emptyList.addEnd(10);
        assertEquals(emptyList.getItem(1), 10);
    }

    @Test
    public void testInsertItem() {
        notEmptyList.add(1,5);
        assertEquals(notEmptyList.getItem(1), 5);
        assertEquals(notEmptyList.getItem(2), 6);
        assertEquals(notEmptyList.getItem(3), 7);
    }

    @Test
    public void testSwapItems() {
        assertEquals(notEmptyList.getItem(1), 6);
        assertEquals(notEmptyList.getItem(3), 8);
        notEmptyList.swap(1,3);
        assertEquals(notEmptyList.getItem(1), 8);
        assertEquals(notEmptyList.getItem(3), 6);
    }

    @Test
    public void testSwapItemsStart() {
        assertEquals(notEmptyList.getItem(0), 4);
        assertEquals(notEmptyList.getItem(3), 8);
        notEmptyList.swap(0,3);
        assertEquals(notEmptyList.getItem(0), 8);
        assertEquals(notEmptyList.getItem(3), 4);
    }

    @Test
    public void testSwapItemsEnd() {
        assertEquals(notEmptyList.getItem(1), 6);
        assertEquals(notEmptyList.getItem(4), 9);
        notEmptyList.swap(1,4);
        assertEquals(notEmptyList.getItem(1), 9);
        assertEquals(notEmptyList.getItem(4), 6);
    }

    @Test
    public void testSwapItemsStartEnd() {
        assertEquals(notEmptyList.getItem(0), 4);
        assertEquals(notEmptyList.getItem(4), 9);
        notEmptyList.swap(0,4);
        assertEquals(notEmptyList.getItem(0), 9);
        assertEquals(notEmptyList.getItem(4), 4);

        assertEquals(notEmptyList.searchItem(4), notEmptyList.getNode(4));
        assertEquals(notEmptyList.searchItem(9), notEmptyList.getNode(0));
    }

    @Test
    public void testTwoOpt15() {
        twoOptList.twoOpt(1,5);

        assertEquals(twoOptList.getItem(1), 6);
        assertEquals(twoOptList.searchItem(6), twoOptList.getNode(1));

        assertEquals(twoOptList.getItem(2), 5);
        assertEquals(twoOptList.searchItem(5), twoOptList.getNode(2));

        assertEquals(twoOptList.getItem(3), 4);
        assertEquals(twoOptList.searchItem(4), twoOptList.getNode(3));

        assertEquals(twoOptList.getItem(4), 3);
        assertEquals(twoOptList.searchItem(3), twoOptList.getNode(4));

        assertEquals(twoOptList.getItem(5), 2);
        assertEquals(twoOptList.searchItem(2), twoOptList.getNode(5));
    }

    @Test
    public void testTwoOpt16() {
        twoOptList.twoOpt(1,6);

        assertEquals(twoOptList.getItem(1), 7);
        assertEquals(twoOptList.searchItem(7), twoOptList.getNode(1));

        assertEquals(twoOptList.getItem(2), 6);
        assertEquals(twoOptList.searchItem(6), twoOptList.getNode(2));
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void testDeepCopy() {
        DoublyLinkedList copy = new DoublyLinkedList(notEmptyList.toList());
        assertEquals(copy.getItem(1), 6);
        assertEquals(copy.getItem(4), 9);

        copy.addEnd(10);
        assertEquals(copy.getItem(5), 10);
        assertNotEquals(notEmptyList.getItem(5), 10);
    }

}
