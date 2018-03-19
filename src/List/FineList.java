package List;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FineList<E extends Comparable> {

    public static void main(String[] args) {
        FineList<Integer> li = new FineList<>();

        List<Thread> lis = new ArrayList<>();
        for (int i = 1; i < 8; i++){
            final int l = i;
            lis.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int x = 1; x < 100; x++){
                        li.add(x * 2);
                    }
                }
            }));
        }
        lis.forEach(Thread::run);
        try {
            lis.forEach(t -> {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
        List<Thread> list = new ArrayList<>();
        for (int i = 1; i < 7; i++){
            final int l = i;
            list.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int x = 1; x < 100; x++){
                        li.remove(x * 2);
                    }
                }
            }));
        }
        list.forEach(Thread::run);

        System.out.println(li.toString());
    }


    private final LockedNode<E> head;
    private int size;

    public FineList(){
        head = new LockedNode<>();
        size = 0;
    }

    public boolean add(E val){
        if (size == 0){
            head.next = new LockedNode<>(val, null, true);
            size++;
            return true;
        }
        LockedNode<E> pred = head.next;
        LockedNode<E> curr = pred.next;
        try {
            pred.lock();
            if (curr != null)
                curr.lock();
            while (!pred.value.equals(val) && curr != null) {
                pred.unlock();
                pred = curr;
                curr = pred.next;
                if (curr != null)
                    curr.lock();
            }
            pred.next = new LockedNode<>(val, curr, true);
            size++;
            return true;
        } finally {
            pred.unlock();
            if (curr!= null)
                curr.unlock();
        }
    }

    public LockedNode<E> find(E val){
        LockedNode<E> pred = head;
        LockedNode<E> curr = head.next;
        try {
            pred.lock();
            curr.lock();
            while (!pred.value.equals(val) && curr != null) {
                pred.unlock();
                pred = curr;
                curr = pred.next;
                curr.lock();
            }
            return pred;
        } finally {
            pred.unlock();
            curr.unlock();
        }
    }

    public boolean remove(E val){
        LockedNode<E> pred = head.next;
        LockedNode<E> curr = pred.next;
        try {
            pred.lock();
            if (curr != null)
                curr.lock();
            while (!pred.value.equals(val) && curr != null) {
                pred.unlock();
                pred = curr;
                curr = pred.next;
                if (curr != null)
                    curr.lock();
            }
            if (curr != null)
                pred.next = curr.next;
            else
                return false;
            size--;
            return true;
        } finally {
            pred.unlock();
            if (curr != null)
                curr.unlock();
        }
    }

    public String toString(){
        LockedNode node = head.next;
        String output = "";
        while (node != null){
            output += node.value.toString() + ", ";
            node = node.next;
        }
        return output;
    }

}

class LockedNode<E>{
    E value;
    LockedNode<E> next;
    boolean isVal;
    Lock lock = new ReentrantLock();

    public LockedNode(E val, LockedNode<E> next, boolean isVal){
        this.value = val;
        this.next = next;
        this.isVal = isVal;
    }
    public LockedNode(){
        this.isVal = false;
        this.next = null;
        this.value = null;
    }

    public void lock(){
        lock.lock();
    }

    public void unlock(){
        lock.unlock();
    }
}