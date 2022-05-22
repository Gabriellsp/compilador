package com.trabalho.compilador.AnalisadorLexico;

import com.trabalho.compilador.TabelaDeSimbolos.TabelaSimbolos;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Lexer {
    public static int line = 1;
    private final FileReader file;
    private char ch = ' '; 
    private TabelaSimbolos ts;
    // instanciar TS
    // private Hashtable words = new Hashtable();
    
    public Lexer(String filename) throws FileNotFoundException {
        try {
            file = new FileReader (filename);
        } catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado");
            throw e;
        }
        ts = new TabelaSimbolos();

        ts.put(new Word("routine", Tag.ROUTINE));
        ts.put(new Word("begin", Tag.BEGIN));
        ts.put(new Word("end", Tag.END));
        ts.put(new Word("declare", Tag.DECLARE));
        ts.put(new Word("int", Tag.INT));
        ts.put(new Word("float", Tag.FLOAT));
        ts.put(new Word("char", Tag.CHAR));
        ts.put(new Word("if", Tag.IF));
        ts.put(new Word("then", Tag.THEN));
        ts.put(new Word("else", Tag.ELSE));
        ts.put(new Word("repeat", Tag.REPEAT));
        ts.put(new Word("until", Tag.UNTIL));
        ts.put(new Word("while", Tag.WHILE));
        ts.put(new Word("do", Tag.DO));
        ts.put(new Word("read", Tag.READ));
        ts.put(new Word("write", Tag.WRITE));
        ts.put(new Word("not", Tag.NOT));
        ts.put(new Word("or", Tag.OR));
        ts.put(new Word("and", Tag.AND));
    }
    
    private void reserve(Word w){
        // adicionar na TS
        // words.put(w.getLexeme(), w); 
    }
    
    private void readch() throws IOException{
        ch = (char) file.read();
    }
    
    private boolean readch(char c) throws IOException{
        readch();
        if (ch != c) return false;
        ch = ' ';
        return true;
    }
    
    public Token scan() throws IOException{
        for (;; readch()) {
            if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b') continue;
            else if (ch == '\n') line++;
            else break;
        }
        switch(ch){
            case '>' -> {
                if (readch('=')) {
                    return Word.ge;
                }
                else {
                    return Word.gt;
                }
            }
            case '<' -> {
                readch();
                return switch (ch) {
                    case '=' -> Word.le;
                    case '>' -> Word.ne;
                    default -> Word.lt;
                };
            }
            case '=' -> {
                return Word.eq;
            }
            case '+' -> {
                return Word.sum;
            }
            case '-' -> {
                return Word.dif;
            }
            case '*' -> {
                return Word.mult;
            }
            case '/' -> {
                return Word.div;
            }
            case ':' -> {
                if (readch('=')) {
                    return Word.assign;
                }
                else {
                    return new Token(':');
                }
            }
            case '(' -> {
                return Word.OP;
            }
            case ')' -> {
                return Word.CP;
            }
            case ';' -> {
                return Word.semi;
            }
            case '{' -> {
                return Word.OB;
            }
            case '}' -> {
                return Word.CB;
            }
            case '[' -> {
                return Word.OBR;
            }
            case ']' -> {
                return Word.CBR;
            }
        }
        
        //Números
        if (Character.isDigit(ch)){
            int value=0;
            do{
                value = 10*value + Character.digit(ch,10);
                readch();
            }while(Character.isDigit(ch));

            return new Num(value);
        }
        
        //Identificadores
        if (Character.isLetter(ch)){
            var sb = new StringBuffer();
            do{
                sb.append(ch);
                readch();
            }while(Character.isLetterOrDigit(ch));
            
            String s = sb.toString();
//            Word w = (Word)words.get(s);
//            if (w != null) return w; //palavra já existe na HashTable
//            w = new Word (s, Tag.ID);
//            words.put(s, w);
//            return w;
        }
        
        //Caracteres não especificados
        Token t = new Token(ch);
        ch = ' ';
        return t;  
    }
}
