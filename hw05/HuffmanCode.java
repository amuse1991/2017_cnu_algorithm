import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;

public class HuffmanCode {
    final static int ASCII_LOWERCASE_END = 122;
    final static int ASCII_LOWERCASE_START = 97;

    Hashtable<String, Integer> freqTable = new Hashtable<>();
    String [] codeList = new String[ASCII_LOWERCASE_END+1]; // 0~122
    HuffMinHeap heap;

    public static void main(String args[]) {
        HuffmanCode huff = new HuffmanCode();
        huff.runHuffmanCode();
        huff.printCode();
    }

    HuffmanCode() {
        this.readFile();
        this.heap = new HuffMinHeap();
    }

    public void runHuffmanCode() {
        HuffHeapNode zLeftNode;
        HuffHeapNode zRightNode;
        int zFreqency;
        HuffHeapNode zNode;
        while(heap.heapArr.size() > 3) {
            // 힙에서 빈도 가장 낮은거 2개 뽑기
            zLeftNode = heap.extract_min();
            zRightNode = heap.extract_min();
            // 가상 노드의 빈도 계산
            zFreqency = zLeftNode.frequency + zRightNode.frequency;
            // 가상 노드 생성
            zNode = new HuffHeapNode(zFreqency,"None", zLeftNode, zRightNode);
            heap.insert(zNode);
        }
        //반복문 끝나면 힙에 2개 남음
        zLeftNode = heap.heapArr.get(1);
        zRightNode = heap.heapArr.get(2);
        zFreqency = zLeftNode.frequency + zRightNode.frequency;

        HuffHeapNode huffTree = new HuffHeapNode(zFreqency,"None", zLeftNode, zRightNode); // 허프만 트리 완성

        treeCodify(huffTree,""); // 완성된 트리에 코드 부여(루트부터 시작해서 아래로 recursion)
    }

    private void treeCodify(HuffHeapNode root, String curCode){
        if(root.letter.equals("None")){ //리프 노드가 아닌 경우
            treeCodify(root.left, curCode+'0');
            treeCodify(root.right, curCode+"1");
        }else{
            codeList[root.letter.charAt(0)] = curCode;
        }
    }

    public void printCode(){
        for(int i = ASCII_LOWERCASE_START; i<=ASCII_LOWERCASE_END; i++) {
            if(codeList[i] != null)
                System.out.println((char)i +","+codeList[i]);
        }
    }

    private void readFile() {
        try {
            String path = System.getProperty("user.dir") + "\\src\\data06_huffman.txt";
            BufferedReader reader = new BufferedReader(new FileReader(path));

            String entireStr = reader.readLine();
            reader.close();

            for (int ascii = ASCII_LOWERCASE_START; ascii <= ASCII_LOWERCASE_END; ascii++) {
                String curChar = String.valueOf(Character.toChars(ascii));
                int numOfThatChar = getHowManyThisChar(entireStr, ascii);
                if (numOfThatChar != 0) freqTable.put(curChar, numOfThatChar); // frequency가 0이 아닐 때만 테이블에 삽입
            }
        } catch (Exception e) {
            System.out.println("파일 읽기 오류");
            System.exit(-1);
        }
    }

    private int getHowManyThisChar(String str, int ascii) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ascii) count++;
        }
        return count;
    }

    private class HuffHeapNode {
        int frequency;
        String letter;
        HuffHeapNode left;
        HuffHeapNode right;

        HuffHeapNode(int freq, String lett) {
            frequency = freq;
            letter = lett;
        }

        HuffHeapNode(int freq, String lett, HuffHeapNode left, HuffHeapNode right) {
            frequency = freq;
            letter = lett;
            this.left = left;
            this.right = right;
        }
    }
    // ************************************ MIN HEAP CODE START ***********************************
        private class HuffMinHeap {
            ArrayList<HuffHeapNode> heapArr = new ArrayList<>();
            int size = 0;

            HuffMinHeap() {
                heapArr.add(null);
                // freqTable을 읽어서 heapArr에 삽입
                for (int ascii = ASCII_LOWERCASE_START; ascii <= ASCII_LOWERCASE_END; ascii++) {
                    String charVal = String.valueOf(Character.toChars(ascii));
                    if (freqTable.containsKey(charVal)) {
                        int freq = freqTable.get(charVal);
                        HuffHeapNode newNode = new HuffHeapNode(freq, charVal);
                        heapArr.add(newNode);
                    }
                }
                //heap size 초기화
                size = heapArr.size();
                //buildMinHeap 해서 최소힙으로 만들기
                buildMinHeap();
            }

            void buildMinHeap() {
                int heapSize = size;
                for (int i = heapSize / 2; i >= 1; i--)
                    minHeapify(heapArr, i);
            }

            private void minHeapify(ArrayList heapArr, int rootIdx) {
                int lCIdx = 2 * rootIdx, rCIdx = (2 * rootIdx) + 1;
                int minIdx;
                HuffHeapNode rootNode = (HuffHeapNode) heapArr.get(rootIdx);
                HuffHeapNode leftChild = null;
                HuffHeapNode rightChild = null;
                if (lCIdx > size && rCIdx > size) return;
                if (lCIdx < size)
                    leftChild = (HuffHeapNode) heapArr.get(lCIdx);
                if (rCIdx < size)
                    rightChild = (HuffHeapNode) heapArr.get(rCIdx);

                if (leftChild != null && leftChild.frequency < rootNode.frequency)
                    minIdx = lCIdx;
                else minIdx = rootIdx;
                HuffHeapNode minNode = (HuffHeapNode) heapArr.get(minIdx);
                if (rightChild != null && rightChild.frequency < minNode.frequency)
                    minIdx = rCIdx;
                if (minIdx != rootIdx) {
                    swapNode(heapArr, rootIdx, minIdx);
                    minHeapify(heapArr, minIdx);
                }
            }

            private void swapNode(ArrayList arr, int aIdx, int bIdx) {
                HuffHeapNode temp = (HuffHeapNode) arr.get(aIdx);
                arr.set(aIdx, arr.get(bIdx));
                arr.set(bIdx, temp);
            }

            void insert(HuffHeapNode newNode){
                int currentIdx = heapArr.size();
                heapArr.add(newNode);
                size++;
                HuffHeapNode parentNode = heapArr.get(currentIdx/2);
                if(parentNode == null) return;

                while(currentIdx != 1 && newNode.frequency < parentNode.frequency){
                    swapNode(heapArr,currentIdx,currentIdx/2);
                    currentIdx = currentIdx/2;
                }
            }
            HuffHeapNode extract_min(){
                HuffHeapNode minNode = heapArr.get(1);
                heapArr.set(1,heapArr.get(heapArr.size()-1));
                heapArr.remove(heapArr.size()-1);
                size = heapArr.size();
                minHeapify(heapArr,1);
                return minNode;
            }
        }
    }

