import java.util.Scanner;

public class Palindromo {
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        String palavra = sc.nextLine();
        while(!(palavra.equals("FIM"))){
            int tam = palavra.length() - 1;
            int i=0;
            boolean teste = true;
            while(tam>i && teste == true){
                if(palavra.charAt(tam) != palavra.charAt(i)){
                    teste=false;
                }
                tam--;
                i++;
            }
            if(teste==true){
                MyIO.println("SIM");
            }else{
                MyIO.println("NAO");
            }
            
        }
        sc.close();
    }
}
