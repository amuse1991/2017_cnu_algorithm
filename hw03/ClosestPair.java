import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class ClosestPair {

    ArrayList<Point> pointList = null;
    private String sortCriteria = null;

    private class Point implements Comparable<Point>{
        double [] coordinates = new double[2];
        Point(double x, double y){
            coordinates[0] = x;
            coordinates[1] = y;
        }

        // Collections.sort()사용하기 위함
        @Override
        public int compareTo(Point p) {
            switch (sortCriteria){
                case "x" :
                    return Double.compare(this.coordinates[0],p.coordinates[0]);
                case "y" :
                    return Double.compare(this.coordinates[1],p.coordinates[1]);
            }
            return 0;
        }
    }

    ClosestPair(){
        pointList = new ArrayList<>();
        readFile();
    }

    public void closestPair(){
        // x축 기준 정렬
        sortCriteria = "x";
        Collections.sort(pointList);
        double result = closestPairFunc(0, pointList.size());
        System.out.println(result);
    }

    private double closestPairFunc(int leftIdx, int numOfPoints){
        if(numOfPoints <= 3) return bruteForce(numOfPoints);
        int mid = numOfPoints/2;
        double leftMinDist = closestPairFunc(0,mid);
        double rightMinDist = closestPairFunc(leftIdx+mid,numOfPoints-mid);

        double minDist = Math.min(leftMinDist,rightMinDist);

        ArrayList<Point> window = new ArrayList<>();
        Point curPoint = null;
        Point midPoint = pointList.get(mid);
        for(int i=0; i<numOfPoints ; i++){
            curPoint=pointList.get(i);
            if(Math.abs(curPoint.coordinates[0] - midPoint.coordinates[0]) < minDist) { // 현재 점과 미드 점의 거리를 계산해 minDist보다 작다면 window에 포함
                window.add(curPoint);
            }
        }
        //y축에 대해 정렬
        sortCriteria = "y";
        Collections.sort(window); // window 에 속한 점들을 y값 기준 정렬
        double tempMinDist = minDist;
        for(int i = 0; i < window.size();i++){
            for (int j = i+1 ; j < window.size() && (window.get(j).coordinates[1] - window.get(j).coordinates[1])< tempMinDist;j++)
                if(euclideanDistance(window.get(i),window.get(j)) < tempMinDist)
                    tempMinDist = euclideanDistance(window.get(i),window.get(j));
        }
        return Math.min(minDist,tempMinDist);
    }

    private double bruteForce(int numOfPoints){

        double minDistance = Double.MAX_VALUE;
        //  돌면서 거리가 가장 짧은 두 점을 고른다
        for(int i = 0 ; i< numOfPoints; i++){
            for(int j = i+1 ; j< numOfPoints; j++){
                if(euclideanDistance(pointList.get(i),pointList.get(j)) < minDistance)
                    minDistance = euclideanDistance(pointList.get(i),pointList.get(j));
            }
        }
        return minDistance;
    }

    // 평면의 두 점 사이의 최단거리 값을 반환해줌
    private double euclideanDistance(Point p1, Point p2){
        double p1x = p1.coordinates[0];
        double p1y = p1.coordinates[1];
        double p2x = p2.coordinates[0];
        double p2y = p2.coordinates[1];

        if(p1x==p2x && p1y == p2y) return 0;
        else return Math.sqrt(Math.abs(Math.pow(p1x-p2x,2)+Math.pow(p1y-p2y,2)));
    }

    private void readFile(){
        try{
            //파일 읽기
            String dir = System.getProperty("user.dir") + "\\src\\closest_data.txt";
            BufferedReader reader = new BufferedReader(new FileReader(dir));
            String line = null;

            while((line=reader.readLine())!=null){
                String [] xAndy = line.split(" "); // x, y를 분리
                Point newPoint = new Point(Double.parseDouble(xAndy[0]),Double.parseDouble(xAndy[1])); //point객체를 만들어 x,y대입
                pointList.add(newPoint); // point list에 추가
            }
            reader.close();
        }
        catch(Exception e) {
            System.exit(-1);
        }
    }
}
