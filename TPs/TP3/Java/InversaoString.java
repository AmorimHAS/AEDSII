import java.util.Scanner; // Importa a classe para ler dados do usuário

public class InversaoString {
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        String frase = sc.nextLine(); // Le a primeira entrada
        
        // Loop principal para processar entradas até a palavra "FIM"
        while(!(frase.equals("FIM"))){
            int tam = frase.length(); // Pega o tamanho da String
            Inversao(frase, tam); // Chama a funcao
            System.out.println(); // Adiciona uma nova linha após a inversão
            
            frase = sc.nextLine(); // Lê a próxima linha
        }
        
        sc.close(); // Fecha o Scanner
    }

    // Função recursiva para inverter e imprimir a string
    public static void Inversao (String frase, int tam){
        // Caso base: se o tamanho for 0, a recursão para
        if(tam == 0){
            return; // Retorna caso a String tenha terminado
        } else {
            // Imprime o último caractere e chama a si mesma para o restante da string
            System.out.print(frase.charAt(tam - 1));
            Inversao(frase, tam - 1);
        }
    }
}