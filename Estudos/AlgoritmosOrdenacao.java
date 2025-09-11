public class AlgoritmosOrdenacao {
    public static void main (String[] args){
        int[] array = new int[]{28,5,6,23,45,6,34,435,463,63,34};
        int n=11;

        //Algoritmo de Selecao
        for(int i=0;i<n-1;i++){
            int menor=i;
            for(int j=(i+1);j<n;j++){
                if(array[j]<array[menor]){
                    menor=j;
                }
            }
            int temp=array[i];
            array[i]=array[menor];
            array[menor]=temp;
        }

        //Algoritmo de Insecao
        for(int i=1;i<n;i++){
            int j=i-1;
            int temp=array[i];
            while(j>=0 && array[j]>temp){
                array[j+1]=array[j];
                j--;
            }
            array[j+1]=temp;
        }

        //Melhoria Selecao
        for (int i = 0; i < n - 1; i++) {
            int menor = i;
            boolean trocou = false;
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[menor]) {
                    menor = j;
                    trocou = true;
                }
            }
            if (trocou) {
                int temp = array[i];
                array[i] = array[menor];
                array[menor] = temp;
            } else {
                break;
            }
        }

        //Melhoria Insercao
        for (int i = 1; i < n; i++) {
            int temp = array[i];
            int inicio = 0, fim = i - 1;
            while (inicio <= fim) {
                int meio = (inicio + fim) / 2;
                if (array[meio] > temp) {
                 fim = meio - 1;
                } else {
                    inicio = meio + 1;
                }
            }
            for (int j = i - 1; j >= inicio; j--) {
                array[j + 1] = array[j];
            }
            array[inicio] = temp;
        }

        //Imprimir array
        for(int i=0;i<n;i++){
            System.out.println(array[i] + "\n");
        }
    }
}