public class Teste {
    public static void main (String[] args){
        int[] array = new int[]{28,5,6,23,45,6,34,435,463,63,34};
        int n=11;
        for(int i=1;i<n;i++){
            int j=i-1;
            int temp=array[i];
            while(j>=0 && array[j]>temp){
                array[j+1]=array[j];
                j--;
            }
            array[j+1]=temp;
        }
        for(int i=0;i<n;i++){
            System.out.println(array[i] + "\n");
        }
        
    }
}
