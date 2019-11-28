package jogo;

import javax.swing.*; //Biblioteca que contem o Painel usado
import java.awt.*; //Pega todas as bibliotecas necessarias
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent; //Biblioteca que contem os eventos do teclado
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jogo.Peca.EspecPeca; //Chamar uma sub-Classe

//Extende o painel, permitindo fazer alterações nele
public final class Controle extends JPanel implements ActionListener{
    
    public int oldX;
    public int oldY;
    public int newX;
    public int newY;
    public int deltaX;
    public int deltaY;
    
    public int x,y;
    
 private static final Color corFundo = new Color(0xffffff); //Seta a cor do background
  private static final String fonteJogo = "ARIAL"; //Seta a fonte do jogo
  private static final int tamanhoPeca = 64; //Seta o tamanho da peça
  private static final int margemPeca = 16; //Seta o tamanho do espaço das peças
  
  private EspecPeca[] pecas; //Vetor com as peças
  boolean condVitoria = false; //Inicia a condiçao de vitoria como falsa
  boolean condDerrota = false; //Inicia a condiçao de derrota como falsa
  int pontuacao = 0, highScore; //Inica a pontuação com 0
  
  JButton win = new JButton("Easy Win");
  
   ClienteWS Movi=new ClienteWS();
   public StringBuffer movimento = new StringBuffer();

