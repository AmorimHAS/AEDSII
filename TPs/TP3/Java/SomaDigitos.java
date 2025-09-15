import java.util.*;

public class SomaDigitos {
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        String num = sc.nextLine();
        while(!(num.equals("FIM"))){
            int tam = num.length();
            int resposta = soma(num,tam);
            System.out.print(resposta);
            System.out.println();
            num = sc.nextLine();
        }
        sc.close();
    }

    public static int soma (String num,int tam){
        if(tam==0){
            return 0;
        }else{
            return (num.charAt(tam-1)-'0') + soma(num,tam-1);
        }
    }
}
