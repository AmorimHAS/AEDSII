/* 
 * Você foi contratado para desenvolver uma agenda de contatos (atributos nome, telefone, email e CPF)
para um escritório de advocacia

 * Um colega sugeriu implementar uma árvore de binária de listas em que a pesquisa na árvore acontece pela 
primeira letra do nome e, quando encontramos a letra, temos uma pesquisa em uma lista de contatos

 * Atividade:
• Crie uma classe Contato contendo os atributos String nome, telefone e e-mail e int CPF
• Crie uma classe Celula contendo os atributos Contato contato e Celula prox
• Crie uma classe No contendo os atributos char letra, No esq e dir; e Celula primeiro e ultimo
• Crie uma classe Agenda contendo o atributo No raiz, os métodos inserir(Contato contato), remover(String nome),
pesquisar(String nome) e pesquisar(int cpf). No construtor, insira todas as letras (Nós): somente letras maiúsculas
e sem acento. Para cada método, mostre o melhor e pior caso
*/

class Contato{
    public String nome;
    public String telefone;
    public String email;
    public int CPF;
    public Contato(String nome, String telefone, String email, int CPF){
        this.nome=nome;
        this.telefone=telefone;
        this.email=email;
        this.CPF=CPF;
    }
}

class Celula{
    public Contato contato;
    public Celula prox;
    public Celula(){
        this.contato=null;
        this.prox=null;
    }
    public Celula(Contato contato){
        this.contato=contato;
        this.prox=null;
    }
}

class No{
    public char letra;
    public No esq;
    public No dir;
    public Celula primeiro;
    public Celula ultimo;
    public No(char letra){
        this.letra = letra;
        this.esq=null;
        this.dir=null;
        this.primeiro= new Celula();
        this.ultimo=this.primeiro;
    }
    
}

public class Agenda {
    No raiz;

    /**
     * Construtor da Agenda.
     * Insere todas as letras maiúsculas (A-Z) na árvore.
     * Para garantir performance (O(log N)), criamos uma árvore balanceada.
     */
    public Agenda() {
        this.raiz = null;
        char[] letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        
        // Chama a função recursiva para construir a árvore balanceada
        this.raiz = construirArvoreBalanceada(letras, 0, letras.length - 1);
    }

    /**
     * Método auxiliar recursivo para construir uma árvore balanceada
     * a partir de um array ordenado de letras.
     */
    private No construirArvoreBalanceada(char[] letras, int inicio, int fim) {
        if (inicio > fim) {
            return null; // Caso base da recursão
        }
        
        // 1. Acha o meio
        int meio = (inicio + fim) / 2;
        
        // 2. Cria o nó raiz
        No no = new No(letras[meio]);
        
        // 3. Constrói recursivamente as sub-árvores
        no.esq = construirArvoreBalanceada(letras, inicio, meio - 1);
        no.dir = construirArvoreBalanceada(letras, meio + 1, fim);
        
        return no;
    }

    /**
     * Método auxiliar para buscar o Nó da árvore correspondente a uma letra.
     * Retorna o Nó (A, B, C...) onde o contato será inserido/buscado.
     */
    private No buscarNoLetra(char letra) {
        letra = Character.toUpperCase(letra);
        return buscarNoLetra(this.raiz, letra);
    }

    private No buscarNoLetra(No no, char letra) {
        if (no == null) {
            return null; // Letra não encontrada (não deveria acontecer)
        }
        
        if (letra < no.letra) {
            return buscarNoLetra(no.esq, letra); // Busca na esquerda
        } else if (letra > no.letra) {
            return buscarNoLetra(no.dir, letra); // Busca na direita
        } else {
            return no; // Encontrou o nó da letra!
        }
    }

