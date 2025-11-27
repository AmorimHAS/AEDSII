class Lista{
    CelulaLista inicio;
    CelulaLista fim;
    CelulaLista maiorPilha(){
        if(inicio==null){
            return null;
        }
        CelulaLista resp = inicio;
        int maiorTam=tamanhoPilha(inicio);
        for(CelulaLista i=inicio.prox;i!=null;i=i.prox){
            int tamI=tamanhoPilha(i);
            if(tamI>maiorTam){
                maiorTam=tamI;
                resp=i;
            }
        }
        return resp;

    }
    int tamanhoPilha(CelulaLista i){
        int resp=0;
        if(i.topo==null){
            return 0;
        }else{
            for(CelulaPilha j=i.topo;j!=null;j=j.proxi,resp++);
        }
        return resp;
    }
}

class CelulaLista{
    CelulaPilha topo;
    CelulaLista prox;
}
class CelulaPilha{
    int elemento;
    CelulaPilha proxi;
}
