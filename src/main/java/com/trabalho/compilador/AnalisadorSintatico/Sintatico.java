/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.trabalho.compilador.AnalisadorSintatico;

import com.trabalho.compilador.AnalisadorLexico.Lexer;
import com.trabalho.compilador.AnalisadorLexico.Tag;
import com.trabalho.compilador.AnalisadorLexico.Token;
import java.util.ArrayList;

/**
 *
 * @author gabriel
 */
public class Sintatico {
     private Lexer lex;
    private Token token;
    private ArrayList erros;
    
    public Sintatico (Lexer lex) throws Exception {
        this.lex = lex;
        this.advance();
        this.erros = new ArrayList();
        System.out.println("Sintatico");
    }
//    public void Sintatico(Lexer lex) throws Exception {
//        this.lex = lex;
//        this.advance();
//        this.erros = new ArrayList();
//    }

    private void eat(int tag) throws Exception {
        if (this.token.tag == tag){
            advance();
        } else {
            int [] token = {tag};
            error(token);
        }
    }

    private void advance() throws Exception {
        this.token = lex.scan(); //lê próximo token
    }

    private void error(int [] tokensEsperados) throws Exception {
        //Erro erro = new Erro(lex.line, this.token, tokensEsperados);
        String erro = "erro1";
        erros.add(erro);
        advance();
    }

    // mulop -> * | / | &&
    private void mulop() throws Exception  {
        switch (token.tag){
            case '*':
                this.eat('*');
                break;
            case '/':
                this.eat('/');
                break;
            case Tag.AND:
                this.eat(Tag.AND);
                break;
            default:
                int [] tokens = {'*', '/', Tag.AND};
                error(tokens);
        }
    }

    // addop -> + | - | ||
    private void addop() throws Exception {
        switch (token.tag){
            case '+':
                this.eat('+');
                break;
            case '-':
                this.eat('-');
                break;
            case Tag.OR:
                this.eat(Tag.OR);
                break;
            default:
                int [] tokens = {'+', '-', Tag.OR};
                error(tokens);
        }
    }

    // relop -> > | >= |  < | <= | != | ==
    private void relop() throws Exception {
        switch (token.tag){
            case '>':
                this.eat('>');
                break;
            case '<':
                this.eat('<');
                break;
            case Tag.GE:
                this.eat(Tag.GE);
                break;
            case Tag.LE:
                this.eat(Tag.LE);
                break;
            case Tag.EQ:
                this.eat(Tag.EQ);
                break;
            case Tag.NE:
                this.eat(Tag.NE);
                break;
            default:
                int [] tokens = {'>', '<', Tag.GE, Tag.LE, Tag.EQ, Tag.NE};
                error(tokens);
        }
    }

    // factor -> ID |  NUMBER | (expression)
    private void factor() throws Exception {
        switch (token.tag){
            case Tag.ID:
                this.eat(Tag.ID);
                break;
            case Tag.INT:
                this.eat(Tag.INT);
                break;
            case '(':
                this.eat('(');
                this.expression();
                this.eat(')');
                break;
            case Tag.LITERAL:
                this.eat(Tag.LITERAL);
                break;
            default:
                int [] tokens = {Tag.ID, Tag.INT, Tag.LITERAL, '('};
                error(tokens);
        }
    }

    // factor -> -factor|  !factor | factor
    private void factora() throws Exception  {
        switch (token.tag){
            case '-':
                this.eat('-');
                this.factor();
                break;
            case '!':
                eat('!');
                this.factor();
                break;
            case Tag.ID:
            case Tag.INT:
            case Tag.LITERAL:
            case '(':
                this.factor();
                break;
            default:
                int [] tokens = {'-', '!', Tag.ID, Tag.INT, Tag.LITERAL, '('};
                error(tokens);
        }
    }

    // z -> mulop factora Z| lambda
    private void z() throws Exception  {
        switch (token.tag){
            case '*':
            case '/':
            case Tag.AND:
                this.mulop();
                this.factora();
                this.z();
                break;
            case ';':
            case ')':
            case '-':
            case '>':
            case Tag.GE:
            case '<':
            case Tag.LE:
            case Tag.NE:
            case Tag.EQ:
            case '+':
            case Tag.OR:
                break;
            default:
                int [] tokens = {'*', '/', Tag.AND, ';',')', '-', '>', Tag.GE, '<', Tag.LE, Tag.NE, Tag.EQ, '+', Tag.OR};
                error(tokens);
        }
    }

    // term -> factora Z
    private void term() throws Exception  {
        switch (token.tag){
            case Tag.ID:
            case '(':
            case '!':
            case '-':
            case Tag.INT:
            case Tag.LITERAL:
                this.factora();
                this.z();
                break;
            default:
                int [] tokens = {'(', '!','-', Tag.INT, Tag.ID, Tag.LITERAL};
                error(tokens);
        }
    }

