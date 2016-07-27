import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class RoboCrab {

    static Path[] listPath;

    public static void main(String[] args) throws FileNotFoundException {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
		Scanner sc = new Scanner(new FileInputStream("res/inputRoboCrab.txt"));
    	
//        Scanner sc = new Scanner(System.in);
        
        int T = sc.nextInt();
        
        listPath = new Path[T];

        Point point1 = new Point(0,0);
        
        for(int i=0; i<T; i++){
            int direction = sc.nextInt();
            int distance = sc.nextInt();
            
            listPath[i] = new Path(point1, direction, distance);
            point1=listPath[i].point2;
                        
        }
        boolean collide =false;
        for(int i=1; i < listPath.length; i++){
            for(int j=i; j-->0;){
                if(isIntersect(listPath[i], listPath[j])){
                    System.out.println(i+1);
                    collide = true;
                    break;
                }
            }
            if(collide)
                break;
            
        }
    }

    public static boolean isOverlap(int s1, int e1, int s2, int e2){
        if(isBetween(s1, e1, s2)||
          isBetween(s1, e1, e2)||
          isBetween(s2, e2, s1)||
          isBetween(s2, e2, e1))
            return true;
        
        return false;
    }
    
    
    public static boolean isIntersect(Path path1, Path path2){
        
        if(path1.dir<3){ // check vertical
            
            if(path2.dir>2)
                return isCross(path2, path1);
            else if(path1.point1.x==path2.point1.x)
                return isOverlap(path1.point1.y, path1.point2.y, path2.point1.y, path2.point2.y);
        }else{
            
            if(path2.dir<3)
                return isCross(path1, path2);
            else if(path1.point1.y == path2.point1.y)
                return isOverlap(path1.point1.x, path1.point2.x, path2.point1.x, path2.point2.x);
        }
        
        return false;
    }
    
    public static boolean isBetween(int start, int end, int test){
        return (0<((end-test)*(test-start)));
    }
    
    public static boolean isCross(Path path1, Path path2){
        if(isBetween(path1.point1.x, path1.point2.x, path2.point2.x)&&
          isBetween(path2.point1.y, path2.point2.y, path1.point2.y))
            return true;
         else
            return false;
    }
	
}

class Point{
    int x;
    int y;
    
    public Point(int X, int Y){
        x=X;
        y=Y;
    }
}

class Path{
    Point point1;
    Point point2;
    int dir;
    
    public Path(Point start, int direction, int distance){
        point1 = start;
        
        int endX=point1.x;
        int endY=point1.y;
        
        switch(direction){
            case 1:
                endY+=distance;
                break;
            case 2:
                endY-=distance;
                break;
            case 3:
                endX+=distance;
                break;
            case 4:
                endX-=distance;
                break;
        }
        
        point2 = new Point(endX,endY);
        dir = direction;
        
    }
    
}


