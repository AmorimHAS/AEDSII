class ArvoreTrie{
    No raiz;
    public boolean pesquisar(String s, No no, int i){
        boolean resp=false;
        if(no.prox[s.charAt(i)]==null){
            resp=false;
        }else if(i==s.length()-1){
            if(no.prox[s.charAt(1)].folha==true){
                no.prox[s.charAt(i)].frequencia ++;
                resp=true;
            }else{
                resp=false;
            }
        }else{
            pesquisar(s,no.prox[s.charAt(i)],i+1);
        }
        return resp;
    }
    public void inserir(String s,No no, int i){
        if(no.prox[s.charAt(i)]==null){
            no.prox[s.charAt(i)]=new No(s.charAt(i));
            if(i==s.length()-1){
                no.prox[s.charAt(i)].folha=true;
                no.prox[s.charAt(i)].frequencia=1;
            }else{
                inserir(s,no.prox[s.charAt(i)],i+1);
            }
        }else if(no.prox[s.charAt(i)].folha==true && i==s.length()-1){
            no.prox[s.charAt(i)].frequencia++;
        }else if(no.prox[s.charAt(i)].folha==false && i<s.length()-1){
            inserir(s,no.prox[s.charAt(i)],i+1);
        }else{
            System.out.println("Erro");
        }
    }
}

class No{
    public char elemento;
    public int tamanho = 255;
    public No[] prox;
    public boolean folha;
    public int frequencia;
    public No (char elemento){
        this.elemento = elemento;
        prox = new No [tamanho];
        for (int i = 0; i < tamanho; i++) prox[i] = null;
        this.folha = false;
        this.frequencia=0;
   }
}