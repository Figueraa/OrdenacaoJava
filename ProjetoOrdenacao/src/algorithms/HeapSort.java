package algorithms;

public class HeapSort {
    public static void sort(int[] arr) {
        int n = arr.length;

        // Construa um heap (max-heap)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Um por um, extraia os elementos do heap
        for (int i = n - 1; i >= 0; i--) {
            // Move o elemento atual para a raiz
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Chama heapify na raiz
            heapify(arr, i, 0);
        }
    }

    private static void heapify(int[] arr, int n, int i) {
        int largest = i; // Raiz
        int left = 2 * i + 1; // Esquerda
        int right = 2 * i + 2; // Direita

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

            // Recursivamente heapify o subárvore afetada
            heapify(arr, n, largest);
        }
    }
}
