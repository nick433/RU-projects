/*
 * tokenizer.c
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
/*
 * Tokenizer type.  You need to fill in the type as part of your implementation.
 */

int strIndex = 0; /* Keeps track of the current character position of the string argument */

int numTokens = 0; /* the number of tokens, to be incremented */

int tokenIndex = -1; /* where we are in token array. getNextTk increments by 1 when called */


int isHexDigit(char c){/*returns 1 (true) if the input char is a proper hexadecimal*/ 

	if(isdigit(c) || 
	c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || 
	c == 'A' || c == 'B' || c == 'C' || c == 'D' || c == 'E' || c == 'F') return 1;
	return 0; 

}
int isOctal(char c){
		
	if( c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || 
	c == '6' || c == '7') return 1;
	return 0; 
}



/*
 * TokenizerT_ represents a token with fields type and text.
 * Each is created dynamically and passed its fields by TKCreate
 */

struct TokenizerT_ {
	
	char** text; //swtich back
	char** type;

};
typedef struct TokenizerT_ TokenizerT;



/* makeToken takes the full string, and a reference to the Tokenizer struct with its index field variable.
 * It goes through, starting at the index, and identifies one token. 
 * Keeping track of that token's length, text, and type, it updates the fields of the struct concerned with those things.
 * The next time the function is called, it starts at 1 more than the end of the previous token.
 */
