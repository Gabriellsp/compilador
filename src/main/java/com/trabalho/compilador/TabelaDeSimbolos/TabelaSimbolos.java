package com.trabalho.compilador.TabelaDeSimbolos;

import com.trabalho.compilador.AnalisadorLexico.Token;
import com.trabalho.compilador.AnalisadorLexico.Word;
import java.util.HashMap;

public class TabelaSimbolos {
    static HashMap table; 
//    protected TabelaSimbolos prev; 
     
    public TabelaSimbolos(){
        table = new HashMap(); //cria a TS para o ambiente
//        prev = n; //associa o ambiente atual ao anterior
    }
    
    // descobrir o que é o Id
    public void put(Word w){
        table.put(w.toString(), w);
    }
    
    // Descobrir o que é o Id
    public Object get(Token token){
        Object found = table.get(token);

        if (found != null) 
            return found;
        return null;

//        for (TabelaSimbolos e = this; e!=null; e = e.prev){
//            Id found = (Id) e.table.get(w);
//            if (found != null) //se Token existir em uma das TS
//            return found;
//        }
//        return null; //caso Token não exista em uma das TS
    }
}

