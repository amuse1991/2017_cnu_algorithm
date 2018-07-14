import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Prim {
    public static void main(String [] args){
        Prim prim = new Prim();
        prim.runPrim();
    }

    private final static Integer INF = Integer.MAX_VALUE;
    private final static int ASCII_SMALL_A = 97;
    private final static int NUM_OF_NODES = 9;
    private PriorityQueue<Node> Q; // Q는 각 정점을 S에 포함시키기 위한 연결 비용을 담는 우선순위 큐
    private boolean [] selected = new boolean[NUM_OF_NODES];
    private int mstWeight = 0;

    private int [][] weight = new int[][]{
            {  0,   4,INF,INF,INF,INF,INF,  8,INF},
            {  4,   0,  8,INF,INF,INF,INF,  11,INF},
            {INF,  8,  0,   7,INF,  4,INF,INF,   2},
            {INF,INF, 7,   0,   9, 14,INF,INF,INF},
            {INF,INF,INF,  9,  0,  10,INF,INF,INF},
            {INF,INF,  4, 14, 10,   0,   2,INF,INF},
            {INF,INF,INF,INF,INF, 2,   0,   1,  6},
            {  8,  11,INF,INF,INF,INF,  1,  0,  7},
            {INF,INF,  2,INF,INF,INF,  6,   7, 0}
    };

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
            this.graphMatrixIdx = name-ASCII_SMALL_A;
        }
    }


    private Prim(){
        // 큐 생성
        Q = new PriorityQueue<Node>(NUM_OF_NODES);
        // 시작정점 0 나머지정점 INF로 초기화
        Node newNode = new Node((char)ASCII_SMALL_A,0);
        Q.add(newNode);
        selected[0] = true;

        for(int i = 1; i<NUM_OF_NODES; i++){
            int ascii = ASCII_SMALL_A+i;
            newNode = new Node((char) ascii, INF); // 무한대로 초기화
            Q.add(newNode);
            selected[i] = false;
        }
    }

    public void runPrim(){
        ArrayList<Node> tempArr = new ArrayList<>(); // 힙 갱신을 위한 임시 배열
        int flag = 0;
        while(!Q.isEmpty()){
            Node u = Q.poll(); // 큐에서 가장 작은 값 출력
            selected[u.graphMatrixIdx] = true;
            mstWeight += u.dist;
            // 연결 비용 갱신 과정
            while (!Q.isEmpty()){
                Node v = Q.poll();
                tempArr.add(v);
                // 선택한 v가 u의 인접 정점일 경우 연결 비용 갱신 대상
                if(isAdjacent(u,v) && weight[u.graphMatrixIdx][v.graphMatrixIdx] < v.dist){
                    v.dist = weight[u.graphMatrixIdx][v.graphMatrixIdx];
                }
            }
            //힙 재구성
            for(Node x : tempArr)
                Q.add(x);
            tempArr.clear();

            // 화면출력과정
            int sName; // w<s,u>를 화면에 출력하기 위한 변수. s는 u의 인접 정점 중에서 weight가 가장 작은 정점.
            int minIdx = -1;
            int min = INF;
            for(int i = 0; i<weight.length; i++){
                if(selected[i] && weight[u.graphMatrixIdx][i] < min  && weight[u.graphMatrixIdx][i] != 0 ){
                    min = weight[u.graphMatrixIdx][i];
                    minIdx = i;
                }
            }

            sName = ASCII_SMALL_A + minIdx;

            if(flag == 0){
                System.out.println("w<"+ " "+","+u.name+">" + " = " + u.dist);
                flag = 1;
            }else{
                System.out.println("w<"+(char)sName+","+u.name+">" + " = " + u.dist);
            }
        }
        System.out.println("\r\nw<MST> = " + mstWeight);
    }

    private boolean isAdjacent(Node u, Node v){ //노드 u와 v가 인접 정점이면 T 아니면 F를 반환한다.
            int elem = weight[u.graphMatrixIdx][v.graphMatrixIdx];
            if(elem != INF && elem!=0) return true;
            else return false;
    }

}

