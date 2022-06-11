package com.trabalho.compilador;
import com.trabalho.compilador.AnalisadorLexico.Lexer;
import com.trabalho.compilador.AnalisadorSintatico.Sintatico;
import java.io.FileNotFoundException;

public class Compilador {

    public static void main(String[] args) throws Exception {
        try {
            var lexico = new Lexer("codigo.txt");
            var sintatico = new Sintatico(lexico);
            if(sintatico.start()){
                System.out.println("Sucesso ao rodar o analisador sintático!");
            }
        } catch(FileNotFoundException e){
            System.out.println("Erro: "+e.getMessage());
        }
        
    }
}
