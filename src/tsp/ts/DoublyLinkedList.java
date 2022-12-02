package tsp.ts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DoublyLinkedList implements DoublyLinkedListInterface {
    //TODO lijst bijhouden met indices en nodes, zodat zoeken naar node constant isé

    /**
     * Set head and tail to null
     */
    private Node head = null;
    private Node tail = null;
    private int numberOfElements = 0;
    private ArrayList<Node> nodeIndicesList = new ArrayList<>();

    /**
     * An inner class for nodes
     */
    class Node{
        int item;
        Node nodeA = null;
        Node nodeB = null;

        public Node(int item) {
            this.item = item;
        }

        public Node getNext(Node previous) {
        if (previous == nodeA) {
            return nodeB;
        } else {
            return nodeA;
        }
        }

        @Override
        public String toString() {
            return String.valueOf(item) + " (" + String.valueOf(nodeA.item) + ", " + String.valueOf(nodeB.item) + ")";
        }
    }

    public DoublyLinkedList() {

    }

    public DoublyLinkedList(List<Integer> list) {
        for (Integer i : list) {
            addEnd(i);
        }
    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    public Node searchItem(int item) {
        Node current = head;
        for (int i = 0; i < numberOfElements; i++) {
            if (current.item == item) {
                return current;
            } else {
                current = current.nodeB;
            }
        }
        return current;
    }

    /**
     * Insert an item at index x of the list
     * @param index the position in the list to add the item
     * @param item the item
     */
    public void add(int index, int item) {
        if (index < 0 || index > numberOfElements)
            throw new IllegalArgumentException();
        Node newNode = new Node(item);
        if (head == null) {
            head = newNode;
            tail = newNode;
            // circular doubly linked list
            head.nodeA = tail;
            tail.nodeB = head;
        }
        else if (index == 0) {
            newNode.nodeB = head;
            head.nodeA = newNode;
            head = newNode;
            // circular doubly linked list
            head.nodeA = tail;
            tail.nodeB = head;
        }
        else if (index == numberOfElements) {
            newNode.nodeA = tail;
            tail.nodeB = newNode;
            tail = newNode;
            // circular doubly linked list
            tail.nodeB = head;
            head.nodeA = tail;
        }
        else {
            Node nodeRef = head;
            for (int i = 1; i < index; i++)
                nodeRef = nodeRef.nodeB;
            newNode.nodeB = nodeRef.nodeB;
            nodeRef.nodeB = newNode;
            newNode.nodeA = nodeRef;
            newNode.nodeB.nodeA = newNode;
        }
        nodeIndicesList.add(index, newNode);
        numberOfElements++;
    }

    /**
     * Add an item to the end of list
     * @param item
     */
    public void addEnd(int item) {
        add(numberOfElements, item);
    }

    public void swap(Node iPrev, Node iCurrent, Node jCurrent, Node jNext) {
        if (iCurrent == head) {
            head = jCurrent;
        } else if (jCurrent == head) {
            head = iCurrent;
        }
        if (iCurrent == tail) {
            tail = jCurrent;
        } else if (jCurrent == tail) {
            tail = iCurrent;
        }
        if (iPrev.nodeA == iCurrent) {
            iPrev.nodeA = jCurrent;
        } else {
            iPrev.nodeB = jCurrent;
        }
        if (jCurrent.nodeA == jNext) {
            jCurrent.nodeA = iPrev;
        } else {
            jCurrent.nodeB = iPrev;
        }
        if (iCurrent.nodeA == iPrev) {
            iCurrent.nodeA = jNext;
        } else {
            iCurrent.nodeB = jNext;
        }
        if (jNext.nodeA == jCurrent) {
            jNext.nodeA = iCurrent;
        } else {
            jNext.nodeB = iCurrent;
        }
    }

    /**
     * Convert the doubly linked list to a list.
     * @return ArrayList A list with the elements of the doubly linked list in correct order
     */
    public ArrayList<Integer> toList() {
        ArrayList<Integer> list = new ArrayList<>();
        Node current = head;
        Node previous = tail;
        if(head == null) {
            return list;
        }
        for(int i = 0; i < numberOfElements; i++) {
            list.add(current.item);
            Node temp = current;
            current = current.getNext(previous);
            previous = temp;
        }
        return list;
    }

    /**
     * Print the items of the doubly linked list.
     */
    public void print() {
        Node current = head;
        Node previous = tail;
        if(head == null) {
            System.out.println("Doubly linked list is empty");
            return;
        }
        for(int i = 0; i < numberOfElements; i++) {
            System.out.print(current.item + " ");
            Node temp = current;
            current = current.getNext(previous);
            previous = temp;
        }
        System.out.println("");
    }
}
