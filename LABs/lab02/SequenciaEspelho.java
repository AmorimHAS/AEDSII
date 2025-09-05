import java.util.Scanner;

public class SequenciaEspelho{
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            int inicio = sc.nextInt();
            int fim = sc.nextInt();
            impimirSequencia(inicio,fim);
            imprimirEspelho(inicio,fim);
            System.out.println();
        }
        sc.close();
    }

    public static void impimirSequencia(int inicio,int fim){
        for(int i=inicio;i<=fim;i++){
            MyIO.print(i);
        }
    }

    public static void imprimirEspelho(int inicio,int fim){
        for(int i=fim;i>=inicio;i--){
            int aux = i;
            int n;
            while(aux>=10){
                n = aux%10;
                MyIO.print(n);
                aux = aux/10;
            }
            MyIO.print(aux);
        }
    }
    
}