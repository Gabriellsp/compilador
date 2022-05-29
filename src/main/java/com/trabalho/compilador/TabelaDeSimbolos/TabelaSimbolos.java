package com.trabalho.compilador.TabelaDeSimbolos;

import com.trabalho.compilador.AnalisadorLexico.Word;
import java.util.HashMap;

public class TabelaSimbolos {
    static HashMap<String, Word> table; 
     
    public TabelaSimbolos(){
        table = new HashMap<String, Word>();
    }
    
    public void put(Word w){
        table.put(w.toString(), w);
    }

    public void imprimirTable() {
        for (var pair : table.entrySet()) {
            System.out.println("LEXEME: "+pair.getKey()+ " - TAG: "+pair.getValue().tag);
        }
    }

    public Object get(String token){
        Object found = table.get(token);

        if (found != null) 
            return found;

        return null;
    }
}

