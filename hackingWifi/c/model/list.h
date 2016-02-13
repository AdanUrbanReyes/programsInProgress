#include <stdio.h>
#include <stdlib.h>
#ifndef __LIST_H__
#define __LIST_H__
typedef struct node{
	char type;//process or hollow
	unsigned short address;//address start this block of memory
	unsigned short size;//size of block memory
	unsigned short nextAddress;//next adress where start next block
	struct node *next,*previus;
}Node;
void printRAM(Node *head){
	while(head!=NULL){
	   printf("|%c|%x|%d|%x|->",head->type,head->address,head->size,head->nextAddress);
	   head=head->next;
	   if(head==NULL)
	      printf("NULL\n");
	}
}
Node *getNode(char type,unsigned short address,unsigned short size,unsigned short nextAddress,Node *next,Node *previus){
	Node *node=(Node *)malloc(sizeof(Node));
	if(node==NULL)
		return node;
	node->type=type;
	node->address=address;
	node->size=size;
	node->nextAddress=nextAddress;
	node->next=next;
	node->previus=previus;
	return node;
}
Node *addNode(Node **head,Node *toAdd){
	if(*head==NULL){
		*head=toAdd;
	}else{
		Node *auxiliary=*head;
		while(auxiliary->next!=NULL){
			auxiliary=auxiliary->next;
		}
		auxiliary->next=toAdd;
		toAdd->previus=auxiliary;
	}
	return toAdd;
}
Node *addNodeBeforeOf(Node **of,Node *toAdd){
	if(*of==NULL || toAdd==NULL){
		return NULL;
	}
	toAdd->next=(*of);//set like next element of toAdd the element *of
	toAdd->previus=(*of)->previus;//set like previus of toAdd the previus element of *of
	if((*of)->previus!=NULL){
		((*of)->previus)->next=toAdd;//set pointer next of previus element of *of for toAdd
	}else{//then (*of) is head of list then need move pointer head to start list (toAdd)
		(*of)->previus=toAdd;//set pointer previus of *of for toAdd
		*of=toAdd;// move *of for toAdd (move header of list to new header list)
		return toAdd;//return element added
	}
	(*of)->previus=toAdd;//set pointer previus of *of for toAdd
	return toAdd;//return element added
}
Node *addNodeAfterOf(Node **of,Node *toAdd){
	if(*of==NULL || toAdd==NULL){
		return NULL;
	}
	toAdd->previus=(*of);
	toAdd->next=(*of)->next;
	if((*of)->next!=NULL){
		((*of)->next)->previus=toAdd;
	}
	(*of)->next=toAdd;
	return toAdd;
}
Node *removeNode(Node **toRemove){
	if(*toRemove==NULL){
		return NULL;
	}
	Node *auxiliary=*toRemove;
	if((*toRemove)->previus==NULL){//then (*toRemove) is head of list then need move pointer head to start list (*toRemove)->next;
		*toRemove=(*toRemove)->next;//move toRemove (header of list) to next elemente 
		(*toRemove)->previus=NULL;//set pointer previus with null (head->previus=NULL)
	}else{
		if(auxiliary->next!=NULL){//if next not is null
			(auxiliary->next)->previus=auxiliary->previus;//set previus of next element of auxiliary with previus element of auxiliary
		}
		if(auxiliary->previus!=NULL){//if previus not is null
			(auxiliary->previus)->next=auxiliary->next;//set next of previus element of auxiliary with next element of auxiliary
		}
	}
	auxiliary->next=auxiliary->previus=NULL;//set null pointer of auxiliary (here is where remove element :D)
	return auxiliary;//return element droped
}
#endif
