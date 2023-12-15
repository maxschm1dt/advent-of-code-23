#include <stdio.h>
#include <stdlib.h>

FILE *fptr;
char c;
int fileLen;

int main(){


    //make sure to put a , at the end of the line!
    fptr = fopen("txt/day15.txt", "r");

    if(fptr == NULL){
        printf("file not found");
        return -1;
    }
    

    fseek(fptr, 1, SEEK_END);
    fileLen = ftell(fptr);
    fseek(fptr, 1, SEEK_SET);


    char input[fileLen];
    fread(input, 1, fileLen, fptr);
    fclose(fptr);

    int result = 0;
    int partRes = 0;

    for (int i = 0; i < fileLen; i++)
    {
        if(input[i] == 44){
            result += partRes;
            partRes = 0;
        }else{
            partRes = ((partRes + input[i])* 17 ) % 256;
        }
    }

    printf("res: %d", result);
    
    return 0;
}


int hash(char s[]){
    
}
