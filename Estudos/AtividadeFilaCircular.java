public class AtividadeFilaCircular {
    class FilaCircular{
        int[] array;
        int primeiro;
        int ultimo;
        FilaCircular(int max){
            array= new int[max+1];
            primeiro=0;
            ultimo=0;
        }
        void enfilerar(int elem){
            if(((ultimo+1)%array.length)==primeiro){
                System.out.println("Erro!");
            }
            array[ultimo]=elem;
            ultimo=(ultimo+1)%array.length;
        }
        int desenfileirar(){
            if(ultimo==primeiro){
                System.out.println("Underflow: fila vazia!");
                return -1;
            }
            int resposta= array[primeiro];
            primeiro=(primeiro+1)%array.length;
            return resposta;
        }
        void mostrar(){
            System.out.println("[ ");
            for(int i=primeiro;i<ultimo;i=((i+1)%array.length)){
                System.out.print(array[i] + " ");
            }
            System.out.println("]");
        }
        boolean pesquisar(int elem){
            boolean resposta=false;
            for(int i=primeiro;i<ultimo;i=(i+1)%array.length){
                if(array[i]==elem){
                    resposta=true;
                }
            }
            return resposta;
        }
    }
}
