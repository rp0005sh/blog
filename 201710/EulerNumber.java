import java.math.BigInteger;
import java.util.HashMap;
/**
 * オイラー数を求めるプログラム
 */
public class Main {

    public static void main(String[] srg) {
        // 0番目から、50番目までのオイラー数を表示
        for (int i = 0; i <= 50; i++)
            System.out.println("E[" + i + "] = " + E(i));
    }

    /**
     * オイラー数の計算
     * @param n 第n番目
     * @return 第n番目のオイラー数を返す
     */
    static BigInteger E(int n) {
        if (n % 2 == 1)  return BigInteger.ZERO;// 奇数番目のときは0
        else if (n == 0) return BigInteger.ONE; // 初期値(0番目)は1

        // 計算開始 ∑ [k=0,n-1] nCi Ei
        BigInteger result,sum = BigInteger.ZERO;
        for (int i = 0; i <= n - 2; i += 2)
            sum = sum.add(C(n, i).multiply(E(i)));

        // E[n] = - ∑ [k=0,n-1] nCi Ei
        result = sum.multiply(BigInteger.valueOf(-1));
        return result;
    }

    /**
     * コンビネーション(nCr)を定義式で計算
     * @param n コンビネーション記号の左側
     * @param r コンビネーション記号の右側
     * @return nCrの計算結果
     */
    static BigInteger C(int n, int r) {
        return (fac(n).divide(fac(n - r))).divide(fac(r));
    }

    /**
     * 階乗(Factorial)の計算
     * @param n 計算対象
     * @return val n!の計算結果
     */
    static BigInteger fac(int n) {
        BigInteger val = BigInteger.ONE;
        for (int i = n; 0 < i; i--) val = val.multiply(BigInteger.valueOf(i));
        return val;
    }
}
