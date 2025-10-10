class FilaCircular {
    private int[] vetor;
    private int primeiro;
    private int ultimo;
    public FilaCircular(int max){
        this.vetor = new int[max+1];
        this.primeiro = 0;
        this.ultimo = 0;
    }

    public void enfileirar(int elem){
        if((ultimo+1)%vetor.length == primeiro){
            System.out.println("Fila Circular cheia");
            return;
        }
        vetor[ultimo]=elem;
        ultimo=(ultimo+1)%vetor.length;
    }

    public int desenfileirar(){
        if(ultimo==primeiro){
            System.out.println("Fila Circular vazia");
            return -1;
        }
        int valorRemovido = vetor[primeiro];
        primeiro=(primeiro+1)%vetor.length;
        return valorRemovido;
    }

    public void mostrar(){
        System.out.print("Fila Circular: [");
        for(int i=primeiro;i!=ultimo;i=(i+1)%vetor.length){
            System.out.print(vetor[i] + " ");
        }
        System.out.println("]");
    }

    public boolean pesquisar(int elem){
        for(int i=primeiro;i!=ultimo;i=(i+1)%vetor.length){
            if(vetor[i]==elem){
                return true;
            }
        }
        return false;
    }
}
