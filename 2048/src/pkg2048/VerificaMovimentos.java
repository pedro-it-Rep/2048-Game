package pkg2048;

import java.awt.event.*;

public class VerificaMovimentos {

    public static boolean[] teclaPressionada = new boolean[256]; //Verifica os movimentos da tecla abaixada
    public static boolean[] teclaLevantada = new boolean [256]; //Verifica os movimentos da tecla levantada, se a volta da tecla
    
    //Construtuor Vazio
    public VerificaMovimentos() {
        
    }
     
    //Função que verifica qual foi a tecla digitada - Precisa do Mouse ainda
    public static void VerificaTecla(){
        
        int i;
        
        for(i=0; i < 8; i++){    
       
       try{
           
           switch(i){
               
               case 0:
                   teclaLevantada[KeyEvent.VK_LEFT] = teclaPressionada[KeyEvent.VK_LEFT];
                   break;
                   
               case 1:
                   teclaLevantada[KeyEvent.VK_RIGHT] = teclaPressionada[KeyEvent.VK_RIGHT];
                   break;
                   
               case 2:
                   teclaLevantada[KeyEvent.VK_UP] = teclaPressionada[KeyEvent.VK_UP];
                   break;
                   
               case 3:
                   teclaLevantada[KeyEvent.VK_DOWN] = teclaPressionada[KeyEvent.VK_DOWN];
                   break;
                   
               case 4:
                   teclaLevantada[KeyEvent.VK_W] = teclaPressionada[KeyEvent.VK_W];
                   break;
                   
               case 5:
                   teclaLevantada[KeyEvent.VK_A] = teclaPressionada[KeyEvent.VK_A];
                   break;
                   
               case 6:
                   teclaLevantada[KeyEvent.VK_S] = teclaPressionada[KeyEvent.VK_S];
                   break;
                   
               case 7:
                   teclaLevantada[KeyEvent.VK_D] = teclaPressionada[KeyEvent.VK_D];
                   break;
               
           }
 
            }catch(Exception e){}
        }
    }  
    
    //Manda um sinal dizendo que a tecla foi pressionada
    public static void TeclaPressionada(KeyEvent e) {
        teclaPressionada[e.getKeyCode()] = true;
    }

    //Manda um sinal dizendo que a tecla foi levantada, vai efetuar o movimento logo após isso
    public static void TeclaSemPressionar(KeyEvent e) {
        teclaPressionada[e.getKeyCode()] = false;
    }
    
    
    //Pega qual tecla foi digitada e retorna qual o movimento desejado
    public static boolean TeclaTyped(int keyEvent) {
        return !teclaPressionada[keyEvent] && teclaLevantada[keyEvent];
    }
    
}