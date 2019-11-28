package ws;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;


/**
 * REST Web Service
 */

@Path("Movimento")
public class GameWS extends Files {

    @Context
    private UriInfo context;
    
    String Varivel = new String("teste");
    public GameWS() {
    }
    
    @GET
    @Produces("application/Json")
    public String getJson() {
        
        String saida = Read();
        Gson g = new Gson();
        String aux = g.toJson(saida);
        Write("vazio");
        
        System.out.println(aux);
        return aux;
    }
    
    @POST
    @Consumes("application/json")
    @Path("alterar")
    public void putInserir(String content){
       //System.out.println(content);
        Write(content);
        Varivel = content;
    }
}