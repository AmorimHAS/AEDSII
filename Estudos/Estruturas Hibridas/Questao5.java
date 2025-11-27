/* * ======== CLASSE Matriz (Principal) ========
 * Contém a lógica solicitada pelo exercício.
 */
class Matriz {
    CelulaMatriz inicio;
    int linha, coluna; // Campos da classe (como na imagem)

    /**
     * MÉTODO SOLICITADO PELO EXERCÍCIO
     * Retorna o início de uma lista duplamente encadeada
     * contendo todos os elementos das listas da diagonal principal.
     */
    public CelulaDupla diagUnificada() {
        
        // 1. Inicializa a nova lista duplamente encadeada (que será o resultado)
        CelulaDupla resultadoInicio = null;
        CelulaDupla resultadoFim = null; // Ponteiro para o último nó (para apêndice rápido)

        // 2. Ponteiro para percorrer a diagonal da matriz
        CelulaMatriz pDiag = this.inicio; // Começa em [0,0]

        // 3. Loop pela DIAGONAL PRINCIPAL (enquanto não sair da grade)
        while (pDiag != null) {
            
            // 4. Acessa a lista interna da célula da diagonal atual
            // Pula a "Célula Cabeça" (sentinela)
            Celula pLista = pDiag.inicio.prox;

            // 5. Loop pela LISTA INTERNA
            while (pLista != null) {
                
                // 6. Cria um NOVO nó para a lista de resultado
                CelulaDupla novoNo = new CelulaDupla(pLista.elemento);

                // 7. Apenda o novo nó ao fim da lista de resultado
                if (resultadoInicio == null) {
                    // A lista de resultado está vazia
                    resultadoInicio = novoNo;
                    resultadoFim = novoNo;
                } else {
                    // A lista de resultado já contém nós
                    resultadoFim.prox = novoNo;
                    novoNo.ant = resultadoFim;
                    resultadoFim = novoNo; // Atualiza o ponteiro do fim
                }
                
                // Avança na lista interna
                pLista = pLista.prox;
            }

            // 8. Avança para o próximo elemento da DIAGONAL
            // De [i,i] para [i+1, i+1]
            // Move para baixo (inf) e depois para a direita (dir)
            if (pDiag.inf != null) {
                pDiag = pDiag.inf.dir;
            } else {
                pDiag = null; // Fim da matriz
            }
        }

        // 9. Retorna o início da lista recém-criada
        return resultadoInicio;
    }
}


/* * ======== CLASSES DA ESTRUTURA (Dadas no exercício) ========
 */

/**
 * Célula da grade 2D (Matriz).
 * Contém ponteiros para vizinhos e para uma lista interna.
 */
class CelulaMatriz {
    CelulaMatriz esq, dir, inf, sup;
    Celula inicio, fim; // Lista interna

    // Construtor (como na imagem)
    public CelulaMatriz() {
        esq = dir = inf = sup = null;
        inicio = fim = new Celula(); // Cria a lista interna com o nó cabeça
    }
}

/**
 * Nó da lista interna (Singly Linked List).
 */
class Celula {
    int elemento;
    Celula prox;

    // Construtor para nó cabeça (sentinela)
    public Celula() {
        this(0);
    }

    // Construtor para nó de dados
    public Celula(int elemento) {
        this.elemento = elemento;
        this.prox = null;
    }
}

/**
 * Nó da lista de resultado (Doubly Linked List).
 */
class CelulaDupla {
    int elemento;
    CelulaDupla prox, ant;

    // (Construtores adicionados para consistência e clareza)
    public CelulaDupla() {
        this(0);
    }

    public CelulaDupla(int elemento) {
        this.elemento = elemento;
        this.prox = this.ant = null;
    }
}