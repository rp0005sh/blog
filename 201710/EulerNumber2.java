    /**
     * オイラー数の計算
     * @param n 第n番目
     * @param histry 計算量削減用インスタンス
     * @return 第n番目のオイラー数を返す
     */
    static BigInteger E(int n, HashMap<Integer,BigInteger> histry) {
        histry = (histry != null) ? histry : new HashMap<>();
        if (n % 2 == 1)  return BigInteger.ZERO;// 奇数番目のときは0
        else if (n == 0) return BigInteger.ONE; // 初期値(0番目)は1
        else if (histry.get(n) != null) return histry.get(n); // すでに計算済みの場合

        // 計算開始 ∑ [k=0,n-1] nCi Ei
        BigInteger result,sum = BigInteger.ZERO;
        for (int i = 0; i <= n - 2; i += 2)
            sum = sum.add(C(n, i).multiply(E(i, histry)));

        // E[n] = - ∑ [k=0,n-1] nCi Ei
        result = sum.multiply(BigInteger.valueOf(-1));
        histry.put(n, result);// 計算結果を保存
        return result;
    }
