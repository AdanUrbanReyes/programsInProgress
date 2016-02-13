#include <stdio.h>
#ifndef __STACK_H__
#define __STACK_H__
typedef struct stack{
	void *element;
	struct stack *down;
}stack;
void push(stack **tope,void *element){
	stack *nvo=(stack *)malloc(sizeof(stack));
	if(nvo==NULL){
		printf("\ndont have more memory");
		return;
	}
	nvo->element=element;
	nvo->down=*tope;
	*tope=nvo;
}
void *pop(stack **tope){
	void *temp;
	if(*tope!=NULL){
		temp=(*tope)->element;
		*tope=(*tope)->down;
		return temp;
	}
	return NULL;	
}
#endif
