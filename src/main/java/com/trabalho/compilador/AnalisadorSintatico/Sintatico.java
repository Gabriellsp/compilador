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
        if(token.tag == Tag.BLOC_COMMENT) advance();
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
                var x = token.tag;
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
        do {
            eat(Tag.SEMICOLON);
            if(token.tag == Tag.END) break;
            if(token.tag == Tag.ELSE) break;
            if( token.tag == Tag.UNTIL) break;
            stmt();
        }while (token.tag == Tag.SEMICOLON);
        
        
        if(token.tag != Tag.END) {              
            if (this.token.tag == Tag.SEMICOLON){
                advance();
            } else {
                if (this.token.tag == Tag.ELSE || this.token.tag == Tag.UNTIL){
//                    System.out.println("Entrei no if" +  Tag.getTagName(token.tag)+ line);
                }
                
                else {
                    int myLine = line -1;                    
                    if(token.tag < 1000)
                        throw new Exception("Esperava-se a tag "+" "+Tag.SEMICOLON+" '"+Tag.getTagName(Tag.SEMICOLON)+"' na linha "+ myLine +"!");
                }                
            }
        }
        
        
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
        term();
        simple_expr_linha();
    }
    
    private void simple_expr_linha() throws Exception {
        switch(token.tag){
            case Tag.SUM:
            case Tag.DIF:
            case Tag.OR:
                addop();
                simple_expr();
                break;
            default:
                break;
        }
    }
    
    private void term() throws Exception {
        factor_a();
        term_linha();
    }
    
    private void term_linha() throws Exception {
        switch(token.tag){
            case Tag.MULT:
            case Tag.DIV:
            case Tag.AND:
                mulop();
                term();
                break;
            default:
                break;
        }
    }

    private void factor_a() throws Exception {
        switch(token.tag){
            case Tag.ID:
            case Tag.CONST_CHAR:
            case Tag.CONST_FLOAT:
            case Tag.CONST_INT:
            case Tag.OP:
                factor();
                break;
            case Tag.NOT:
                eat(Tag.NOT);
                factor();
                break;
            case Tag.DIF:
                eat(Tag.DIF);
                factor();
                break;
            default:
                System.out.println("---------------------------------ERROR----------------------------------");
                throw new Exception("Espera-se um ID, CONSTANTE, (, NOT ou - na linha "+line+"!");
        }
    }
    
    private void factor() throws Exception {
        switch(token.tag){
            case Tag.ID:
                eat(Tag.ID);
                break;
            case Tag.CONST_CHAR:
                eat(Tag.CONST_CHAR);
                break;
            case Tag.CONST_FLOAT:
                eat(Tag.CONST_FLOAT);
                break;
            case Tag.CONST_INT:
                eat(Tag.CONST_INT);
                break;
            case Tag.OP:
                eat(Tag.OP);
                expression();
//                System.out.println("Ta no fecha parenteses " +  Tag.getTagName(token.tag)+ " " + token.tag);
                eat(Tag.CP);
                break;
            default:
                System.out.println("---------------------------------ERROR----------------------------------");
                throw new Exception("Espera-se um ID, CONSTANTE ou (  na linha "+line+"!");
        }
    }
    
    private void mulop() throws Exception {
        switch(token.tag){
            case Tag.MULT:
                eat(Tag.MULT);
                break;
            case Tag.DIV:
                eat(Tag.DIV);
                break;
            case Tag.AND:
                eat(Tag.AND);
                break;
            default:
                System.out.println("---------------------------------ERROR----------------------------------");
                throw new Exception("Espera-se um *, / ou AND  na linha "+line+"!");
        }
    }
    
    private void addop() throws Exception {
        switch(token.tag){
            case Tag.SUM:
                eat(Tag.SUM);
                break;
            case Tag.DIF:
                eat(Tag.DIF);
                break;
            case Tag.OR:
                eat(Tag.OR);
                break;
            default:
                System.out.println("---------------------------------ERROR----------------------------------");
                throw new Exception("Espera-se um +, - ou OR  na linha "+line+"!");
        }
    }

    private void if_stmt() throws Exception {
      switch(token.tag){
        case Tag.IF:
            eat(Tag.IF);
            condition();
            eat(Tag.THEN);
            stmt_list();
            if_stmt_linha();
            break;
        default:
           System.out.println("---------------------------------ERROR----------------------------------");
           throw new Exception("Espera-se um IF na linha " + line + "!");
      }
    }

    private void if_stmt_linha() throws Exception {
        switch (token.tag) {
        case Tag.END:
//            System.out.println("-----------------------Entro no IF----------------------------------");
            eat(Tag.END);
            break;
        case Tag.ELSE:
//            System.out.println("-----------------------Entro no ELSE----------------------------------");
            eat(Tag.ELSE);
            stmt_list();
            eat(Tag.END);
            break;
        default:
            System.out.println("---------------------------------ERROR----------------------------------");
            throw new Exception("Espera-se um END ou Else na linha " + line + "!");
        }
    }

    private void condition() throws Exception { 
        switch(token.tag){
            case Tag.ID:
            case Tag.CONST_CHAR:
            case Tag.CONST_FLOAT:
            case Tag.CONST_INT:
            case Tag.OP:
            case Tag.NOT:
            case Tag.DIF:
                expression();
                break;
            default:
              System.out.println("---------------------------------ERROR----------------------------------");
              throw new Exception("Condition() " + line + "!");
        }
    }
    
    private void expression() throws Exception { 
        switch(token.tag){
            case Tag.ID:
            case Tag.CONST_CHAR:
            case Tag.CONST_FLOAT:
            case Tag.CONST_INT:
            case Tag.OP:
            case Tag.NOT:
            case Tag.DIF:
                simple_expr();
                expression_linha(); break;
            default:
              System.out.println("---------------------------------ERROR----------------------------------");
              throw new Exception("Expression() " + line + "!");
        }
    }

    private void expression_linha() throws Exception { 
        switch(token.tag){
            
            case Tag.EQ:
            case Tag.GT:
            case Tag.GE:
            case Tag.LT:
            case Tag.LE:
            case Tag.NE:
                relop();
                simple_expr();
                break;
            default:
                break;
//              System.out.println("---------------------------------ERROR----------------------------------");
//              throw new Exception("Expression_linha() " + line + "!");
        }
    }
    
    private void relop() throws Exception {
        switch(token.tag){
            case Tag.EQ:
                eat(Tag.EQ);
                break;
            case Tag.GT:
                eat(Tag.GT);
                break;
            case Tag.GE:
                eat(Tag.GE);
                break;
            case Tag.LT:
                eat(Tag.LT);
                break;
            case Tag.LE:
                eat(Tag.LE);
                break;
            case Tag.NE:
                eat(Tag.NE);
                break;
            default:
              System.out.println("---------------------------------ERROR----------------------------------");
              throw new Exception("Espera-se >, >=, <, <=  ou <> na linha " + line + "!");
        }
    }
    
    private void while_stmt() throws Exception {
        stmt_prefix();
        stmt_list();
        eat(Tag.END);
    }
    
    private void stmt_prefix() throws Exception {
        switch(token.tag){
            case Tag.WHILE:
                eat(Tag.WHILE);
                condition();
                eat(Tag.DO);
                break;
            default:
                System.out.println("---------------------------------ERROR----------------------------------");
                throw new Exception("Espera-se WHILE na linha " + line + "!");
        }
    }

    private void repeat_stmt() throws Exception {
//        System.out.println("-------repeat_stmt()------repeat_stmt() ----repeat_stmt()-----");
        eat(Tag.REPEAT);
        stmt_list();
//        System.out.println("-------UNTIL1 ---------UNTIL1 ----UNTIL1 ----UNTIL1 ---UNTIL1 -------");
        stmt_suffix();
    }
    
    private void stmt_suffix() throws Exception {
//        System.out.println("-------stmt_suffix()-----stmt_suffix()-stmt_suffix()-------");
        eat(Tag.UNTIL);
        condition();
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
        switch(token.tag) {
            case Tag.ID:
            case Tag.CONST_CHAR:
            case Tag.CONST_FLOAT:
            case Tag.CONST_INT:
            case Tag.OP:
            case Tag.NOT:
            case Tag.DIF:
                simple_expr();
                break;
            case Tag.LITERAL:
                eat(Tag.LITERAL);
                break;
            default:
              System.out.println("---------------------------------ERROR----------------------------------");
              throw new Exception("Expression() " + line + "!");
        }
    }


}
