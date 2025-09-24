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
        Map<Integer, Double> result = new HashMap<>();
        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                int expo = this.exponents[i] + other.exponents[j];
                double coeff = this.coefficients[i] * other.coefficients[j];
                result.put(expo, result.getOrDefault(expo, 0.0) + coeff);
            }
        }

        // 转换成数组
        int size = result.size();
        double[] newCoeffs = new double[size];
        int[] newExps = new int[size];
        int idx = 0;
        for (int key : result.keySet()) {
            newExps[idx] = key;
            newCoeffs[idx] = result.get(key);
            idx++;
        }

        return new Polynomial(newCoeffs, newExps);
    }

    // === 保存到文件 ===
    public void saveToFile(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write(toString());
        bw.close();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < coefficients.length; i++) {
            double c = coefficients[i];
            int e = exponents[i];
            if (i > 0 && c >= 0) sb.append("+");
            if (e == 0) sb.append(c);
            else if (e == 1) sb.append(c + "x");
            else sb.append(c + "x" + e);
        }
        return sb.toString();
    }
}
