#include <stdio.h>
#include <stdlib.h>
#include "stack.h"
#ifndef __ARBOL_H__
#define __ARBOL_H__
/*
	crateNodo solo crea una hoja del arbol y regresa la hoja creada en caso de que no alla memoria imprime un error, recive como parametro el numbero o dato que se desea agregar ala hoja
	addNodo agrega una hoja al arbol tomando en cuenta el valor de nodo a agregar lo pone ala izquierda si es mayor y ala izquierda si es mayor	
*/
typedef struct nodo{
	long number;
	struct nodo *left,*right;
}nodo;
nodo *createNodo(long data){
	nodo *nvo=(nodo *)malloc(sizeof(nodo));
	if(nvo==NULL){
		printf("\ndont have more memory\terror to create nodo");
		return NULL;
	}
	nvo->number=data;
	nvo->left=nvo->right=NULL;
	return nvo;
}
void addNodo(nodo **raiz,nodo *nodoToAdd){
	if(*raiz==NULL){
		*raiz=nodoToAdd;
		return;
	}
	nodo *aux=*raiz;
	while(1){
		if(nodoToAdd->number<aux->number){//add nodo to left
			if(aux->left==NULL){
				aux->left=nodoToAdd;
				return ;
			}else aux=aux->left;
		}
		else{//add nodo to right
			if(aux->right==NULL){
				aux->right=nodoToAdd;
				return;
			}else aux=aux->right;
		}
	}
}
void inOrden(nodo *raiz){
	stack *pila=NULL;
	do{
		while(raiz!=NULL){
			push(&pila,(void *)raiz);
			raiz=raiz->left;
		}
		raiz=(nodo *)pop(&pila);
		printf(" %ld ",raiz->number);
		raiz=raiz->right;	
	}while(pila!=NULL||raiz!=NULL);
}
#endif
