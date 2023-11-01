import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Checklist {
    private ArrayList<String> perguntas;

    public ArrayList<String> getPerguntas() {
        return perguntas;
    }

    public void setPerguntas(ArrayList<String> perguntas) {
        this.perguntas = perguntas;
    }

    public static void salvarPerguntas(ArrayList<String> perguntas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("checklist.csv"))) {
            for (String pergunta : perguntas) {
                writer.println(pergunta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> carregarPerguntas(ArrayList<String> perguntas) {
        try (BufferedReader reader = new BufferedReader(new FileReader("checklist.csv"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                perguntas.add(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return perguntas;
    }

    public static void adicionarPergunta(ArrayList<String> perguntas) {
        Scanner teclado = new Scanner(System.in);
        String pergunta;

        do {
            System.out.print("Digite uma pergunta (ou digite 'sair' para parar): ");
            pergunta = teclado.nextLine();
            if (!pergunta.equalsIgnoreCase("sair")) {
                perguntas.add(pergunta);
            }
        } while (!pergunta.equalsIgnoreCase("sair"));
    }
    public static void ApagarCSV(){
        try {
            FileWriter fileWriter = new FileWriter("checklist.csv", false);
            fileWriter.close();
            System.out.println("O arquivo CSV foi esvaziado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
