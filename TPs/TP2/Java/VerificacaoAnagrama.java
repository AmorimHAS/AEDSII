import java.util.Scanner;

public class VerificacaoAnagrama {
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine(); // Le as primeiras frases
        // Loop até a palavra inserida for FIM
        while(!(str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M')){
            char[] palavra1 = new char[1000]; // palavra1 é um array para guardar a primeira palavra
            char[] palavra2 = new char[1000]; // palavra2 é um array para guardar a segunda palavra

            int i=0;
            int tam1=0; // Variavel para guardar o tamanho da primeira palavra
            for(;str.charAt(i)!=' ';i++){ // guarda a primeira palavra no seu array
                palavra1[tam1] = Character.toLowerCase(str.charAt(i));
                tam1++;
            }
            tam1++;
            palavra1[tam1]='\0'; //coloca um \0 no final do array
            
            i+=3; // pula o i para ir pra segunda palavra
            int tam2=0;
            for(;i<str.length();i++){ // guarda a segunda palavra no seu array
                palavra2[tam2] = Character.toLowerCase(str.charAt(i));
                tam2++;
            }
            tam2++;
            palavra2[tam2]='\0'; //coloca um \0 no final do array

            if(tam1==tam2){ // Verificar se as duas palavras tem o mesmo tamanho
                char temp;

                for(int k=0;k<(tam1-1);k++){ // Metodo de seleçao para ordenar o array da palavra1 em ordem da tabela ascII
                    int menor=k;
                    for(int j=(k+1);j<tam1;j++){
                        if(palavra1[menor]>palavra1[j]){
                            menor=j;
                        }
                    }
                    // Swap para colocar o menor na frente
                    temp=palavra1[k];
                    palavra1[k]=palavra1[menor];
                    palavra1[menor]=temp;
                }

                for(int k=0;k<(tam2-1);k++){ // Metodo de seleçao para ordenar o array da palavra2 em ordem da tabela ascII
                    int menor=k;
                    for(int j=(k+1);j<tam2;j++){
                        if(palavra2[menor]>palavra2[j]){
                            menor=j;
                        }
                    }
                    // Swap para colocar o menor na frente
                    temp=palavra2[k];
                    palavra2[k]=palavra2[menor];
                    palavra2[menor]=temp;
                }

                // Chamando o metodo para verificar se as duas palavras sao ou nao anagramas e imprimir sim ou nao
                if(verificarAnagrama(palavra1,palavra2,tam1)==true){
                    System.out.println("SIM");
                }else{
                    System.out.println("NAO");
                }
            }else{ // Caso as duas palavras nao tenham o mesmo tamanho, NAO é anagrama
                System.out.println("NAO");
            }
            str = sc.nextLine(); // Ler a proxima linha para continuar o loop
        }
    }
    // Verifica se é um anagrama ou nao
    public static boolean verificarAnagrama(char palavra1[], char palavra2[], int tam1) {
        for(int i=0;i<tam1;i++){
            if(palavra1[i]!=palavra2[i]){ // Compara as duas palavras e retorna false se alguma for diferente
                return false;
            }
        }
        return true; // Retorna true se forem anagramas
    }

}