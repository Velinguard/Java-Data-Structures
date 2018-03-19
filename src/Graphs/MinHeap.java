package Graphs;

/**
 * A Priority Min Pseudo Heap Implementation naively, but effectively, not a
 * true heap as it is not perfectly balanced, but in most situations will work.
 *
 * Notation:
 * The function
 *  minHeap(NODE)
 * is defined as:
 *  ∀ node : nodes (from NODE)
 *      {node.value <= node.left.value && node.value <= node.right.value
 *      || node.left == null && node.right == null
 *      || node.left == null && node.value <= node.right.value
 *      || node.value <= node.left.value && node.right == null}
 * and
 * The function
 *  minHeap(NODEFROM, NODETO)
 * is defined as:
 *  ∀ node : nodes (from NODEFROM) (to NODETO)
 *      {node.value <= node.left.value && node.value <= node.right.value
 *      || node.left == null && node.right == null
 *      || node.left == null && node.value <= node.right.value
 *      || node.value <= node.left.value && node.right == null}
 *
 * @param <T>
 */

public class MinHeap<T extends Comparable> {
    public Node<T> root;
    private boolean hasChanged;

    public MinHeap(T root){
        this.root = new Node<>(root);
    }

    //Testing function
    public static void main(String[] args) {
        MinHeap<Integer> heap = new MinHeap<>(4);
        heap.root.print();
        for (int i = 0; i < 500; i++){
            heap.add(i);
        }/*
        for (int i = 0; i < 500 - 2; i++) {
            heap.remove(heap.root.value);
        }*/
        heap.root.print();
    }

    /**
     * PRE: value != null && value instance of Comparable
     * POST: minHeap(root)
     * @param value
     */
    public void add(T value){
        Node temp = root;

        if (root == null){
            root = new Node<>(value);
            return;
        }

        //Add checks only children, root must be explicitly checked first.
        if (value.compareTo(temp.value) == -1) {
            root = swap(temp, new Node<>(value));
            return;
        }

        addHelper(temp, value);
    }

    /**
     * PRE: from != null && to != null && minHeap(from) && minHeap(to)
     * POST: r = to && minHeap(to')
     * @param from
     * @param to
     * @return
     */
    private Node<T> swap(Node<T> from, Node<T> to){
        if (!from.hasRight()){
            to.setLeft(from);
            to.setRight(from.right);
            from.setRight(null);
            return to;
        }
        if (!from.hasLeft()){
            to.setRight(from);
            to.setLeft(from.left);
            from.setLeft(null);
            return to;
        }
        to.setLeft(from);
        return to;
    }

    /**
     * PRE: temp != null && value != null && minHeap(root, temp)
     * POST: minHeap(root, temp'.left) && minHeap(root, temp'.right)
     * @param temp
     * @param value
     * @return
     */
    private boolean addHelper(Node<T> temp, T value){
        if (!temp.hasLeft() || !temp.hasRight()){
            if(temp.value.compareTo(value) == 1) {
                Node t = new Node(temp);
                swap(t, new Node<>(value));
                return true;
            }
            if (!temp.hasRight()){
                temp.setRight(new Node(value));
                return true;
            }
            temp.setLeft(new Node<>(value));
            return true;
        }
        if (value.compareTo(temp.left.value) == -1){
            swap(temp.left, new Node<>(value));
            return true;
        } else if (value.compareTo(temp.right.value) == -1){
            swap(temp.right, new Node<>(value));
            return true;
        }
        //By randomly selecting between adding to left and right branch first we
        //are able to actually balance the heap!
        if (Math.random() < 0.5) {
            if (addHelper(temp.right, value))
                return true;
            return addHelper(temp.left, value);
        }
        if (addHelper(temp.left, value))
            return true;
        return addHelper(temp.right, value);
    }

    /**
     * PRE: minHeap(root) && ∃ node:Nodes (from root) {node.value.equals(value)}
     * POST: minHeap(root) && ∄ node:Nodes (from root) {node.value.equals(value}
     *      && root' ≈ root \ {value}
     * @param value
     */
    public void remove(T value){
        hasChanged = false;
        root = removeHelper(root, value);
    }

    public Node<T> removeHelper(Node<T> node, T value){
        if (node == null)
            return null;
        if (node.value.equals(value)){
            node = new Node<T>(filterDown(node.left, node.right));
            hasChanged = true;
            return node;
        }
        node.left = removeHelper(node.left, value);
        if (!hasChanged)
            node.right = removeHelper(node.right, value);
        return node;
    }


    /**
     * PRE: minHeap(left) && minHeap(right)
     * POST: r = node && left < right -> node = left
     *      && right <= left -> node = right
     *      && minHeap(node)
     *
     * @param left
     * @param right
     * @return
     */
    private Node<T> filterDown(Node<T> left, Node<T> right){
        if (left == null)
            return right;
        if (right == null)
            return left;
        Node<T> other;
        if (left.value.compareTo(right.value) == -1){
            //left < right
            if (left.right == null){
                left.setRight(right);
                return left;
            }
            other = new Node<T>(left.right);
            left.setRight(right);
            left.setLeft(filterDown(left.left, other));
            return left;
        }
        if (right.left == null){
            right.left = left;
            return right;
        }
        other = new Node<T>(right.left);
        right.setLeft(left);
        right.setRight(filterDown(right.right, other));
        return right;
    }

    @Override
    public String toString(){
        return toStringHelper(root, 0);
    }

    private String toStringHelper(Node<T> current, int level) {
        String st = "";
        level++;
        if (current.hasLeft())
            st += toStringHelper(current.left, level) + ", ";
        st += current.value + "(At level: " + (level - 1) + "), ";
        if (current.hasRight())
            st += toStringHelper(current.right, level) + ", ";
        return st;
    }

}

class Node<T extends Comparable>{
    Node<T> left;
    Node<T> right;
    T value;

    public Node (T value){
        this.value = value;
        this.left = null;
        this.right = null;
    }

    /**
     * Clones the input Node.
     * PRE:
     * POST: node'.equals(node) && node' != node
     *
     * @param node
     */
    public Node (Node<T> node){
        if (node == null)
            return;
        this.value = node.value;
        this.left = node.left;
        this.right = node.right;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public boolean hasRight(){
        return right != null;
    }

    public boolean hasLeft(){
        return left != null;
    }

    /**
     * PRE:
     * POST: String representing the tree.
     *
     */
    public void print() {
        print("", true);
    }

    private void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + value.toString());
        if (hasLeft())
            left.print(prefix + (isTail ? "    " : "│   "), false);
        if (hasRight()){
            right.print(prefix + (isTail ?"    " : "│   "), true);
        }
    }


}