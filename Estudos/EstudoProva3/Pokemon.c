#include <stdio.h>
#include <string.h>

typedef struct{
    char nome[1000];
}Pokemon;

int main(){
    int N;
    char S[1000];
    Pokemon array[151];
    int quant=0;
    int jaExiste;
    scanf("%d", &N);
    for(int i=0;i<N;i++){
        scanf("%s",S);
        jaExiste=0;
        for(int j=0;j<quant;j++){
            if(strcmp(S,array[j].nome)==0){
                jaExiste++;
            }
        }
        if(jaExiste==0){
            int x;
            for(x=0;S[x]!='\0';x++){
                array[quant].nome[x]=S[x];
            }
            quant++;
        }
    }
    int pokemonsFaltam;
    pokemonsFaltam=151-quant;
    printf("Falta(m) %d pomekon(s).",pokemonsFaltam);
}
