class ArvoreArvore{
    No raiz;
    int contarPalavras(String padrao){
        int tam= padrao.length();
        char primeiraLetra= padrao.charAt(0);
        No noLetra=buscarNo(primeiraLetra,raiz);
        No2 no2Letra = noLetra.raiz;
        int quant=contarMesmoTam(tam,no2Letra);
        return quant;
    }
    No buscarNo(char x,No i){
        if(i==null){
            return null;
        }else if(x<i.letra){
            return buscarNo(x,i.esq);
        }else if(x>i.letra){
            return buscarNo(x,i.dir);
        }else{
            return i;
        }
    }
    int contarMesmoTam(int x,No2 i){
        if(i==null){
            return 0;
        }
        int quant=0;
        if(i.palavra.length()==x){
            quant++;
        }
        return quant + contarMesmoTam(x,i.esq) + contarMesmoTam(x,i.dir);
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
