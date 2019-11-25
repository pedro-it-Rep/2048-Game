package jogo;

import jogo.Peca.EspecPeca;
import jogo.Controle;

/**
 *
 * @author marcelo
 */
public abstract class ExtendableListener {

    protected EspecPeca[] holders;
    
    public ExtendableListener (EspecPeca[] holders) {
        this.holders = holders;
    }
}