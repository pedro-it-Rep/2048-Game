package ws;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Files {
    
    String Caminho = new String("C:\\Users\\18016568.LAB-INF.000\\Downloads\\2048-Game-3.0\\2048-Game-3.0\\Jogo\\src\\direction.txt");

    String Variavel = new String();
    public Files()
    {
    
    }
    public static String Read(){
        String conteudo = "";
        try {
            FileReader camArq = new FileReader("C:\\Users\\18016568.LAB-INF.000\\Downloads\\2048-Game-3.0\\2048-Game-3.0\\Jogo\\src\\direction.txt");
            
            BufferedReader lerArq = new BufferedReader(camArq);
            String linha="";
            try {
                linha = lerArq.readLine();
                while(linha!=null){
                    conteudo += linha;
                    linha = lerArq.readLine();
                }
                camArq.close();
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
            FileWriter camArq = new FileWriter("C:\\Users\\18016568.LAB-INF.000\\Downloads\\2048-Game-3.0\\2048-Game-3.0\\Jogo\\src\\direction.txt");
            PrintWriter gravarArq = new PrintWriter(camArq);
            gravarArq.println(Texto);
            gravarArq.close();
            return true;
        }catch(IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public String getVari() {
        return Variavel;
    }

    public void setVari(String Variavel) {
        this.Variavel = Variavel;
    }

    
}