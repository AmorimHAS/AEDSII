class PilhaLinear {
    private int[] vetor;
    private int indice;
    public PilhaLinear(int max){
        this.vetor = new int[max];
        this.indice = 0;
    }

    public void empilhar(int elem){
        if(indice == vetor.length){
            System.out.println("Pilha cheia");
            return;
        }
        vetor[indice]=elem;
        indice++;
    }

    public int desempilhar(){
        if(indice == 0){
            System.out.println("Pilha vazia");
            return -1;
        }
        int valorRemovido = vetor[indice-1];
        indice --;
        return valorRemovido;
    }

    public void mostrar(){
        System.out.print("Pilha: [");
        for(int i=0;i<indice;i++){
            System.out.print(vetor[i] + " ");
        }
        System.out.println("]");
    }

    public boolean pesquisar(int elem){
        for(int i=0;i<indice;i++){
            if(vetor[i]==elem){
                return true;
            }
        }
        return false;
    }
}
