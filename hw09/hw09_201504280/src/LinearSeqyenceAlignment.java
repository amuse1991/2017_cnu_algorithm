import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LinearSeqyenceAlignment {
    public static void main(String args[]){
        LinearSeqyenceAlignment lsa = new LinearSeqyenceAlignment();
        //lsa.align();
    }
    private final static int GAP_COST = 1;
    private final static int MISMATCH_COST = 2;
    private static String stringX;
    private static String stringY;
    private static int[] xOPTCostArr;
    private static int[] yOPTCostArr;
    private int diagonalCost;
    private ArrayList<Node> arrowPath = new ArrayList<>();
    private class Node{
        int x;
        int y;
        Node(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    //init
    private LinearSeqyenceAlignment(){
        stringInput();

        //두 배열을 생성 및 초기화
        int n = stringX.length();

        xOPTCostArr = new int[n];
        yOPTCostArr = new int[n];

        xOPTCostArr[0] = 0;
        yOPTCostArr[0] = 0;
        diagonalCost = 0;

        for (int i = 1; i <= stringX.length(); i++) {
            yOPTCostArr[i] = yOPTCostArr[i - 1] + GAP_COST;
            xOPTCostArr[i] = xOPTCostArr[i -1 ] + GAP_COST;
        }
    }

    //xOPTCostArr[1..i] 와 yOPTCostArr[1..i](prefix) 사이의 optimal 배열 반환
    private int[] allYPrefixCosts(int[]x, int idx, int[]y){

        int [] tempArr = new int[idx];

        for(int r=1; r<idx; r++){
            int alignCost = matchCost(stringX.charAt(r),stringY.charAt(r)) + diagonalCost;
            int xGapCost = GAP_COST + x[r-1];
            int yGapCost = GAP_COST + y[r-1];

            tempArr[r] = Math.min(alignCost,Math.min(xGapCost,yGapCost));
        }
        return tempArr;
    }

    //x[i..n] 과 y[i..m] (suffix) 사이의 optimal 배열 반환
    private int[] allYSuffixCosts(int[]x, int idx, int[]y){
        int length = (x.length - idx)+1;
        int [] tempArr = new int[length];

        for(int r=1; r<tempArr.length; r++){
            int alignCost = matchCost(stringX.charAt(r+idx),stringY.charAt(r+idx)) + diagonalCost;
            int xGapCost = GAP_COST + x[r-1];
            int yGapCost = GAP_COST + y[r-1];

            tempArr[r] = Math.min(alignCost,Math.min(xGapCost,yGapCost));
        }
        return tempArr;
    }

    public void align(int[]x,int[]y, int n, int m, int bestQIdx){
        if(n<=2 || m<=2) {

        }
        int [] yPrefix = allYPrefixCosts(x,n/2,y);
        int [] ySuffix = allYSuffixCosts(x,n/2,y);

        int bestQVal=yPrefix[bestQIdx]+ySuffix[bestQIdx];

        for(int q=1; q<=m; q++){
            int cost = yPrefix[q]+ySuffix[q];
            if(cost<bestQVal){
                bestQIdx = q;
                bestQVal = cost;
            }
        }
        Node optimalYNode = new Node(n/2,bestQIdx);
        arrowPath.add(optimalYNode);
        align(x,y,n/2,m,bestQIdx);
    }

    private int standardAlignment(String stringA, String stringB) {
        int[][] optimalTable = new int[stringA.length() + 1][stringB.length() + 1];
        for (int i = 1; i <= stringA.length(); i++) {
            for (int j = 1; j <= stringB.length(); j++) {
                int caseAlignCost;
                if (stringA.charAt(i - 1) == stringB.charAt(j - 1)) {
                    caseAlignCost = 0;
                } else caseAlignCost = MISMATCH_COST;

                int align = caseAlignCost + optimalTable[i - 1][j - 1];
                int xGap = GAP_COST + optimalTable[i][j - 1];
                int yGap = GAP_COST + optimalTable[i - 1][j];

                int min = Math.min(Math.min(align, xGap), yGap);
                optimalTable[i][j] = min;
            }
        }
        return optimalTable[stringA.length()][stringB.length()];
    }


    //문자 일치하면 0, 일치하지 않으면 mismatch cost반환
    private int matchCost(char c1, char c2) {
        if (c1 == c2) {
            return 0;
        } else return MISMATCH_COST;
    }

    private void stringInput() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("String 1 : ");
            String str = input.readLine();
            stringX = str;
            System.out.print("String 2 : ");
            str = input.readLine();
            stringY = str;
        }catch(java.io.IOException e){
            System.exit(-1);
        }
    }
}
