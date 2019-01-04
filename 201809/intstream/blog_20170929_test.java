/** 
 * Stream<Integer>�ɕϊ�����Comparator���g���č~���\�[�g
 */
class Main1 {
    public static void main(String[] args) {
        // �v���J�n
        long start = System.currentTimeMillis();

        int[] arr = java.util.stream.IntStream.range(0, 50000000)
            .boxed().sorted(java.util.Comparator.reverseOrder()).mapToInt(Integer::valueOf).toArray();

        // �v������
        long end = System.currentTimeMillis();
        System.out.println("������������:" + (end - start) + "[msec]");
    }
}

/** 
 * IntStream�̂܂܍~���\�[�g
 */
class Main2 {
    public static void main(String[] args) {
        // �v���J�n
        long start = System.currentTimeMillis();

        int[] arr = java.util.stream.IntStream.range(0, 50000000)
            .map(n -> -n).sorted().map(n -> -n).toArray();

        // �v������
        long end = System.currentTimeMillis();
        System.out.println("������������:" + (end - start) + "[msec]");
    }
}