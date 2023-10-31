import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Conformidade {
    private String descricao;
    private String classificacao;
    private Date dataRevisao;
    private String status;
    private Date dataAlteracaoStatus;

    public Conformidade(String descricao, String classificacao, Date dataRevisao, String status) {
        this.descricao = descricao;
        this.classificacao = classificacao;
        this.dataRevisao = dataRevisao;
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public Date getDataRevisao() {
        return dataRevisao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public Date getDataAlteracaoStatus() {
        return dataAlteracaoStatus;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public void setDataRevisao(Date dataRevisao) {
        this.dataRevisao = dataRevisao;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setDataAlteracaoStatus(Date dataAlteracaoStatus) {
        this.dataAlteracaoStatus = dataAlteracaoStatus;
    }

    public static void carregarConformidades(List<Conformidade> conformidades) {
        try (BufferedReader reader = new BufferedReader(new FileReader("conformidades.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String descricao = parts[0];
                    String classificacao = parts[1];
                    Date dataRevisao = new SimpleDateFormat("yyyy-MM-dd").parse(parts[2]);
                    String status = parts[3];
                    conformidades.add(new Conformidade(descricao, classificacao, dataRevisao, status));
                }
            }
        } catch (FileNotFoundException e) {
            // Arquivo não existe (não é um erro)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void salvarConformidades(List<Conformidade> requisitos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("conformidades.csv"))) {
            for (Conformidade requisito : requisitos) {
                String linha = requisito.getDescricao() + "," + requisito.getClassificacao() + "," +
                        new SimpleDateFormat("yyyy-MM-dd").format(requisito.getDataRevisao());
                writer.println(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void visualizarConformidades(List<Conformidade> requisitos) {
        for (Conformidade requisito : requisitos) {
            System.out.println("Descrição: " + requisito.getDescricao());
            System.out.println("Classificação: " + requisito.getClassificacao());
            System.out.println("Data até próxima revisão: " + new SimpleDateFormat("dd/MM/yyyy").format(requisito.getDataRevisao()));
            System.out.println();
        }
    }

    public static void adicionarConformidade(Scanner scanner, List<Conformidade> conformidades) {
        Scanner teclado = new Scanner(System.in);
        scanner.nextLine(); // Consumir a nova linha pendente
        int conf;
        int taxa = 0;
        int total = 0;
        for (int i = 0; i <= 14; i++) {
            //System.out.println(pergunta do checklist);
            System.out.println("O Resultado esperado está conforme? (1 para sim, 2 para não)");
            conf = Integer.parseInt(teclado.nextLine());
            switch (conf) {
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
                    String status = "Em análise";
                    try {
                        Date dataRevisao = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
                        conformidades.add(new Conformidade(descricao, classificacao, dataRevisao, status));
                        System.out.println("Requisito não conforme adicionado com sucesso.");
                    } catch (ParseException e) {
                        System.out.println("Formato de data inválido. O requisito não será adicionado.");
                    }
            }
            total += 1;
        }
        System.out.println("A taxa de aderência é :" + ((taxa / total)) / 100 + "%");
    }

    public static void editarConformidade(Scanner scanner, List<Conformidade> conformidades) {
        System.out.print("Informe a descrição do requisito a ser editado: ");
        String descricao = scanner.next();
        boolean encontrado = false;
        for (Conformidade conformidade : conformidades) {
            if (conformidade.getDescricao().equals(descricao)) {
                encontrado = true;
                scanner.nextLine(); // Consumir a nova linha pendente
                System.out.print("Nova descrição (ou pressione Enter para manter a atual): ");
                String novaDescricao = scanner.nextLine();
                if (!novaDescricao.isEmpty()) {
                    conformidade.setDescricao(novaDescricao);
                }
                System.out.print("Nova classificação (Alta/Média/Baixa ou pressione Enter para manter a atual): ");
                String novaClassificacao = scanner.nextLine();
                if (!novaClassificacao.isEmpty()) {
                    conformidade.setClassificacao(novaClassificacao);
                }
                System.out.print("Nova data até próxima revisão (formato dd/MM/yyyy ou pressione Enter para manter a atual): ");
                String novaData = scanner.nextLine();
                if (!novaData.isEmpty()) {
                    try {
                        Date dataRevisao = new SimpleDateFormat("dd/MM/yyyy").parse(novaData);
                        conformidade.setDataRevisao(dataRevisao);
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
