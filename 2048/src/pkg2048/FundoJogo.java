package pkg2048;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class FundoJogo {
    
    public static final int linhasFundo = 4;
    public static final int colunaFundo = 4;
    
    private final int pecaInicial = 2;
    private PecasJogo[][] tabuleiro;
    private boolean condicaoVitoria;
    private boolean condicaoDerrota;
    
    private BufferedImage fundoTabuleiro;
    private BufferedImage tabuleiroCompleto;
    private int x;
    private int y;
    
    private boolean inicioGame;
    
    private static int espacoPecas = 10;
    public static int larguraTabuleiro = (colunaFundo + 1) * espacoPecas + colunaFundo * PecasJogo.larguraPeca;
    public static int alturaTabuleiro = (linhasFundo + 1) * espacoPecas + linhasFundo * PecasJogo.alturaPeca;
    
    public FundoJogo(int x, int y){
        
        this.x = x;
        this.y = y;
        
        tabuleiro = new PecasJogo[linhasFundo][colunaFundo];
        
        fundoTabuleiro = new BufferedImage(larguraTabuleiro, alturaTabuleiro, BufferedImage.TYPE_INT_RGB);
        tabuleiroCompleto = new BufferedImage(larguraTabuleiro, alturaTabuleiro, BufferedImage.TYPE_INT_RGB);
        
        criaImagemFundo();
        IniciaPecas();
        
    }
    
    public enum Direcoes{
        
        LEFT, RIGHT, UP, DOWN;
    }
    
    private void criaImagemFundo(){
        
        int i, j, x, y;
        
        Graphics2D aux = (Graphics2D) fundoTabuleiro.getGraphics();
        aux.setColor(Color.darkGray);
        aux.fillRect(0, 0, larguraTabuleiro, alturaTabuleiro);
        aux.setColor(Color.lightGray);
        
        for(i = 0; i < larguraTabuleiro; i++){
            for(j = 0; j < alturaTabuleiro; j++){
                
              x = espacoPecas + espacoPecas * j + PecasJogo.larguraPeca *j;
              y = espacoPecas + espacoPecas * i + PecasJogo.alturaPeca * i;
              
              aux.fillRoundRect(x, y, PecasJogo.larguraPeca, PecasJogo.alturaPeca, PecasJogo.curvaLargura, PecasJogo.curvaAltura);
                
            } 
        }   
    }
    
    private void IniciaPecas(){
        int i;
        
        for(i = 0; i < pecaInicial; i++){
            pecaAleatoria();
        }
    }
    
    private void pecaAleatoria(){
        
        int localizacaoPeca, linhaAux, colunaAux;

        Random geraAleatorio = new Random();
        boolean geracaoInvalida = true;
        
        while(geracaoInvalida){
            
            localizacaoPeca = geraAleatorio.nextInt(linhasFundo * colunaFundo);
            linhaAux = localizacaoPeca / linhasFundo;
            colunaAux = localizacaoPeca % colunaFundo;
            
            PecasJogo atual = tabuleiro[linhaAux][colunaAux];
            
            if(atual == null){
                
                int valor = geraAleatorio.nextInt(10)< 9 ? 2 : 4;
                PecasJogo pecas = new PecasJogo(valor, PegaCordX(colunaAux), PegaCrodY(linhaAux));
                tabuleiro[linhaAux][colunaAux] = pecas;
                geracaoInvalida = false;
                
            }
  
        }
    }
    
    
	public int PegaCordX(int col) {
		return espacoPecas + col * PecasJogo.larguraPeca + col * espacoPecas;
	}

	public int PegaCrodY(int row) {
		return espacoPecas + row * PecasJogo.alturaPeca + row * espacoPecas;
	}

    
    
    
    public void Renderizacao(Graphics2D aux){
       
        int i, j;
        
        Graphics2D aux2 = (Graphics2D)tabuleiroCompleto.getGraphics();
        aux2.drawImage(fundoTabuleiro, 0, 0, null);
        
        for(i = 0; i < linhasFundo; i++){
            for(j = 0; j < colunaFundo; j++){
                PecasJogo atual = tabuleiro[i][j];
                if(atual == null) continue;
                atual.RenderizaJogo(aux2);
            }
        }
        
        aux.drawImage(fundoTabuleiro, x, y, null);
        aux2.dispose();
    }
    
    public void AtualizaJogoTabuleiro(){
        
        VerificaTecla();
        
         for(int i = 0; i < linhasFundo; i++){
            for(int j = 0; j < colunaFundo; j++){
                
                PecasJogo atual = tabuleiro[i][j];
                if(atual == null) continue;
                atual.AtualizaJogo();
                
                if(atual.PegaValor() == 2048){
                    condicaoVitoria = true;
                    //Animação vitoria
                }
            }
         }
    }
    
    private void VerificaTecla(){
        
        if(VerificaMovimentos.TeclaTyped(KeyEvent.VK_RIGHT) || VerificaMovimentos.TeclaTyped(KeyEvent.VK_D)){
            if(!inicioGame) inicioGame = true;    
        }
        if(VerificaMovimentos.TeclaTyped(KeyEvent.VK_LEFT) || VerificaMovimentos.TeclaTyped(KeyEvent.VK_A)){
            if(!inicioGame) inicioGame = true;    
        }
        if(VerificaMovimentos.TeclaTyped(KeyEvent.VK_UP) ||VerificaMovimentos.TeclaTyped(KeyEvent.VK_W)){
            if(!inicioGame) inicioGame = true;    
        }
        if(VerificaMovimentos.TeclaTyped(KeyEvent.VK_DOWN) || VerificaMovimentos.TeclaTyped(KeyEvent.VK_S)){
            if(!inicioGame) inicioGame = true;    
        }
        
        
    }
}
