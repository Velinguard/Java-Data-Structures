public class LinkedLists {

    public static void main(String[] args){
        LinkedList ll = new LinkedList(new Integer(2));
        Object o = new Integer(3);

        ll.addNode(new Node(o, null));
        ll.addNode(new Node(new Integer(2), null));
        ll.addNode(new Node(new Integer(4), null));
        ll.addNodeAt(new Node(new Integer(5), null), o);
        ll.addAtHead(4);

        ll.print();
        System.out.println(ll.length());
    }

}

class LinkedList{
    private Node head;

    public LinkedList(Object o){
        head = new Node(o, null);
    }

    public LinkedList(Node n){
        head = n;
    }

    public void addAtHead (Object o){
        head = new Node (o, head);
    }

    public void addNodeAt (Node nd, Object o){
        head = addNodeHelper(head, nd, o);
    }

    public void addNode(Node nd){
        head = addNodeHelper(head, nd, null);
    }

    public int length(){
        Node head = this.head;
        int counter = 0;
        while (head.getNextNode() != null) {
            head = head.getNextNode();
            counter++;
        }
        return counter + 1;
    }

    private Node addNodeHelper(Node head, Node nd, Object o){
        if (head.getNextNode() == null){
            head.setNextNode(nd);
            return head;
        } else if (head.getData().equals(o)){
            nd.setNextNode(head.getNextNode());
            head.setNextNode(nd);
            return head;
        }
        head.setNextNode(addNodeHelper(head.getNextNode(), nd, o));
        return head;
    }

    public Node getNode(Object o){
        Node head = this.head;
        while (!o.equals(head.getData())) {
            if (head.getNextNode() == null) {
                return null;
            }
            head = head.getNextNode();
        }
        return head;
    }

    public void print(){
        Node head = this.head;
        System.out.print("[");
        do {
            System.out.print(head.getData().toString() + ", ");
            head = head.getNextNode();
        } while (head.getNextNode() != null);
        System.out.print(head.getData() + "]\n");
    }

}

class Node{
    private final Object data;
    private Node nextNode;

    public Node(){
        this.data = null;
        this.nextNode = null;
    }

    public Node(Object data, Node nextNode){
        this.data = data;
        this.nextNode = nextNode;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public Object getData() {
        return data;
    }

    public Node setData(Object data) {
        return new Node(data, nextNode);
    }
}
