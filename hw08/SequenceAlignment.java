import java.io.BufferedReader;
import java.io.InputStreamReader;

//input1 : ocurrance
//input2 : occurrence
public class SequenceAlignment {
    public static void main(String args[]) {
        SequenceAlignment s = new SequenceAlignment();
        s.runSequenceAlignemnt();
    }

    private final static int GAP_COST = 1; //sigma
    private final static int MISMATCH_COST = 2; //alpha(p,q)
    private String stringA;
    private String stringB;
    private int[][] optimalTable;


    private SequenceAlignment() {
        stringInput();
        optimalTable = new int[stringA.length() + 1][stringB.length() + 1];
    }

    public void runSequenceAlignemnt() {
        for (int i = 1; i <= stringA.length(); i++) {
            optimalTable[i][0] = optimalTable[i - 1][0] + GAP_COST;
        }
        for (int j = 1; j <= stringB.length(); j++) {
            optimalTable[0][j] = optimalTable[0][j - 1] + GAP_COST;
        }

        for (int i = 1; i <= stringA.length(); i++) {
            for (int j = 1; j <= stringB.length(); j++) {
                int align = caseAlignCost(i, j) + optimalTable[i - 1][j - 1];
                int xGap = caseXGapCost(i, j);
                int yGap = caseYGapCost(i, j);

                int min = Math.min(Math.min(align, xGap), yGap);
                optimalTable[i][j] = min;
            }
        }
        printTable(optimalTable);
    }

    //align 시키는 경우
    private int caseAlignCost(int i, int j) {
        if (stringA.charAt(i - 1) == stringB.charAt(j - 1)) {
            return 0;
        } else return MISMATCH_COST;
    }

    //x_i를 gap 시키는 경우
    private int caseXGapCost(int i, int j) {
        return GAP_COST + optimalTable[i][j - 1];
    }

    //y_j를 gap 시키는 경우
    private int caseYGapCost(int i, int j) {
        return GAP_COST + optimalTable[i - 1][j];
    }

    private void printTable(int[][] table) {
        System.out.println("MINCOST : " + optimalTable[stringA.length()][stringB.length()]);
        String [] splitedStringA = stringA.split("");
        String [] splitedStringB = stringB.split("");
        System.out.print("\t\t");
        for(String s : splitedStringB){
            System.out.print(s + "\t");
        }
        System.out.println();

        for (int i = 0; i < stringA.length() + 1; i++) {
            if(i>=1) System.out.print(splitedStringA[i-1]+"\t");
            else System.out.print("  \t");

            for (int j = 0; j < stringB.length() + 1; j++) {
                System.out.print(table[i][j] + "\t");
            }
            System.out.print("\r\n");
        }

        int i = stringA.length();
        int j = stringB.length();

        for(int r = optimalTable.length; r>=0;r--){
            if(r==0)System.out.print("OPT["+i+"]"+"["+j+"] -> OPT[0][0]");
            else System.out.print("OPT["+i+"]"+"["+j+"]"+" -> ");

            int leftWeight = GAP_COST + optimalTable[i][j-1];
            int topWeight = GAP_COST + optimalTable[i-1][j];
            int diagonalWeight = caseAlignCost(i, j)+optimalTable[i-1][j-1];

            int min = Math.min(Math.min(leftWeight,topWeight),diagonalWeight);
            if(min == leftWeight){
                j--;
            }else if(min == topWeight){
                i--;
            }else{
                i--;
                j--;
            }
        }
    }

    private void stringInput() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("String 1 : ");
            String str = input.readLine();
            stringA = str;
            System.out.print("String 2 : ");
            str = input.readLine();
            stringB = str;
        }catch(java.io.IOException e){
            System.exit(-1);
        }
    }
}