import java.io.*;
import java.util.ArrayList;

public class QuickSort {
    int partition(ArrayList arr, int left, int right){
        int pivot = (int)arr.get(left);
        int low = left+1;
        int high = right;

        while(low<high){
            while(pivot >= (int)arr.get(low) && low <= right)
                low++;
            while(pivot <= (int)arr.get(high) && high >= (left+1))
                high--;
            if(low <= high)
                swap(arr,low,high);
        }
        swap(arr,left,high);
        return high;
    }
    void quickSort(ArrayList arr, int left, int right){
        if(left <= right){
            int pivot = partition(arr,left,right);
            quickSort(arr,left,pivot-1);
            quickSort(arr,pivot+1,right);
        }
    }
    private void swap(ArrayList arr, int aIdx, int bIdx){
        int temp = (int)arr.get(aIdx);
        arr.set(aIdx,arr.get(bIdx));
        arr.set(bIdx,temp);
    }
    void readFile(ArrayList<Integer> dataArr) {
        try {
            // 파일 읽기
            String dir = System.getProperty("user.dir") + "\\src\\input.txt";
            BufferedReader reader = new BufferedReader(new FileReader(dir));
            String targetData = reader.readLine();
            reader.close();

            // " "를 기준으로 분리
            String[] stringDataArr = targetData.split(" ");
            // String 형태로 되어있는 데이터를 Integer형으로 변환하여 dataArr에 삽입
            for (int i = 0; i < stringDataArr.length; i++) {
                Integer val = new Integer(Integer.parseInt(stringDataArr[i]));
                dataArr.add(i, val);
            }
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
    }
    void printFile(ArrayList<Integer> dataArr) {
        //파일출력
        try {
            String dir = System.getProperty("user.dir") + "\\src\\201504280_output.txt";
            BufferedWriter w = new BufferedWriter(new FileWriter(dir));
            StringBuffer s = new StringBuffer();
            for (int i = 0; i < dataArr.size(); ) {
                s.append(dataArr.get(i));
                i++;
                if (i == dataArr.size()) break;
                s.append(" ");
            }
            String s2 = s.toString();
            w.write(s2);
            w.close();
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
    }
}
