package Sort;

public class BinaryInsertionSort extends Sort {
    void BinaryInsertionSort(){
        int j;
        int key; //정렬 대상
        for(int i = 1; i<dataArr.size(); ++i){
            j = i-1;
            key = dataArr.get(i);

            int location = bSearch(key,0,j); // binary search로 적절한 위치 찾아주는 과정

            //삽입할 공간을 만들기 위해 밀어주는 과정
            while(j>=location){
                dataArr.set(j+1,dataArr.get(j));
                j--;
            }
            // 데이터 삽입
            dataArr.set(j+1, key);
        }
        printFile();
    }
    private int bSearch(int key, int low, int high){
        //high가 low보다 작거나 같아질 때 까지 적절한 위치를 찾지 못한 경우
        if (high <= low){
            if(key > dataArr.get(low)){
                return (low+1);
            }else return low;
        }
        int middle = (low+high)/2;
        if(key == dataArr.get(middle)) return middle+1; //적절한 위치를 찾은 경우 이를 return
        else if(key > dataArr.get(middle)) return bSearch(key,middle+1,high); //key가 dataArr[middle]보다 크다면 middle기준으로 오른쪽 부분에 함수 recursion
        else return bSearch(key,low,middle-1); //key가 dataArr[middle]보다 작다면 middle기준으로 왼쪽 부분에 함수 recursion
    }
}

