import java.util.Scanner;

public class InversaoString {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        while(!(str.length()==3 && str.charAt(0)=='F' && str.charAt(1)=='I' && str.charAt(2)=='M')){
            int tam = str.length();
            char[] array = new char[tam];
            for(int i=(tam-1);i>=0;i--){
                array[tam-1-i] = str.charAt(i);
            }
            String novaStr = new String(array);
            System.out.println(novaStr);
            str = sc.nextLine();
        }
        sc.close();
    }
}
