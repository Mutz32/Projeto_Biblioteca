import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

    public static final String ARQUIVO_JSON = "livros.json";

    public static void main(String[] args) {
        List<Livro> livros = lerLivrosDoArquivo();

        try (ServerSocket serverSocket = new ServerSocket(33333)) {
            System.out.println("Servidor iniciado. Aguardando conexões...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress());

                Thread clienteThread = new Thread(new ClienteHandler(socket, livros));
                clienteThread.start();
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }

    private static List<Livro> lerLivrosDoArquivo() {
        List<Livro> livros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_JSON))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Livro livro = Livro.fromJson(linha);
                livros.add(livro);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo JSON: " + e.getMessage());
        }
        return livros;
    }
}
