package tsp.ts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DoublyLinkedList implements DoublyLinkedListInterface {
    /**
     * Initialize head and tail to null.
     */
    private Node head = null;
    private Node tail = null;
    /**
     * Initialize the number of elements of the Doubly Linked List to 0.
     */
    private int numberOfElements = 0;

    /**
     * An inner class for nodes. A node has 2 child pointers and an integer.
     */
    class Node{
        /**
         * The actual item of the node.
         */
        int item;
        /**
         * The first child.
         */
        Node nodeA = null;
        /**
         * The second child/
         */
        Node nodeB = null;

        /**
         * Constructor for the node. Sets the item of the node to the given item.
         * @param item int The item of the node.
         */
        public Node(int item) {
            this.item = item;
        }

        /**
         * Get the next node based on the given previous node. This is needed because the two child nodes don't have the
         * information if it was the previous or the next node.
         * @param previous Node The previous node.
         * @return Node The node following this node.
         */
        public Node getNext(Node previous) {
        if (previous == nodeA) {
            return nodeB;
        } else {
            return nodeA;
        }
        }
    }

    public DoublyLinkedList() {

    }

    /**
     * Translation constructor. This constructor appends every item of the list to the end of a new Doubly Linked List.
     * @param list List The list to convert into a Doubly Linked List.
     */
    public DoublyLinkedList(List<Integer> list) {
        for (Integer i : list) {
            addEnd(i);
        }
    }

    /**
     * Get the head of the Doubly Linked List.
     * @return Node The head of the Doubly Linked List.
     */
    public Node getHead() {
        return head;
    }

    /**
     * Get the tail of the Doubly Linked List.
     * @return Node The tail of the Doubly Linked List.
     */
    public Node getTail() {
        return tail;
    }

    /**
     * Insert an item at index x of the Doubly Linked List.
     * @param index int The position in the list to add the item.
     * @param item int The item.
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
        numberOfElements++;
    }

    /**
     * Add an item to the end of the list.
     * @param item int The item.
     */
    public void addEnd(int item) {
        add(numberOfElements, item);
    }

    /**
     * Swap to nodes in the Doubly Linked List. This function is an element of the class DoublyLinkedList because
     * a swap is an universal function for Doubly Linked List.
     * @param iPrev Node The node before the node i.
     * @param iCurrent Node The node i, the first node of the swap.
     * @param jCurrent Node The node j, the node to swap with i.
     * @param jNext Node The node after the node j.
     */
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
}
