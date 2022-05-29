package com.trabalho.compilador.AnalisadorLexico;

public class Char extends Token {
    
    public final char value;
    
    public Char(char value){
        super(Tag.CHAR);
        this.value = value;
    }
    public String toString(){
        return "" + value;
    }
}
