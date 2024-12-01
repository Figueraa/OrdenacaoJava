package analysis; 
import java.util.Random;

import visualization.DataExporter;

public class PerformanceTest {
    public static void main(String[] args) {
        int[] tamanhos = {100, 1000, 10000, 50000, 100000}; // Tamanhos para os testes
        for (int tamanho : tamanhos) {
            // Gerar o array aleatório
            int[] arr = ArrayGenerator.gerarArrayAleatorio(tamanho);

            // Testar os algoritmos de ordenação
            System.out.println("Testando para tamanho: " + tamanho);
            testarAlgoritmo("Bubble Sort", arr.clone(), new BubbleSort());
            testarAlgoritmo("Selection Sort", arr.clone(), new SelectionSort());
            testarAlgoritmo("Insertion Sort", arr.clone(), new InsertionSort());
            testarAlgoritmo("Merge Sort", arr.clone(), new MergeSort());
            testarAlgoritmo("Quick Sort", arr.clone(), new QuickSort());
            testarAlgoritmo("Heap Sort", arr.clone(), new HeapSort());
            System.out.println("----------------------------------------------");
        }
    }

    public static void testarAlgoritmo(String nomeAlgoritmo, int[] arr, Ordenador algoritmo) {
        long tempoExecucao = Timer.medirTempoExecucao(arr, algoritmo);
        long inicio = System.nanoTime();
        long fim = System.nanoTime();
        long duracaoNano = fim - inicio;
        
        // Exibir o tempo de execução
        System.out.println(nomeAlgoritmo + ": " + tempoExecucao + " nanosegundos");
        DataExporter.exportToCSV("resultados.csv", nomeAlgoritmo, arr.length, duracaoNano);

    }
}

class ArrayGenerator {
    // Gera um array de inteiros aleatório com um determinado tamanho
    public static int[] gerarArrayAleatorio(int tamanho) {
        int[] arr = new int[tamanho];
        Random rand = new Random();
        for (int i = 0; i < tamanho; i++) {
            arr[i] = rand.nextInt(100000); // Valor aleatório entre 0 e 100000
        }
        return arr;
    }
}

interface Ordenador {
    void ordenar(int[] arr);
}

class Timer {
    public static long medirTempoExecucao(int[] arr, Ordenador algoritmo) {
        long inicio = System.nanoTime();
        algoritmo.ordenar(arr);
        long fim = System.nanoTime();
        return fim - inicio; // Retorna o tempo em nanosegundos
    }
}

class BubbleSort implements Ordenador {
    @Override
    public void ordenar(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}

class SelectionSort implements Ordenador {
    @Override
    public void ordenar(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }
}

class InsertionSort implements Ordenador {
    @Override
    public void ordenar(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int chave = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > chave) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = chave;
        }
    }
}

class MergeSort implements Ordenador {
    @Override
    public void ordenar(int[] arr) {
        if (arr.length < 2) return;
        int meio = arr.length / 2;
        int[] left = new int[meio];
        int[] right = new int[arr.length - meio];
        System.arraycopy(arr, 0, left, 0, meio);
        System.arraycopy(arr, meio, right, 0, arr.length - meio);
        
        ordenar(left);
        ordenar(right);
        
        merge(arr, left, right);
    }
    
    private void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }
        while (i < left.length) {
            arr[k++] = left[i++];
        }
        while (j < right.length) {
            arr[k++] = right[j++];
        }
    }
}

class QuickSort implements Ordenador {
    @Override
    public void ordenar(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }
    
    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}

class HeapSort implements Ordenador {
    @Override
    public void ordenar(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }
    
    private void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }
        
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest);
        }
    }
}
