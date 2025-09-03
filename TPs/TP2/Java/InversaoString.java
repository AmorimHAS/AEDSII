import java.util.Scanner; 

public class InversaoString { 
    public static void main(String[] args){ 
        Scanner sc = new Scanner(System.in); 
        String str = sc.nextLine(); // Lê uma linha de texto e armazena na variável 'str'.
        // Enquanto a string lida não for "FIM", continua o loop.
        while(!(str.length()==3 && str.charAt(0)=='F' && str.charAt(1)=='I' && str.charAt(2)=='M')){
            int tam = str.length(); // Armazena o tamanho da string na variável 'tam'.
            char[] array = new char[tam]; // Cria um array de caracteres com o mesmo tamanho da string.
            for(int i=(tam-1);i>=0;i--){// Loop que percorre a string do último para o primeiro caractere.
                array[tam-1-i] = str.charAt(i); // Atribui os caracteres invertidos ao array.
            }
            String novaStr = new String(array); // Cria uma nova string a partir do array de caracteres invertido.
            System.out.println(novaStr); // Imprime a string invertida.
            str = sc.nextLine(); // Lê a linha de entrada para continuar o loop.
        }
        sc.close();
    }
}