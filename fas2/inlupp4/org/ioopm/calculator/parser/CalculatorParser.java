package org.ioopm.calculator.parser;
import org.ioopm.calculator.ast.*;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.io.IOException;

/**
 * @file CalculatorParser.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class CalculatorParser
 * @brief Class for CalculatorParser. Used to parse Strings into valid SymbolicExpressions
 */
public class CalculatorParser {
    private StreamTokenizer st;


    
    /**
     * @brief Parse a String into a valid SymbolicExpression
     * @param parseString String to be parsed
     * @return a new Symbolicexpression if the String containe1d valid syntax, else null
     */
    public SymbolicExpression parse(String parseString){
	st = new StreamTokenizer(new StringReader(parseString));
	st.ordinaryChar('-');
	st.ordinaryChar('/');
	st.ordinaryChar('<');
	st.ordinaryChar('>');
	st.eolIsSignificant(true);

	try{
	    SymbolicExpression expr = top_level();
	    return expr;
	} catch(IOException e) {
	    System.out.println(e);
	}
	return null;
    }

    private SymbolicExpression Unexpected() throws IOException{
	this.st.nextToken();
	String unexpected;
	switch(this.st.ttype){
	case StreamTokenizer.TT_WORD:
	    unexpected = this.st.sval;
	    break;
	case StreamTokenizer.TT_NUMBER:
	    unexpected = Double.toString(this.st.nval);
	    break;
	case StreamTokenizer.TT_EOF:
	    unexpected = "EOF";
	    break;
	case StreamTokenizer.TT_EOL:
	    unexpected = "EOL";
	    break;
	default:
	    unexpected = Character.toString(this.st.ttype);
	    break;
	}
	throw new SyntaxErrorException ("Unexpected: " + unexpected);
    }

    private SymbolicExpression top_level() throws IOException{
	SymbolicExpression state = statement();
	this.st.nextToken();
	if (this.st.ttype == this.st.TT_EOL || this.st.ttype == this.st.TT_EOF){
	    return state;
	} else {
	    this.st.pushBack();
	    return Unexpected();
	}
    }
    private SymbolicExpression statement() throws IOException{
	this.st.nextToken();
	if (this.st.ttype == this.st.TT_WORD){
	    String command = this.st.sval.toLowerCase();
	    if (command.equals("quit") || command.equals("vars") || command.equals("clear")){
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
	    else if (command.equals("clear")){
		return Clear.instance();
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

    private SymbolicExpression conditional() throws IOException{
	SymbolicExpression lScope;
	SymbolicExpression rScope;
    
	SymbolicExpression left = expression();

	String operator; 
    
	this.st.nextToken(); 
	switch(this.st.ttype){
	case '<':
	    if (this.st.nextToken() == '=') {
		operator = "<=";
	    } else {
		operator = "<";
		this.st.pushBack();
	    }
	    break;
	case '>':
	    if (this.st.nextToken() == '=') {
		operator = ">=";
	    } else {
		operator = ">";
		this.st.pushBack();
	    }
	    break;
	case '=':
	    if (this.st.nextToken() == '=') {
		operator = "==";
	    } else {
		throw new SyntaxErrorException("Expected ==");
	    }
	    break;
	default:
	    throw new SyntaxErrorException("Incorrect operator");
	} 
	    
	SymbolicExpression right = expression();
	    
	if (this.st.ttype == '{'){
	    lScope = primary();
	} else {
	    throw new SyntaxErrorException("Expected scope"); 
	}

	this.st.nextToken();
	if(this.st.ttype == this.st.TT_WORD && this.st.sval.toLowerCase().equals("else")){
	    this.st.nextToken();
	    if (this.st.ttype == '{'){
		this.st.pushBack();
		rScope = primary();
	    } else {
		throw new SyntaxErrorException("Expected scope"); 
	    }
	} else {
	    throw new SyntaxErrorException("Expected else"); 
	}
	    return new Conditional(left, operator, right, (Scope)lScope, (Scope)rScope);
    }
    
    private SymbolicExpression expression() throws IOException{ 
	this.st.nextToken();
	    
	if(this.st.ttype == this.st.TT_WORD && this.st.sval.toLowerCase().equals("if")){
	    return conditional();
	}
	
	this.st.pushBack();
	
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
	} else if (this.st.ttype == '{'){
	    SymbolicExpression ass = new Scope(assignment());
	    if (this.st.nextToken() != '}'){
		throw new SyntaxErrorException ("Expected '}'");
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
	    } else if (unary.equals("vars") || unary.equals("quit") || unary.equals("clear")) {
		throw new SyntaxErrorException("Invalid identifier");
	    } else {
		this.st.pushBack();
		return identifier();
	    }
	} else if ((this.st.ttype >= 'a' && this.st.ttype <= 'z') || (this.st.ttype >= 'A' && this.st.ttype <= 'Z')){
	    this.st.pushBack();
	    return identifier();
	} else {
	    this.st.pushBack();
	    return Unexpected();
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
	String id;
	if (this.st.ttype == this.st.TT_NUMBER){
	    throw new SyntaxErrorException("Invalid identifier");
	}
	if (this.st.ttype != this.st.TT_WORD){
	    id = Character.toString(this.st.ttype);
	} else {
	    id = this.st.sval;
	}
	String id_lower = id.toLowerCase();
	if (id_lower.equals("sin") || id_lower.equals("cos") || id_lower.equals("log") || id_lower.equals("exp") || id_lower.equals("vars") || id_lower.equals("quit") || id_lower.equals("clear") || id_lower.equals("if") || id_lower.equals("else")){
	    throw new SyntaxErrorException("Invalid identifier");
	}
	if (Constants.namedConstants.containsKey(id)){
	    return new NamedConstant(id);
	} else {
	    return new Variable(id);
	}
    }
}
