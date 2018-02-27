package List;

public class Tester {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>(new Integer[] {0,1,2,4,3,1,2});

        list.add(new Integer(3));
        list.remove(1);
        System.out.println(list.toString());
    
    }
}
