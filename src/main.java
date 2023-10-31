import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;

public class main {
    public static void main(String[] args) {
        List<Conformidade> conformidades = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        // Carregando conformidades a partir de um arquivo CSV (se existir)
        Conformidade.carregarConformidades(conformidades);

        boolean loop = true;
        while (loop) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Visualizar requisitos");
            System.out.println("2 - Adicionar requisito não conforme");
            System.out.println("3 - Editar requisito não conforme");
            System.out.println("4 - Sair");
            System.out.print("Opção: ");
            int op = scanner.nextInt();

            switch (op){
                case 1:
                    Conformidade.visualizarConformidades(conformidades);
                    break;
                case 2:
                    Conformidade.adicionarConformidade(scanner, conformidades);
                    break;
                case 3:
                    Conformidade.editarConformidade(scanner, conformidades);
                    break;
                case 4:
                    Conformidade.salvarConformidades(conformidades);
                    System.out.println("Saindo do programa. Requisitos salvos.");
                    loop = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}


