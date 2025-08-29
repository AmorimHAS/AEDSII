#include <stdio.h>

int main(){
    char str1[100],str2[100],str3[100];
    while (scanf("%s %s",str1,str2)==2){
        int i=0,j=0,k=0;
        while(str1[i]!='\0' && str2[j]!='\0'){
            str3[k]=str1[i];
            k++,i++;
            str3[k]=str2[j];
            k++,j++;
        }
        while(str1[i]!='\0'){
            str3[k]=str1[i];
            k++,i++;
        }
        while(str2[j]!='\0'){
            str3[k]=str2[j];
            k++,j++;
        }
        str3[k]='\0';
        printf("%s\n",str3);
    }

}
