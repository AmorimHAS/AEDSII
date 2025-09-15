import java.util.*;

public class InversaoString{
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        String frase = sc.nextLine();
        while(!(frase.equals("FIM"))){
            int tam = frase.length();
            Inversao(frase, tam);
            System.out.println();
            frase = sc.nextLine();
        }
        sc.close();
    }

    public static void Inversao (String frase, int tam){
        if(tam==0){
            return;
        }else{
            System.out.print(frase.charAt(tam-1));
            tam -=1;
            Inversao(frase,tam);
        }
    }
}