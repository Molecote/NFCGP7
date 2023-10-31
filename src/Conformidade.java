import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;

class Conformidade {
    int id = 0;
    private String descricao;
    private String classificacao = "N/A";
    private LocalDate dataRevisao;
    private ConformidadeStatusEnum status;
    private LocalDate dataAlteracaoStatus;

    public Conformidade(String descricao, String classificacao, LocalDate dataRevisao, ConformidadeStatusEnum status, LocalDate dataAlteracaoStatus) {
        this.id += 1;
        this.descricao = descricao;
        this.classificacao = classificacao;
        this.dataRevisao = dataRevisao;
        this.status = status;
        this.dataAlteracaoStatus = dataAlteracaoStatus;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public LocalDate getDataRevisao() {
        return dataRevisao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ConformidadeStatusEnum getStatus() {
        return status;
    }

    public LocalDate getDataAlteracaoStatus() {
        return dataAlteracaoStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public void setDataRevisao(LocalDate dataRevisao) {
        this.dataRevisao = dataRevisao;
    }

    public void setStatus(ConformidadeStatusEnum status) {
        this.status = status;
    }

    public void setDataAlteracaoStatus(LocalDate dataAlteracaoStatus) {
        this.dataAlteracaoStatus = dataAlteracaoStatus;
    }


    public static void carregarConformidades(ArrayList<Conformidade> conformidades) {
        try (BufferedReader reader = new BufferedReader(new FileReader("conformidades.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String descricao = parts[0];
                    String classificacao = parts[1];
                    LocalDate dataRevisao = LocalDate.parse(parts[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String statusString = parts[3];
                    ConformidadeStatusEnum statusEnum = ConformidadeStatusEnum.valueOf(statusString);
                    LocalDate dataAlteracaoStatus = LocalDate.parse(parts[4], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    conformidades.add(new Conformidade(descricao, classificacao, dataRevisao, statusEnum, dataAlteracaoStatus));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void salvarConformidades(ArrayList<Conformidade> conformidades) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("conformidades.csv"))) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (Conformidade conformidade : conformidades) {
                String linha = conformidade.getDescricao() + "," + conformidade.getClassificacao() + "," +
                        dateFormatter.format(conformidade.getDataRevisao()) + "," +
                        conformidade.getStatus() + "," +
                        dateFormatter.format(conformidade.getDataAlteracaoStatus());
                writer.println(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void visualizarConformidades(ArrayList<Conformidade> conformidades) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Conformidade conformidade : conformidades) {
            System.out.println("Descrição: " + conformidade.getDescricao());
            System.out.println("Classificação: " + conformidade.getClassificacao());
            System.out.println("Data até próxima revisão: " + dateFormatter.format(conformidade.getDataRevisao()));
            System.out.println();
        }
    }

    public static void adicionarConformidade(ArrayList<Conformidade> conformidades) {
        Scanner teclado = new Scanner(System.in);
        int conf;
        int taxa = 0;
        int total = 0;
        ArrayList<String> perguntas = Checklist.carregarPerguntas(new ArrayList<>());
        if (perguntas.isEmpty()) {
            System.out.println("O arquivo de checklist está vazio. Adicione perguntas primeiro.");
            return;
        }
        for (String pergunta : perguntas) {
            System.out.println("Pergunta do checklist: ");
            System.out.println(pergunta);
            System.out.println("O Resultado esperado está conforme? (1 para sim, 2 para não)");
            conf = Integer.parseInt(teclado.nextLine());
            switch (conf) {
                case 1:
                    System.out.println("Ok! Arquivo adicionado a taxa de aderência.");
                    taxa += 1;
                    break;
                case 2:
                    System.out.print("Descrição do requisito não conforme: ");
                    String descricao = teclado.nextLine();
                    System.out.print("Classificação do requisito não conforme (Comlexa - 5 dias/Mediana - 3 dias/Simples - 1 dia): ");
                    String classificacao = teclado.nextLine();
                    System.out.print("Data até próxima revisão (formato yyyy-MM-dd): ");
                    ConformidadeStatusEnum status = ConformidadeStatusEnum.ANALISE;
                    try {
                        LocalDate dataRevisao = LocalDate.parse(teclado.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        conformidades.add(new Conformidade(descricao, classificacao, dataRevisao, status, LocalDate.now()));
                        System.out.println("Requisito não conforme adicionado com sucesso.");
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato de data inválido. O requisito não será adicionado.");
                    }
            }
            total += 1;
        }
        System.out.println("A taxa de aderência é: " + ((double) taxa / total) * 100 + "%");
    }

    public static void editarConformidade(ArrayList<Conformidade> conformidades) {
        Scanner teclado = new Scanner(System.in);
        System.out.print("Informe a identificação (Id) da conformidade a ser editada: ");
        int id = teclado.nextInt();
        boolean encontrado = false;
        for (Conformidade conformidade : conformidades) {
            if (conformidade.getId() == id) {
                encontrado = true;
                teclado.nextLine();
                System.out.print("Nova descrição (ou pressione Enter para manter a atual): ");
                String novaDescricao = teclado.nextLine();
                if (!novaDescricao.isEmpty()) {
                    conformidade.setDescricao(novaDescricao);
                }

                System.out.print("Nova classificação de não conformidade (Alta/Média/Baixa ou pressione Enter para manter a atual): ");
                String novaClassificacao = teclado.nextLine();
                if (!novaClassificacao.isEmpty()) {
                    conformidade.setClassificacao(novaClassificacao);
                }

                System.out.print("Novo status (0 = CONFORME, 1 = NAO_CONFORME, 2 = EM_ESCALA ou Enter para manter o status atual): ");
                int novoStatusInt = teclado.nextInt();
                ConformidadeStatusEnum novoStatus;
                if (novoStatusInt >= 0) {

                    switch (novoStatusInt) {
                        case 0:
                            novoStatus = ConformidadeStatusEnum.CONFORME;
                            break;
                        case 1:
                            novoStatus = ConformidadeStatusEnum.NAO_CONFORME;
                            break;
                        case 2:
                            novoStatus = ConformidadeStatusEnum.EM_ESCALA;
                            break;
                        default:
                            novoStatus = ConformidadeStatusEnum.ANALISE;
                            break;
                    }
                    conformidade.setStatus(novoStatus);
                    conformidade.setDataAlteracaoStatus(LocalDate.now());
                }

                System.out.print("Nova data até próxima revisão (formato yyyy-MM-dd ou pressione Enter para manter a atual): ");
                String novaData = teclado.nextLine();
                if (!novaData.isEmpty()) {
                    try {
                        LocalDate dataRevisao = LocalDate.parse(novaData, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        conformidade.setDataRevisao(dataRevisao);
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato de data inválido. Os dados não foram atualizados.");
                    }
                }
                System.out.println("Conformidade editado com sucesso.");
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Conformidade não encontrado.");
        }
    }
}
