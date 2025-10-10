class ListaLinear {
    private int[] vetor;
    private int indice;
    public ListaLinear(int max){
        this.vetor = new int[max];
        this.indice = 0;
    }

    public void inserirInicio(int elem){
        if(indice == vetor.length){
            System.out.println("Lista cheia");
            return;
        }
        for(int i=indice;i>0;i--){
            vetor[i]=vetor[i-1];
        }
        vetor[0]=elem;
        indice++;
    }

    public void inserirFim(int elem){
        if(indice == vetor.length){
            System.out.println("Lista cheia");
            return;
        }
        vetor[indice]=elem;
        indice++;
    }

    public void inserir(int elem, int pos){
        if(indice == vetor.length){
            System.out.println("Lista cheia");
            return;
        }
        for(int i=indice;i>pos;i--){
            vetor[i]=vetor[i-1];
        }
        vetor[pos]=elem;
        indice++;
    }

    public int removerInicio(){
        if(indice==0){
            System.out.println("Lista vazia");
            return -1;
        }
        int valorRemovido=vetor[0];
        for(int i=0;i<indice-1;i++){
            vetor[i]=vetor[i+1];
        }
        indice--;
        return valorRemovido;
    }

    public int removerFim(){
        if(indice==0){
            System.out.println("Lista vazia");
            return -1;
        }
        int valorRemovido=vetor[indice-1];
        indice--;
        return valorRemovido;
    }

    public int remover(int elem, int pos){
        if(indice==0){
            System.out.println("Lista vazia");
            return -1;
        }
        int valorRemovido=vetor[pos];
        for(int i=pos;i<indice-1;i++){
            vetor[i]=vetor[i+1];
        }
        indice--;
        return valorRemovido;
    }

    public void mostrar(){
        System.out.print("Lista: [");
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