import java.util.*;

public class BFS {
    public static void main(String args[]){
        BFS bfs = new BFS();
        bfs.runBFS();

    }
    private final static int INF = Integer.MAX_VALUE;
    private final static int NUMBER_OF_VERTEX = 8;
    private static Vertex[] vertices = new Vertex[NUMBER_OF_VERTEX];
    private static HashMap nameMap;
    private static int[][] graphMatrix ={
            //s r t u v w x y
            {0  ,1  ,INF,INF,INF,1,INF,INF}, //s
            {1  ,0  ,INF,INF,1,INF,INF,INF}, //r
            {INF,INF,0,1,INF,1,1,INF},  //t
            {INF,INF,1,0,INF,INF,1,1},  //u
            {INF,1,INF,INF,0,INF,INF,INF}, //v
            {1  ,INF,1,INF,INF,0,1,0}, //w
            {INF,INF,1,1,INF,1,0,1}, //x
            {INF,INF,INF,1,INF,INF,1,0} //y
    };

    //큐
    private LinkedList<Vertex> Q = new LinkedList<>();

    // ******* Vertex 객체 *********
    private class Vertex {
        int id;
        String color;
        int distance;
        Vertex parent;

        Vertex(int id){
            this.id = id;
            this.color = "WHITE";
            this.distance = INF;
            this.parent = null;
        }
    }
    // ******* Vertex 객체 *********

    private BFS(){
        nameMap = initNameMap();
        //정점 생성해서 정점배열에 저장
        //정점은 color : white, distance : INF, parent : null로 초기화됨
        for(int i = 0; i<NUMBER_OF_VERTEX; i++){
            vertices[i] = new Vertex(i);
        }
    }
    public void runBFS() {
        //시작정점 초기화
        vertices[0].color = "GRAY";
        vertices[0].distance = 0;
        vertices[0].parent = null;

        Q.offer(vertices[0]);

        while (!Q.isEmpty()) {
            Vertex u = Q.poll();
            for (int column = 0; column < NUMBER_OF_VERTEX; column++) {
                Vertex v = vertices[column];
                if (isAdj(u, v) && v.color.equals("WHITE")) { // u에 인접한 정점이면서, 아직 탐색되지 않은 정점이면
                    v.color = "GRAY";
                    v.distance = u.distance + 1;
                    v.parent = u;
                    Q.offer(v);
                }
            }
            //내부 for문에 의해 u의 인접정점은 모두 탐색되었을 것이므로 BLACK으로 바꿈
            u.color = "BLACK";
        }
        print();
    }
    private void print(){
        System.out.println("정점\t"+"부모정점\t"+"비용\t");
        System.out.println("-------------------------------");
        for(int i = 0; i<NUMBER_OF_VERTEX; i++){
            int name =vertices[i].id;
            int cost = vertices[i].distance;
            int p;
            try {
                p = vertices[i].parent.id;
                 //시작정점은 부모정점없으니까 null point exception 나옴
            }catch (NullPointerException e){
                p = 0;
            }

            System.out.println(nameMap.get(name)+"\t"+nameMap.get(p)+"\t"+cost+"\t");
        }
    }
    private boolean isAdj(Vertex u, Vertex v){
        int tmp = graphMatrix[u.id][v.id];
        if(tmp == INF || tmp == 0){
            return false;
        }else return true;
    }

    private HashMap<Integer,String> initNameMap(){
        HashMap<Integer,String> temp = new HashMap<>();
        //s r t u v w x y
        temp.put(0,"s");
        temp.put(1,"r");
        temp.put(2,"t");
        temp.put(3,"u");
        temp.put(4,"v");
        temp.put(5,"w");
        temp.put(6,"x");
        temp.put(7,"y");

        return temp;
    }
}
