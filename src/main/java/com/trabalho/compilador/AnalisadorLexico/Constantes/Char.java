package com.trabalho.compilador.AnalisadorLexico.Constantes;

import com.trabalho.compilador.AnalisadorLexico.Tag;
import com.trabalho.compilador.AnalisadorLexico.Token;

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
