public class Doidona {
    CelulaT1[] t1;
    int tamT1, tamT2;

    void inserir(int x){
        int posT1 = hashT1(x);
        int posT2 = hashT2(x);
        int posRehashT2 = rehashT2(x);
        int posT3 = hashT3(x);
        if(t1[posT1].t2.tab[posT2]==-1){
            t1[posT1].t2.tab[posT2]=x;
        }else if(t1[posT1].t2.tab[posRehashT2]==-1){
            t1[posT1].t2.tab[posRehashT2]=x;
        }else if(posT3==0){
            Celula temp = new Celula(x);
        if (t1[posT1].t2.primeiroT3 == null) {
            t1[posT1].t2.primeiroT3 = temp;
            t1[posT1].t2.ultimoT3 = temp;
        } else {
            t1[posT1].t2.ultimoT3.prox = temp;
            t1[posT1].t2.ultimoT3 = temp;
        }
        }else{
            t1[posT1].t2.raizT3 = inserirArvore(x,t1[posT1].t2.raizT3);
        }
    }
    No inserirArvore(int x,No i){
        if(i==null){
            i=new No(x);
        }else if(x<i.numero){
            i.esq=inserirArvore(x,i.esq);
        }else if(x>i.numero){
            i.dir=inserirArvore(x,i.dir);
        }else{
            System.out.println("Erro!");
        }
        return i;
    }

    int hashT1(int x){
        return x % tamT1;
    }

    int hashT2(int x){
        return x % tamT2;
    }

    int rehashT2(int x){
        return ++x % tamT2;
    }

    int hashT3(int x){
        return x % 2;
    }

}

class CelulaT1{
    public int elemento;
    public HashT2 t2;
}

class HashT2{
    int[] tab;
    Celula primeiroT3, ultimoT3;
    No raizT3;
}

class No{
    int numero;
    No esq, dir;
    No(int x){
        this.numero=x;
        No esq=dir=null;
    }
}

class Celula{
    int numero;
    Celula prox;
    Celula(int x){
        this.numero=x;
        this.prox=null;
    }
}