    // ==========================================================
    // MÉTODO: INSERIR(Contato contato)
    // ==========================================================
    public void inserir(Contato contato) {
        if (contato == null || contato.nome == null || contato.nome.isEmpty()) {
            System.out.println("Erro: Contato inválido.");
            return;
        }

        // 1. Acha o nó da letra (ex: 'A')
        char primeiraLetra = contato.nome.charAt(0);
        No noLetra = buscarNoLetra(primeiraLetra);

        if (noLetra == null) {
            System.out.println("Erro: Letra '" + primeiraLetra + "' não faz parte da agenda.");
            return;
        }

        // 2. Insere na lista (sempre no fim - O(1))
        Celula novaCelula = new Celula(contato);
        noLetra.ultimo.prox = novaCelula;
        noLetra.ultimo = novaCelula;
        
        System.out.println(contato.nome + " inserido na letra " + noLetra.letra);
    }

    // ==========================================================
    // MÉTODO: PESQUISAR(String nome)
    // ==========================================================
    public Contato pesquisar(String nome) {
        if (nome == null || nome.isEmpty()) {
            return null;
        }

        // 1. Acha o nó da letra (ex: 'B')
        char primeiraLetra = nome.charAt(0);
        No noLetra = buscarNoLetra(primeiraLetra);

        if (noLetra == null) {
            return null; // Letra não existe
        }

        // 2. Procura na lista encadeada daquela letra
        // (Começa do 'primeiro.prox' para pular a célula cabeça)
        for (Celula tmp = noLetra.primeiro.prox; tmp != null; tmp = tmp.prox) {
            if (tmp.contato.nome.equals(nome)) {
                return tmp.contato; // Achou!
            }
        }

        return null; // Não achou
    }

    // ==========================================================
    // MÉTODO: REMOVER(String nome)
    // ==========================================================
    public boolean remover(String nome) {
        if (nome == null || nome.isEmpty()) {
            return false;
        }

        // 1. Acha o nó da letra
        char primeiraLetra = nome.charAt(0);
        No noLetra = buscarNoLetra(primeiraLetra);

        if (noLetra == null) {
            return false; // Letra não existe
        }

        // 2. Procura na lista encadeada, guardando a célula ANTERIOR
        Celula anterior = noLetra.primeiro;
        Celula atual = noLetra.primeiro.prox;

        while (atual != null) {
            if (atual.contato.nome.equals(nome)) {
                // Achou! Remove o 'atual'
                anterior.prox = atual.prox;

                // Caso especial: se o removido era o ÚLTIMO
                if (atual == noLetra.ultimo) {
                    noLetra.ultimo = anterior;
                }
                
                return true; // Removeu com sucesso
            }
            anterior = atual;
            atual = atual.prox;
        }

        return false; // Não encontrou para remover
    }

    // ==========================================================
    // MÉTODO: PESQUISAR(int cpf)
    // ==========================================================
    public Contato pesquisar(int cpf) {
        // Este é o método mais lento.
        // Ele precisa percorrer TODA a árvore (Nós A-Z)
        // e TODAS as listas de cada nó.
        return pesquisarCpf(this.raiz, cpf);
    }

    /**
     * Método auxiliar recursivo para pesquisar o CPF.
     * (Faz um percurso em-ordem: Esq, Raiz, Dir)
     */
    private Contato pesquisarCpf(No no, int cpf) {
        if (no == null) {
            return null; // Chegou ao fim de um galho
        }
        
        // 1. Busca na sub-árvore esquerda
        Contato achado = pesquisarCpf(no.esq, cpf);
        if (achado != null) {
            return achado;
        }
        
        // 2. Busca na lista do nó ATUAL (letra)
        for (Celula tmp = no.primeiro.prox; tmp != null; tmp = tmp.prox) {
            if (tmp.contato.CPF == cpf) {
                return tmp.contato; // Achou!
            }
        }
        
        // 3. Busca na sub-árvore direita
        achado = pesquisarCpf(no.dir, cpf);
        
        return achado; // Retorna o resultado (pode ser null)
    }
}