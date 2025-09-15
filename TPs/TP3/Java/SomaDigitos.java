import java.util.Scanner; // Importa a classe para ler dados de entrada

public class SomaDigitos {
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        String num = sc.nextLine(); // Le a primeira entrada
        
        // Loop para processar entradas até a palavra "FIM"
        while(!(num.equals("FIM"))){
            int tam = num.length(); // Pega o tamanho da String
            int resposta = soma(num, tam); // Chama a funcao
            
            System.out.println(resposta); // Imprime a soma dos dígitos
            
            num = sc.nextLine(); // Lê a próxima linha
        }
        
        sc.close(); // Fecha o Scanner
    }

    // Função recursiva que soma os dígitos de uma string
    public static int soma (String num, int tam){
        // Caso base: se o tamanho for 0, retorna 0
        if(tam == 0){
            return 0; //Retorna 0 caso a String tenha terminado
        } else {
            // Passo recursivo: pega o último dígito, converte para int e soma com o restante da string
            return (num.charAt(tam-1) - '0') + soma(num, tam - 1);
        }
    }
}
