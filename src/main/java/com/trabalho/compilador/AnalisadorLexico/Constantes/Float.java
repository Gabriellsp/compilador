package com.trabalho.compilador.AnalisadorLexico.Constantes;

import com.trabalho.compilador.AnalisadorLexico.Tag;
import com.trabalho.compilador.AnalisadorLexico.Token;

public class Float extends Token {
    public final float value;
    
    public Float(float value){
        super(Tag.CONST_FLOAT);
        this.value = value;
    }
    public String toString(){
        return "" + value;
    }
}
