#include <stdio.h>
#include <stdbool.h>

int main (){
    int A, B;
    scanf("%i %i",&A, &B);
    int SA[A];
    int SB[B];
    for(int i=0;i<A;i++){
        scanf("%i",&SA[i]);
    }
    for(int i=0;i<B;i++){
        scanf("%i",&SB[i]);
    }
    int i;
    int j=0;
    int cont=0;
    int aux;
    for(i=0;i<B;i++){
        while(j<A){
            if(SB[i]==SA[j]){
                cont++;
                aux=j;
                j=A;
            }else{
                j++;
            }
        }
        j=aux;
    }
    if(cont==B){
        printf("S\n");
    }else{
        printf("N\n");
    }
}