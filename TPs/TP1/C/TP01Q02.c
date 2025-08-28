#include <stdio.h>
#include <string.h>
#include <stdbool.h>

bool descobrirPalindromo(char str[],int TAM,int i){
    if (i >= TAM){
        return true;
    }
    if(str[i]!=str[TAM]){
        return false;
    }else{
        return descobrirPalindromo(str,TAM-1,i+1);
    }
}

int main(){
    char str[10000];
    while(fgets(str, sizeof(str), stdin) != NULL){
        int TAM = strlen(str);
        int i=0;
        if (TAM > 0 && str[TAM - 1] == '\n') {
            str[--TAM] = '\0';
        }
        bool palindromo = descobrirPalindromo(str,TAM-1,i);
        if(palindromo==1){
        printf("SIM\n");
        }else{
            printf("NAO\n");
        }
    }
    return 0;
}