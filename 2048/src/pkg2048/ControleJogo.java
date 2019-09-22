package pkg2048;

import javax.swing.JPanel; //Biblioteca que contem o Painel usado ...

import java.awt.event.KeyEvent; //Biblioteca que contem os eventos do teclado
import java.awt.event.KeyListener; //Usando para verificar as mudanças de estado de uma variavel
import java.awt.image.BufferedImage; //Permite o uso de imagens ao longo do programa
import java.awt.*; //Pega todas as bibliotecas necessarias

//Extends - extende uma classe que ja foi criada, tendo assim uma hierarquia
//Implements - usado para fazer uma interface, tambem usada para o encapsulamento(segurança futura) do programa
//KeyListener - verifica as mudanças de estado de uma variavel
//Runnable - transforma a classe em um Thread, permitindo a extensão de outras classes.
//Threads -  permite a execução de 2 ou mais coisas juntamente com o resto do programa.
public class ControleJogo extends JPanel implements KeyListener, Runnable{
    
    
    //static = não pode ser alterado ao longo o programa, tendo o mesmo valor para qualquer objeto.
    //final = mantem um valor constante para a instancia que foi declarada.
    //static final = mantem o valor de uma variavel até o fim de um programa.
    public static final int larguraJogo = 650;
    public static final int alturaJogo = 830;
    
    //Define a fonte (letras) do jogo
    public static final Font main = new Font("Clear Sans", Font.PLAIN, 28); 
    
    private Thread jogo; //Inicia uma Thread para o jogo
    private boolean estaRodando; //Verificar se o jogo está rodando
            
    private BufferedImage fundoTela = new BufferedImage(larguraJogo, alturaJogo, BufferedImage.TYPE_INT_RGB); //Carrega o background do jogo na memoria
    
    
    //Define o inicio do jogo
    public ControleJogo(){
        
        setFocusable(true); 
        setPreferredSize(new Dimension(larguraJogo, alturaJogo)); //Seta o tamanho da tela
        addKeyListener(this); //Verifica se alguma tecla esta sendo pressionada
    }
    
    private void AtualizaJogo(){
   
    }
    
    //Metodo usado para renderizar as peças do jogo
    private void RenderizaJogo(){
        
        Graphics2D aux = (Graphics2D) fundoTela.getGraphics(); //Usado para definir a cor do background
        aux.setColor(Color.WHITE); //Define o fundo da tela como branco.
        aux.fillRect(0, 0, larguraJogo, alturaJogo); //Preenche o espaço definido
        aux.dispose(); //Libera os recursos antes usados, liberando para novos usos
        
        Graphics2D aux2 = (Graphics2D) getGraphics(); //Usado para carregar a imagem
        aux2.drawImage(fundoTela, 0, 0, null); //Carrega a imagem que esta na memoria
        aux2.dispose(); //Libera os recursos antes usados, liberando para novos usos
        
    }
    
    
    
    @Override
    public void keyTyped(KeyEvent tecla) {
        
    }

    @Override
    public void keyPressed(KeyEvent tecla) {
        
    }

    @Override
    public void keyReleased(KeyEvent tecla) {
        
    }

    @Override
    //Necessario ser implementado para a inicialização da thread.
    public void run() {
       
        int atualizacao = 0;//Flag para atualizar o jogo
        
        while(estaRodando){ //Verifica se o jogo esta rodando
        
        boolean renderizaJogo = false; 

        if(renderizaJogo){
            
            RenderizaJogo();
            renderizaJogo = false;
            
        } else {
            //Se der algum erro durante a execução do jogo
            try{
                Thread.sleep(1);
                
            }catch(Exception e){
                
                e.printStackTrace();
            }    
        }
        }
        
    }
    
   //synchronized =  
  //Função que atravez das Threads, inicia o jogo 
   public synchronized void inicio(){
       
       if(estaRodando) return; //Se for verdade só continua rodando
       
       estaRodando = true; //Se não estiver rodando, define como verdadeiro e começa o jogo
       jogo = new Thread(this, "jogo"); //Cria uma nova Thread
       jogo.start(); //Inicia a Thread
       
   } 
    
   
   public synchronized void fim(){
       
       if(!estaRodando) return; //Se  estiver rodando, continua
       
       estaRodando = false; //Se for para finalizar o jogo, torna a flag como false
       System.exit(0); //Termina o programa
       
   }
    
}
