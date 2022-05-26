/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.trabalho.compilador.AnalisadorLexico;

/**
 *
 * @author gabrielluis
 */
public class Char extends Token {
    
    public final char value;
    
    public Char(char value){
        super(Tag.CHAR);
        this.value = value;
    }
    public String toString(){
        return "" + value;
    }
}
