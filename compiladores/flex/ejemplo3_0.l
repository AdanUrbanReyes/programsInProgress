%option yylineno
%{
	void imp_pal(FILE *,int);
	enum{CIDENTIFICADOR=128,ERROR};
%}
IDENTIFICADOR [a-zA-Z_][a-zA-Z0-9_]*
%%
{IDENTIFICADOR}	{return CIDENTIFICADOR;}
\n	{}
.	{return ERROR;}
%%
int main(int ari,char **arc){
	int token;
	FILE *fileOut,*fileError;
	if(ari<3){
		printf("\nerror ejecuta como ./nameEjecutable fileOut fileError");
		puts("");return 0;
	}
	if((yyin=fopen(arc[1],"r"))==NULL)
		printf("\nerror el archivo %s no se pudo abrir",arc[1]);
	if((fileOut=fopen(arc[2],"w"))==NULL)
		printf("\nerror el archivo %s no se pudo abrir",arc[1]);
	if((fileError=fopen(arc[3],"w"))==NULL)
		printf("\nerror el archivo %s no se pudo abrir",arc[1]);
	token=yylex();
	while(token){
		switch(token){
			case CIDENTIFICADOR:
				imp_pal(fileOut,CIDENTIFICADOR);
			break;
			case ERROR:
				imp_pal(fileError,ERROR);
			break;
		}
		token=yylex();
	}
	fclose(yyin);
	fclose(fileOut);
	fclose(fileError);
	puts("");return 0;
}
void imp_pal(FILE *file,int type){
	if(type==ERROR)
		fprintf(file,"error en la linea %d por -> ",yylineno);
	fprintf(file,"%s\n",yytext);
}
