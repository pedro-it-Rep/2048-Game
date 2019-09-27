
package jogo;

import javax.swing.*; //Biblioteca que contem o Painel usado
import java.awt.*; //Pega todas as bibliotecas necessarias
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent; //Biblioteca que contem os eventos do teclado
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import jogo.Peca.Tile; //Chamar uma sub-Classe

//Extende o painel, permitindo fazer alterações nele
public class Controle extends JPanel {
    
 private static final Color corFundo = new Color(0xffffff); //Seta a cor do background
  private static final String fonteJogo = "ARIAL"; //Seta a fonte do jogo
  private static final int tamanhoPeca = 64; //Seta o tamanho da peça
  private static final int margemPeca = 16; //Seta o tamanho do espaço das peças

  private Tile[] pecas;
  boolean condVitoria = false;
  boolean condDerrota = false;
  int pontuacao = 0;

  public Controle() {
    setPreferredSize(new Dimension(350, 450));
    setFocusable(true);
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
          Reinicio();
        }
        if (!VerificaMovimento()) {
          condDerrota = true;
        }

        if (!condVitoria && !condDerrota) {
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
              down();
              break;
            case KeyEvent.VK_S:
              down();
              break;  
            case KeyEvent.VK_UP:
              up();
              break;
            case KeyEvent.VK_W:
              up();
              break;  
          }
        }

        if (!condVitoria && !VerificaMovimento()) {
          condDerrota = true;
        }
        repaint();
      }
    });
    Reinicio();
  }

  public void Reinicio() {
    pontuacao = 0;
    condVitoria = false;
    condDerrota = false;
    pecas = new Tile[4 * 4];
    for (int i = 0; i < pecas.length; i++) {
      pecas[i] = new Tile();
    }
    AdicionaPeca();
    AdicionaPeca();
  }

  public void MovEsquerda() {
    boolean needAddTile = false;
    for (int i = 0; i < 4; i++) {
      Tile[] line = getLine(i);
      Tile[] merged = mergeLine(moveLine(line));
      setLine(i, merged);
      if (!needAddTile && !compare(line, merged)) {
        needAddTile = true;
      }
    }

    if (needAddTile) {
      AdicionaPeca();
    }
  }

  public void MovDireita() {
    pecas = rotate(180);
    MovEsquerda();
    pecas = rotate(180);
  }

  public void up() {
    pecas = rotate(270);
    MovEsquerda();
    pecas = rotate(90);
  }

  public void down() {
    pecas = rotate(90);
    MovEsquerda();
    pecas = rotate(270);
  }

  private Tile tileAt(int x, int y) {
    return pecas[x + y * 4];
  }

  private void AdicionaPeca() {
    List<Tile> list = availableSpace();
    if (!availableSpace().isEmpty()) {
      int index = (int) (Math.random() * list.size()) % list.size();
      Tile emptyTime = list.get(index);
      emptyTime.value = Math.random() < 0.9 ? 2 : 4;
    }
  }

  private List<Tile> availableSpace() {
    final List<Tile> list = new ArrayList<Tile>(16);
    for (Tile t : pecas) {
      if (t.isEmpty()) {
        list.add(t);
      }
    }
    return list;
  }

  private boolean isFull() {
    return availableSpace().size() == 0;
  }

  boolean VerificaMovimento() {
    if (!isFull()) {
      return true;
    }
    for (int x = 0; x < 4; x++) {
      for (int y = 0; y < 4; y++) {
        Tile t = tileAt(x, y);
        if ((x < 3 && t.value == tileAt(x + 1, y).value)
          || ((y < 3) && t.value == tileAt(x, y + 1).value)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean compare(Tile[] line1, Tile[] line2) {
    if (line1 == line2) {
      return true;
    } else if (line1.length != line2.length) {
      return false;
    }

    for (int i = 0; i < line1.length; i++) {
      if (line1[i].value != line2[i].value) {
        return false;
      }
    }
    return true;
  }

  private Tile[] rotate(int angle) {
    Tile[] newTiles = new Tile[4 * 4];
    int offsetX = 3, offsetY = 3;
    if (angle == 90) {
      offsetY = 0;
    } else if (angle == 270) {
      offsetX = 0;
    }

    double rad = Math.toRadians(angle);
    int cos = (int) Math.cos(rad);
    int sin = (int) Math.sin(rad);
    for (int x = 0; x < 4; x++) {
      for (int y = 0; y < 4; y++) {
        int newX = (x * cos) - (y * sin) + offsetX;
        int newY = (x * sin) + (y * cos) + offsetY;
        newTiles[(newX) + (newY) * 4] = tileAt(x, y);
      }
    }
    return newTiles;
  }

  private Tile[] moveLine(Tile[] oldLine) {
    LinkedList<Tile> l = new LinkedList<Tile>();
    for (int i = 0; i < 4; i++) {
      if (!oldLine[i].isEmpty())
        l.addLast(oldLine[i]);
    }
    if (l.size() == 0) {
      return oldLine;
    } else {
      Tile[] newLine = new Tile[4];
      ensureSize(l, 4);
      for (int i = 0; i < 4; i++) {
        newLine[i] = l.removeFirst();
      }
      return newLine;
    }
  }

  private Tile[] mergeLine(Tile[] oldLine) {
    LinkedList<Tile> list = new LinkedList<Tile>();
    for (int i = 0; i < 4 && !oldLine[i].isEmpty(); i++) {
      int num = oldLine[i].value;
      if (i < 3 && oldLine[i].value == oldLine[i + 1].value) {
        num *= 2;
        pontuacao += num;
        int ourTarget = 2048;
        if (num == ourTarget) {
          condVitoria = true;
        }
        i++;
      }
      list.add(new Tile(num));
    }
    if (list.size() == 0) {
      return oldLine;
    } else {
      ensureSize(list, 4);
      return list.toArray(new Tile[4]);
    }
  }

  private static void ensureSize(java.util.List<Tile> l, int s) {
    while (l.size() != s) {
      l.add(new Tile());
    }
  }

  private Tile[] getLine(int index) {
    Tile[] result = new Tile[4];
    for (int i = 0; i < 4; i++) {
      result[i] = tileAt(i, index);
    }
    return result;
  }

  private void setLine(int index, Tile[] re) {
    System.arraycopy(re, 0, pecas, index * 4, 4);
  }
    
    
     @Override
  public void paint(Graphics g) {
    super.paint(g);
    g.setColor(corFundo);
    g.fillRect(0, 0, this.getSize().width, this.getSize().height);
    for (int y = 0; y < 4; y++) {
     for (int x = 0; x < 4; x++) {   
        drawTile(g, pecas[x + y * 4], x, y);
      }
   }
  }

  private void drawTile(Graphics g2, Tile tile, int x, int y) {
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

    String s = String.valueOf(value);
    final FontMetrics fm = getFontMetrics(font);

    final int w = fm.stringWidth(s);
    final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

    if (value != 0)
      g.drawString(s, xOffset + (tamanhoPeca - w) / 2, yOffset + tamanhoPeca - (tamanhoPeca - h) / 2 - 2);

        
    if (condVitoria || condDerrota) {
      g.setColor(new Color(255, 255, 255, 30));
      g.fillRect(0, 0, getWidth(), getHeight());
      g.setColor(new Color(78, 139, 202));
      g.setFont(new Font(fonteJogo, Font.BOLD, 48));
      if (condVitoria) {
        g.drawString("You won!", 80, 150);
      }
      if (condDerrota) {
        g.drawString("Game over!", 50, 130);
        g.drawString("You lose!", 70, 200);
      }
      if (condVitoria || condDerrota) {
        g.setFont(new Font(fonteJogo, Font.PLAIN, 16));
        g.setColor(new Color(128, 128, 128, 128));
        g.drawString("Press ESC to play again", 90, getHeight() - 20);
      }
    }
    g.setFont(new Font(fonteJogo, Font.PLAIN, 18));
    g.drawString("Score: " + pontuacao, 130, 370);

  }

  private static int offsetCoors(int arg) {
    return arg * (margemPeca + tamanhoPeca) + margemPeca;
  }
 
    
    
    
    
}