void makeToken(char* str, TokenizerT* token){
	/*Assumes the token is properly formatted. A MAL token like 0xQ or 2.b can be properly formatted but '08' would have already been filtered out*/
	/*this function consists of a disgusting amount of conditionals*/
	char* holder;	
	int j = 0; /*j is the index of holder which will be copied later into token->text*/
	holder = malloc(sizeof(char)*(strlen(str)+1)); /*added that +1 */
	int i = strIndex;
	int length = strlen(str)+1;
	if(str[i] == ' '){
		i++;
		strIndex = i;
		return;
	}
	if(isalpha(str[i])){
		while(isalpha(str[i])){
			holder[j] = str[i];
			i++;
			j++;
		}
		int r,flag=0,m;
		char s[18][18]={"sizeof","if","break","else","for","while","int","char","goto","continue","return","double","float","long","const","struct","unsigned","typedef"};
		for(r=0;r<18;r++){
			m = strcmp(holder, s[r]);
			if(m == 0)
				flag = 1;
		}
		if(flag==1){
			token->type[numTokens] = "keyword";
			token->text[numTokens] = holder;
			strIndex = i;
			numTokens++;
			return;
		}	
	 
		token->type[numTokens] = "word";
		token->text[numTokens] = holder;
		strIndex = i;
		numTokens++;
		return;
	}


	/*possible options at this point: zero, integer, float, hex, oct */

	if(str[i] == '0'){
		/*possibly mal, zero, octal, hex, float*/
		holder[j] = str[i];
		i++;
		j++;
		if(!(isdigit(str[i]) || (str[i]=='.') || (str[i]=='x') || (str[i]=='X'))){ /*the character following '0' is none of these*/
			token->type[numTokens] = "zero";
			token->text[numTokens] = holder;
			strIndex = i;
			numTokens++;
			return;

		}

		if(length == 1){
			strIndex = i;
			token->type[numTokens] = "zero";
			token->text[numTokens] = holder;
			numTokens++;
			return;
		}

		if(str[i] == 'x' || str[i] == 'X'){
			holder[j] = str[i];
			i++;
			j++;
			if(length == 2){
				token->type[numTokens] = "malformed";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
			}

			if(isHexDigit(str[i])){
				holder[j] = str[i];
				i++;
				j++;
				while(isHexDigit(str[i])){
					holder[j] = str[i];
					i++;
					j++;
				}
				token->type[numTokens] = "hex constant";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
			}
			
			
			else{	
				holder[j] = str[i];
				i++;
				j++;
				token->type[numTokens] = "malformed";
				token->text[numTokens] = holder;
				strIndex = i;	
				numTokens++;
				return;
			}


		}
		

		else if(isOctal(str[i])){
			holder[j] = str[i];
			i++;
			j++;
			while(isOctal(str[i])){
				holder[j] = str[i];
				i++;
				j++;
			}
			token->type[numTokens] = "oct constant";
			token->text[numTokens] = holder;
			strIndex = i;	
			numTokens++;
			return;
			
		}
		

		else if(str[i] == '.'){  /*this code will be recycled for the # or exponent cases*/
			holder[j] = str[i];
			i++;
			j++;
			/*options at this point: mal, float, mal, mal, float*/
			if(!(isdigit(str[i]))){
				holder[j] = str[i];
				i++;
				j++;
				token->type[numTokens] = "malformed input";
				token->text[numTokens] = holder;
				strIndex = i;	
				numTokens++;
				return;
			}
			/*at this point we know we have 0. followed by a digit.*/
			while(isdigit(str[i])){
				holder[j] = str[i];
				i++;
				j++;
			}
			
			if (!(str[i] == 'e' || str[i] == 'E')){
				token->type[numTokens] = "float";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
			}

			if(str[i] == 'e' || str[i] == 'E'){
				holder[j] = str[i];
				i++;
				j++;
					
				if(!(isdigit(str[i]) || str[i] == '+' || str[i] == '-')   ){
					holder[j] = str[i];
					i++;
					j++;
					token->type[numTokens] = "malformed input";
					token->text[numTokens] = holder;
					strIndex = i;
					numTokens++;
					return;
				}
				if(str[i] == '+' || str[i] == '-'){
					holder[j] = str[i];
					i++;
					j++;
				}
				if(isdigit(str[i])){
					while(isdigit(str[i])){
						holder[j] = str[i];
						i++;
						j++;
					}
				token->type[numTokens] = "float";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
				}
				/*must be mal at this point*/
				i++; /*make sure we leave the token that cause mal*/
				token->type[numTokens] = "malformed input";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
				
			}	
		
		
		} /*this ends the '.' case*/
			
	}
	
	if(isdigit(str[i])){
		if(length == 1){
			holder[j] = str[i];
			i++;
			token->type[numTokens] = "malformed input";
			token->text[numTokens] = holder;
			strIndex = i;
			numTokens++;
			return;
			
		}
		while(isdigit(str[i])){
			holder[j] = str[i];
			i++;
			j++;
			if(i==length){
				token->type[numTokens] = "integer";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
			return;
			}
		}
		
		/*this is where we reuse the '.' condition from above*/
		if(str[i] == '.'){
			holder[j] = str[i];
			i++;
			j++;
			/*options at this point: mal, float, mal, mal, float*/
			if(!(isdigit(str[i]))){
				holder[j] = str[i];
				i++;
				j++;
				token->type[numTokens] = "malformed input";
				token->text[numTokens] = holder;
				strIndex = i;	
				numTokens++;
				return;
			}
			/*at this point we know we have . followed by a digit.*/
			while(isdigit(str[i])){
				holder[j] = str[i];
				i++;
				j++;
			}
			
			if (!(str[i] == 'e' || str[i] == 'E')){
				token->type[numTokens] = "float";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
			}

			if(str[i] == 'e' || str[i] == 'E'){
				holder[j] = str[i];
				i++;
				j++;
					
				if(!(isdigit(str[i]) || str[i] == '+' || str[i] == '-')   ){
					holder[j] = str[i];
					i++;
					j++;
					token->type[numTokens] = "malformed input";
					token->text[numTokens] = holder;
					strIndex = i;
					numTokens++;
					return;
				}
				if(str[i] == '+' || str[i] == '-'){
					holder[j] = str[i];
					i++;
					j++;
				}
				if(isdigit(str[i])){
					while(isdigit(str[i])){
						holder[j] = str[i];
						i++;
						j++;
					}
				}
				token->type[numTokens] = "float";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
				
				/*must be mal at this point*/
				i++; /*make sure we leave the token that cause mal*/
				token->type[numTokens] = "malformed input";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
				
			}
			
			
			
		


		
		} /*this ends the '.' case*/
		
		
			token->type[numTokens] = "integer";
			token->text[numTokens] = holder;
			strIndex = i;
			numTokens++;
			return;
	

	} /*end case of non-0 digit*/
	if(str[i] == '/'){ /*checks for * comments */
		i++;
		if(str[i]== '*'){
			i++;
			while(i<strlen(str)){
				if(str[i] == '*'){
					if(str[i+1] == '/'){
						i++; i++;
						strIndex = i;
						return;	
					}
				}
				holder[j]=str[i];
				i++;
				j++;
			}	
		return;
		}
	}
	if(str[i] == '\"' || str[i] == '\''){
		i++;
		int f = 0;
		int ihold = i;
		while(str[i] != str[ihold-1]){
			holder[j] = str[i];
			i++;
			j++;
			if(i==strlen(str)-1){ /*this checks the condition that there is one ' or " in the whole string */
				i = ihold;
				f = 1;
				break;
			}
				
		}
		if(f == 1){ /*the original i is still 1 index past the quote */
			holder[j] = str[i-1];
			if(str[i-1] == '\''){
				token->type[numTokens] = "single quote";
				token->text[numTokens] = "\'";
				strIndex = i;
				numTokens++;
				return;
			}
			if(str[i-1] == '\"'){
				token->type[numTokens] = "double quote";
				token->text[numTokens] = "\"";
				strIndex = i;
				numTokens++;
				return;
			}
			return;
		}
		i++;
		j++;
		token->type[numTokens] = "string token";
		token->text[numTokens] = holder;
		strIndex = i;
		numTokens++;
		return;
	}
	

	if(str[i] == '['){
		holder[j] = str[i];
		i++;
		j++;
		token->type[numTokens] = "left brace";
		token->text[numTokens] = holder;
		strIndex = i;
		numTokens++;
		return;
	}
	if(str[i] == '+'){
		holder[j] = str[i];
		i++;
		j++;
			if(str[i] == '='){
				holder[j] = str[i];
				i++;
				j++;
				token->type[numTokens] = "plus equals sign";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
			}
		token->type[numTokens] = "plus sign";
		token->text[numTokens] = "+";
		strIndex = i;
		numTokens++;
		return;
	}
	if(str[i] == ']'){
		holder[j] = str[i];
		i++;
		j++;
		token->type[numTokens] = "right brace";
		token->text[numTokens] = holder;
		strIndex = i;
		numTokens++;
		return;
	}
	if(str[i] == ')'){
		holder[j] = str[i];
		i++;
		j++;
		token->type[numTokens] = "right parenthesis";
		token->text[numTokens] = holder;
		strIndex = i;
		numTokens++;
		return;
	}
	if(str[i] == '('){
		holder[j] = str[i];
		i++;
		j++;
		token->type[numTokens] = "left parenthesis";
		token->text[numTokens] = holder;
		strIndex = i;
		numTokens++;
		return;
	}
	if(str[i] == '.'){
		holder[j] = str[i];
		i++;
		j++;
		token->type[numTokens] = "decimal point";
		token->text[numTokens] = holder;

		strIndex = i;
		numTokens++;
		return;
	}
	if(str[i] == '*'){
		holder[j] = str[i];
		i++;
		j++;
		
		if(str[i]!='\0'){
			if(str[i] == '='){
				token->type[numTokens] = "multiply equals sign";
				token->text[numTokens] = "*=";
				strIndex = i;
				numTokens++;
				return;
			}
		}
		
		token->type[numTokens] = "multiply";
		token->text[numTokens] = holder;
		strIndex = i;
		numTokens++;
		return;
	}
	if(str[i] == '/'){
		holder[j] = str[i];
		i++;
		j++;
		if(str[i] == '='){
			holder[j] = str[i];
			i++;
			j++;
			token->type[numTokens] = "divide equals sign";
			token->text[numTokens] = holder;
			strIndex = i;
			numTokens++;
			return;
		}
		token->type[numTokens] = "division sign";
		token->text[numTokens] = "/";
		strIndex = i;
		numTokens++;
		return;
	}
	if(str[i] == '%'){
		holder[j] = str[i];
		i++;
		j++;
			if(str[i] == '='){
				holder[j] = str[i];
				i++;
				j++;
				token->type[numTokens] = "modulus equals sign";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
			}
		token->type[numTokens] = "modulus sign";
		token->text[numTokens] = "%";
		strIndex = i;
		numTokens++;
		return;
	}
	if(str[i] == '?'){
		holder[j] = str[i];
		i++;
		j++;
		token->type[numTokens] = "ternary operator";
		token->text[numTokens] = holder;
		strIndex = i;
		numTokens++;
		return;
	}
	
	
	if(str[i] == '&'){
		holder[j] = str[i];
		i++;
		j++;
			if(str[i] == '='){
				holder[j] = str[i];
				i++;
				j++;
				token->type[numTokens] = "AND equals sign";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
			}	
			if(str[i] == '&'){
				holder[j] = str[i];
				i++;
				j++;
				token->type[numTokens] = "logical AND sign";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
			}
		token->type[numTokens] = "address/bitwise AND sign";
		token->text[numTokens] = "&";
		strIndex = i;
		numTokens++;
		return;
	}


	if(str[i] == '~'){
		holder[j] = str[i];
		i++;
		j++;
		token->type[numTokens] = "one's complement sign";
		token->text[numTokens] = holder;
		strIndex = i;
		numTokens++;
		return;
	}
	
	if(str[i] == '-'){
		holder[j] = str[i];
		i++;
		j++;
				
			if(str[i] == '>'){
				holder[j] = str[i];
				i++;
				j++;
				token->type[numTokens] = "struct pointer";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
			}
			if(str[i] == '='){
				holder[j] = str[i];
				i++;
				j++;
				token->type[numTokens] = "minus equals sign";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
			}
		token->type[numTokens] = "substraction sign";
		token->text[numTokens] = "-";
		strIndex = i;
		numTokens++;
		return;
	}
	
	if(str[i] == ','){
		holder[j] = str[i];
		i++;
		j++;
		token->type[numTokens] = "discard left expression sign";
		token->text[numTokens] = holder;
		strIndex = i;
		numTokens++;
		return;
	}
	if(str[i] == '^'){
		holder[j] = str[i];
		i++;
		j++;
		
			if(str[i] == '='){
				holder[j] = str[i];
				i++;
				j++;
				token->type[numTokens] = "bitwise exclusive OR equals sign";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
			}

		token->type[numTokens] = "bitwise exclusive OR sign";
		token->text[numTokens] = holder;
		strIndex = i;
		numTokens++;
		return;
	}
	
	if(str[i] == '|'){
		holder[j] = str[i];
		i++;
		j++;
			if(str[i] == '|'){
				holder[j] = str[i];
				i++;
				j++;
				token->type[numTokens] = "logical OR sign";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
			}
		token->type[numTokens] = "bitwise OR sign";
		token->text[numTokens] = "|";
		strIndex = i;
		numTokens++;
		return;
	}
	
	if(str[i] == '<'){
		holder[j] = str[i];
		i++;
		j++;
			if(str[i] == '='){
				holder[j] = str[i];
				i++;
				j++;
				token->type[numTokens] = "less than or equal to";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
			}
		token->type[numTokens] = "less than sign";
		token->text[numTokens] = "<";
		strIndex = i;
		numTokens++;
		return;
	}
	if(str[i] == '>'){
		holder[j] = str[i];
		i++;
		j++;
			if(str[i] == '='){
				holder[j] = str[i];
				i++;
				j++;
				token->type[numTokens] = "greater than or equal to";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
			}
		token->type[numTokens] = "greater than sign";
		token->text[numTokens] = ">";
		strIndex = i;
		numTokens++;
		return;
	}	
	if(str[i] == '='){
		holder[j] = str[i];
		i++;
		j++;
			if(str[i] == '='){
				holder[j] = str[i];
				i++;
				j++;
				token->type[numTokens] = "equals to";
				token->text[numTokens] = holder;
				strIndex = i;
				numTokens++;
				return;
			}
		token->type[numTokens] = "assignment sign";
		token->text[numTokens] = "=";
		strIndex = i;
		numTokens++;
		return;
	}
	
	

	/*this line of code should never execute*/
	strIndex = i+1;
	return;
}



