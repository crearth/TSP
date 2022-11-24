package tsp;

public class DoublyLinkedList implements DoublyLinkedListInterface {
    /**
     * Set head and tail to null
     */
    Node head = null;
    Node tail = null;
    int numberOfElements = 0;

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
            head.previous = tail;
            tail.next = head;
        }
        else if (index == 0) {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
            head.previous = tail;
            tail.next = head;
        }
        else if (index == numberOfElements) {
            newNode.previous = tail;
            tail.next = newNode;
            tail = newNode;
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
        Node temp;
        temp = Node1.next;
        Node1.next = Node2.next;
        Node2.next = temp;

        if (Node1.next != null)
            Node1.next.prev = Node1;
        if (Node2.next != null)
            Node2.next.prev = Node2;

        temp = Node1.prev;
        Node1.prev = Node2.prev;
        Node2.prev = temp;

        if (Node1.prev != null)
            Node1.prev.next = Node1;
        if (Node2.prev != null)
            Node2.prev.next = Node2;
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
