package tsp.ts;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

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
        emptyList.addEnd(10);
        ArrayList<Integer> list = emptyList.toList();
        int index0 = list.get(0);
        int index1 = list.get(1);
        assertEquals(index0, 4);
        assertEquals(index1, 10);

    }

    @Test
    public void testInsertItem() {
        notEmptyList.add(1,5);
        ArrayList<Integer> list = notEmptyList.toList();
        int index1 = list.get(1);
        int index2 = list.get(2);
        int index3 = list.get(3);
        assertEquals(index1, 5);
        assertEquals(index2, 6);
        assertEquals(index3, 7);
    }

    @Test
    public void testSwapNodes() {
        DoublyLinkedList.Node iPrev = notEmptyList.getTail();
        DoublyLinkedList.Node iCurrent = notEmptyList.getHead();
        DoublyLinkedList.Node jCurrent = iCurrent.getNext(iPrev);
        DoublyLinkedList.Node jNext = jCurrent.getNext(iCurrent);

        notEmptyList.swap(iPrev, iCurrent, jCurrent, jNext);
        System.out.println(notEmptyList.toList());
    }

    @Test
    public void testToList() {
        ArrayList<Integer> list = new ArrayList();
        list.add(4);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        assertEquals(notEmptyList.toList(), list);
    }

}
