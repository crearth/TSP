package tsp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DoublyLinkedListTest {
    private DoublyLinkedList emptyList;
    private DoublyLinkedList notEmptyList;

    @Before
    public void setUp() {
        emptyList = new DoublyLinkedList();
        notEmptyList = new DoublyLinkedList();
        notEmptyList.addEnd(4);
        notEmptyList.addEnd(6);
        notEmptyList.addEnd(7);
        notEmptyList.addEnd(8);
        notEmptyList.addEnd(9);
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
    }
}
