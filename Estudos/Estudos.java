import java.util.*;

public class Estudos {
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[][] matriz = new int[N][M];
        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                matriz[i][j] = sc.nextInt();
            }
        }
    }
}
