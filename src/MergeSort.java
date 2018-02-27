public class MergeSort {

    public static void main(String[] args){
        int[] list = new int[]{
            1,9,3,6,5
        };
        MergeSort merger = new MergeSort();
        merger.mergeSort(list, 0, list.length - 1);
        System.out.print("[");
        for (int i = 0; i < list.length - 1; i++){
            System.out.print(list[i] + ", ");
        }
        System.out.print(list[list.length - 1] + "]");
    }

    public void mergeSort(int[] list, int start, int end){
        if (start < end){
            int middle = (start + end) / 2;
            mergeSort(list, start, middle);
            mergeSort(list, middle + 1, end);
            merge(list, start, middle, end);
        }
    }
    public void merge(int[] list, int start, int middle, int end){
        int sLeft = middle - start + 1;
        int sRight = end - middle;
        int[] left = new int[sLeft];
        int[] right = new int[sRight];
        for (int i = 0; i < sLeft; i++){
            left[i] = list[i +start];
        }
        for (int i = 0; i < sRight; i++){
            right[i] = list[i + middle + 1];
        }
        int i = 0, j = 0;
        for (int k = start; k < end; k++){
            if (i >= left.length){
                list[k] = right[j];
                j++;
            } else if (j >= right.length){
                list[k] = left[i];
                i++;
            } else {
                if (left[i] < right[j]) {
                    list[k] = left[i];
                    i++;
                } else {
                    list[k] = right[j];
                    j++;
                }
            }
        }
    }


}
