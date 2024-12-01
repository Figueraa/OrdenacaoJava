package visualization;

import java.io.FileWriter;
import java.io.IOException;

public class DataExporter {
    public static void exportToCSV(String fileName, String nomeAlgoritmo, int tamanho, long duracaoNano) {
        // Adicionar os dados ao arquivo CSV
        try (FileWriter writer = new FileWriter(System.getProperty("user.home") + "/Desktop/resultados.csv", true)) {  // 'true' para não sobrescrever o arquivo
            writer.write(String.format("%d,%s,%d\n", tamanho, nomeAlgoritmo, duracaoNano));
            System.out.println("Dados escritos no arquivo resultados.csv");
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
