package tsp;

import java.lang.reflect.Array;
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
    private ArrayList<Node> indicesList = new ArrayList<Node>();

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

        @Override
        public String toString() {
            return String.valueOf(previous.item) + " <- " + String.valueOf(item) + " -> " + String.valueOf(next.item);
        }
    }

    public DoublyLinkedList() {

    }

    public DoublyLinkedList(List<Integer> list) {
        DoublyLinkedList newDoublyLinkedList = new DoublyLinkedList();
        for (Integer i : list) {
            newDoublyLinkedList.addEnd(i);
        }
    }

    public int getItem(int index) {
        return indicesList.get(index).item;
    }

    public Node getNode(int index) {
        return indicesList.get(index);
    }

    public int getIndexOfNode(Node node) {
        return indicesList.indexOf(node);
    }

    public Node searchItem(int item) {
        Node current = head;
        for (int i = 0; i < numberOfElements; i++) {
            if (current.item == item) {
                return current;
            } else {
                current = current.next;
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
    public void addEnd(int item) {
        add(numberOfElements, item);
    }

    public void swap(int i, int j) {
        Node nodeI = indicesList.get(i);
        Node nodeJ = indicesList.get(j);
        swap(nodeI, nodeJ);
    }

    public void swap(Node nodeI, Node nodeJ) {
        int i = getIndexOfNode(nodeI);
        int j = getIndexOfNode(nodeJ);
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

        if (nodeI == head) {
            head = nodeJ;
        } else if (nodeJ == head) {
            head = nodeI;
        }

        if (nodeI == tail) {
            tail = nodeJ;
        } else if (nodeJ == tail) {
            tail = nodeI;
        }

        Collections.swap(indicesList, i, j);
    }

    public void twoOpt(int i, int j) {
        while(j >= i) {
            swap(i,j);
            i++;
            j--;
        }
    }

    public void twoOpt(Node nodeI, Node nodeJ) {
        int i = getIndexOfNode(nodeI);
        int j = getIndexOfNode(nodeJ);
        while(j >= i) {
            swap(i,j);
            i++;
            j--;
        }
    }

    public ArrayList<Integer> toList() {
        ArrayList<Integer> list = new ArrayList<>();
        Node current = head;
        if(head == null) {
            return list;
        }
        for(int i = 0; i < numberOfElements; i++) {
            list.add(current.item);
            current = current.next;
        }
        return list;
    }

    /**
     * Print the items of the doubly linked list.
     */
    public void print() {
        Node current = head;
        if(head == null) {
            System.out.println("Doubly linked list is empty");
            return;
        }
        for(int i = 0; i < numberOfElements; i++) {
            System.out.print(current.item + " ");
            current = current.next;
        }
    }
}
