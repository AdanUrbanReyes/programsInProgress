%{
int simbolos = 0;
int palabras = 0;
int lineas = 0;
%}

%%
[a-zA-Z]+ {palabras++;simbolos += strlen(yytext);}
\n            {simbolos++; lineas++; }
.              {simbolos++; }
%%
int main (int argc, char **argv)
{
               yylex();
               printf("lineas:%8d \npalabras:%8d \nsimbolos:%8d \n", lineas, palabras, simbolos);
               return (0);
}
