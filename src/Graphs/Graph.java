package Graphs;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    List<Connections> connected;

    public Graph(){
        connected = new ArrayList<>();
    }
    public Graph(List<Connections> connected){
        this.connected = connected;
    }


    public void addConnnection(Graph to, int weight){
        connected.add(new Connections(this, to, weight));
    }

    public int isConnectedTo(Graph graphTo){
        return connected.stream().filter(t -> t.to == graphTo)
            .map(t -> t.weight).findFirst().orElse(3000);
    }

}
class Connections{
    Graph from;
    Graph to;
    int weight;

    public Connections(Graph from, Graph to, int weight){
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}
class operations{

    public static void main(String[] args){
        ArrayList<Graph> nodes = new ArrayList<>();
        nodes.add(new Graph());
        nodes.add(new Graph());
        nodes.add(new Graph());
        nodes.add(new Graph());
        nodes.get(0).addConnnection(nodes.get(1), 2);
        nodes.get(0).addConnnection(nodes.get(2), 3);
        nodes.get(1).addConnnection(nodes.get(2), 4);
        nodes.get(2).addConnnection(nodes.get(3), 1);

        System.out.println(toStrings(toMatrixDirected(nodes)));
        System.out.println(toStrings(floydsAlgorithm(toMatrixDirected(nodes))));
        System.out.println(toStrings(widestPathProblem(toMatrixDirected(nodes))));
        System.out.println("------------");
        System.out.println(toStrings(toMatrixUnDirected(nodes)));
        System.out.println(toStrings(floydsAlgorithm(toMatrixUnDirected(nodes))));
        System.out.println(toStrings(widestPathProblem(toMatrixUnDirected(nodes))));
        System.out.println("------------");

        nodes = new ArrayList<>();

        nodes.add(new Graph());
        nodes.add(new Graph());
        nodes.add(new Graph());
        nodes.add(new Graph());
        nodes.add(new Graph());
        nodes.add(new Graph());
        nodes.add(new Graph());
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

        System.out.println(toStrings(toMatrixUnDirected(nodes)));
        System.out.println(toStrings(floydsAlgorithm(toMatrixUnDirected(nodes))));
        System.out.println(toStrings(widestPathProblem(toMatrixUnDirected(nodes))));

    }

    public static int[][] floydsAlgorithm(int[][] matrix){
        for (int k = 0; k < matrix.length; k++){
            for (int i = 0; i < matrix.length; i++){
                for (int j = 0; j < matrix.length; j++){
                    matrix[i][j] = Math.min(matrix[i][j],matrix[i][k] + matrix[j][k]);
                }
            }
        }
        return matrix;
    }

    public static int[][] widestPathProblem(int[][] matrix){
        //Change definition of graph from:
        //if (!path.exists) then (arc = inf) else (arc = path); to:
        //if (!path.exists) then (arc = 0) else (arc = path);
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

        return matrix;
    }


    public static int[][] toMatrixDirected(ArrayList<Graph> nodes){
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

        return matrix;
    }

    public static int[][] toMatrixUnDirected(ArrayList<Graph> nodes){
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

        return matrix;
    }

    public static String toStrings(int[][] matrix){
        String out = "";
        for(int i = 0; i < matrix.length; i++){
            for (int x: matrix[i]){
                if (x == 3000){
                    out += "\u221E, ";
                } else {
                    out += x + ", ";
                }
            }
            out += "\n";
        }
        return out;
    }
}