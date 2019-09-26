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
    public static final int larguraJogo = 400;
    public static final int alturaJogo = 630;
    
    //Define a fonte (letras) do jogo
    public static final Font main = new Font("Century Gothic", Font.PLAIN, 28); 
    
    private Thread jogo; //Inicia uma Thread para o jogo
    private boolean estaRodando; //Verificar se o jogo está rodando
    private FundoJogo tabuleiro;
    
    //TYPE_INT_RGB - define a cor dos objetos como um inteiro, e ignora o canal principal de cores
    private BufferedImage fundoTela = new BufferedImage(larguraJogo, alturaJogo, BufferedImage.TYPE_INT_RGB); //Carrega o background do jogo na memoria
    
    
    //Construtor que define o inicio do jogo
    public ControleJogo(){
        
        setFocusable(true); 
        setPreferredSize(new Dimension(larguraJogo, alturaJogo)); //Seta o tamanho da tela
        addKeyListener(this); //Verifica se alguma tecla esta sendo pressionada
        
        
        tabuleiro = new FundoJogo(larguraJogo / 2 - FundoJogo.larguraTabuleiro / 2, alturaJogo - FundoJogo.alturaTabuleiro - 10);
        
    }
    
    private void AtualizaJogo(){    
        
        tabuleiro.AtualizaJogoTabuleiro();
        VerificaMovimentos.VerificaTecla(); //Chama a função que verifica qual tecla está sendo pressionada
    }
    
    //Metodo usado para renderizar as peças do jogo
    private void RenderizaJogo(){
        
        Graphics2D aux = (Graphics2D) fundoTela.getGraphics(); //Usado para definir a cor do background
        aux.setColor(Color.WHITE); //Define o fundo da tela como branco.
        aux.fillRect(0, 0, larguraJogo, alturaJogo); //Preenche o espaço definido
        tabuleiro.Renderizacao(aux);
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
        
        VerificaMovimentos.TeclaPressionada(tecla);
        
    }

    @Override
    public void keyReleased(KeyEvent tecla) {
        
        VerificaMovimentos.TeclaSemPressionar(tecla);
        
    }

      @Override
    //Necessario ser implementado para a inicialização da thread.
    public void run() {
       
        int atualizacao = 0;
        double taxaAtualizacaoNS = 1000000000.0 / 60;

        //Ultimo update em nanoseg
        double antesAtualizacao = System.nanoTime();

        //Updates necessários - caso der erro em render()
        double unprocessed = 0;

        while(estaRodando) {

            boolean shouldRender = false;

            double tempoAtual = System.nanoTime();
            unprocessed = unprocessed + (tempoAtual - antesAtualizacao) / taxaAtualizacaoNS;
            antesAtualizacao = tempoAtual;

            while (unprocessed >= 1) {
                atualizacao++;
                AtualizaJogo();
                unprocessed--;
                shouldRender = true;

            }

            if (shouldRender) {

                RenderizaJogo();
                shouldRender = false;
            } else {
                try {
                    Thread.sleep(1); //Sleep thread -> shouldRender = false
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    
   //synchronized =  permite que mais de 1 Thread use o metodo, possibilitando algo sincronizado
  //Metodo que atraves das Threads, inicia o jogo 
   public synchronized void inicio(){
       
       if(estaRodando) return; //Se for verdade só continua rodando
       
       estaRodando = true; //Se não estiver rodando, define como verdadeiro e começa o jogo
       jogo = new Thread(this, "jogo"); //Cria uma nova Thread
       jogo.start(); //Inicia a Thread
       
   } 
    
   //synchronized =  permite que mais de 1 Thread use o metodo, possibilitando algo sincronizado
  //Metodo que atraves das Threads, finaliza o jogo e o programa
   public synchronized void fim(){
       
       if(!estaRodando) return; //Se  estiver rodando, continua
       
       estaRodando = false; //Se for para finalizar o jogo, torna a flag como false
       System.exit(0); //Termina o programa
       
   }
    
}