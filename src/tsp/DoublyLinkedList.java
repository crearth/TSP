package tsp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DoublyLinkedList implements DoublyLinkedListInterface {
    //TODO lijst bijhouden met indices en nodes, zodat zoeken naar node constant isé

    /**
     * Set head and tail to null
     */
    Node head = null;
    Node tail = null;
    int numberOfElements = 0;

    ArrayList<Node> indicesList = new ArrayList<Node>();

    /**
     * An inner class for nodes
     */
    class Node{
        int item;
        Node previous = null;
        Node next = null;

        public Node(int item) {
            this.item = item;
        }
    }

    /**
     * Insert an item at position x of the list
     * @param item the item to add
     * @param position the position in the list to add the item
     */
    public void insertItem(int item, int position) {
        // maybe not needed
    }

    public void add(int index, int item) {
        if (index < 0 || index > numberOfElements)
            throw new IllegalArgumentException();
        Node newNode = new Node(item);
        if (head == null) {
            head = newNode;
            tail = newNode;
            // circular doubly linked list
            head.previous = tail;
            tail.next = head;
        }
        else if (index == 0) {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
            // circular doubly linked list
            head.previous = tail;
            tail.next = head;
        }
        else if (index == numberOfElements) {
            newNode.previous = tail;
            tail.next = newNode;
            tail = newNode;
            // circular doubly linked list
            tail.next = head;
            head.previous = tail;
        }
        else {
            Node nodeRef = head;
            for (int i = 1; i < index; i++)
                nodeRef = nodeRef.next;
            newNode.next = nodeRef.next;
            nodeRef.next = newNode;
            newNode.previous = nodeRef;
            newNode.next.previous = newNode;
        }
        indicesList.add(index, newNode);
        numberOfElements++;
    }

    /**
     * Add an item to the end of list
     * @param item
     */
    public void insertItemEnd(int item) {
        add(numberOfElements, item);
    }

    public void swap(int i, int j) {
        Node nodeI = indicesList.get(i);
        Node nodeJ = indicesList.get(j);
        Node temp;
        temp = nodeI.next;
        nodeI.next = nodeJ.next;
        nodeJ.next = temp;

        if (nodeI.next != null)
            nodeI.next.previous = nodeI;
        if (nodeJ.next != null)
            nodeJ.next.previous = nodeJ;

        temp = nodeI.previous;
        nodeI.previous = nodeJ.previous;
        nodeJ.previous = temp;

        if (nodeI.previous != null)
            nodeI.previous.next = nodeI;
        if (nodeJ.previous != null)
            nodeJ.previous.next = nodeJ;
    }

    /**
     * Print the items of the doubly linked list.
     */
    public void printItems() {
        Node current = head;
        if(head == null) {
            System.out.println("Doubly linked list is empty");
            return;
        }
        while(current != null) {
            System.out.print(current.item + " ");
            current = current.next;
        }
    }
}
