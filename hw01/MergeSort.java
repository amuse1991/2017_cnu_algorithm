package Sort;

import java.io.*;
import java.util.ArrayList;

public class MergeSort extends Sort {
    // 맨 처음 호출되는 merge sort 함수
    public void mergeSort() {
        int left = 0;
        int right = (dataArr.size())-1;
        int mid; //mid를 기준으로 양쪽으로 분할

        if(left < right){ // 분할할 수 있는지 판단
            mid = (left+right)/2; // 중간 지점을 mid에 저장
            mergeSort(left,mid); // mid를 기준으로 left에 함수 recursion
            mergeSort(mid+1,right); // mid를 기준으로 right에 함수 recursion
            merge(left,mid,right); //정렬합병
        }
        // recursion 끝나면 파일로 출력
        this.printFile();
    }

    //recursion용 mergeSort함수 오버라이드
    private void mergeSort(int left, int right) {
        int mid; //mid를 기준으로 양쪽으로 분할

        if(left < right){ // 분할할 수 있는지 판단
            mid = (left+right)/2; // 중간 지점을 mid에 저장
            mergeSort(left,mid); // mid를 기준으로 left에 함수 recursion
            mergeSort(mid+1,right); // mid를 기준으로 right에 함수 recursion
            merge(left,mid,right); //정렬합병
        }
    }

    private void merge(int left, int mid, int right) {
        int fIdx = left; //배열 A의 시작 인덱스를 담는 변수
        int rIdx = mid + 1; // 배열 B의 시작 인덱스를 담는 변수

        //두 배열을 통합시키는데 사용할 임시 배열
        //ArrayList<Integer> tempArr = new ArrayList<>();
        int [] tempArr = new int [dataArr.size()];

        int sIdx = left;

        // 실질적인 비교 정렬 및 통합이 진행되는 부분
        while (fIdx <= mid && rIdx <= right) {
            if (dataArr.get(fIdx) <= dataArr.get(rIdx))
                tempArr[sIdx++] = dataArr.get(fIdx++);
            else
                tempArr[sIdx++] = dataArr.get(rIdx++);
        }

        /*만약 배열 A가 모두 tempArr로 옮겨져서 텅 비면
          배열 B의 남은 데이터를 모두 tempArr로 옮김
          그 반대의 경우 A의 남은 데이터를 tempArr로 옮김
        */
        int i;
        if (fIdx > mid) {
            for (i = rIdx; i <= right; i++) {
                tempArr[sIdx++] = dataArr.get(i);
                if(tempArr[tempArr.length-1] != 0) break;
            }
        } else {
            for (i = fIdx; i <= right; i++) {
                tempArr[sIdx++] = dataArr.get(i);
                if(tempArr[tempArr.length-1] != 0) break;
            }
        }
        // tempArr에서 dataArr로 데이터를 옮기는 과정

        for (i = left; i <= right; i++) {
            dataArr.set(i,tempArr[i]);
        }
        //가비지컬렉터 호출해서 tempArr제거
        tempArr = null;
        System.gc();
    }
}
