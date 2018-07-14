package Sort;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.io.*;
import java.util.ArrayList;
//1번~3번 정렬에 공통적으로 사용되는 메소드와 필드 가지는 추상클래스
abstract class Sort {
    protected static ArrayList<Integer> dataArr = new ArrayList<>();
    Sort(){
        readFile();
    }
    void readFile() {
        try {
            // 파일 읽기
            String dir = System.getProperty("user.dir") + "\\src\\Sort\\input.txt";
            BufferedReader reader = new BufferedReader(new FileReader(dir));
            String targetData = reader.readLine();
            reader.close();

            // " "를 기준으로 분리
            String[] stringDataArr = targetData.split(" ");
            // String 형태로 되어있는 데이터를 Integer형으로 변환하여 dataArr에 삽입
            for (int i = 0; i < stringDataArr.length; i++) {
                Integer val = new Integer(Integer.parseInt(stringDataArr[i]));
                this.dataArr.add(i, val);
            }
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    void printFile() {
        //파일출력
        try {
            String dir = System.getProperty("user.dir") + "\\src\\Sort\\201504280_output.txt";
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


public class InsertionSort extends Sort {
    void insertionSort() {
        int insData;
        int i, j;

        for (i = 1; i < dataArr.size(); i++) {
            insData = dataArr.get(i);
            for (j = i - 1; j >= 0; j--) {
                if (dataArr.get(j) > insData) dataArr.set(j+1,dataArr.get(j));
                else break;
            }
            dataArr.set(j+1,insData);
        }
        printFile();
    }

}