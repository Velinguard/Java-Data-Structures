package List;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LinkedList<E> implements List<E> {
    private Node<E> head;
    private int size;

    public LinkedList(){
        head = null;
        size = 0;
    }

    public LinkedList(E[] values){
        size = 0;
        head = new Node<E>(values[0]);
        for(int i = 1; i < values.length; i++){
            add(values[i]);
        }
        assert (this.size == values.length);
    }

    public boolean removeLast(){
        assert(!isEmpty());

        getNode(size - 2).removeHead();
        return true;
    }

    public Node<E> getNode(int index) throws ArrayIndexOutOfBoundsException{
        assert(!isEmpty());

        Node<E> node = head;
        for (; index > 0; index--){
            node = head.getHead();
        }
        return node;
    }

    @Override
    public E get(int index) throws ArrayIndexOutOfBoundsException{
        assert(!isEmpty());
        return getNode(index).get();
    }

    @Override
    public boolean add(E e){
        int size = this.size;
        Node node = head;
        for(; size > 0; size--){
            node = node.getHead();
        }
        node.addHead(new Node(e));
        this.size++;
        return true;
    }

    @Override
    public void add(int index, E element){
        Node node = head;
        Node previous = null;
        for(; index >= 0; index--){
            previous = node;
            node = node.getHead();
        }
        previous.addHead(new Node(element));
        previous.getHead().addHead(node);
        size++;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        assert(!isEmpty());

        Node<E> node = head;
        int size = this.size;
        while (size > 0){
            if (node.get().equals(o)){
                return true;
            }
            node = node.getHead();
            size--;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        assert(!isEmpty());

        int size = this.size;
        Node node = head;
        Node previous = null;
        while(size > 0 && !node.get().equals(o)){
            previous = node;
            node = node.getHead();
            size--;
        }
        previous.addHead(node.getHead());

        this.size--;
        return true;
    }

    @Override
    public void clear() {
        head.removeHead();
        size = 0;
    }

    @Override
    public E set(int index, E element) {
        assert(!isEmpty());

        Node node = head;
        for(; index >= 0; index--){
            node = node.getHead();
        }
        node.update(element);
        return element;
    }

    @Override
    public E remove(int index) {
        assert(!isEmpty());

        Node<E> node = head;
        Node previous = null;
        while(index > 0){
            previous = node;
            node = node.getHead();
            index--;
        }
        E output = node.get();
        previous.addHead(node.getHead());
        size--;
        return output;
    }

    /**
     *
     * @param object
     * @return index
     * @throws ArrayIndexOutOfBoundsException
     */
    @Override
    public int indexOf(Object o) {
        assert(!isEmpty());

        Node node = head;
        while(!node.get().equals(o)){
            node = node.getHead();
            size--;
        }
        return this.size;
    }

    @Override
    public int lastIndexOf(Object o) {
        assert(!isEmpty());

        Node node = head;
        int lastIndex = -1;
        for(int i = 0; i < size; i++){
            if (node.get().equals(o))
                lastIndex = i;
            node = node.getHead();
        }
        return lastIndex;
    }

    @Override
    public String toString(){
        String output = "[";
        int size = this.size;
        Node node = head;
        for(; size > 0; size--){
            output += node.get().toString() + ", ";
            node = node.getHead();
        }
        output += node.get().toString() + "]";
        return output;
    }

    // <editor-fold defaultstate="collapsed" desc=" ${Not Used} "> ${selection line}${cursor}
    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return a;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }
    // </editor-fold>
}
class Node<E> {
    private E data;
    private Node head;
    public Node(E data){
        this.data = data;
        this.head = null;
    }

    /**
     * Changed head of the node, used to init head.
     * @param head
     */
    public void addHead(Node head){
        this.head = head;
    }

    public void removeHead(){
        addHead(null);
    }

    /**
     * Gets next node, if one exists.
     * @return head
     * @throws ArrayIndexOutOfBoundsException
     */
    public Node getHead() throws ArrayIndexOutOfBoundsException{
        if (head.equals(null)){
            throw new ArrayIndexOutOfBoundsException();
        }
        return head;
    }

    public E get(){
        return data;
    }

    public void update(E data){
        this.data = data;
    }

}