/*
 * TKCreate creates a new TokenizerT object for a given token stream
 * (given as a string).
 * 
 * TKCreate should copy the arguments so that it is not dependent on
 * them staying immutable after returning.  (In the future, this may change
 * to increase efficiency.)
 *
 * If the function succeeds, it returns a non-NULL TokenizerT.
 * Else it returns NULL.
 *
 * You need to fill in this function as part of your implementation.
 */

TokenizerT *TKCreate(char* ts) {
	
	/*copy string to new ptr*/
	char* str;
	str = malloc(sizeof(char)*strlen(ts));
	strcpy(str, ts);

	TokenizerT* token = (TokenizerT*)malloc(sizeof(TokenizerT));
	token->type = malloc(sizeof(char*));
	token->text = malloc(sizeof(char*));	

	while(strIndex < strlen(str)+1){
		
		makeToken(str, token);
		token->type = realloc(token->type,sizeof(char*)*(numTokens+1));
		token->text = realloc(token->text,sizeof(char*)*(numTokens+1));	
	}
	return token;


}




/*
 * TKDestroy destroys a TokenizerT object.  It should free all dynamically
 * allocated memory that is part of the object being destroyed.
 *
 * You need to fill in this function as part of your implementation.
 */

void TKDestroy( TokenizerT * tk ) {

	free(tk);

}

