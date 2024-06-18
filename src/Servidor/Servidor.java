package Servidor;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class Servidor {

    private static final int PORTA = 33333;
    private static final String ARQUIVO_JSON = "livros.json";

    public static void main(String[] args) {
        List<Livro> livros = lerLivrosDoArquivo();

        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
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
                Livro livro = parseJsonParaLivro(linha);
                livros.add(livro);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo JSON: " + e.getMessage());
        }
        return livros;
    }

    private static Livro parseJsonParaLivro(String linhaJson) {
        // Não está lendo o JSON
    	
        return null;
    }

    private static void salvarLivrosNoArquivo(List<Livro> livros) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_JSON))) {
            for (Livro livro : livros) {
                String linhaJson = converterLivroParaJson(livro);
                if (linhaJson != null) {
                    bw.write(linhaJson);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar no arquivo JSON: " + e.getMessage());
        }
    }

    private static String converterLivroParaJson(Livro livro) {
        // Não está lendo o JSON
        return null;
    }

    private static class ClienteHandler implements Runnable {
        private Socket socket;
        private List<Livro> livros;

        public ClienteHandler(Socket socket, List<Livro> livros) {
            this.socket = socket;
            this.livros = livros;
        }

        @Override
        public void run() {
            try (ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream())) {

                while (true) {
                    String operacao = (String) entrada.readObject();
                    System.out.println("Operação recebida: " + operacao);

                    switch (operacao) {
                        case "LISTAR":
                            saida.writeObject(livros);
                            break;
                        case "CADASTRAR":
                            Livro novoLivro = (Livro) entrada.readObject();
                            cadastrarLivro(novoLivro);
                            saida.writeObject("Livro cadastrado com sucesso.");
                            break;
                        case "ALUGAR":
                            String tituloAlugar = (String) entrada.readObject();
                            String respostaAluguel = alugarLivro(tituloAlugar);
                            saida.writeObject(respostaAluguel);
                            break;
                        case "DEVOLVER":
                            String tituloDevolver = (String) entrada.readObject();
                            String respostaDevolucao = devolverLivro(tituloDevolver);
                            saida.writeObject(respostaDevolucao);
                            break;
                        default:
                            System.out.println("Operação desconhecida: " + operacao);
                            break;
                    }
                    salvarLivrosNoArquivo(livros);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao processar requisição: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Erro ao fechar o socket: " + e.getMessage());
                }
            }
        }

        private void cadastrarLivro(Livro novoLivro) {
            livros.add(novoLivro);
            System.out.println("Novo livro cadastrado: " + novoLivro);
        }

        private String alugarLivro(String titulo) {
            for (Livro livro : livros) {
                if (livro.getNome().equals(titulo)) {
                    if (livro.getNumExemplares() > 0) {
                        livro.setNumExemplares(livro.getNumExemplares() - 1);
                        return "Livro alugado com sucesso.";
                    } else {
                        return "Não há exemplares disponíveis para este livro.";
                    }
                }
            }
            return "Livro não encontrado.";
        }

        private String devolverLivro(String titulo) {
            for (Livro livro : livros) {
                if (livro.getNome().equals(titulo)) {
                    livro.setNumExemplares(livro.getNumExemplares() + 1);
                    return "Livro devolvido com sucesso.";
                }
            }
            return "Livro não encontrado.";
        }
    }
}