import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Livro implements Serializable {
    private static final long serialVersionUID = 1L;

    private String autor;
    private String nome;
    private String genero;
    private int numExemplares;

    public Livro(String autor, String nome, String genero, int numExemplares) {
        this.autor = autor;
        this.nome = nome;
        this.genero = genero;
        this.numExemplares = numExemplares;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getNumExemplares() {
        return numExemplares;
    }

    public void setNumExemplares(int numExemplares) {
        this.numExemplares = numExemplares;
    }

    // Método para converter o objeto Livro para JSON
    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("autor", this.autor);
        jsonObject.put("nome", this.nome);
        jsonObject.put("genero", this.genero);
        jsonObject.put("numExemplares", this.numExemplares);
        return jsonObject.toJSONString();
    }

    // Método estático para converter JSON em objeto Livro
    public static Livro fromJson(String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        String autor = (String) jsonObject.get("autor");
        String nome = (String) jsonObject.get("nome");
        String genero = (String) jsonObject.get("genero");
        long numExemplares = (long) jsonObject.get("numExemplares");
        return new Livro(autor, nome, genero, (int) numExemplares);
    }

    // Método para salvar o objeto Livro em um arquivo JSON
    public static void salvarJson(String nomeArquivo, Livro livro) {
        try (FileWriter file = new FileWriter(nomeArquivo)) {
            file.write(livro.toJson());
            System.out.println("Arquivo JSON salvo com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo JSON: " + e.getMessage());
        }
    }

    // Método estático para carregar um objeto Livro a partir de um arquivo JSON
    public static Livro carregarJson(String nomeArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonBuilder.append(line);
            }
            String json = jsonBuilder.toString();
            return Livro.fromJson(json);
        } catch (IOException | ParseException e) {
            System.err.println("Erro ao carregar arquivo JSON: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return "Livro{" +
                "autor='" + autor + '\'' +
                ", nome='" + nome + '\'' +
                ", genero='" + genero + '\'' +
                ", numExemplares=" + numExemplares +
                '}';
    }
}
