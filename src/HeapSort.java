import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class HeapSort {

    private final BinaryNode sorted;

    public HeapSort(Stack<Comparable> li){
        Tree t = new Empty();
        do{
            t = t.addNode(new Leaf (li.pop()));
        } while (!li.isEmpty());
        System.out.println(t.toString());
        this.sorted = (BinaryNode) t;
    }

    public String toString(){
        return sorted.toString();
    }

    public String toString(List<Comparable> st){
        return "["
            + st.stream().filter(item -> item != null).reduce((x, y) -> x + "," + y).orElse("")
            + "]";
    }

    public static void main(String[] args){
        Tree node = new BinaryNode(3, new Leaf(2), new Leaf(5));
        node = node.addNode(new Leaf(7));
        node = node.addNode(new Leaf(6));
        node = node.addNode(new Leaf (3));
        node = node.addNode(new Leaf (12));
        System.out.println(node.toString());

        Stack<Comparable> li = new Stack<>();
        li.push(1); li.push(8); li.push(3); li.push(12); li.push(4);

        HeapSort sorter = new HeapSort(li);
        System.out.println(sorter.toString(sorter.sorted.breadthSearch()));
        System.out.println(sorter.toString(sorter.sorted.depthSearch()));
    }
}

interface Tree{
    void makeNotVisited();
    Boolean getIsVisited();
    void makeVisited();
    Comparable getValue();
    Tree addNode(Tree t);
    String toString();
}

class BinaryNode implements Tree{
    private final Tree smaller;
    private final Tree bigger;
    private final Comparable data;
    private boolean isVisited;

    public BinaryNode(Comparable data, Tree smaller, Tree bigger){
        this.smaller = smaller;
        this.bigger = bigger;
        this.data = data;
        this.isVisited = false;
    }

    public void makeVisited(){isVisited = true;}
    public Boolean getIsVisited(){return isVisited;}

    public Comparable getValue() {
        return data;
    }

    public BinaryNode addNode(Tree t){
        if (t.getValue().compareTo(this.data) == 1) {
            if (bigger.getClass() == Empty.class) {
                return new BinaryNode(data, this.smaller, t);
            } else {
                return new BinaryNode(data, this.smaller, bigger.addNode(t));
            }
        } else {
            if (smaller.getClass() == Empty.class) {
                return new BinaryNode(data, t, this.bigger);
            } else {
                return new BinaryNode(data, smaller.addNode(t), this.bigger);
            }
        }
    }

    @Override
    public String toString(){
        if (smaller.getClass() == Empty.class){
            return data.toString() + bigger.toString();
        } else if (bigger.getClass() == Empty.class){
            return smaller.toString() + data.toString();
        }
        return smaller.toString() + data.toString() + bigger.toString();
    }

    @Override
    public void makeNotVisited() {
        this.isVisited = false;
        smaller.makeNotVisited();
        bigger.makeNotVisited();
    }

    public List<Comparable> depthSearch(){
        makeNotVisited();
        Stack<Tree> st = new Stack<>();
        List<Comparable> li = new LinkedList<>();
        st.add(this);

        while (!st.empty()){
            Tree parent = st.pop();
            if (parent.getClass() == BinaryNode.class){
                if(!((BinaryNode) parent).smaller.getIsVisited()){
                    st.push(((BinaryNode) parent).smaller);
                }
                if(!((BinaryNode) parent).bigger.getIsVisited()){
                    st.push(((BinaryNode) parent).bigger);
                }
            } else {
                if (!parent.getIsVisited()) {
                    li.add(parent.getValue());
                    parent.makeVisited();
                }
            }

        }
        return li;
    }


    public List<Comparable> breadthSearch(){
        makeNotVisited();
        Queue<Tree> qu = new LinkedList<>();
        List<Comparable> li = new LinkedList();
        qu.add(this);
        this.isVisited = true;
        li.add(data);
        while (!qu.isEmpty()){
            Tree parent = qu.remove();
            if (parent.getClass() == BinaryNode.class){
                BinaryNode parentN = (BinaryNode) parent;
                if (!parentN.smaller.getIsVisited()){
                    qu.add(parentN.smaller);
                    li.add(parentN.smaller.getValue());
                    parentN.smaller.makeVisited();
                }
                if (!parentN.bigger.getIsVisited()){
                    qu.add(parentN.bigger);
                    li.add(parentN.bigger.getValue());
                    parentN.bigger.makeVisited();
                }
            }
        }
        return li;
    }
}

class Leaf implements Tree{
    private final Comparable data;
    private boolean isVisited;

    public Leaf (Comparable data){
        this.data = data;
        this.isVisited = false;
    }

    @Override
    public void makeNotVisited() {
        this.isVisited = false;
    }

    public void makeVisited(){isVisited = true;}
    public Boolean getIsVisited(){return isVisited;}


    public String toString(){
        return data.toString();
    }

    public Comparable getValue() {
        return data;
    }

    public BinaryNode addNode(Tree t){
        if (t.getValue().compareTo(this.data) == 1){
            return new BinaryNode(data, new Empty(), t);
        } else {
            return new BinaryNode(data, t, new Empty());
        }
    }
}

class Empty implements Tree{

    public Empty(){

    }

    @Override
    public void makeNotVisited() {

    }
    public void makeVisited(){}
    public Boolean getIsVisited(){return false;}
    public String toString(){
        return "";
    }
    @Override
    public Comparable getValue() {
        return null;
    }
    @Override
    public Tree addNode(Tree t) {
        return t;
    }
}