package Graphs;

import java.util.*;
import java.util.stream.Collectors;

public class Graph<E> {
    List<Connections> connected;
    E value;
    boolean visited;

    public Graph(){
        connected = new ArrayList<>();
    }

    public void giveValue(E value){
        this.value = value;
    }

    public Graph(List<Connections> connected){
        this.connected = connected;
    }

    public void updateConnection(Graph to, int weight){
        connected = connected.stream().filter(t -> !t.to.equals(to))
                .collect(Collectors.toList());
        addConnnection(to, weight);
    }


    public void addConnnection(Graph to, int weight){
        connected.add(new Connections(this, to, weight));
    }

    public int isConnectedTo(Graph graphTo){
        return connected.stream().filter(t -> t.to == graphTo)
            .map(t -> t.weight).findFirst().orElse(3000);
    }

    @Override
    public String toString(){
        return value.toString();
    }

    public void makeVisited(Graph<E> v){
        v.visited = false;
        for (Connections con: v.connected){
            if (con.visited = true){
                con.visited = false;
            }
            if (con.to.visited = true) {
                makeVisited(con.to);
            }
        }
    }

}
class Connections{
    Graph from;
    Graph to;
    int weight;
    boolean visited;

    public Connections(Graph from, Graph to, int weight){
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}
class operations{

    public static void main(String[] args){
        List<Graph<Character>> nodes = new ArrayList<>();
        nodes.add(new Graph<>());
        nodes.add(new Graph<>());
        nodes.add(new Graph<>());
        nodes.add(new Graph<>());
        nodes.get(0).addConnnection(nodes.get(1), 2);
        nodes.get(0).addConnnection(nodes.get(2), 3);
        nodes.get(1).addConnnection(nodes.get(2), 4);
        nodes.get(2).addConnnection(nodes.get(3), 1);


        for (int i = 0; i < nodes.size(); i++){
            nodes.get(i).giveValue((char) ((int) 'A' + i));
        }

        Algorithms algorithm4 = new Algorithms(nodes);

        algorithm4.floydsAlgorithm(true);
        System.out.println(algorithm4.toString());
        algorithm4.floydsAlgorithm(false);
        System.out.println(algorithm4.toString());
        algorithm4.widestPathProblem(true);
        System.out.println(algorithm4.toString());
        algorithm4.widestPathProblem(false);
        System.out.println(algorithm4.toString());
        algorithm4.bfs();
        System.out.println(algorithm4.toString(false));
        algorithm4.dfs();
        System.out.println(algorithm4.toString(false));
        algorithm4.primms();
        System.out.println(algorithm4.toString(false));

        nodes = new ArrayList<>();
        nodes.add(new Graph<>());
        nodes.add(new Graph<>());
        nodes.add(new Graph<>());
        nodes.add(new Graph<>());
        nodes.add(new Graph<>());
        nodes.add(new Graph<>());
        nodes.add(new Graph<>());
        nodes.get(0).addConnnection(nodes.get(1), 40);
        nodes.get(0).addConnnection(nodes.get(2), 8);
        nodes.get(0).addConnnection(nodes.get(3), 11);
        nodes.get(1).addConnnection(nodes.get(2), 29);
        nodes.get(1).addConnnection(nodes.get(4), 17);
        nodes.get(2).addConnnection(nodes.get(3), 3);
        nodes.get(2).addConnnection(nodes.get(4), 31);
        nodes.get(3).addConnnection(nodes.get(5), 46);
        nodes.get(4).addConnnection(nodes.get(5), 40);
        nodes.get(4).addConnnection(nodes.get(6), 53);
        nodes.get(5).addConnnection(nodes.get(6), 15);

        for (int i = 0; i < nodes.size(); i++){
            nodes.get(i).giveValue((char) ((int) 'A' + i));
        }

        Algorithms algorithm7 = new Algorithms(nodes);

        algorithm7.floydsAlgorithm(true);
        System.out.println(algorithm7.toString());
        algorithm7.floydsAlgorithm(false);
        System.out.println(algorithm7.toString());
        algorithm7.widestPathProblem(true);
        System.out.println(algorithm7.toString());
        algorithm7.widestPathProblem(false);
        System.out.println(algorithm7.toString());
        algorithm7.dfs();
        System.out.println(algorithm7.toString(false));
        algorithm7.bfs();
        System.out.println(algorithm7.toString(false));
        algorithm7.primms();
        System.out.println(algorithm7.toString(false));

    }
}

class Algorithms{
    List<Graph<Character>> nodes;
    int[][] matrix;

