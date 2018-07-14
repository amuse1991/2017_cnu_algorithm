import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class LoopInvariant {
    int [] list = null;

    LoopInvariant(){
        this.list = readFile();
    }


    void runSearch(){
        System.out.println("key값을 입력하세요");
        Scanner scan = new Scanner(System.in);
        int key = scan.nextInt();
        scan.nextLine();

        int result = search(key);
        if(result == Integer.MIN_VALUE){
            System.out.println("key 값을 찾을 수 없습니다");
        }
        else System.out.println("해당 key값의 위치 : " + result);
    }

    private int search (int key){
        //초기값 설정
        int low = 0;
        int high = list.length-1;
        int mid;
        int result=0;

        for(int i=0; i<list.length-1 ; i++)
            if(list[i]>list[i+1]) System.out.println("초기 조건 불만족");

        while(low<=high){
            //invariant 확인
            if(key >= list[low] && key <= list[high])
                System.out.println("[1]"+"key:"+key+", "+ "low:"+low+", " + "high:" + high + ", " + "\r\n" + "결과:invariant 만족");
            else System.out.println("[1]"+"key:"+key+", "+ "low:"+low+", " + "high:" + high + ", " + "\r\n" + "결과:invariant 불만족");

            //logic
            mid = (low+high)/2;
            if(key == list[mid]) { result = mid; break; }
            else if(key > list[mid]) low = mid+1;
            else high = mid-1;

            //invariant 확인
            if(key >= list[low] && key <= list[high])
                System.out.println("[2]"+"key:"+key+", "+ "low:"+low+", " + "high:" + high + ", " + "\r\n" + "결과:invariant 만족");
            else System.out.println("[2]"+"key:"+key+", "+ "low:"+low+", " + "high:" + high + ", " + "\r\n" + "결과:invariant 불만족");
        }

        if( key == list[result] && !(low>high) && result >= low && result <= high)  //Postcondition 확인
        {
            System.out.println("Postcondition 만족");
            return result;
        }
        else return Integer.MIN_VALUE;
    }


    private int[] readFile(){
        try{
            String path = System.getProperty("user.dir") + "\\src\\invariant_data.txt";
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            reader.close();

            String [] splitedList = line.split(" ");
            int [] resultList = new int[splitedList.length];

            for(int i = 0; i<splitedList.length ; i++){
                resultList[i] = Integer.parseInt(splitedList[i]);
            }
            return resultList;
        }
        catch(Exception e) {
            return null;
        }
    }
}