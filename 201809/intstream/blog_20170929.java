class Main {
    public static void main(String[] args) {
        int[] arr = {3, 2, 4, -5, -2, 14, 5, 6, 4};
        arr = java.util.Arrays.stream(arr) // �X�g���[�����
            .map(n -> -n) // �������]
            .sorted()     // �����\�[�g
            .map(n -> -n) // �������](���ɖ߂�)
            .toArray();   // �z��

        // ���ʏo��
        System.out.println(java.util.Arrays.toString(arr));
        // ���ʁF[14, 6, 5, 4, 4, 3, 2, -2, -5]�ɂȂ����I�����I
    }
}