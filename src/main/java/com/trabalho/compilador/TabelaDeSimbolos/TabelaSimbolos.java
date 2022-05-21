package com.trabalho.compilador.TabelaDeSimbolos;

import com.trabalho.compilador.AnalisadorLexico.Token;
import java.util.Hashtable;
import com.trabalho.compilador.TabelaDeSimbolos.Id;

public class TabelaSimbolos {
    static Hashtable table; 
//    protected TabelaSimbolos prev; 
     
    public TabelaSimbolos(){
        table = new Hashtable(); //cria a TS para o ambiente
//        prev = n; //associa o ambiente atual ao anterior
    }
    
    // descobrir o que é o Id
    public void put(Token token /*, Id i*/){
        table.put(token,'i'); // alterar para i variável
    }
    
    // Descobrir o que é o Id
    public Id get(Token token){
        Id found = (Id) table.get(token);

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

