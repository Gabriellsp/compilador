package com.trabalho.compilador.AnalisadorLexico;

public class Word extends Token{
    private String lexeme = "";
    public static final Word eq = new Word ("=", Tag.EQ);
    public static final Word ne = new Word ("<>", Tag.NE);
    public static final Word lt = new Word ("<", Tag.LT);
    public static final Word le = new Word ("<=", Tag.LE);
    public static final Word gt = new Word (">", Tag.GT);
    public static final Word ge = new Word (">=", Tag.GE);
    
    public static final Word sum = new Word ("+", Tag.SUM);
    public static final Word dif = new Word ("-", Tag.DIF);
    public static final Word mult = new Word ("*", Tag.MULT);
    public static final Word div = new Word ("/", Tag.DIV);
    public static final Word and = new Word ("and", Tag.AND);
    public static final Word or = new Word ("or", Tag.OR);
    
    public static final Word OP = new Word ("(", Tag.OP);
    public static final Word CP = new Word (")", Tag.CP);
    public static final Word semi = new Word (";", Tag.SEMICOLON);
    public static final Word OB = new Word ("{", Tag.OB);
    public static final Word CB = new Word ("}", Tag.CB);
    public static final Word assign = new Word (":=", Tag.ASSIGN_STATEMENT);
    public static final Word OBR = new Word ("[", Tag.OBR);
    public static final Word CBR = new Word ("]", Tag.CBR);
    public static final Word comma = new Word (",", Tag.COMMA);
    
    public Word (String s, int tag){
        super (tag);
        lexeme = s;
    }
    
    public String toString(){
        return "" + lexeme;
    }
}
