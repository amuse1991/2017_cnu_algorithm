import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Dijkstra {

    public static void main(String [] args){
        Dijkstra dijkstra = new Dijkstra();
        System.out.println("dijkstra's algorithm.");
        dijkstra.runDijkstra();
    }

    private final static int INIT_PQ_SIZE = 25;
    private final static int NUM_OF_NODES = 5;
    private final static int ASCII_A = 65;
    private final static Integer INF = Integer.MAX_VALUE;


    private int [][] weight = new int[][] {
            {0, 10, 3, INF, INF},
            {INF, 0, 1, 2, INF},
            {INF, 4, 0, 8, 2},
            {INF, INF, INF, 0, 7},
            {INF, INF, INF, 9, 0}};

    private PriorityQueue<Node> Q;
    private ArrayList<Node> S = new ArrayList<>();


    private Dijkstra(){
        //큐 생성
        Q = new PriorityQueue<Node>(INIT_PQ_SIZE);


        //큐 초기화
        Node newNode = new Node((char)ASCII_A,0); // 시작정점 dist값은 0
        Q.add(newNode);

        for(int i = 1; i<NUM_OF_NODES; i++){
            int ascii = ASCII_A+i;
            newNode = new Node((char) ascii, INF); // 무한대로 초기화
            Q.add(newNode); //큐에 넣어줌
        }
    }

    public void runDijkstra(){
        for(int i = 0 ; !Q.isEmpty() ; i++){
            System.out.println("=============================================");
            Node u = Q.poll(); //V-S에서 dist값이 가장 작은 노드 출력
            S.add(u); //S에 추가
            System.out.println("S["+ i +"]"+" : "+ "d["+u.name+"]"+ " = "+ u.dist);
            System.out.println("--------------------------------------------");

            distanceCalculate(u); // 확장된 S를 기준으로 거리 갱신
        }
    }

    private void distanceCalculate(Node u){ //u를 포함해 확장된 S를 기준으로 거리값 갱신
        // w의 거리 값을 새로 갱신함
        Node v;
        int qSize = Q.size();
        Node [] tempArr = new Node[qSize];

        for(int i= 0 ; i<qSize; i++){
            v = Q.poll();
            tempArr[i] = v;

            if (weight[u.graphMatrixIdx][v.graphMatrixIdx] == INF) { // w[u][v] == INF이면 갱신 안함
                System.out.println("Q[" + i + "]" + " : " + "d[" + v.name + "]" + " = " + v.dist);
                continue;
            }
            if (u.dist + weight[u.graphMatrixIdx][v.graphMatrixIdx] < v.dist) { // d[u]+w[u][v] < d이면 d[v]갱신
                int temp = v.dist;
                v.dist = u.dist + weight[u.graphMatrixIdx][v.graphMatrixIdx];
                System.out.println("Q[" + i + "]" + " : " + "d[" + v.name + "]" + " = " + temp+ "-> d["+v.name+"] = "+v.dist);
            }
            else System.out.println("Q[" + i + "]" + " : " + "d[" + v.name + "]" + " = " + v.dist);
        }
        for(Node x : tempArr)
            Q.add(x);
    }

    private class Node implements Comparable<Node>{
        @Override
        public int compareTo(Node o) {

            return this.dist - o.dist;
        }

        char name; // 노드 이름
        int dist; //현재 가지고 있는 최단거리값
        int graphMatrixIdx;
        Node(char name, int dist){
            this.name = name;
            this.dist = dist;
            this.graphMatrixIdx = name-ASCII_A;
        }
    }
}

