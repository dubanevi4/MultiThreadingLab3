package lt.esdc.multithreading;

public class Demo {
    public static void main(String[] args) {
        System.out.println("=== Демонстрация Part1 ===");
        Part1.main(args);
        System.out.println("\n=== Part1 завершена ===\n");

        System.out.println("=== Демонстрация Part2 ===");
        Part2.main(args);
        System.out.println("\n=== Part2 завершена ===\n");

        System.out.println("=== Демонстрация Part3 ===");
        Part3.main(args);
        System.out.println("\n=== Part3 завершена ===\n");

        System.out.println("=== Демонстрация Part4 ===");
        Part4.main(args);
        System.out.println("\n=== Part4 завершена ===\n");

        System.out.println("=== Демонстрация Part5 ===");
        try {
            Part5.main(args);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        System.out.println("\n=== Part5 завершена ===");
    }
}
