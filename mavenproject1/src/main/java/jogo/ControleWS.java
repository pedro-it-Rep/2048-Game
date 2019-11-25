package jogo;

import com.google.gson.Gson;
//import dto.Command;
//import enumerators.Direction;
import spark.Request;
import spark.Response;
import static spark.Spark.halt;
import jogo.Controle;
import jogo.Peca.EspecPeca;


public class ControleWS {

    static Gson gson = new Gson();

    public static String executeCommand(Request req, Response res, EspecPeca[] holders) {
            res.header("Content-Type", "application/json");
            boolean movimento = false;
            Controle teste = new Controle();
            String response = "";

            Command command = gson.fromJson(req.body(), Command.class);

            Direction direction = command.getDirection();
            if (direction == null) {
                res.status(400);
                return response = "{ \"error\": \"Invalid Params\"}";
            }

            switch(direction) {
            case up:
                teste.MovCima();
                teste.repaint();
                movimento = true;
                res.status(200);
                response = "{ \"status\": \"ok\" }";
                break;
            case left:
                teste.MovEsquerda();
                teste.repaint();
                movimento = true;
                res.status(200);
                response = "{ \"status\": \"ok\" }";
                break;
            case down:
                teste.MovBaixo();
                teste.repaint();
                movimento = true;
                res.status(200);
                response = "{ \"status\": \"ok\" }";
                break;
            case right:
                teste.MovDireita();
                teste.repaint();
                movimento = true;
                res.status(200);
                response = "{ \"status\": \"ok\" }";
                break;
            default:
            }

            if (res.status() != 200 && res.status() != 400) {
                response = "{ \"error\": \"INTERNAL\" }";
                res.status(500);
            }
            return response;
        }
}