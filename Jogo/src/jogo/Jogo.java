package jogo;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Jogo {
    
    Controle teste = new Controle();

    public static void main(String[] args) {
        
      try{
    JFrame game = new JFrame();
    game.setTitle("2048 Game");
    game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    game.setSize(350, 450);
    game.setResizable(false);
    
    game.add(new Controle());

    game.setLocationRelativeTo(null);
    game.setVisible(true);
        
    }catch(Exception e){System.out.println("Erro na inicialização do programa");}
    }
    
}
