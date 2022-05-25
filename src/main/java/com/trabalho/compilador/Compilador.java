package com.trabalho.compilador;
import com.trabalho.compilador.AnalisadorLexico.Lexer;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Compilador {

    public static void main(String[] args) {
        try {
            var lexico = new Lexer("file.txt");
            try {
                lexico.returnTokensFromFile();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } catch(FileNotFoundException e){
            System.out.println("Erro: "+e.getMessage());
        }
        
    }
}
