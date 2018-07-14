import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class BellmanFord {
    public static void main(String args[]){
        BellmanFord bf = new BellmanFord();
        bf.run();
    }
    private final static int INF = Integer.MAX_VALUE;
    private static int numOfNode;
    private static int startVertexNumber;
    private static int terminalVertexNumber;
    private static int numOfEdge;
    private static ArrayList<Edge> edges;
    private static int [] opt;
    private static int [] succ;

    private class Edge{
        int v;
        int w;
        int weight;
        Edge(int v, int w, int weight){
            this.v = v;
            this.w = w;
            this.weight = weight; // v, w 사이의 가중치
        }
    }

    private BellmanFord(){
        init();
    }

    private void init(){
        try{
            String path = System.getProperty("user.dir") + "\\src\\data10.txt";
            BufferedReader reader = new BufferedReader(new FileReader(path));

            String line = reader.readLine();
            numOfNode = Integer.valueOf(line);

            line = reader.readLine();
            startVertexNumber = Integer.valueOf(Character.toString(line.charAt(0)));
            terminalVertexNumber = Integer.valueOf(Character.toString(line.charAt(2)));

            line = reader.readLine();
            numOfEdge = Integer.valueOf(line);

            edges = new ArrayList<>();

            String [] tempStrArr;
            for(line = reader.readLine() ; line != null; line = reader.readLine()){
                tempStrArr = line.split(" ");
                int v = Integer.valueOf(tempStrArr[0]);
                int w = Integer.valueOf(tempStrArr[1]);
                int weight = Integer.valueOf(tempStrArr[2]);

                edges.add(new Edge(v,w,weight));
            }
            reader.close();

            succ = new int[numOfNode];
        }
        catch(Exception e) {
            e.printStackTrace();
            return;
        }
    }
    public void run(){
        // Step 1: Initialize distances from src to all other
        // vertices as INFINITE
        opt = new int[numOfNode];
        opt[0] = 0;
        for(int i = 1; i<numOfNode; i++){
            opt[i] = INF;
        }
        // Step 2: Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other vertex can
        // have at-most |V| - 1 edges
        for(int i = 1 ; i<numOfNode; ++i){
            for(int j=0; j<numOfEdge; ++j){
                Edge edge = edges.get(j);
                int v = edge.v;
                int w = edge.w;
                int weight = edge.weight;
                if((opt[v] != INF) && (opt[v]+weight < opt[w])) {
                    opt[w] = opt[v] + weight;
                    succ[v] = w;
                }
            }
            // Step 3: check for negative-weight cycles.  The above
            // step guarantees shortest distances if graph doesn't
            // contain negative weight cycle. If we get a shorter
            //  path, then there is a cycle.
            for(int j=0; j<numOfEdge; ++j){
                Edge edge = edges.get(j);
                int v = edge.v;
                int w = edge.w;
                int weight = edge.weight;
                if((opt[v] != INF) && (opt[v]+weight < opt[w]))
                    System.out.println("그래프에 네거티브 웨이트 사이클이 존재합니다.");
            }
        }
        print();
    }
    void print()
    {
        System.out.println("Shortest distance :" + opt[terminalVertexNumber]);
        System.out.println("Path : ");

        int cur = startVertexNumber;
        while(cur!=terminalVertexNumber){
            int next = succ[cur];
            String curStr = String.valueOf(cur);
            String nextStr = String.valueOf(next);
            System.out.println("( "+ curStr +" , " + nextStr + " )");
            cur = next;
        }
    }
}
