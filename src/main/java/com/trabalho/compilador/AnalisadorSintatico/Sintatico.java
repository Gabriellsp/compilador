package com.trabalho.compilador.AnalisadorSintatico;

import com.trabalho.compilador.AnalisadorLexico.Lexer;
import static com.trabalho.compilador.AnalisadorLexico.Lexer.line;
import com.trabalho.compilador.AnalisadorLexico.Tag;
import com.trabalho.compilador.AnalisadorLexico.Token;

public class Sintatico {
    private final Lexer lex;
    private Token token;
    
    public Sintatico (Lexer lex) throws Exception {
        this.lex = lex;
        advance();
    }
    
    private void advance() throws Exception {
        this.token = lex.scan();
    }

    private void eat(int tag) throws Exception {
        if (this.token.tag == tag){
            advance();
        } else {
            System.out.println("---------------------------------ERROR----------------------------------");
            throw new Exception("Esperava-se a tag "+" "+tag+" '"+Tag.getTagName(tag)+"' na linha "+line+"!");
        }
    }
    
    public boolean start() throws Exception {
        switch(token.tag){
            case Tag.ROUTINE -> {
                eat(Tag.ROUTINE);
                body();
                return true;
            }
            default -> {
                System.out.println("---------------------------------ERROR----------------------------------");
                throw new Exception("Símbolo de atribuição inválido na linha "+line+"!");
            }
        }
    }

    private void body() throws Exception {
        switch(token.tag){
            case Tag.DECLARE -> {
                decl_list();
                begin();
            }
            default -> {
                System.out.println("---------------------------------ERROR----------------------------------");
                throw new Exception("Espera-se um token DECLARE na linha "+line+"!");
            }
        }
    }

    private void decl_list() throws Exception {
        switch(token.tag){
            case Tag.DECLARE -> {
                eat(Tag.DECLARE);
                decl();
                while (token.tag == Tag.SEMICOLON) {
                    eat(Tag.SEMICOLON);
                    if(token.tag == Tag.BEGIN) break;
                    decl();
                }
                break;
            }
            default -> {
                System.out.println("---------------------------------ERROR----------------------------------");
                throw new Exception("Espera-se um token DECLARE na linha "+line+"!");
            }
        }
    }

    private void decl() throws Exception {
        switch(token.tag){
            case Tag.INT -> {
                eat(Tag.INT);
                ident_list();
            }
            case Tag.FLOAT -> {
                eat(Tag.FLOAT);
                ident_list();
            }
            case Tag.CHAR -> {
                eat(Tag.CHAR);
                ident_list();
            }
            default -> {
                System.out.println("---------------------------------ERROR----------------------------------");
                throw new Exception("Espera-se um tipo (INT, FLOAT ou CHAR ) na linha "+line+"!");
            }
        }
    }

    private void ident_list() throws Exception {
        switch(token.tag){
            case Tag.ID -> {
                eat(Tag.ID);
                if(token.tag != Tag.COMMA && token.tag != Tag.SEMICOLON) {
                    System.out.println("---------------------------------ERROR----------------------------------");
                    throw new Exception("Espera-se um ',' ou ';' na linha "+line+"!");
                }
                while (token.tag == Tag.COMMA) {
                    eat(Tag.COMMA);
                    ident_list();
                }
                break;
            }
            default -> {
                System.out.println("---------------------------------ERROR----------------------------------");
                throw new Exception("Espera-se um ID na linha "+line+"!");
            }
        }
    }

    private void begin() throws Exception {
        switch(token.tag){
            case Tag.BEGIN -> {
                eat(Tag.BEGIN);
                stmt_list();
                eat(Tag.END);
            }
            default -> {
                System.out.println("---------------------------------ERROR----------------------------------");
                throw new Exception("Espera-se um begin' na linha "+line+"!");
            }
        }
    }

    private void stmt_list() throws Exception {
        stmt();
    }

    private void stmt() throws Exception {
        switch(token.tag){
            case Tag.ID:
                assign_stmt();
                break;
            case Tag.IF:
                if_stmt();
                break;
            case Tag.WHILE:
                while_stmt();
                break;
            case Tag.REPEAT:
                repeat_stmt();
                break;
            case Tag.READ:
                read_stmt();
                break;
            case Tag.WRITE:
                write_stmt();
                break;
            default:
                System.out.println("---------------------------------ERROR----------------------------------");
                throw new Exception("Espera-se um ID, IF, WHILE, REPEAT, READ ou WRITE na linha "+line+"!");
        }
        
    }

    private void assign_stmt() throws Exception {
        eat(Tag.ID);
        eat(Tag.ASSIGN_STATEMENT);
        simple_expr();
    }
    
    private void simple_expr() throws Exception {
        
    }

    private void if_stmt() throws Exception {
        
    }

    private void while_stmt() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void repeat_stmt() throws Exception {
        eat(Tag.REPEAT);
    }

    private void read_stmt() throws Exception {
        eat(Tag.READ);
        eat(Tag.OP);
        eat(Tag.ID);
        eat(Tag.CP); 
    }

    private void write_stmt() throws Exception {
        eat(Tag.WRITE);
        eat(Tag.OP);
        writable();
        eat(Tag.CP);
    }

    private void writable()throws Exception {
        // implementar a bagaceira
    }

}