    // a -> addop term A | lambda
    private void a() throws Exception {
        switch (token.tag){
            case '+':
            case '-':
            case Tag.OR:
                this.addop();
                this.term();
                this.a();
                break;
            case ';':
            case ')':
            case '>':
            case Tag.GE:
            case '<':
            case Tag.LE:
            case Tag.NE:
            case Tag.EQ:
                break;
            default:
                int [] tokens = {';',')', '-', '>', Tag.GE, '<', Tag.LE, Tag.NE, Tag.EQ, '+', Tag.OR};
                error(tokens);
        }
    }

    // simpleexpr -> term A
    private void simpleexpr() throws Exception {
        switch (token.tag){
            case Tag.ID:
            case '(':
            case '!':
            case '-':
            case Tag.INT:
            case Tag.LITERAL:
                this.term();
                this.a();
                break;
            default:
                int [] tokens = {'(', '!','-', Tag.INT, Tag.ID, Tag.LITERAL};
                error(tokens);
        }
    }

    // expression -> simpleexpr expression’
    private void expression() throws Exception  {
        switch (token.tag){
            case Tag.ID:
            case '(':
            case '!':
            case '-':
            case Tag.INT:
            case Tag.LITERAL:
                this.simpleexpr();
                this.expressionL();
                break;
            default:
                int [] tokens = {'(', '!', '-', Tag.INT, Tag.ID, Tag.LITERAL};
                error(tokens);
        }
    }

    // expression' -> relop simpleexpr | lambda
    private void expressionL() throws Exception  {
        switch (token.tag){
            case '>':
            case '<':
            case Tag.GE:
            case Tag.LE:
            case Tag.EQ:
            case Tag.NE:
                this.relop();
                this.simpleexpr();
                break;
            case ')':
                break;
            default:
                int [] tokens = { '>', Tag.GE, '<', Tag.LE, Tag.NE, Tag.EQ, ')'};
                error(tokens);
        }
    }

    // writable -> simpleexpr
    private void writable() throws Exception  {
        switch (token.tag){
            case Tag.ID:
            case '(':
            case '!':
            case '-':
            case Tag.INT:
            case Tag.LITERAL:
                this.simpleexpr();
                break;
            default:
                int [] tokens = {'(', '!', '-', Tag.INT, Tag.ID, Tag.LITERAL};
                error(tokens);
        }
    }

    // writestmt -> write (writable)
    private void writestmt() throws Exception  {
        switch (token.tag){
            case Tag.WRITE:
                this.eat(Tag.WRITE);
                this.eat('(');
                this.writable();
                this.eat(')');
                break;
            default:
                int [] tokens = {Tag.WRITE};
                error(tokens);
        }
    }

    // readstmt -> read (identifier)
    private void readstmt() throws Exception  {
        switch (token.tag){
            case Tag.READ:
                this.eat(Tag.READ);
                this.eat('(');
                this.eat(Tag.ID);
                this.eat(')');
                break;
            default:
                int [] tokens = {Tag.READ};
                error(tokens);
        }
    }

    // dosuffix -> while ( condition )
    private void dosuffix() throws Exception  {
        switch (token.tag){
            case Tag.WHILE:
                this.eat(Tag.WHILE);
                this.eat('(');
                this.condition();
                this.eat(')');
                break;
            default:
                int [] tokens = {Tag.WHILE};
                error(tokens);
        }
    }

    // dostmt -> do { stmtlist } dosuffix
    private void dostmt() throws Exception {
        switch (token.tag){
            case Tag.DO:
                this.eat(Tag.DO);
                this.eat('{');
                this.stmtlist();
                this.eat('}');
                this.dosuffix();
                break;
            default:
                int [] tokens = {Tag.DO};
                error(tokens);
        }
    }

    // condition -> expression
    private void condition() throws Exception  {
        switch (token.tag){
            case Tag.ID:
            case '(':
            case '!':
            case '-':
            case Tag.INT:
            case Tag.LITERAL:
                this.expression();
                break;
            default:
                int [] tokens = {Tag.ID, '(', '!', '-', Tag.INT,  Tag.LITERAL};
                error(tokens);
        }
    }

    // ifstmt' -> else { stmtlist } | lambda
    private void ifstmtPrime() throws Exception {
        switch (token.tag){
            case Tag.ELSE:
                this.eat(Tag.ELSE);
                this.eat('{');
                this.stmtlist();
                this.eat('}');
                break;
            case ';':
                break;
            default:
                int [] tokens = {Tag.ELSE, ';'};
                error(tokens);
        }
    }

    // ifstmt -> if ( condition ) { stmtlist } ifstmt'
    private void ifstmt() throws Exception {
        switch (token.tag){
            case Tag.IF:
                this.eat(Tag.IF);
                this.eat('(');
                this.condition();
                this.eat(')');
                this.eat('{');
                this.stmtlist();
                this.eat('}');
                this.ifstmtPrime();
                break;
            default:
                int [] tokens = {Tag.IF};
                error(tokens);
        }
    }

