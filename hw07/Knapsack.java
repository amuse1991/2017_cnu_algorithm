import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Knapsack {
    public static void main(String[] args) {
        Knapsack k = new Knapsack();
        k.runKnapsack();
    }
    private static String [][] selectItemArr;
    private static int numOfItem;
    private static int weightLimit;
    private static int[][] valueAndWeightArr;
    private static int[][] optimalTable;

    private Knapsack() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            //첫 번째 라인 읽어서 아이템 개수와 가방 무게 제한 파악
            String str = input.readLine();
            String[] tempStrArr = str.split(" ");
            numOfItem = Integer.parseInt(tempStrArr[0]);
            weightLimit = Integer.parseInt(tempStrArr[1]);

            //각 아이템 정보 저장할 배열 생성
            valueAndWeightArr = new int[2][numOfItem];

            //각 아이템 정보 저장
            for (int i = 0; i < numOfItem; i++) {
                str = input.readLine();
                tempStrArr = str.split(" ");
                valueAndWeightArr[0][i] = Integer.parseInt(tempStrArr[0]);
                valueAndWeightArr[1][i] = Integer.parseInt(tempStrArr[1]);
            }

            //OPT 생성
            optimalTable = new int[numOfItem + 1][weightLimit + 1];
        } catch (java.io.IOException e) {
            System.exit(-1);
        }
    }

    public void runKnapsack(){
        selectItemArr = new String[numOfItem+1][weightLimit+1];

        makeOPT();

        //OPT 출력
        printTable(optimalTable,weightLimit+1,numOfItem+1);
        //max값과 선택된 아이템 출력
        System.out.println("max : " + optimalTable[numOfItem][weightLimit]);
        System.out.print("item : ");
        //printTable(selectItemArr,weightLimit+1,numOfItem+1);
        String s = selectItemArr[numOfItem][weightLimit];
        s = s.replace("0","");
        for(int i = 0 ; i<s.length(); i++) {
            System.out.print(s.charAt(i)+" ");
        }
    }

    private void makeOPT(){
        // i = 0 인 경우는 OPT(i,w) = 0
        // w가 0인 경우는 아이템 선택할 수 없으므로 OPT(i,w)의 값도 0
        for(int i = 0; i<optimalTable.length; i++) {
            optimalTable[i][0]=0; selectItemArr[i][0] = "0";
        }

        for(int i = 0 ; i< weightLimit+1; i++) {
            optimalTable[0][i]=0; selectItemArr[0][i] = "0";
        }

        for(int i =1; i<= numOfItem; i++){
            int currentItemWeight = valueAndWeightArr[1][i-1];
            int currentItemValue = valueAndWeightArr[0][i-1];
            String itemName = String.valueOf(i);
            String selectedItems = "";
            for(int j=1; j <= weightLimit; j++){
                selectedItems = "";
                int useVal;
                int notUseVal = optimalTable[i-1][j]; //i번째 아이템을 사용하지 않을 경우 OPT(i-1,w)의 값을 그냥 가져오면 됨
                //i번째 아이템을 사용할 경우 남는 무게 제한 계산
                // i번째 아이템을 선택한 것이므로, i번째 아이템 선택 후 남은 무게제한은 현재무게제한(j) - 현재아이템의무게가 된다.
                int leftWeight = j - currentItemWeight;

                if(leftWeight < 0){ // 남은 무게가 0보다 작으면 i번째 아이템 못넣는 것이므로 i번째 아이템을 사용하지 않는 경우로 처리(wi > w 이면 OPT(i-1,w)
                    optimalTable[i][j] = notUseVal;
                    selectedItems = selectItemArr[i-1][j];
                }
                else{ //남은 무게가 0보다 큰 경우에 i번째 아이템을 선택 가능
                    useVal = optimalTable[i-1][leftWeight]; //i-1번째 단계에서 무게가 leftWeight이하의 경우 중 가장 큰(optimal한) 가치를 읽어온다.
                    useVal += currentItemValue; // i번째 아이템을 선택한 것이므로 i번째 아이템의 가치를 더해준다.
                    if(useVal >= notUseVal){ // 아이템을 사용
                        optimalTable[i][j] = useVal;

                        selectedItems = selectedItems.concat(selectItemArr[i-1][leftWeight]);
                        selectedItems = selectedItems.concat(itemName);
                    }else{ // 아이템을 사용하지 않음
                        optimalTable[i][j] = notUseVal;
                        selectedItems = selectItemArr[i-1][j];
                    }
                }
                selectItemArr[i][j] = selectedItems;
            }
        }
    }


    private void printTable(int[][] table, int x, int y){
        for(int i = 0; i<y; i++){
            for (int j=0; j<x; j++){
                System.out.print("\t"+table[i][j]+"\t");
            }
            System.out.print("\r\n");
        }
    }
}
