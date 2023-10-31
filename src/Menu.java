import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Menu {
    public static void exibirMenu_Geral() {
        Scanner teclado = new Scanner(System.in);
        int op;
        do {
            System.out.println("Escolha um dos menus: ");
            System.out.println("1. Menu de Checklist");
            System.out.println("2. Menu de Não Conformidade");
            System.out.println("3. Encerrar");
            op = teclado.nextInt();
            switch (op) {
                case 1:
                    exibirMenu_Checklist();
                    break;
                case 2:
                    exibirMenu_Conformidade();
                    break;
                case 3:
                    System.out.println("Saindo do programa.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (op != 3);


    }


    public static void exibirMenu_Checklist() {
        ArrayList<String> perguntas = new ArrayList<>();
        Checklist.carregarPerguntas(perguntas);
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("Menu:");
            System.out.println("1. Adicionar Pergunta");
            System.out.println("2. Salvar Perguntas em um Arquivo");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    Checklist.adicionarPergunta(perguntas);
                    break;
                case 2:
                    Checklist.salvarPerguntas(perguntas);
                    break;
                case 3:
                    System.out.println("Saindo do programa.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 3);
    }

    public static void exibirMenu_Conformidade() {
        List<Conformidade> conformidades = new ArrayList<>();
        Conformidade.carregarConformidades(conformidades);
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("Menu:");
            System.out.println("1. Adicionar Não Conformidade");
            System.out.println("2. Editar Não Conformidades de um Arquivo");
            System.out.println("3. Salvar Não Conformidades em um Arquivo");
            System.out.println("4. Visualizar Não Conformidades de um Arquivo");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    Conformidade.adicionarConformidade(conformidades);
                    break;
                case 2:
                    Conformidade.editarConformidade(conformidades);
                    break;
                case 3:
                    Conformidade.salvarConformidades(conformidades);
                    break;
                case 4:
                    Conformidade.visualizarConformidades(conformidades);
                    break;
                case 5:
                    System.out.println("Saindo do programa.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 5);
    }

}
