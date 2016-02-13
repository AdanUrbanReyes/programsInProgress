#include <stdio.h>
#include <stdlib.h>
#include "algoritmos.h"
int main(int ari,char **arc){
	if(ari!=3){
		printf("\nejecute como %s numeroDeNumerosAOrdenar metodoParaOrdenar",arc[0]);
		return -1;
	}
	int numberToRead=atoi(arc[1]);
	long *numbers=(long *)malloc(sizeof(long)*numberToRead);
	saveNumberInArray(numbers,numberToRead);	
//	tree(numbers,numberToRead);
//	bubbleSimple(numbers,numberToRead);
//	bubbleBest(numbers,numberToRead);
//	insercion(numbers,numberToRead);
	seleccion(numbers,numberToRead);
	puts("");return 0;
}
