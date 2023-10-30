import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;

public class main {
    public static void main(String[] args) {
        List<Requisito> requisitos = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        // Carregando requisitos a partir de um arquivo CSV (se existir)
        Requisito.carregarRequisitos(requisitos);

        boolean loop = true;
        while (loop) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Visualizar requisitos");
            System.out.println("2 - Adicionar requisito não conforme");
            System.out.println("3 - Editar requisito não conforme");
            System.out.println("4 - Sair");
            System.out.print("Opção: ");
            int op = scanner.nextInt();

            switch (op) {
                case 1:
                    Requisito.visualizarRequisitos(requisitos);
                    break;
                case 2:
                    Requisito.adicionarRequisito(scanner, requisitos);
                    break;
                case 3:
                    Requisito.editarRequisito(scanner, requisitos);
                    break;
                case 4:
                    Requisito.salvarRequisitos(requisitos);
                    System.out.println("Saindo do programa. Requisitos salvos.");
                    loop = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
