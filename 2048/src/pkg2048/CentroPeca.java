package pkg2048;

import java.awt.*; 
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

//Classe auxiliar usada para pegar o centro da peça, atualizada constantemente
public class CentroPeca {
    
    
    private CentroPeca() { }
    
    public static int centroLargura(String centro, Font font, Graphics2D aux){
       
        aux.setFont(font);
        Rectangle2D bounds = aux.getFontMetrics().getStringBounds(centro, aux); //Pega qual o centro do retangulo 2D baseado nos tamanhos que foram colocados na classe de desenho
        return (int)bounds.getWidth(); //Retorna a largura do Retangulo
        
    }
    
     public static int centroAltura(String centro, Font font, Graphics2D aux){
        
        aux.setFont(font);
        if(centro.length() == 0) return 0;
        
        //Textlayout - Biblioteca usada para obter o limite de texto do retangulo
        //getFontRenderContext -  pega a fonte que está sendo usada e passa para o objeto
        TextLayout tipoTexto = new TextLayout(centro, font, aux.getFontRenderContext()); 
        //getBounds - pega o limite do retangulo que foi definido na outra classe
        return (int)tipoTexto.getBounds().getHeight(); //Retorna a altura do Retangulo
    }
}