    // assignstmt -> identifier = simple_expr
    private void assignstmt() throws Exception {
        switch (token.tag){
            case Tag.ID:
                this.eat(Tag.ID);
                this.eat('=');
                this.simpleexpr();
                break;
            default:
                int [] tokens = {Tag.ID};
                error(tokens);
        }
    }

    // stmt -> writestmt | readstmt | dostmt | ifstmt | assignstmt
    private void stmt() throws Exception {
        switch (token.tag){
            case Tag.WRITE:
                this.writestmt();
                break;
            case Tag.READ:
                this.readstmt();
                break;
            case Tag.DO:
                this.dostmt();
                break;
            case Tag.IF:
                this.ifstmt();
                break;
            case Tag.ID:
                this.assignstmt();
                break;
            default:
                int [] tokens = {Tag.WRITE, Tag.READ, Tag.DO, Tag.IF, Tag.ID};
                error(tokens);
        }
    }

    // stmtaux -> stmt ; stmtaux | lambda
    private void stmtaux() throws Exception  {
        switch (token.tag){
            case Tag.WRITE:
            case Tag.READ:
            case Tag.DO:
            case Tag.IF:
            case Tag.ID:
                this.stmt();
                this.eat(';');
                this.stmtaux();
                break;
            case '}':
//            case Tag.STOP:
//                break;
            default:
//                int [] tokens = {Tag.WRITE, Tag.READ, Tag.DO, Tag.IF, Tag.ID, '}', Tag.STOP};
                int [] tokens = {Tag.WRITE, Tag.READ, Tag.DO, Tag.IF, Tag.ID, '}'};
                error(tokens);
        }
    }

    // stmtlist -> stmt ; stmtaux
    private void stmtlist() throws Exception  {
        switch (token.tag){
            case Tag.WRITE:
            case Tag.READ:
            case Tag.DO:
            case Tag.IF:
            case Tag.ID:
                this.stmt();
                this.eat(';');
                this.stmtaux();
                break;
            default:
                int [] tokens = {Tag.WRITE, Tag.READ, Tag.DO, Tag.IF, Tag.ID};
                error(tokens);
        }
    }

    // body ->  init stmtlist stop
//    private void body()  {
//        switch (token.TAG){
//            case Tag.INIT:
//                this.eat(Tag.INIT);
//                this.stmtlist();
//                this.eat(Tag.STOP);
//                break;
//            default:
//                int [] tokens = {Tag.INIT};
//                error(tokens);
//        }
//    }

    // type -> float | int | string
    private void type() throws Exception  {
        switch (token.tag){
            case Tag.FLOAT:
                this.eat(Tag.FLOAT);
                break;
            case Tag.INT:
                this.eat(Tag.INT);
                break;
//            case Tag.STRING:
//                this.eat(Tag.STRING);
//                break;
            default:
                int [] tokens = {Tag.FLOAT, Tag.INT};
//                int [] tokens = {Tag.FLOAT, Tag.INT, Tag.STRING};
                error(tokens);
        }
    }

    // identlistaux -> , identifier identlistaux | lambda
    private void identlistaux() throws Exception  {
        switch (token.tag){
            case ',':
                this.eat(',');
                this.eat(Tag.ID);
                this.identlistaux();
                break;
            case ';':
                break;
            default:
                int [] tokens = {',',';'};
                error(tokens);
        }
    }

    // identlist -> identifier identlistaux
    private void identlist() throws Exception  {
        switch (token.tag){
            case Tag.ID:
                this.eat(Tag.ID);
                this.identlistaux();
                break;
            default:
                int [] tokens = {Tag.ID};
                error(tokens);
        }
    }

    // decl -> type identlist
    private void decl()  throws Exception {
        switch (token.tag){
            case Tag.FLOAT:
            case Tag.INT:
//            case Tag.STRING:
//                this.type();
//                this.identlist();
//                break;
            default:
//                int [] tokens = {Tag.FLOAT, Tag.INT, Tag.STRING};
                  int [] tokens = {Tag.FLOAT, Tag.INT};
                error(tokens);
        }
    }

    // decllist -> decl ; decllist | lambda
    private void decllist() throws Exception {
        switch (token.tag){
            case Tag.FLOAT:
            case Tag.INT:
//            case Tag.STRING:
//                this.decl();
//                this.eat(';');
//                this.decllist();
//                break;
//            case Tag.INIT:
//                break;
            default:
//                int [] tokens = {Tag.FLOAT, Tag.INT, Tag.STRING, Tag.INIT};
                int [] tokens = {Tag.FLOAT, Tag.INT};
                error(tokens);
        }
    }

//     program ->  class identifier decllist body
//    public void program()  {
//        switch (token.tag){
//            case Tag.CLASS:
//                this.eat(Tag.CLASS);
//                this.eat(Tag.ID);
//                this.decllist();
//                this.body();
//                break;
//            default:
//                int [] tokens = {Tag.CLASS};
//                error(tokens);
//        }
//    }

    public ArrayList getErros() {
        return erros;
    }
}
