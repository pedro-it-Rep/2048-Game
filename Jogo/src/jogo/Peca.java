
package jogo;

import java.awt.Color;

public class Peca {
    //Cria uma sub-classe que não tera seu valor alterado
    static class EspecPeca {
    int value; //Valor da peça

    public EspecPeca() {
      this(0);
    }

    //Valor recebe o numero da peça
    public EspecPeca(int num) {
      value = num;
    }

    //Se a peça estiver vazia, coloca 0
    public boolean isEmpty() {
      return value == 0;
    }

    public Color getForeground() {
      return value < 16 ? new Color(0x776e65) :  new Color(0xf9f6f2);
    }

    //Define a cor das peças
    public Color getBackground() {
       try{ 
      switch (value) {
        case 2:    return new Color(0xeee4da);
        case 4:    return new Color(0xede0c8);
        case 8:    return new Color(0xf2b179);
        case 16:   return new Color(0xf59563);
        case 32:   return new Color(0xf67c5f);
        case 64:   return new Color(0xf65e3b);
        case 128:  return new Color(0xedcf72);
        case 256:  return new Color(0xedcc61);
        case 512:  return new Color(0xedc850);
        case 1024: return new Color(0xedc53f);
        case 2048: return new Color(0xedc22e);
      }
       } catch(Exception e) {System.out.println("Erro no set de Cores");} 
       
      return new Color(0xcdc1b4); 
    }  
}
}
