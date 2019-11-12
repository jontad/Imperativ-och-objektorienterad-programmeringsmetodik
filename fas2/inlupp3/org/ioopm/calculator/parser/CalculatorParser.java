package org.ioopm.calculator.parser;
import org.ioopm.calculator.ast.*;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.io.IOException;

public class CalculatorParser {
    private StreamTokenizer st;

    public SymbolicExpression parse(String parseString){
	st = new StreamTokenizer(new StringReader(parseString));
	st.ordinaryChar('-');
	st.ordinaryChar('/');
	st.eolIsSignificant(true);

	try{
	    SymbolicExpression expr = top_level();
	    return expr;
	} catch(IOException e) {
	    System.out.println(e);
	}
	return null;
    }

    private SymbolicExpression top_level() throws IOException{ //TODO Add throws to all functions
	SymbolicExpression state = statement();
	this.st.nextToken();
	if (this.st.ttype == this.st.TT_EOL || this.st.ttype == this.st.TT_EOF){
	    return state;
	} else {
	    System.out.println(this.st.ttype);
	    throw new SyntaxErrorException ("Expected EOL or EOF");
	}
    }
    private SymbolicExpression statement() throws IOException{
	this.st.nextToken();
	if (this.st.ttype == this.st.TT_WORD){
	    String command = this.st.sval.toLowerCase();
	    if (command.equals("quit") || command.equals("vars")){
		this.st.pushBack();
		return command();
	    }
	}
	this.st.pushBack();
	return assignment();
    }
    private SymbolicExpression command() throws IOException{
	this.st.nextToken();
	if (this.st.ttype == this.st.TT_WORD){
	    String command = this.st.sval.toLowerCase();
	    if (command.equals("quit")){
		return Quit.instance();
	    }
	    else if (command.equals("vars")){
		return Vars.instance();
	    }
	} 
	throw new RuntimeException("Called command() without command");
	
    }
    private SymbolicExpression assignment() throws IOException{
	SymbolicExpression l = expression();
        while (this.st.nextToken() == '='){
	    SymbolicExpression r = identifier();
	    l = new Assignment(l, r);
	}
	this.st.pushBack();
	return l;
    }
    
    private SymbolicExpression expression() throws IOException{
	SymbolicExpression sum = term();
	this.st.nextToken();
	while (this.st.ttype == '+' || this.st.ttype == '-') {
	    if(this.st.ttype == '+'){
                sum = new Addition(sum, term());
            } else {
                sum = new Subtraction(sum, term());
            }
	    this.st.nextToken();
	}
	this.st.pushBack();
	return sum;
    }
    private SymbolicExpression term() throws IOException{
	SymbolicExpression product = factor();
	this.st.nextToken();
	while (this.st.ttype == '*' || this.st.ttype == '/') {
	    if(this.st.ttype == '*'){
                product = new Multiplication(product, term());
            } else {
                product = new Division(product, term());
            }
	}
	this.st.pushBack();
	return product;
    }
    private SymbolicExpression factor() throws IOException{
	return primary();
    }
    private SymbolicExpression primary() throws IOException{
	this.st.nextToken();
	if (this.st.ttype == '('){
	    SymbolicExpression ass = assignment();
	    if (this.st.nextToken() != ')'){
		throw new SyntaxErrorException ("Expected ')'");
	    }
	    return ass;
	} else if (this.st.ttype == '-'){
	    this.st.pushBack();
	    return unary();
	} else if (this.st.ttype == this.st.TT_NUMBER) {
	    this.st.pushBack();
	    return number();
	} else if (this.st.ttype == this.st.TT_WORD){
	    String identifier = this.st.sval;
	    String unary = identifier.toLowerCase();
	    if (unary.equals("exp") || unary.equals("log") || unary.equals("sin") || unary.equals("cos")) {
		this.st.pushBack();
		return unary();
	    } else if (unary.equals("vars") || unary.equals("quit")) {
		throw new SyntaxErrorException("Invalid identifier");
	    } else {
		this.st.pushBack();
		return identifier();
	    }
	} else if ((this.st.ttype >= 'a' && this.st.ttype <= 'z') || (this.st.ttype >= 'A' && this.st.ttype <= 'Z')){
	    this.st.pushBack();
	    return identifier();
	} else {
	    throw new SyntaxErrorException("Invalid syntax");
	}
    }
    private SymbolicExpression unary() throws IOException{
	this.st.nextToken();
	String unary;
	if (this.st.ttype != this.st.TT_WORD){
	    unary = Character.toString(this.st.ttype);
	} else {
	    unary = this.st.sval.toLowerCase();
	}
	SymbolicExpression prim = primary();
	switch(unary){
	case "-":
	    return new Negation(prim);
	case "exp":
	    return new Exp(prim);
	case "log":
	    return new Log(prim);
	case "sin":
	    return new Sin(prim);
	case "cos":
	    return new Cos(prim);
	default:
	    throw new RuntimeException("Called unary() with invalid unary");
	}
	
    }
    private SymbolicExpression number() throws IOException{
	this.st.nextToken();
	if (this.st.ttype == this.st.TT_NUMBER){
	    return new Constant(this.st.nval);
	} else {
	    throw new RuntimeException("Called number() without number");
	}
    }
    private SymbolicExpression identifier() throws IOException{
        this.st.nextToken();
	if (this.st.ttype == this.st.TT_WORD){
	    String identifier = this.st.sval.toLowerCase();
	    if (identifier.equals("sin") || identifier.equals("cos") ||identifier.equals("log") ||identifier.equals("exp") ||identifier.equals("vars") ||identifier.equals("quit")){
		throw new SyntaxErrorException("Invalid identifier");
	    }
	    return new Variable(this.st.sval);
	} else {
	    return new Variable(Character.toString(this.st.ttype));
	}
    }
}
