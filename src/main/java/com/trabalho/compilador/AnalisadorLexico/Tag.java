package com.trabalho.compilador.AnalisadorLexico;

public class Tag {
    public final static int 
            EQ=200,
            GT=201,
            GE=202,
            LT=203,
            LE=204,
            NE=205,
            
            SUM=206,
            DIF=207,
            MULT=208,
            DIV=209,
            
            OP=211,
            CP=212,
            SEMICOLON=213,
            OB=214,
            CB=215,
            ASSIGN_STATEMENT=216,
            OBR=217,
            CBR=218,
           
            ROUTINE=219,
            BEGIN=220,
            END=221,
            DECLARE=222,
            INT=223,
            FLOAT=224,
            CHAR=225,
            IF=226,
            THEN=227,
            ELSE=228,
            REPEAT=229,
            UNTIL=230,
            WHILE=231,
            DO=232,
            READ=233,
            WRITE=234,
            NOT=235,
            OR=236,
            AND=237,
            
            ID=238,
            CONST_CHAR=239,
            LITERAL=240,
            BLOC_COMMENT=241,
            COMMA= 242,
            CONST_INT=243,
            CONST_FLOAT=244
            ;
    
    public static String getTagName(int tag){
        switch(tag){
            case EQ:
                return "=";
            case GT:
                return ">";
            case GE:
                return ">=";
            case LT:
                return "<";
            case LE:
                return "<=";
            case NE:
                return "<>";
            case SUM:
                return "+";
            case DIF:
                return "-";
            case MULT:
                return "*";
            case DIV:
                return "/";               
            case OP:
                return "(";
            case CP:
                return ")";
            case SEMICOLON:
                return ";";
            case OB:
                return "{";
            case CB:
                return "}";
            case ASSIGN_STATEMENT:
                return ":=";
            case OBR:
                return "[";
            case CBR:
                return "]";
            case ROUTINE:
                return "routine";
            case BEGIN:
                return "begin";
            case END:
                return "end";
            case DECLARE:
                return "declare";
            case INT:
                return "int";
            case FLOAT:
                return "float";
            case CHAR:
                return "char";
            case IF:
                return "if";
            
            case THEN:
                return "then";
            case ELSE:
                return "else";
            case REPEAT:
                return "repeat";
            case UNTIL:
                return "until";
            case WHILE:
                return "while";
            case DO:
                return "do";
            case READ:
                return "else";
            case WRITE:
                return "repeat";
            case NOT:
                return "not";
            case OR:
                return "or";
            case AND:
                return "and";
            case ID:
                return "ID";
            case COMMA:
                return ","; 
            default:
                return null;
        }
    }
}
