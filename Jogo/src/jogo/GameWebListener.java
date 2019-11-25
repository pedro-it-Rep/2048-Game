package jogo;

import jogo.ControleWS;
import static spark.Spark.post;
import jogo.Peca.EspecPeca;

public class GameWebListener extends ExtendableListener {

    public GameWebListener (EspecPeca[] holders) {
        super(holders);
    }


    public void initServer() {
        post("/control", (req, res) -> { return ControleWS.executeCommand(req, res, holders); });
    }
}