/*
 * TKGetNextToken returns the next token from the token stream as a
 * character string.  Space for the returned token should be dynamically
 * allocated.  The caller is responsible for freeing the space once it is
 * no longer needed.
 *
 * If the function succeeds, it returns a C string (delimited by '\0')
 * containing the token.  Else it returns 0.
 *
 * You need to fill in this function as part of your implementation.
 */

char *TKGetNextToken( TokenizerT * tk ) {
	tokenIndex++; /*starting index is -1*/
	char *result = malloc(strlen(tk->text[tokenIndex])+strlen(tk->type[tokenIndex])+4);//+4 for the zero-terminator, 2 quotes, and space
    
        strcpy(result, tk->type[tokenIndex]);
        strcat(result, " ");
	strcat(result, "\"");
	strcat(result, tk->text[tokenIndex]);
	strcat(result, "\"");
        return result;
}

/*
 * main will have a string argument (in argv[1]).
 * The string argument contains the tokens.
 * Print out the tokens in the second string in left-to-right order.
 * Each token should be printed on a separate line.
 */

int main(int argc, char **argv) {
	if(argc < 2){
		printf("No arguments!\n");
		return 1;
	}
	if(argc > 2){
		printf("Too many arguments!\n");
		return 1;
	}
	
	TokenizerT* token = TKCreate(argv[1]);
	int e;
	char* hold;
	for(e=0;e<numTokens;e++){ /*this uses TKGet to cycle through the tokens and get the string we want to print out */
		hold = TKGetNextToken(token);
		printf("%s\n",hold);
	}
	free(hold);
	TKDestroy(token);
	return 0;



}

