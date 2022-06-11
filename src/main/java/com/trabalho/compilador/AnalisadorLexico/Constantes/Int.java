package com.trabalho.compilador.AnalisadorLexico.Constantes;

import com.trabalho.compilador.AnalisadorLexico.Tag;
import com.trabalho.compilador.AnalisadorLexico.Token;

public class Int extends Token {
    
    public final int value;
    
    public Int(int value){
        super(Tag.CONST_INT);
        this.value = value;
    }
    public String toString(){
        return "" + value;
    }
}
