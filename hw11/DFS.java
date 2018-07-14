import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DFS {
    public static void main(String args[]){
        DFS dfs = new DFS();
        dfs.dfs();
        dfs.print();
    }
    private final static int INF = Integer.MAX_VALUE;
    private final static int NUMBER_OF_VERTEX = 6;
    private static HashMap<Integer,String> nameMap;
    private static Vertex[] vertices;
    private static int[][] graphMatrix = {
            {0,1,INF,1,INF,INF},
            {INF,0,INF,INF,1,INF},
            {INF,INF,0,INF,1,1},
            {INF,1,INF,0,INF,INF},
            {INF,INF,INF,1,0,INF},
            {INF,INF,INF,INF,INF,1}
    };
    private static int time = 0;

    private class Vertex{
        int id;
        int d;
        int f;
        String color;
        Vertex parent;

        Vertex(int id){
            this.id = id;
            this.d = INF;
            this.f = INF;
            this.color = "WHITE";
            this.parent = null;
        }
    }

    private DFS(){
        vertices = new Vertex[NUMBER_OF_VERTEX];
        for(int i = 0 ; i<NUMBER_OF_VERTEX; i++){
            vertices[i] = new Vertex(i);
        }
        nameMap = initNameMap();
    }

    public void dfs(){
        for(int i=0; i<NUMBER_OF_VERTEX; i++){
            Vertex u = vertices[i];
            if(u.color.equals("WHITE")){
                dfsVisit(u);
            }
        }
    }
    private void dfsVisit(Vertex u){
        time = time+1;
        u.d = time;
        u.color = "GRAY";

        ArrayList<Integer> adjVertexNumberList = new ArrayList<>(); //u에 인접한 정점 번호를 가진 리스트
        int row = u.id;
        for(int column = 0; column<NUMBER_OF_VERTEX; column++) {
            if (graphMatrix[row][column] != INF && graphMatrix[row][column] != 0) {
                adjVertexNumberList.add(column);
            }
        }

        for(int i : adjVertexNumberList){
            Vertex v = vertices[i];
            if(v.color.equals("WHITE")){
                v.parent = u;
                dfsVisit(v);
            }
        }
        u.color = "BLACK";
        time = time+1;
        u.f = time;
    }

    public void print(){
        System.out.println("정점\t"+"부모정점\t"+"발견시간\t"+"탐색완료시간\t");
        System.out.println("-------------------------------");
        for(int i = 0; i<NUMBER_OF_VERTEX; i++){
            int name =vertices[i].id;
            int d = vertices[i].d;
            int f = vertices[i].f;
            int p;
            try {
                p = vertices[i].parent.id;
                //시작정점은 부모정점없으니까 null point exception 나옴
            }catch (NullPointerException e){
                p = 0;
            }

            System.out.println(nameMap.get(name)+"\t"+nameMap.get(p)+"\t"+d+"\t"+f+"\t");
        }
    }

    private HashMap<Integer,String> initNameMap(){
        HashMap<Integer,String> temp = new HashMap<>();
        temp.put(0,"u");
        temp.put(1,"v");
        temp.put(2,"w");
        temp.put(3,"x");
        temp.put(4,"y");
        temp.put(5,"z");
        return temp;
    }
}
