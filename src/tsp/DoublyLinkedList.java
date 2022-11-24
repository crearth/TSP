package tsp;

public class DoublyLinkedList implements DoublyLinkedListInterface {
    /**
     * Set head and tail to null
     */
    Node head = null;
    Node tail = null;

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

    /**
     * Add an item to the end of list
     * @param item
     */
    public void insertItemEnd(int item) {
        //Create a new node
        Node newNode = new Node(item);

        //if list is empty, head and tail points to newNode
        if(head == null) {
            head = tail = newNode;
            head.previous = null;
            tail.next = null;
        }
        else {
            //add newNode to the end of list. tail->next set to newNode
            tail.next = newNode;
            //newNode->previous set to tail
            newNode.previous = tail;
            //newNode becomes new tail
            tail = newNode;
            //tail's next point to null
            tail.next = null;
        }
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
