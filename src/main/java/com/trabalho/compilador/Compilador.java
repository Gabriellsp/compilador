package com.trabalho.compilador;
import com.trabalho.compilador.AnalisadorLexico.Lexer;

public class Compilador {

    public static void main(String[] args) {
        try {
            var lexico = new Lexer("file.txt");
        } catch(Exception e){
            System.out.println("Erro: "+e.getMessage());
        }
        
    }
}
