import java.io.*;

public class Driver {
    public static void main(String[] args) throws Exception {
        // 手动构造两个多项式
        Polynomial p1 = new Polynomial(new double[]{6, -2, 5}, new int[]{0, 1, 3}); // 6 -2x +5x^3
        Polynomial p2 = new Polynomial(new double[]{1, 1}, new int[]{0, 1});        // 1 + x

        Polynomial result = p1.multiply(p2);
        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);
        System.out.println("p1 * p2 = " + result);

        // 测试文件读取
        Polynomial p3 = new Polynomial(new File("input.txt"));  // 假设文件内容：5-3x2+7x8
        System.out.println("From file: " + p3);
        p3.saveToFile("output.txt");
    }
}

