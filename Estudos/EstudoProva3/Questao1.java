class No{
    public int elemento;
    public No dir, esq;
}
class Arvore{
    private No raiz;
    public boolean isMax(double valor){
        int quantNos=quantNos(raiz);
        double tamanho = valor * Math.log(quantNos) / Math.log(2);
        int altura=altura(raiz);
        return altura<=tamanho;
    }
    public int quantNos(No i){
        if(i==null){
            return 0;
        }
        return 1 + quantNos(i.dir) + quantNos(i.esq);
    }
    public int altura(No no){
        if (no == null) {
            return 0; 
        }
        int alturaEsq = altura(no.esq);
        int alturaDir = altura(no.dir);
        
        // Retorna 1 + a maior altura entre os filhos
        if (alturaEsq > alturaDir) {
            return 1 + alturaEsq;
        } else {
            return 1 + alturaDir;
        }
    }
}
