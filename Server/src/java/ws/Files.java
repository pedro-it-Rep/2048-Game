package ws;

/**
 *
 * @author 18726471
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


/**
 *
 * @author victorreis
 */
public class Files {
    
    String Caminho =new String("C:\\Users\\18016568\\Downloads\\2048-Game-master-20191127T125056Z-001\\2048-Game-master\\Jogo\\src\\direction.txt");


    String Vari =new String();
    public Files()
    {
    
    }
    public static String Read(){
        String conteudo = "";
        try {
            FileReader arq = new FileReader("C:\\Users\\18016568\\Downloads\\2048-Game-master-20191127T125056Z-001\\2048-Game-master\\Jogo\\src\\direction.txt");
            
            BufferedReader lerArq = new BufferedReader(arq);
            String linha="";
            try {
                linha = lerArq.readLine();
                while(linha!=null){
                    conteudo += linha;
                    linha = lerArq.readLine();
                }
                arq.close();
                return conteudo;
            } catch (IOException ex) {
                System.out.println("Erro: Não foi possível ler o arquivo!");
                return "";
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Erro: Arquivo não encontrado!");
            return "";
        }
    }
    
    public static boolean Write(String Texto){
        try {
            FileWriter arq = new FileWriter("C:\\Users\\18016568\\Downloads\\2048-Game-master-20191127T125056Z-001\\2048-Game-master\\Jogo\\src\\direction.txt");
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.println(Texto);
            gravarArq.close();
            return true;
        }catch(IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public String getVari() {
        return Vari;
    }

    public void setVari(String Vari) {
        this.Vari = Vari;
    }

    
}