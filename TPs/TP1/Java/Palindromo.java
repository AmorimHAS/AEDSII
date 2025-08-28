import java.util.Scanner;

public class Palindromo {
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        String palavra = sc.nextLine(); // lê a primeira linha de entrada

        // enquanto a palavra digitada não for "FIM"
        while(!(palavra.equals("FIM"))) {
            int tam = palavra.length() - 1; // índice do último caractere da palavra
            int i = 0;
            boolean teste = true; // variável que verifica se é palíndromo

            // compara os caracteres da palavra do início e do fim
            while(tam > i && teste == true) {
                if(palavra.charAt(tam) != palavra.charAt(i)) { // se os caracteres forem diferentes
                    teste = false; 
                }
                tam--;
                i++;
            }

            // imprime o resultado
            if(teste == true) {
                MyIO.println("SIM");
            } else {
                MyIO.println("NAO");
            }

            palavra = sc.nextLine(); // lê a próxima palavra para continuar o loop
        }

        sc.close(); // fecha o Scanner
    }
}