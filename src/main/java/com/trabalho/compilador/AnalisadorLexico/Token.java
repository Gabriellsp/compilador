package com.trabalho.compilador.AnalisadorLexico;

public class Token {
    public final int tag; 
    public Token (int t){
        tag = t;
    }
    public String toString(){
        return ""+(char)tag;
    }
}
