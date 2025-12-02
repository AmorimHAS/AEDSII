import java.util.Scanner;

// Classe que representa a estrutura de dados (TAD)
class Pacote {
    // Deixei public para facilitar o acesso na ordenação manual
    public int id;

    public Pacote(int id) {
        this.id = id;
    }

    // Método para formatar a saída
    // Retorna "Package " seguido do número com 3 casas
    public String toString() {
        return String.format("Package %03d", this.id);
    }
}

public class SistemaTCP {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Loop principal (Leitura do arquivo inteiro)
        while (sc.hasNext()) {
            String token = sc.next();

            // Identifica o início de um caso de teste ("1")
            if (token.equals("1")) {
                
                // SEM ArrayList: Criamos um vetor com tamanho seguro (ex: 1000 posições)
                Pacote[] vetor = new Pacote[1000];
                int n = 0; // Variável para controlar quantos pacotes já guardamos

                // Loop de leitura dos pacotes
                while (true) {
                    String leitura = sc.next(); // Lê "Package" ou "0"

                    if (leitura.equals("0")) {
                        break; // Fim do caso de teste
                    }

                    if (leitura.equals("Package")) {
                        int id = sc.nextInt();
                        
                        // Instancia o objeto e guarda na posição 'n'
                        vetor[n] = new Pacote(id);
                        n++; // Incrementa o contador
                    }
                }

                // --- ORDENAÇÃO MANUAL (Bubble Sort) ---
                // Como não podemos usar Collections.sort, fazemos na mão
                for (int i = 0; i < n - 1; i++) {
                    for (int j = 0; j < n - 1 - i; j++) {
                        // Se o ID atual for maior que o próximo, troca
                        if (vetor[j].id > vetor[j + 1].id) {
                            Pacote temp = vetor[j];
                            vetor[j] = vetor[j + 1];
                            vetor[j + 1] = temp;
                        }
                    }
                }

                // --- SAÍDA ---
                // Percorre apenas até 'n' (quantidade real de pacotes lidos)
                for (int i = 0; i < n; i++) {
                    System.out.println(vetor[i].toString());
                }
                System.out.println(); // Linha em branco exigida
            }
        }
        
        sc.close();
    }
}