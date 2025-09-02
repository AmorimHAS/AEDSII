import java.util.Scanner;

class Numero{
    int valor;
    int modulo;
    boolean par;
    Numero(int valor,int M){
        this.valor = valor;
        this.modulo = valor%M;
        if(valor%2==0){
            this.par=true;
        }else{
            this.par=false;
        }
    }
}

public class Sort{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        while(N!=0 && M!=0){
            Numero[] numeros = new Numero[N];
            for(int i=0;i<N;i++){
                int valor = sc.nextInt();
                numeros[i]= new Numero(valor,M);
            }
            for (int i = 0; i < (N - 1); i++) {
                int menor = i;
                for (int j = (i + 1); j < N; j++){
                    if (trocarNumeros(numeros,menor,j)==true){
                        menor = j;
                    }
                }
                Numero temp = numeros[i];
                numeros[i] = numeros[menor];
                numeros[menor] = temp;
            }
            System.out.println(N+" "+M);
            for(int i=0;i<N;i++){
                System.out.println(numeros[i].valor);
            }
            N = sc.nextInt();
            M = sc.nextInt();
        }
        System.out.println(N+" "+M);
        sc.close();
    }
    public static boolean trocarNumeros(Numero numeros[],int menor,int j){
        if(numeros[menor].modulo > numeros[j].modulo){
            return true;
        }else if(numeros[menor].modulo < numeros[j].modulo){
            return false;
        }else{
            if(numeros[menor].par==true && numeros[j].par==false){
                return true;
            }else if(numeros[menor].par==false && numeros[j].par==true){
                return false;
            }else if(numeros[menor].par==false && numeros[j].par==false){
                if(numeros[menor].valor>=numeros[j].valor){
                    return false;
                }else{
                    return true;
                }
            }else{
                if(numeros[menor].valor<=numeros[j].valor){
                    return false;
                }else{
                    return true;
                        }
            }
        }
    }
}