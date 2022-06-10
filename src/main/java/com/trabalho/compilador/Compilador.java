package com.trabalho.compilador;
import com.trabalho.compilador.AnalisadorLexico.Lexer;
import com.trabalho.compilador.AnalisadorSintatico.Sintatico;
import java.io.FileNotFoundException;

public class Compilador {

    public static void main(String[] args) {
        try {
            var lexico = new Lexer("codigo.txt");
            
           // var sintatico = new Sintatico(lexico);
            try {
                lexico.returnTokensFromFile();
//                System.out.println("oiiii");
//                System.out.println(lexico);
//                var sintatico = new Sintatico(lexico);
//                sintatico.program();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } catch(FileNotFoundException e){
            System.out.println("Erro: "+e.getMessage());
        }
        
    }
}
