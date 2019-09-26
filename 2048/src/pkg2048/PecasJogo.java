package pkg2048;

import java.awt.*; //Pega todas as bibliotecas necessarias
import java.awt.image.BufferedImage;

public class PecasJogo {
    
     //static = não pode ser alterado ao longo o programa, tendo o mesmo valor para qualquer objeto.
    //final = mantem um valor constante para a instancia que foi declarada.
    //static final = mantem o valor de uma variavel até o fim de um programa.
    public static final int larguraPeca = 80; //Define a largura da peça
    public static final int alturaPeca = 80; //Define a altura da peça
    public static final int atualizacaoPeca = 20; //Define a velocidade que a peça vai andar
    public static final int curvaLargura = 10; //Usado para fazer a curva na peça, igual ao jogo original
    public static final int curvaAltura = 10; //Usado para fazer a curva na peça, igual ao jogo original
    
    private int valorPeca; //Verifica o valor da peça
    private BufferedImage imagemPeca; //Define a imagem da peça, que vai ser mostrada para o usuario.
    private Color fundoPeca; //Define a cor de fundo da peça, variavel ao longo do jogo
    private Color numeroPeca; //Define o numero atual da peça, o seu valor
    private Font font; //Fonte que os numeros vão aparecer
    private int x;
    private int y;
    
    //Construtor que vai ser responsavel por chamar a função que faz o controle das peças
    public PecasJogo(int valorPeca, int x, int y){
        
        this.valorPeca = valorPeca; //Recebe o valor da peça
        this.x = x; //Recebe a cordenada X para saber onde está a peça
        this.y = y; //Recebe a cordenada Y para saber onde está a peça
        
        //TYPE_INT_ARGB - define a cor dos objetos como um inteiro, e utiliza o canal principal de cores
        imagemPeca = new BufferedImage(larguraPeca, alturaPeca, BufferedImage.TYPE_INT_ARGB); //Carrega o background do jogo na memoria
        
        desenhaPeca(); //Chama o metodo responsavel por desenhar cada peça do jogo
        
    }
    
    //Define o desenho (cor do fundo e da letra) de cada peça de acordo com o seu numero
    private void desenhaPeca(){
        
        Graphics2D aux = (Graphics2D)imagemPeca.getGraphics(); //Usado para colorir a peça
        switch (valorPeca) {
            case 2:
                fundoPeca = new Color(0xe9e9e9e); //Cor Cinza Claro
                numeroPeca = new Color(0x000000); //Cor Preta
                break;
            case 4:
                fundoPeca = new Color(0xe6daab);
                numeroPeca = new Color(0x000000); //Cor Preta
                break;
            case 8:
                fundoPeca = new Color(0xf79d3d);
                numeroPeca = new Color(0xffffff); //Cor Branca
                break;
            case 16:
                fundoPeca = new Color(0xf28007);
                numeroPeca = new Color(0xffffff); //Cor Branca
                break;
            case 32:
                fundoPeca = new Color(0xf55e3b);
                numeroPeca = new Color(0xffffff); //Cor Branca
                break;
            case 64:
                fundoPeca = new Color(0xff0000);
                numeroPeca = new Color(0xffffff); //Cor Branca
                break;
            case 128:
                fundoPeca = new Color(0xe9de84);
                numeroPeca = new Color(0xffffff); //Cor Branca
                break;
            case 256:
                fundoPeca = new Color(0xf6e873);
                numeroPeca = new Color(0xffffff); //Cor Branca
                break;
            case 512:
                fundoPeca = new Color(0xf5e455);
                numeroPeca = new Color(0xffffff); //Cor Branca
                break;
            case 1024:
                fundoPeca = new Color(0xf7e12c);
                numeroPeca = new Color(0xffffff); //Cor Branca
                break;
            case 2048:
                fundoPeca = new Color(0xffe400);
                numeroPeca = new Color(0xffffff); //Cor Branca
                break;
            default:
                fundoPeca = Color.black;
                numeroPeca = Color.white;
                break;
        } 
            
            aux.setColor(new Color(0, 0, 0, 0)); //Colocar a cor da peça como invisivel
            aux.fillRect(0, 0, larguraPeca, alturaPeca); //Preenche um retangulo do tamanho da peça que desejamos
            
            aux.setColor(fundoPeca); //Seta a cor ao fundo da peça
            aux.fillRoundRect(0, 0, larguraPeca, alturaPeca, curvaLargura, curvaAltura); //Da a curvatura para a peça, imitando ao jogo original
    
            aux.setColor(numeroPeca); //Seta a cor ao numero da peça
            
            if(valorPeca <= 64) { //Verifica se o valor tem mais de 2 digitos para fazer o ajuste dentro da peça
                
                font = ControleJogo.main.deriveFont(36f); //Copia a fonte utilizada para o objeto, apenas redefinindo o tamanho.
            } else {
                font = ControleJogo.main; //Copia a fonte utilizada para o objeto.
            }
            
            aux.setFont(font);
            
            int centroX = larguraPeca / 2 - CentroPeca.centroLargura("" + valorPeca, font, aux) / 2; //Pega o centro da peça em X
            int centroY = alturaPeca / 2 - CentroPeca.centroAltura("" + valorPeca, font, aux) / 2; //Pega o centro da peça em Y
            
            aux.drawString("" +valorPeca, centroX, centroY); //Desenha o valor da peça no centro, como uma string
            aux.dispose(); //Libera os recursos antes usados, liberando para novos usos
    }
    
    public void AtualizaJogo(){
        
    }
    
    public void RenderizaJogo(Graphics2D aux){
        aux.drawImage(imagemPeca, x, y, null);
    }
    
    public int PegaValor(){
        return valorPeca;
    }
}