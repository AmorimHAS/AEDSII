class FilaLinear {
    private int[] vetor;
    private int indice;
    public FilaLinear(int max){
        this.vetor = new int[max];
        this.indice = 0;
    }

    public void enfileirar(int elem){
        if(indice == vetor.length){
            System.out.println("Fila cheia");
            return;
        }
        vetor[indice]=elem;
        indice++;
    }

    public int desenfileirar(){
        if(indice == 0){
            System.out.println("Fila vazia");
            return -1;
        }
        int valorRemovido = vetor[0];
        for(int i=0;i<indice-1;i++){
            vetor[i]=vetor[i+1];
        }
        indice--;
        return valorRemovido;
    }

    public void mostrar(){
        System.out.print("Fila: [");
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
