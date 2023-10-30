import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;

public class main {
    public static void main(String[] args) {
        List<Requisito> requisitos = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        // Carregando requisitos a partir de um arquivo CSV (se existir)
        carregarRequisitos(requisitos);

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
                    visualizarRequisitos(requisitos);
                    break;
                case 2:
                    adicionarRequisito(scanner, requisitos);
                    break;
                case 3:
                    editarRequisito(scanner, requisitos);
                    break;
                case 4:
                    salvarRequisitos(requisitos);
                    System.out.println("Saindo do programa. Requisitos salvos.");
                    loop = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void carregarRequisitos(List<Requisito> requisitos) {
        try (BufferedReader reader = new BufferedReader(new FileReader("requisitos.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String descricao = parts[0];
                    String classificacao = parts[1];
                    Date dataRevisao = new SimpleDateFormat("yyyy-MM-dd").parse(parts[2]);
                    requisitos.add(new Requisito(descricao, classificacao, dataRevisao));
                }
            }
        } catch (FileNotFoundException e) {
            // Arquivo não existe (não é um erro)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void salvarRequisitos(List<Requisito> requisitos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("requisitos.csv"))) {
            for (Requisito requisito : requisitos) {
                String linha = requisito.getDescricao() + "," + requisito.getClassificacao() + "," +
                        new SimpleDateFormat("yyyy-MM-dd").format(requisito.getDataRevisao());
                writer.println(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void visualizarRequisitos(List<Requisito> requisitos) {
        for (Requisito requisito : requisitos) {
            System.out.println("Descrição: " + requisito.getDescricao());
            System.out.println("Classificação: " + requisito.getClassificacao());
            System.out.println("Data até próxima revisão: " + new SimpleDateFormat("dd/MM/yyyy").format(requisito.getDataRevisao()));
            System.out.println();
        }
    }

    private static void adicionarRequisito(Scanner scanner, List<Requisito> requisitos) {
        Scanner teclado = new Scanner(System.in);
        scanner.nextLine(); // Consumir a nova linha pendente
        int conf;
        double taxa = 0;
        double total = 0;
        for (int i = 0; i <=14; i++){
            //System.out.println(pergunta do checklist);
            System.out.println("O Resultado esperado está conforme? (1 para sim, 2 para não)");
            conf = Integer.parseInt(teclado.nextLine());
            switch (conf){
                case 1:
                    System.out.println("");
                    taxa += 1;
                    break;
                case 2:
                    System.out.print("Descrição do requisito não conforme: ");
                    String descricao = scanner.nextLine();
                    System.out.print("Classificação do requisito não conforme (Alta - 5 dias/Média - 3 dias/Baixa - 1 dia): ");
                    String classificacao = scanner.nextLine();
                    System.out.print("Data até próxima revisão (formato dd/MM/yyyy): ");
                    try {
                        Date dataRevisao = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
                        requisitos.add(new Requisito(descricao, classificacao, dataRevisao));
                        System.out.println("Requisito não conforme adicionado com sucesso.");
                    } catch (ParseException e) {
                        System.out.println("Formato de data inválido. O requisito não será adicionado.");
                    }
            }
            total += 1;
        }
        System.out.println("A taxa de aderência é :" + ((taxa/total))/100 +"%");
    }

    private static void editarRequisito(Scanner scanner, List<Requisito> requisitos) {
        System.out.print("Informe a descrição do requisito a ser editado: ");
        String descricao = scanner.next();
        boolean encontrado = false;
        for (Requisito requisito : requisitos) {
            if (requisito.getDescricao().equals(descricao)) {
                encontrado = true;
                scanner.nextLine(); // Consumir a nova linha pendente
                System.out.print("Nova descrição (ou pressione Enter para manter a atual): ");
                String novaDescricao = scanner.nextLine();
                if (!novaDescricao.isEmpty()) {
                    requisito.setDescricao(novaDescricao);
                }
                System.out.print("Nova classificação (Alta/Média/Baixa ou pressione Enter para manter a atual): ");
                String novaClassificacao = scanner.nextLine();
                if (!novaClassificacao.isEmpty()) {
                    requisito.setClassificacao(novaClassificacao);
                }
                System.out.print("Nova data até próxima revisão (formato dd/MM/yyyy ou pressione Enter para manter a atual): ");
                String novaData = scanner.nextLine();
                if (!novaData.isEmpty()) {
                    try {
                        Date dataRevisao = new SimpleDateFormat("dd/MM/yyyy").parse(novaData);
                        requisito.setDataRevisao(dataRevisao);
                    } catch (ParseException e) {
                        System.out.println("Formato de data inválido. Os dados não foram atualizados.");
                    }
                }
                System.out.println("Requisito editado com sucesso.");
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Requisito não encontrado.");
        }
    }
}