    public Algorithms(List<Graph<Character>> nodes){
        this.nodes = nodes;
    }

    public void floydsAlgorithm(boolean isDirected){
        if(isDirected) {
            toMatrixUnDirected();
        } else {
            toMatrixDirected();
        }
        nodes.get(0).makeVisited(nodes.get(0));

        for (int k = 0; k < matrix.length; k++){
            for (int i = 0; i < matrix.length; i++){
                for (int j = 0; j < matrix.length; j++){
                    matrix[i][j] = Math.min(matrix[i][j],matrix[i][k] + matrix[j][k]);
                }
            }
        }
    }

    public void widestPathProblem(boolean isDirected){
        //Change definition of graph from:
        if(isDirected) {
            toMatrixUnDirected();
        } else {
            toMatrixDirected();
        }
        nodes.get(0).makeVisited(nodes.get(0));

        for (int y = 0; y < matrix.length; y++){
            for (int x = 0; x < matrix.length; x++){
                if (matrix[y][x] == 3000) matrix[y][x] = 0;
            }
        }

        for (int k = 0; k < matrix.length; k++){
            for (int i = 0; i < matrix.length; i++){
                for (int j = 0; j < matrix.length; j++){
                    matrix[i][j] = Math.max(matrix[i][j],Math.min(matrix[i][k], matrix[j][k]));
                }
            }
        }
    }

    public void toMatrixDirected(){
        int[][] matrix = new int[nodes.size()][nodes.size()];

        for (int from = 0; from < nodes.size(); from++){
            for (int to = 0; to < nodes.size(); to++){
                int weight = nodes.get(from).isConnectedTo(nodes.get(to));
                matrix[from][to] = weight;
                weight = nodes.get(to).isConnectedTo(nodes.get(from));
                matrix[to][from] = weight;
            }
        }

        for (int x = 0; x < nodes.size(); x++){
            matrix[x][x] = 0;
        }

        this.matrix = matrix;
    }

    public void toMatrixUnDirected(){
        int[][] matrix = new int[nodes.size()][nodes.size()];

        for (int from = 0; from < nodes.size(); from++){
            for (int to = 0; to < nodes.size(); to++){
                int weight = Math.min(nodes.get(from).isConnectedTo(nodes.get(to)),
                        nodes.get(to).isConnectedTo(nodes.get(from)));
                matrix[from][to] = weight;
                matrix[to][from] = weight;
            }
        }

        for (int x = 0; x < nodes.size(); x++){
            matrix[x][x] = 0;
        }

        this.matrix = matrix;
    }

    public String toString(boolean isDirected){
        if(isDirected) {
            toMatrixUnDirected();
        } else {
            toMatrixDirected();
        }
        return toString();
    }

    public String toString() {
        String out = "    ";
        String bottomTop;
        for (int i = 0; i < matrix.length; i++){
            out += " " + (char) ((int) 'A' + i) + " |";
            /*if ( i <= 9 ){
                out += " " + i + " |";
            } else if (i <= 99){
                out += " " + i + "|";
            } else {
                out += i + "|";
            }*/
        }
        String bar = "   ";
        for (int i = 0; i < matrix.length; i++){
            bar += "----";
        }
        bar += "   ";
        bottomTop = out;
        out += "\n" + bar + "\n";

        for(int i = 0; i < matrix.length; i++){
            out += (char) ((int) 'A' + i) + " |";
            for (int x: matrix[i]){
                if (x == 3000){
                    out += "| \u221E ";
                } else {
                    if ( x <= 9 ){
                        out += "| " + x + " ";
                    } else if (x <= 99){
                        out += "| " + x + "";
                    } else {
                        out += "|" + x + "";
                    }
                }
            }
            out += "| " + (char) ((int) 'A' + i) + "\n";
        }

        out += bar + "\n" + bottomTop;
        out += "\n" ;

        return out;
    }

    public void dfs(){
        nodes.get(0).makeVisited(nodes.get(0));
        Stack<Graph> stack = new Stack<>();
        List<Graph> output = new ArrayList<>();
        stack.push(nodes.get(0));

        while (!stack.empty()){
            Graph<Character> temp = stack.pop();
            temp.visited = true;

            output.add(temp);
            System.out.println(temp.toString());

            for (Connections y : temp.connected){
                if (!y.to.visited){
                    stack.push(y.to);
                }
            }
        }
    }

