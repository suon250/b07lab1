public class Polynomial {
    private double[] coefficients;

    // 无参构造函数，表示零多项式
    public Polynomial() {
        this.coefficients = new double[]{0};
    }

    // 带参构造函数，传入系数数组
    public Polynomial(double[] coefficients) {
        // 拷贝一份，避免外部数组修改影响
        this.coefficients = new double[coefficients.length];
        System.arraycopy(coefficients, 0, this.coefficients, 0, coefficients.length);
    }

    // 多项式相加
    public Polynomial add(Polynomial other) {
        int maxLen = Math.max(this.coefficients.length, other.coefficients.length);
        double[] result = new double[maxLen];

        for (int i = 0; i < maxLen; i++) {
            double a = (i < this.coefficients.length) ? this.coefficients[i] : 0;
            double b = (i < other.coefficients.length) ? other.coefficients[i] : 0;
            result[i] = a + b;
        }

        return new Polynomial(result);
    }

    // 计算在某个 x 值下的多项式值
    public double evaluate(double x) {
        double sum = 0;
        for (int i = 0; i < coefficients.length; i++) {
            sum += coefficients[i] * Math.pow(x, i);
        }
        return sum;
    }

    // 判断某个值是否为多项式的根
    public boolean hasRoot(double x) {
        return Math.abs(evaluate(x)) < 1e-6; // 允许一点点误差
    }
}
