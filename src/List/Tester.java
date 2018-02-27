package List;

import java.util.ArrayList;
import java.util.List;

public class Tester {
    public static void main(String[] args) {
        List<Thread> thread = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Thread th = new Thread(new runner());
            th.run();
            thread.add(th);
        }

        for (int i = 0; i < thread.size(); i++){
            try {
                thread.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
class runner implements Runnable{
    LinkedList<Integer> list;

    public runner(){
        list = new LinkedList<>(new Integer[] {0, 1, 2});
    }

    @Override
    public void run(){
        int i = 1000;
        for (; i > 0; i--) {
            //list.add(new Integer(3));
            list.add(new Integer(4));
            list.remove(2   );
            list.add(new Integer(2));
            list.add(new Integer(3));
            list.remove(1);
            list.remove(1);
        }
        output();
        /*
        LinkedList<String> list2 = new LinkedList<>(new String[] {"Hello", "How", "Are"});

        list2.add("You");
        list2.add(1, ",");
        System.out.println(list2.toString());*/
    }
    public void output(){
        System.out.println(list.toString());
    }
}
