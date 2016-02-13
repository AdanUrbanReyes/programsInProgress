%option main
%{
int simbolos = 0;
int palabras = 0;
int lineas = 0;
%}

%%
[a-zA-Z]+ {palabras++;simbolos += strlen(yytext);
printf("palablas:%d simbolos:%d \n",palabras, simbolos);}
\n {simbolos++; lineas++; printf("simbolos:%d")
