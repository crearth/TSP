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
            head.previous = null;
            head = tail = newNode;
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

    //print all the nodes of doubly linked list
    public void printNodes() {
        //Node current will point to head
        Node current = head;
        if(head == null) {
            System.out.println("Doubly linked list is empty");
            return;
        }
        System.out.println("Nodes of doubly linked list: ");
        while(current != null) {
            //Print each node and then go to next.
            System.out.print(current.item + " ");
            current = current.next;
        }
    }
}
