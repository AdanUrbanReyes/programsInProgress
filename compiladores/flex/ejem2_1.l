%{
int simbolos = 0;
int palabras = 0;
int lineas = 0;
%}

PALABRA [a-zA-Z]+
SALTO \n
%%
{PALABRA} {palabras++; simbolos += strlen(yytext); }
{SALTO} {simbolos++; lineas++; }
.		{simbolos++;}
%%
main(int argc, char **argv)
{
	if(argc > 1)
		yyin = fopen( argv[1], "r");
	else
		yyin = stdin;
		
	yylex();
	printf("lineas: %8d\nPalabras: %8d\nSimbolos: %8d\n", lineas, palabras, simbolos);
	fclose(yyin);
}
