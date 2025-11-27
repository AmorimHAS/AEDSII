class ArvoreArvore{
    No raiz;
    int contarPalavras(char primeiro,char ultimo){
        No noLetra= buscarNo(primeiro,raiz);
        if (noLetra == null) {
            return 0;
        }
        No2 no2Letra= noLetra.raiz;
        int quant=buscarTermIgual(ultimo,no2Letra);
        return quant;
    }
    No buscarNo(char x,No i){
        if(i==null){
            return null;
        }else if(i.letra<x){
            return buscarNo(x,i.dir);
        }else if(i.letra>x){
            return buscarNo(x,i.esq);
        }else{
            return i;
        }
    }
    int buscarTermIgual(char x, No2 i){
        if(i==null){
            return 0;
        }
        int cont=0;
        int tam=i.palavra.length();
        if(i.palavra.charAt(tam-1)==x){
            cont++;
        }
        return cont + buscarTermIgual(x, i.esq) + buscarTermIgual(x, i.dir);
    }
}
class No{
    char letra;
    No esq, dir;
    No2 raiz;
}
class No2{
    String palavra;
    No2 esq, dir;
}
