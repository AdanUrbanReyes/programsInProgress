%{
int simbolos = 0;
int palabras = 0;
int lineas = 0;
%}

PALABRA [a-zA-Z]+
SALTO \n
%%
{PALABRA} {return CPALABRA;}
{SALTO} {return CSALTO;}
.		{return COTRO;}

%%
main(int argc, char **argv)
{
	int elToken;
	
	if(argc > 1)
		yyin = fopen( argv[1], "r");
		yyout = fopen( argv[2], "w");
	else
		yyin = stdin;
		
	elToken = yylex();
	
	while(elToken){
		switch(elToken){
			case CPALABRA:
				palabras++; simbolos += yyleng;
				break;
			case CSALTO:
				simbolos++; lineas++;
				break;
			case COTRO
				simbolos++;
				break;
		}
		elToken = yylex();
	}
	printf("Lineas: %8d\nPalabras: %8d\nSimbolos: %8d\n", lineas, palabras, simbolos);
	fclose(yyin);
	fclose(yyout);
	return (0);
}
