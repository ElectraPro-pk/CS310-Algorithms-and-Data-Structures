/*
 *
 * SinglyLinkedList.java
 */

public class SinglyLinkedList<T>{
    private Node<T> head;
    private int size;

    public SinglyLinkedList(){
        head = new Node<T>(null, null);
    }

    private void insertAfter(Node<T> node, T element){
        Node<T> n = new Node<T>(element, node.next);
        node.next = n;
        size++;
    }

    public boolean add(T o){
        Node<T> current = head;
        while (current.next != null)
            current = current.next;
        insertAfter(current, o);
        size++;
        return true;
    }

    public T get(int index) {
        Node<T> current = head.next; // start after dummy
        int i = 0;
        while (current.next != null && i < index) {
            current = current.next;
            i++;
        }
        if (i == index)
            return current.data;
        else
            throw new IndexOutOfBoundsException();
    }

    public int size(){ return size; }

    public boolean isEmpty() { return size == 0; }

    public static void main(String[] args) {
        SinglyLinkedList<String> list = new SinglyLinkedList<String>();
        list.add("foo");
        list.add("bar");
        list.add("bar");
        System.out.println("list size = " + list.size());
        System.out.println("#0 element = " + list.get(0));
        System.out.println("#1 element = " + list.get(1));
        System.out.println("#2 element = " + list.get(2));
        SinglyLinkedList<Integer> list1 = new SinglyLinkedList<Integer>();
        list1.add(6);
        System.out.println("list1 size = " + list1.size());
        System.out.println("#0 element = " + list1.get(0));
        // this will throw an IndexOutOfBoundsException
        System.out.println("#1 element = " + list1.get(1));
    }
}
class Node<T>{
    T data;
    Node<T> next;

    Node(T data, Node<T> next){
        this.data = data;
        this.next = next;
    }
}
