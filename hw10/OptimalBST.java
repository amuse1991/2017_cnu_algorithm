import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class OptimalBST {
    public static void main(String args[]){
        OptimalBST bst = new OptimalBST();
        bst.run();
    }

    private ArrayList<Float> dataTableP;
    private ArrayList<Float> dataTableQ;
    private float[][] wTable;
    private float[][] eTable;
    private int [][] rootTable;
    int size;

    private OptimalBST() {
        readFile();
        size = dataTableP.size()-1;
        wTable = new float[size+2][size+1];
        eTable = new float[size+2][size+1];
        rootTable = new int[size+1][size+1];
    }

    public void run(){
        findOptimalTree();
        print();
    }

    private void findOptimalTree() {
        //e, w 초기화
        for(int i = 1; i<=size+1; i++){
            eTable[i][i-1] = dataTableQ.get(i-1);
            wTable[i][i-1] = dataTableQ.get(i-1);
        }
        for (int l = 1; l<size+1; l++){
            for(int i = 1; i<=size-l+1;i++){
                int j = i+l-1;
                eTable[i][j] = Float.MAX_VALUE;
                wTable[i][j] = wTable[i][j-1]+dataTableP.get(j)+dataTableQ.get(j);
                for (int r = i; r<=j; r++){
                    float t = eTable[i][r-1]+eTable[r+1][j]+wTable[i][j];
                    if(t < eTable[i][j]){
                        eTable[i][j] = t;
                        rootTable[i][j] = r;
                    }
                }
            }
        }

    }
    private void printTable(float [][] tb){
        for(int i =1 ; i < tb.length ; i++){
            for(int j = 0 ; j < tb[0].length; j++){
                System.out.print(String.format("%.2f",tb[i][j]) + "\t\t");
            }
            System.out.print("\r\n");
        }
    }
    private void printTable(int [][] tb){
        for(int i =1 ; i < tb.length ; i++){
            for(int j = 0 ; j < tb[0].length; j++){
                System.out.print(tb[i][j] + "\t\t");
            }
            System.out.print("\r\n");
        }
    }
    private void print(){
        System.out.println("Optimal BST cost : " + eTable[1][eTable.length-2]);

        System.out.println();
        System.out.println("e table :");
        printTable(eTable);
        System.out.println();

        System.out.println("w table :");
        printTable(wTable);
        System.out.println();

        System.out.println("root table :");
        printTable(rootTable);
    }

    private void readFile(){
        try {
            dataTableP = new ArrayList<>();
            dataTableQ = new ArrayList<>();

            String path = System.getProperty("user.dir") + "\\src\\data11.txt";
            BufferedReader reader = new BufferedReader(new FileReader(path));

            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                String [] tempStr = line.split("\t");
                dataTableP.add(Float.parseFloat(tempStr[0]));
                dataTableQ.add(Float.parseFloat(tempStr[1]));
            }
        }
        catch(java.io.IOException e)
        {
            e.printStackTrace();
            System.out.println("파일 입출력 에러");
        }
    }
}

