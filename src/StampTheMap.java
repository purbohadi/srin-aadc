import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class StampTheMap {
    
    private static int N, M;
    private static char[][] data;
    private static boolean[][] stamped;

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();
        data = new char[N][M];

        for (int i = 0; i < N; i++) {
            String line = sc.next();
            for (int j = 0; j < M; j++) {
            data[i][j] = line.charAt(j);
            }
        }

        int left = 1;
        int right = Math.min(N, M) + 1;
        while (right - left > 1) {
            int middle = (left + right) / 2;
            if (isPossibleToColor(middle)) {
            left = middle;
            } else {
            right = middle;
            }
        }

        System.out.println(left);
    }
    
    private static boolean isPossibleToColor(int width) {

        stamped = new boolean[N][M];

        if(width==0)
            return false;

        for (int row = 0; row < N-width+1; row++) {
            for (int col = 0; col < M-width+1; col++) {
            if (isStampAble(col, row, width)) {
                markCell(col, row, width);
            }
            }
        }

        if(isAllStamped())
            return true;

        return false;
    }

    private static boolean isStampAble(int x, int y, int w) {

        for (int i = y; i < w + y; i++) {
            for (int j = x; j < w + x; j++) {
            if (data[i][j] == '#')
                return false;
            }
        }
        return true;
    }
    
    private static boolean isAllStamped(){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
            if (!stamped[i][j]&&data[i][j]=='_') {
                return false;
            }
            }
        }
        return true;
    }
    
    private static void markCell(int startX, int startY, int w){
        for (int i = startY; i < startY+w; i++) {
            for (int j = startX; j < startX+w; j++) {
            stamped[i][j]=true;
            }
        }
    }
}