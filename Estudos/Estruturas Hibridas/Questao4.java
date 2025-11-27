class Matriz {
    CelulaMat inicio;
    void removerImpar(){
        if(inicio==null){
            return;
        }
        CelulaMat pLinha=inicio;
        while(pLinha!=null){
            CelulaMat pColuna=pLinha;
            while(pColuna!=null){
                Celula anterior=pColuna.primeiro;
                Celula atual= pColuna.primeiro.proxi;
                while(atual!=null){
                    if((atual.numero%2)!=0){
                        anterior.proxi=atual.proxi;
                        if (atual == pColuna.ultimo) {
                        pColuna.ultimo = anterior;
                        }
                        atual.proxi=null;
                        atual=anterior.proxi;
                    }else{
                        anterior=anterior.proxi;
                        atual=atual.proxi;
                    }
                }
                pColuna=pColuna.prox;
            }
            pLinha=pLinha.inf;
        }
    }
}

class CelulaMat{
    CelulaMat prox, ant, sup, inf;
    Celula primeiro, ultimo;
    public CelulaMat(){
        prox=ant=sup=inf=null;
        primeiro = ultimo = new Celula();
    }
}

class Celula{
    int numero;
    Celula proxi;
    public Celula(){
        this.numero=0; 
        this.proxi=null;
    }
    public Celula(int elemento){
        this.numero=elemento; 
        this.proxi=null; 
    }
}

