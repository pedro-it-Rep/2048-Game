package pkg2048;

import javax.swing.JFrame; //Biblioteca que contem as funcionalidades do JFrame


public class Main {

    public static void main(String[] args) {
   
 //------------------------------------- Definições dos Objetos usados ---------------------------------------------------------------
        
        ControleJogo controleJogo = new ControleJogo(); //Define o Objeto da Classe que vai controlar o inicio do Jogo
        
        
 //------------------------------------- Definições da Parte Grafica -----------------------------------------------------------------
       JFrame janelaJogo = new JFrame("2048 - Paradigmas B - Fabricio e Pedroit");  //Define o Titulo da Janela que vai abrir
       janelaJogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Encerra o programa quando a janela é fechada
       janelaJogo.setResizable(false); //Evita que a janela mude de tamanho, evitando bugs no jogo
       janelaJogo.add(controleJogo);  //Adiciona o item desejado em determinado local de uma fila. No nosso caso ele terá a prioridade maxima.
       janelaJogo.pack(); //Ajusta a tela para os componentes(botão, lacuna, etc) que serão definidos
       janelaJogo.setLocationRelativeTo(null); //Faz com que o jogo inicio no centro da tela
       janelaJogo.setVisible(true);
       
       
       
       
  //------------------------------------- Definições dos Metodos ---------------------------------------------------------------------    

        controleJogo.inicio();
    }
    
}