    public void bfs(){
        Queue<Graph> queue = new ArrayDeque<>();
        nodes.get(0).makeVisited(nodes.get(0));

        String out = "";

        List<Graph> output = new ArrayList<>();
        queue.add(nodes.get(0));

        while (!queue.isEmpty()){
            Graph<Character> temp = queue.remove();

            output.add(temp);
            out += temp.toString() + "->";

            for (Connections y : temp.connected){
                if (!y.to.visited){
                    y.to.visited = true;
                    queue.add(y.to);
                }
            }
        }
        System.out.println(out);

    }

    public void primms(){
        Graph<Character> start = nodes.get(0);
        nodes.get(0).makeVisited(nodes.get(0));

        List<Connections> output = new ArrayList<>();
        List<Graph> visited = new ArrayList<>();
        visited.add(start);
        start.visited = true;

        String out = "Primms: \n";

        while (visited.size() != nodes.size()){
            int min = Integer.MAX_VALUE;
            Connections minConnection = null;

            for (Graph<Character> g: visited){
                for (Connections to: g.connected){
                    if (to.weight < min && !to.to.visited){
                        min = to.weight;
                        minConnection = to;
                    }
                }
            }

            //DoThing
            out += minConnection.from + " -> " + minConnection.to + "\n";
            visited.add(minConnection.to);
            minConnection.to.visited = true;
            output.add(minConnection);
        }

        System.out.println(out);
    }

    public void kruskals(){
        List<Connections> connections = new ArrayList<>();
        toMatrixUnDirected();
        nodes.set(0, tableToGraph());
        Graph<Character> start = nodes.get(0);

        //Sorts list of connections
        {
            Stack<Graph> traversal = new Stack<>();
            traversal.push(start);

            while (!traversal.empty()) {
                Graph<Character> g = traversal.pop();
                for (Connections c : g.connected) {
                    if (!c.visited) {
                        if (c.weight != 3000) {
                            connections.add(c);
                            if (!c.to.visited) {
                                traversal.push(c.to);
                            }
                        }
                    }
                }
            }
            connections.sort((a, b) -> (new Integer(a.weight)).compareTo(b.weight));
        }

        start.makeVisited(start);

        String out = "Kruskals:\n";

        Map<Graph, Graph> toMakeVisited = new HashMap<>();

        int[][] empty = new int[nodes.size()][nodes.size()];
        for (int y = 0; y < empty.length; y ++){
            for (int x = 0; x < empty.length; x++){
                if (y == x) {
                    empty[y][x] = 0;
                } else {
                    empty[y][x] = 3000;
                }
            }
        }
        Graph<Character> forests = tableToGraph();

        Map<Character, Graph<Character>> forest = new HashMap<>();
        forest.put(forests.value, forests);

        for (Connections c: forests.connected){
            forest.put((char) c.to.value, c.to);
        }

        //Can make more efficient by counting number of vertices
        for (Connections c: connections){

            forest.get(c.from.value).updateConnection(forest.get(c.to.value), c.weight);

            boolean contains = true;

            for (Graph<Character> g : forest.values()){
                contains = contains && containsCycle(g);
            }

            if (contains){
                forest.get(c.from.value).updateConnection(forest.get(c.to.value), 3000);
            } else {
                out += c.from.toString() + "->" + c.to.toString() + "\n";
            }
        }
        System.out.println(out);
        //System.out.println(connections.stream().map(a -> a.from.toString() + " -> " + a.to.toString() + "\n").reduce(String::concat).get());
    }

    //An adaption of depth first search
    public boolean containsCycle(Graph x){
        Stack<Graph> stack = new Stack<>();
        nodes.get(0).makeVisited(nodes.get(0));
        stack.push(x);

        while (!stack.empty()){
            Graph<Character> temp = stack.pop();
            temp.visited = true;

            for (Connections y : temp.connected){
                if (y.weight != 3000) {
                    if (y.to.visited) {
                        //System.out.println(y.from.toString() + "->" + y.to.toString());
                        return true;
                    } else {
                        stack.push(y.to);
                    }
                }
            }
        }
        return false;
    }

    public Graph tableToGraph(){
        List<Graph<Character>> graphs = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++){
            graphs.add(new Graph<>());
            graphs.get(i).giveValue((char) ((int) 'A' + i));
        }

        for (int y = 0; y < matrix.length; y++){
            for (int x = 0; x < matrix.length; x++){
                if (matrix[y][x] != 0){
                    graphs.get(y).addConnnection(graphs.get(x), matrix[y][x]);
                }
            }
        }
        return graphs.get(0);
    }

}