import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MaxPriorityQueue {
    public static void main(String[] args) {

        Heap maxPQ = new Heap();
        maxPQ.buildMaxHeap();

        screenPrint(maxPQ);

        String inputStr;
        int inputInt;
        Scanner scan = new Scanner(System.in);
        while(true) {
                inputInt = scan.nextInt();
                switch (inputInt) {
                    case 1: //추가
                        System.out.println("***작업 추가***");
                        System.out.println("작업 우선순위를 입력하세요");
                        inputInt = scan.nextInt();
                        scan.nextLine();
                        System.out.println("작업명을 입력하세요");
                        inputStr = scan.nextLine();
                        HeapNode newNode = new HeapNode(inputInt, ", " + inputStr);
                        maxPQ.insert(newNode);
                        screenPrint(maxPQ);
                        break;
                    case 2: //최대값
                        HeapNode maxNode = maxPQ.max();
                        System.out.println("*** MAX : " + maxNode.priority + maxNode.name + " ***");
                        screenPrint(maxPQ);
                        break;
                    case 3: // 최대 우선순위 작업 처리
                        maxPQ.extract_max();
                        System.out.println("*** 한 개의 작업을 처리했습니다 *** ");
                        screenPrint(maxPQ);
                        break;
                    case 4: // 원소 키값 증가
                        int index;
                        System.out.println("수정할 노드 인덱스를 입력하세요");
                        index = scan.nextInt();
                        scan.nextLine();
                        System.out.println("key값을 입력하세요");
                        inputInt = scan.nextInt();
                        scan.nextLine();
                        maxPQ.increaseKey(index, inputInt);
                        screenPrint(maxPQ);
                        break;
                    case 5: //작업 제거
                        System.out.println("삭제할 노드 인덱스를 입력하세요");
                        inputInt = scan.nextInt();
                        scan.nextLine();
                        maxPQ.delete(inputInt);
                        screenPrint(maxPQ);
                        break;
                    case 6: //종료
                        System.exit(0);
                        break;
                    default:
                        System.out.println("1~6번 작업중 하나를 선택해 주세요");
                }
        }

    }
    static void screenPrint(Heap heap){
        System.out.println("***현재 우선순위 큐에 저장되어 있는 작업 대기 목록은 다음과 같습니다***\r\n");
        for(int i=1; i<=(heap.size-1); i++){
            System.out.println(heap.heapArr.get(i).priority + heap.heapArr.get(i).name);
        }
        System.out.println("\r\n");
        System.out.println("---------------------------------------------------\r\n");
        System.out.println("1. 작업 추가    2. 최대값  3. 최대 우선순위 작업 처리\r\n");
        System.out.println("4. 원소 키값 증가          5.작업 제거       6. 종료\r\n");
        System.out.println("---------------------------------------------------\r\n");
    }
}

class HeapNode{
    int priority;
    String name;
    HeapNode(int priority, String name){
        this.priority = priority;
        this.name = name;
    }
}
class Heap {

    ArrayList<HeapNode> heapArr = new ArrayList<>();
    int size = 0;

    Heap(){
        try {
            String dir = System.getProperty("user.dir") + "\\src\\data_heap.txt";
            BufferedReader reader = new BufferedReader(new FileReader(dir));
            String s;
            heapArr.add(0,null);
            HeapNode newNode = null;

            while ((s = reader.readLine()) != null) {
                int number = Integer.parseInt(s.substring(0,s.indexOf(",")));
                String nodeName = s.substring(s.indexOf(","),s.length());

                newNode = new HeapNode(number,nodeName);
                heapArr.add(newNode);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
        size = heapArr.size();
    }

    private void maxHeapify(ArrayList heapArr, int rootIdx){
        int lCIdx = 2*rootIdx, rCIdx = (2*rootIdx)+1;
        int largestIdx=0;
        HeapNode rootNode = (HeapNode)heapArr.get(rootIdx);
        HeapNode leftChild = null;
        HeapNode rightChild = null;
        if(lCIdx > size && rCIdx > size) return;
        if (lCIdx < size)
            leftChild = (HeapNode)heapArr.get(lCIdx);
        if (rCIdx < size)
            rightChild = (HeapNode)heapArr.get(rCIdx);

        if(leftChild != null && leftChild.priority > rootNode.priority)
            largestIdx = lCIdx;
        else largestIdx = rootIdx;
        HeapNode largestNode = (HeapNode)heapArr.get(largestIdx);
        if (rightChild != null && rightChild.priority > largestNode.priority)
            largestIdx = rCIdx;
        if (largestIdx != rootIdx){
            swapNode(heapArr,rootIdx,largestIdx);
            maxHeapify(heapArr, largestIdx);
        }
    }


    void buildMaxHeap(){
        int heapSize = size;
        for(int i = heapSize/2; i >= 1; i--)
            maxHeapify(heapArr,i);
    }

    private void swapNode(ArrayList arr, int aIdx, int bIdx){
        HeapNode temp = (HeapNode)arr.get(aIdx);
        arr.set(aIdx,arr.get(bIdx));
        arr.set(bIdx,temp);
    }

    void insert(HeapNode newNode){
        int currentIdx = heapArr.size();
        heapArr.add(newNode);
        size++;
        HeapNode parentNode = (HeapNode)heapArr.get(currentIdx/2);
        if(parentNode == null) return;

        while(currentIdx != 1 && newNode.priority > parentNode.priority){
            swapNode(heapArr,currentIdx,currentIdx/2);
            currentIdx = currentIdx/2;
        }
    }
    HeapNode extract_max(){
        HeapNode maxNode = heapArr.get(1);
        heapArr.set(1,heapArr.get(size-1));
        heapArr.remove(size-1);
        size = heapArr.size();
        maxHeapify(heapArr,1);
        return maxNode;
    }

    HeapNode max(){
        HeapNode maxNode = heapArr.get(1);
        return maxNode;
    }

    void delete(int idx){
        heapArr.remove(idx);
        size = heapArr.size();
        //heapArr.set(idx,heapArr.get(size));
        maxHeapify(heapArr,1);
    }

    void increaseKey(int idx, int value){
        HeapNode targetNode = heapArr.get(idx);
        if(targetNode.priority > value) System.out.println("현재 키값보다 작은 값으로는 증가시킬 수 없습니다.");
        else {
            targetNode.priority = value;
            maxHeapify(heapArr,1);
        }
    }
}