  public Controle() {
    setPreferredSize(new Dimension(350, 450)); 
    setFocusable(true); //Deixa o jogo visivel
    
    win.addActionListener(this);
    
    addMouseListener(new MouseAdapter(){

       
        public void mousePressed(MouseEvent e){
            
        oldX =e.getX();
        oldY =e.getY();

        }
        
       @Override
        public void mouseReleased(MouseEvent e) {
            
            if (!condVitoria && !condDerrota){
            
       newX=e.getX();
       newY=e.getY();
        
        deltaY = newY - oldY;
        deltaX = newX - oldX;
              
       
               if(deltaX > 0){
                  
                   if(deltaX > deltaY){
                       MovDireita();
                   }else{
                       if(deltaY > 0){
                           MovBaixo();
                       }else{
                           MovCima();
                       }
                  
                   }
               }else{
                   if(deltaX < deltaY){
                       MovEsquerda();
                   }else{
                       if(deltaY > 0){
                           MovBaixo();
                       }else{
                           MovCima();
                       }
                       
                   }
               }
               
            } 
            if (!condVitoria && !VerificaMovimento())
            condDerrota = true;
       
            repaint(); //PintaNovamente o background
        }   
    
    });   
    
    addKeyListener(new KeyAdapter() { //Adiciona um "Ouvinte" da tecla 
     
      @Override
      //Função que é obriado a ter por causa da biblioteca
      //Verifica qual tecla está sendo pressionada
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { //Se for apertado ESC, reinicia o jogo
          Reinicio();
        }
        
        //Se n tiver mais movimentos, perdeu o jogo
        if (!VerificaMovimento()) {
          condDerrota = true;
        }
        //Verifica os movimentos de cada tecla
        if (!condVitoria && !condDerrota) {
             try{
          switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
              MovEsquerda();
              break;
            case KeyEvent.VK_A:
              MovEsquerda();  
              break;
            case KeyEvent.VK_RIGHT:
              MovDireita();
              break;
            case KeyEvent.VK_D:
              MovDireita();
              break;
            case KeyEvent.VK_DOWN:
              MovBaixo();
              break;
            case KeyEvent.VK_S:
              MovBaixo();
              break;  
            case KeyEvent.VK_UP:
              MovCima();
              break;
            case KeyEvent.VK_W:
              MovCima();
              break;     
          }
          }catch(Exception Erro){System.out.println("Erro na detecção do teclado");}
        }
        
        //Verifica se não tem mais movimentos
        if (!condVitoria && !VerificaMovimento()) {
          condDerrota = true;
        }
        runMicroService();
        repaint(); //PintaNovamente o background, atualizando o jogo
      }
    });
    
    Reinicio(); //Reinicia o game
  }
  
    //Reinica o game
  public void Reinicio() {
      //Zera todas as variaveis
    pontuacao = 0;
    condVitoria = false;
    condDerrota = false;
    pecas = new EspecPeca[4 * 4]; //Seta o tamanho do jogo. Se for <4, da erro no programa
    //Pega o numero de peças que vai ter no jogo
    for (int i = 0; i < pecas.length; i++) {
      pecas[i] = new EspecPeca();
    }
    AdicionaPeca(); //Adiciona uma nova peça
    AdicionaPeca(); //Adiciona uma nova peça
  }
  
   public void ReinicioEasy() {
      //Zera todas as variaveis
    pontuacao = 0;
    condVitoria = false;
    condDerrota = false;
    pecas = new EspecPeca[4 * 4]; //Seta o tamanho do jogo. Se for <4, da erro no programa
    //Pega o numero de peças que vai ter no jogo
    for (int i = 0; i < pecas.length; i++) {
      pecas[i] = new EspecPeca();
    }
    AdicionaPecaEasy(); //Adiciona uma nova peça
    AdicionaPecaEasy(); //Adiciona uma nova peça
  }

  //Faz o movimento das peças para esquerda
  public void MovEsquerda() {
      
    boolean adicionaNovaPeca = false;

    try{
        
    //Atualiza o jogo com linha por linha
    for (int i = 0; i < 4; i++) {
      EspecPeca[] linhaJogo = novaLinha(i); //Pega uma nova linha para adicionar no jogo
      EspecPeca[] juntaJogo = juntaLinha(MovLinhas(linhaJogo)); //Atualiza todas as linhas do jogo no vetor
      setLine(i, juntaJogo); //Atualiza a parte visual do jogo
      //Verifica se é necessario adicionar uma nova peça
      if (!adicionaNovaPeca) {
        adicionaNovaPeca = true;
      }
    }
    }catch(Exception e){}
    //Verifica se é necessario adicionar uma nova peça
   if (adicionaNovaPeca) {
      AdicionaPeca();
    }
  }

  public void MovDireita() {
    pecas = rotacaoImagem(180); //Rotaciona as peças para o movimento da direita
    MovEsquerda(); //Necessario para fazer o movimento
    pecas = rotacaoImagem(180); //Rotaciona as peças para o movimento da direita
  }

  public void MovCima() {
    pecas = rotacaoImagem(270); //Rotaciona as peças para o movimento para cima
    MovEsquerda(); //Necessario para fazer o movimento
    pecas = rotacaoImagem(90); //Rotaciona as peças para o movimento para cima
  }

  public void MovBaixo() {
    pecas = rotacaoImagem(90); //Rotaciona as peças para o movimento para baixo
    MovEsquerda(); //Necessario para fazer o movimento
    pecas = rotacaoImagem(270); //Rotaciona as peças para o movimento para baixo
  }

  //Usado para atualizar as peças durante o jogo
  private EspecPeca AtualizaPecas(int x, int y) {
    return pecas[x + y * 4]; //Retorna a posição da peça, * 4 por causa do tamanho do tabuleiro
  }

  //Adiciona uma nova peça no jogo
  private void AdicionaPeca() {
      //List facilita na aplicação do vetor
    List<EspecPeca> list = EspacoDisponivel(); //Lista Ligada feita para adicionar uma nova peça de forma aleatoria
    if (!EspacoDisponivel().isEmpty()) { //Se tiver lugar disponivel
      //Pega um valor aletorio baseado no bord do jogo  
      int index = (int) (Math.random() * list.size()) % list.size();
      EspecPeca emptyTime = list.get(index); //Pega o intervalo de numeros para ter uma posição
      emptyTime.value = Math.random() < 0.9 ? 2 : 4; //Pega um numero aletario entre 2 e 4 e coloca em uma das posiçoes de 0 a 9

    }
  }
  
   private void AdicionaPecaEasy() {
      //List facilita na aplicação do vetor
    List<EspecPeca> list = EspacoDisponivel(); //Lista Ligada feita para adicionar uma nova peça de forma aleatoria
    if (!EspacoDisponivel().isEmpty()) { //Se tiver lugar disponivel
      //Pega um valor aletorio baseado no bord do jogo  
      int index = (int) (Math.random() * list.size()) % list.size();
      EspecPeca emptyTime = list.get(index); //Pega o intervalo de numeros para ter uma posição
      emptyTime.value = Math.random() < 0.9 ? 1024 : 1024; //Pega um numero aletario entre 2 e 4 e coloca em uma das posiçoes de 0 a 9

    }
  }
  
  //Verifica se tem algum espaço vazio no nosso jogo, criando uma lista de peças
  private List<EspecPeca> EspacoDisponivel() {
      //final = mantem um valor constante para a instancia que foi declarada.
    final List<EspecPeca> listaPecas = new ArrayList<EspecPeca>(16); //Lista com um valor estatico recebe um vetor de 16 espaço.
    for (EspecPeca t : pecas) { //Até qie t = pecas
      if (t.isEmpty()) { //Se estiver vazio, adiciona uma nova peça no vetor
        listaPecas.add(t);
      }
    }
    return listaPecas; //Retorna a lista de peças que serão usadas
  }

  //Verifica se está cheio
  private boolean VerificaCheio() {
    return EspacoDisponivel().size() == 0; //Se estiver cheio, finaliza com as peças da nossa lista
  }
  
  //Verifica se tem algum movimento no jogo
  boolean VerificaMovimento() {
      //Se não estiver cheio, deixa o jogo continuar
    if (!VerificaCheio()) {
      return true;
    }
    
    //For para atualizar o jogo, separado em X e Y
    for (int x = 0; x < 4; x++) {
      for (int y = 0; y < 4; y++) {
          //T recebe a posição da peça
        EspecPeca t = AtualizaPecas(x, y);
        //Se o valor de X for menor q 3 e valor da X for a cordenada igual ao retornado pela função - Mesma coisa para Y
        //Se for verdade o jogo pode continuar
        if ((x < 3 && t.value == AtualizaPecas(x + 1, y).value)
          || ((y < 3) && t.value == AtualizaPecas(x, y + 1).value)) {
          return true;
        }
      }
    }
    return false;
  }



  //Faz a rotação da imagem, para fazer o movimento da propria. Como a imagem é apenas um quadrado não da problema.
  private EspecPeca[] rotacaoImagem(int angulo) {
    EspecPeca[] newEspecPecas = new EspecPeca[4 * 4];
    int offsetX = 3, offsetY = 3; //Tamanho maximo de x e y, lembrando na computação 4 = 0 - 3
    if (angulo == 90) { //Lembrando das aulas de calculo, quando o seno tem angulação de 90 graus, ele é 0, n precisando de Y
      offsetY = 0;
    } else if (angulo == 270) { //Lembrando das aulas de calculo, quando o cos tem angulação de 270 graus, ele tende a 0, n precisando de X
      offsetX = 0;
    }

    double rad = Math.toRadians(angulo); //Pegando os valores em radiano
    int cos = (int) Math.cos(rad); //Usando a biblioteca, colocamos o valor de Cos em radianos
    int sin = (int) Math.sin(rad); //Usando a biblioteca, colocamos o valor de Sen em radianos
    for (int x = 0; x < 4; x++) {
      for (int y = 0; y < 4; y++) {
        int novoX = (x * cos) - (y * sin) + offsetX; //Gera um novo valor para X
        int novoY = (x * sin) + (y * cos) + offsetY; //Gera um novo valor para Y
        newEspecPecas[(novoX) + (novoY) * 4] = AtualizaPecas(x, y); //Recebe uma nova peça, que será colocada nessa posição
      }
    }
    return newEspecPecas; //Retorna uma nova peça
  }

  //Função usada para mover toda a linha, separamos o trabalho em linhas, já que em matriz estavamos tendo problemas
  private EspecPeca[] MovLinhas(EspecPeca[] linhaAntiga) {
    LinkedList<EspecPeca> aux = new LinkedList<>(); //Aux recebe uma lista ligada, onde facilita caminhar entre os elementos
    for (int i = 0; i < 4; i++) {
        //Verifica se a linha está vazia, se estiver adiciona a ultima linha encontrada.
      if (!linhaAntiga[i].isEmpty())
        aux.addLast(linhaAntiga[i]);
    }
    //Se o tamanho da lista for 0, a gente usa a mesma linha passada por parametro
    if (aux.size() == 0) {
      return linhaAntiga;
    } else {
      EspecPeca[] newLine = new EspecPeca[4]; //Nova linha com o tamanho 4, que é o total de colunas que temos no jogo
      GaranteTamanho(aux, 4); //Garante que o tamanho da peça vai ser igual as outras
      for (int i = 0; i < 4; i++) {
        newLine[i] = aux.removeFirst();
      }
      return newLine;
    }
  }

  //Metodo usado para juntar as peças se forem iguais, atualizando toda a linha
  private EspecPeca[] juntaLinha(EspecPeca[] oldLine) {
    LinkedList<EspecPeca> list = new LinkedList<>();
    for (int i = 0; i < 4 && !oldLine[i].isEmpty(); i++) { //Verifica se a linha não esta vazia
      int num = oldLine[i].value; //Num recebe o valor da linha
      if (i < 3 && oldLine[i].value == oldLine[i + 1].value) { //Se os valores forem iguais, a gente multiplica o valor por 2, seguindo a tematica do jogo.
        num *= 2;
        pontuacao += num; //Soma o valor na pontuação geral
        int objetivo = 2048; //Nosso objetivo para o fim do jogo
        if (num == objetivo) { 
          condVitoria = true;
        }
        i++;
      }
      list.add(new EspecPeca(num)); //Adiciona um novo numero na linha
    }
    if (list.size() == 0) { //Se a lista estiver vazia, retorna a ultima linha
      return oldLine;
    } else {
      GaranteTamanho(list, 4); //Garante o tamanho da linha, e passa o tamanho max
      return list.toArray(new EspecPeca[4]); //Retorna uma lista com 4 espaços
    }
  }

  //Adiciona uma nova peça na lista, garantindo que não vai passar o numero de colunas e nem linhas
  private static void GaranteTamanho(java.util.List<EspecPeca> l, int tamMax) {
    while (l.size() != tamMax) {
      l.add(new EspecPeca());
    }
  }

  //Pega uma nova linha para o jogo
  private EspecPeca[] novaLinha(int index) {
    EspecPeca[] resultado = new EspecPeca[4]; //Recebe um novo vetor de tamanho 4, o maximo para o jogo
    for (int i = 0; i < 4; i++) {
      resultado[i] = AtualizaPecas(i, index); //Recebe o valor retornado baseado no valor de i e do tamanho maximo
    }
    return resultado;
  }

  //Copia uma linha e retorna ela com diferentes valores e posições das novas peças.
  private void setLine(int index, EspecPeca[] re) {
    System.arraycopy(re, 0, pecas, index * 4, 4);
  }
    
    
     @Override
     //Metodo usado para printar o jogo no JPanel
  public void paint(Graphics aux) {
      try{
    super.paint(aux);  //Recebe a cor definida na classe da peça
    aux.setColor(corFundo); //Cor do fundo do jogo
    aux.fillRect(0, 0, this.getSize().width, this.getSize().height); //Completa o fundo da peça baseado no tamanho das peças definido ao longo do programa e o tamanho da tela
    for (int y = 0; y < 4; y++) {
     for (int x = 0; x < 4; x++) {   
        desenhaPeca(aux, pecas[x + y * 4], x, y); //Desenha o fundo das peças no jogo
     }
      }
   } catch(Exception e){}
  }

  //Usado para printar as peças no JPanel, colocando a cor no game
  private void desenhaPeca(Graphics g2, EspecPeca tile, int x, int y) {
 
    Graphics2D g = ((Graphics2D) g2);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
    int value = tile.value;
    int xOffset = offsetCoors(x);
    int yOffset = offsetCoors(y);
    g.setColor(tile.getBackground());
    g.fillRoundRect(xOffset, yOffset, tamanhoPeca, tamanhoPeca, 14, 14);
    g.setColor(tile.getForeground());
    final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
    final Font font = new Font(fonteJogo, Font.BOLD, size);
    g.setFont(font);
    
    win.setLayout(null);
    win.setBounds(210, 380, 100, 40);
    add(win);
    
    
    String s = String.valueOf(value);
    final FontMetrics fm = getFontMetrics(font);

    final int w = fm.stringWidth(s);
    final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];
    
    
    //Desenha o valor da peça na propria peça
    if (value != 0)
      g.drawString(s, xOffset + (tamanhoPeca - w) / 2, yOffset + tamanhoPeca - (tamanhoPeca - h) / 2 - 2);
    
    //Verifica a condição de vitoria ou derrota, mudando a cor do fundo para dar uma diferenciada no fim do game    
    if (condVitoria || condDerrota) {
      g.setColor(new Color(255, 255, 255, 30));
      g.fillRect(0, 0, getWidth(), getHeight());
      g.setColor(new Color(78, 139, 202));
      g.setFont(new Font(fonteJogo, Font.BOLD, 48));
      if (condVitoria) {
        g.drawString("You won!", 70, 150); //Printa a mensagem de vitoria
        highScore = pontuacao;
      }
      if (condDerrota) { //Printa a mensagem de derrota
 
        g.drawString("Game over!", 50, 130);
        g.drawString("You lose!", 70, 200);
        highScore = pontuacao;
      }
      if (condVitoria || condDerrota) { //Permite o usuario jogar novamente
        g.setFont(new Font(fonteJogo, Font.PLAIN, 16));
        g.setColor(new Color(128, 128, 128, 128));
      }
    }

    g.setFont(new Font(fonteJogo, Font.PLAIN, 18));
    g.drawString("Score: " + pontuacao, 20, 370); //Pontuação do jogo
    g.drawString("High Score: " + highScore, 180, 370); //Pontuação do jogo
    g.drawString("Press ESC to reset", 20, 410);
    
    
  }

  private static int offsetCoors(int arg) {
    return arg * (margemPeca + tamanhoPeca) + margemPeca;
  }   

    @Override
    public void actionPerformed(ActionEvent ae) {
    repaint();    
    ReinicioEasy();
    
    
    }
    
    public void runMicroService() { //funcao chamada na Janela.java PARA rodar a hellokitty - thread
       
        Runnable Run;
        Run = new Runnable() { //thread
            public void run() {
                
                while(true){
                    try {
                        movimento=Movi.sendGet();
                    } catch (Exception ex) {
                        Logger.getLogger(Controle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    System.out.println("\nmovimento: "+ movimento);
                    if(movimento.toString().contains("cima")){
                        System.out.println("dentro do if cima: "+movimento);
                        MovCima();
                        repaint();
                    }else if(movimento.toString().contains("baixo")){
                        System.out.println("dentro do if baixo: "+movimento);
                        MovBaixo();
                        repaint();
                    }else if(movimento.toString().contains("esquerda")){
                        System.out.println("dentro do if esquerda: "+movimento);
                        MovEsquerda();
                        repaint();
                    }else if(movimento.toString().contains("direita")){
                        System.out.println("dentro do if direita: "+movimento);
                        MovDireita();
                        repaint();
                    }else if(movimento.toString().contains("resetar")){
                        System.out.println("dentro do if resetar: "+movimento);
                        Reinicio();
                        repaint();
                    }
                     
                    else System.out.println("erro!!!");
                    
                    
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {}
                }
            }
        };
        Thread MS = new Thread(Run);
        MS.start();  
    }
}