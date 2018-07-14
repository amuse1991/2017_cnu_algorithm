import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Comparator;

public class MinimizeLateness{
    int numOfWorks;
    Work [] workList;

    public static void main(String args[]){
        MinimizeLateness ml = new MinimizeLateness();
        System.out.println(ml.getMaxLateness());
    }

    MinimizeLateness(){
        this.readFile();
        Arrays.sort(workList,new WorkListComparator()); // limit time 순서대로 정렬
    }

    private class Work{
        int workTime;
        int limitTime;

        Work(int wt, int lt){
            workTime = wt;
            limitTime = lt;
        }
    }

    public int getMaxLateness(){
        int currentTime = 0;
        int lateness;
        int maxLateness=0;
        for(int i = 0; i<workList.length; i++){
            int finishTime = currentTime + workList[i].workTime;
            currentTime += workList[i].workTime;
            lateness = finishTime - workList[i].limitTime;

            if(lateness > maxLateness) maxLateness = lateness; //lateness가 maxLateness보다 크면 maxLateness갱신
        }
        return maxLateness;
    }

    private void readFile(){
        // 파일을 읽어서 numOfWorks, workList를 초기화
        {
            try{
                String path = System.getProperty("user.dir") + "\\src\\data06_lateness.txt";
                BufferedReader reader = new BufferedReader(new FileReader(path));

                // 작업 개수 읽어서 numOfWorks초기화
                numOfWorks = Integer.parseInt(reader.readLine());

                Work [] tempWorkArr = new Work[numOfWorks];

                String line = reader.readLine();
                for(int i = 0 ; line != null; line = reader.readLine(), i++){
                    String [] tempStrArr = line.split(" ");
                    int wt = Integer.parseInt(tempStrArr[0]);
                    int lt = Integer.parseInt(tempStrArr[1]);
                    Work tempWork = new Work(wt,lt);

                    tempWorkArr[i] = tempWork;
                }
                workList = tempWorkArr;
                reader.close();
            }
            catch(Exception e) {
                System.out.println("파일 읽기 오류");
                System.exit(-1);
            }
        }
    }
    public class WorkListComparator implements Comparator<Work>{ //Work 배열을 limitTime을 기준으로 정렬해줌
        @Override
        public int compare(Work work1, Work work2){
            int work1LT = work1.limitTime;
            int work2LT = work2.limitTime;
            if(work1LT < work2LT) return -1;
            else if (work1LT == work2LT) return 0;
            else return 1;
        }
    }
}
