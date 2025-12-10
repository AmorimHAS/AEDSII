class Doidona {
    private No raiz;
    public int tamT1, tamT2;

    boolean pesquisar(String nome){
        boolean resp=false;
        int posT1=hashT1(nome.charAt(nome.length()-1));
        int posReHashT1=rehashT1(nome.charAt(nome.length()-1));
        int posT2=hashT2(nome.length());
        No no=pesquisarArvore(nome.charAt(0),raiz);
        if(nome.equals(no.t1.palavras[posT1])){
            resp=true;
        }else if(nome.equals(no.t1.palavras[posReHashT1])){
            resp=true;
        }else{
            for(Celula i=no.t1.t2.celulast2[posT2].inicio;i!=null;i=i.prox){
                if(nome.equals(i.palavra)){
                    resp=true;
                    i=no.t1.t2.celulast2[posT2].fim;
                }else if(nome.compareTo(i.palavra)<0){
                    i=no.t1.t2.celulast2[posT2].fim;
                }
            }
        }
        return resp;
    }

    No pesquisarArvore(char c, No i){
        if(c == i.caracter){
            return i;
        }else if(c < i.caracter){
            return pesquisarArvore(c,i.esq);
        }else{
            return pesquisarArvore(c,i.dir);
        }
    }

    int hashT1(char x){
        return x % tamT1;
    }
    int rehashT1(char x){
        return ++x % tamT1;
    }
    int hashT2(int x){
        return x % tamT2;
    }
}

class No {
    public char caracter;
    public T1 t1;
    public No esq, dir;
}

class T1 {
    public String[] palavras;
    public T2 t2;
}

class T2 {
    public CelulaT2[] celulast2;
}

class CelulaT2 {
    public Celula inicio;
    public Celula fim;
}

class Celula {
    public String palavra;
    public Celula prox;
}