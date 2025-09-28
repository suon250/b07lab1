package lab2_2;
import java.io.*;

import java.util.*;

public class Polynomial {
    private double[] coefficients;  // 非零系数
    private int[] exponents;        // 对应指数

    // === 构造函数 1：从系数数组和指数数组直接构造 ===
    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    // === 构造函数 2：从文件读入 ===
    // 文件中假设是一行，比如: "5-3x2+7x8"
    public Polynomial(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine().trim();
        br.close();
        parsePolynomial(line);
    }

    private void parsePolynomial(String poly) {
        // 把 - 替换成 +- 来分割
        poly = poly.replace("-", "+-");
        if (poly.startsWith("+")) poly = poly.substring(1);
        String[] terms = poly.split("\\+");

        List<Double> coeffList = new ArrayList<>();
        List<Integer> expoList = new ArrayList<>();

        for (String t : terms) {
            if (t.isEmpty()) continue;
            double coeff;
            int expo;
            if (t.contains("x")) {
                String[] parts = t.split("x");
                // 系数部分
                if (parts[0].equals("") || parts[0].equals("+")) coeff = 1;
                else if (parts[0].equals("-")) coeff = -1;
                else coeff = Double.parseDouble(parts[0]);
                // 指数部分
                if (parts.length == 1 || parts[1].equals("")) expo = 1;
                else expo = Integer.parseInt(parts[1]);
            } else {
                coeff = Double.parseDouble(t);
                expo = 0;
            }
            coeffList.add(coeff);
            expoList.add(expo);
        }

        this.coefficients = coeffList.stream().mapToDouble(Double::doubleValue).toArray();
        this.exponents = expoList.stream().mapToInt(Integer::intValue).toArray();
    }

    // === 多项式乘法 ===
    public Polynomial multiply(Polynomial other) {
        // 找最大指数
        int maxExp = Arrays.stream(this.exponents).max().orElse(0) +
                     Arrays.stream(other.exponents).max().orElse(0);

        // 用数组存储结果，下标就是指数
        double[] resultCoeffs = new double[maxExp + 1];

        // 累加乘积结果
        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                int expo = this.exponents[i] + other.exponents[j];
                double coeff = this.coefficients[i] * other.coefficients[j];
                resultCoeffs[expo] += coeff;
            }
        }

        // 把非零项提取出来
        List<Double> coeffList = new ArrayList<>();
        List<Integer> expoList = new ArrayList<>();
        for (int e = 0; e <= maxExp; e++) {
            if (resultCoeffs[e] != 0) {
                coeffList.add(resultCoeffs[e]);
                expoList.add(e);
            }
        }

        double[] newCoeffs = coeffList.stream().mapToDouble(Double::doubleValue).toArray();
        int[] newExps = expoList.stream().mapToInt(Integer::intValue).toArray();

        return new Polynomial(newCoeffs, newExps);
    }

    // === 保存到文件 ===
    public void saveToFile(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write(toString());
        bw.close();
    }
}
