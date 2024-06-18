package Servidor;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Cliente {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 33333;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Cliente conectado ao servidor.");

            while (true) {
                mostrarMenu();
                int escolha = lerEscolha(scanner);

                switch (escolha) {
                    case 1:
                        listarLivros(saida, entrada);
                        break;
                    case 2:
                        cadastrarLivro(saida, entrada, scanner);
                        break;
                    case 3:
                        alugarLivro(saida, entrada, scanner);
                        break;
                    case 4:
                        devolverLivro(saida, entrada, scanner);
                        break;
                    case 5:
                        System.out.println("Encerrando cliente.");
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro na conexão com o servidor: " + e.getMessage());
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. Listar livros");
        System.out.println("2. Cadastrar novo livro");
        System.out.println("3. Alugar livro");
        System.out.println("4. Devolver livro");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerEscolha(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Por favor, insira um número válido: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void listarLivros(ObjectOutputStream saida, ObjectInputStream entrada) throws IOException, ClassNotFoundException {
        saida.writeObject("LISTAR");
        List<Livro> livros = (List<Livro>) entrada.readObject();
        System.out.println("Livros disponíveis:");
        for (Livro livro : livros) {
            System.out.println(livro);
        }
    }

    private static void cadastrarLivro(ObjectOutputStream saida, ObjectInputStream entrada, Scanner scanner) throws IOException, ClassNotFoundException {
        scanner.nextLine(); // Limpar o buffer
        System.out.print("Digite o nome do livro: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o autor do livro: ");
        String autor = scanner.nextLine();
        System.out.print("Digite o gênero do livro: ");
        String genero = scanner.nextLine();
        System.out.print("Digite o número de exemplares do livro: ");
        int numExemplares = scanner.nextInt();

        Livro novoLivro = new Livro(autor, nome, genero, numExemplares);
        saida.writeObject("CADASTRAR");
        saida.writeObject(novoLivro);
        String resposta = (String) entrada.readObject();
        System.out.println(resposta);
    }

    private static void alugarLivro(ObjectOutputStream saida, ObjectInputStream entrada, Scanner scanner) throws IOException, ClassNotFoundException {
        scanner.nextLine(); // Limpar o buffer
        System.out.print("Digite o nome do livro para alugar: ");
        String nome = scanner.nextLine();
        saida.writeObject("ALUGAR");
        saida.writeObject(nome);
        String resposta = (String) entrada.readObject();
        System.out.println(resposta);
    }

    private static void devolverLivro(ObjectOutputStream saida, ObjectInputStream entrada, Scanner scanner) throws IOException, ClassNotFoundException {
        scanner.nextLine(); // Limpar o buffer
        System.out.print("Digite o nome do livro para devolver: ");
        String nome = scanner.nextLine();
        saida.writeObject("DEVOLVER");
        saida.writeObject(nome);
        String resposta = (String) entrada.readObject();
        System.out.println(resposta);
    }
}