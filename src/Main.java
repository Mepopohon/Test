import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final Map<Character, Integer> romanToInt = new HashMap<>();
    private static final Map<Integer, String> intToRoman = new HashMap<>();

    static {
        romanToInt.put('I', 1);
        romanToInt.put('V', 5);
        romanToInt.put('X', 10);

        intToRoman.put(1, "I");
        intToRoman.put(4, "IV");
        intToRoman.put(5, "V");
        intToRoman.put(9, "IX");
        intToRoman.put(10, "X");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String input = sc.nextLine();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static String calc(String input) throws Exception {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new Exception("Некорректный формат ввода");
        }

        String num1 = parts[0];
        String num2 = parts[2];
        char operation = parts[1].charAt(0);

        boolean isRoman = isRoman(num1) && isRoman(num2);
        boolean isArabic = isArabic(num1) && isArabic(num2);

        if (!(isRoman || isArabic)) {
            throw new Exception("Числа должны быть либо оба арабскими, либо оба римскими");
        }
        int a;
        if (isRoman)
            a = romanToInt(num1);
        else
            a = Integer.parseInt(num1);

        int b;
        if (isRoman)
            b = romanToInt(num2);
        else
            b = Integer.parseInt(num2);

        if (a < 1 || a > 10 || b < 1 || b > 10) {
            throw new Exception("Числа должны быть от 1 до 10 включительно");
        }
        int result = switch (operation) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> throw new Exception("Некорректная операция");
        };
        if (isRoman) {
            if (result < 1) {
                throw new Exception("Результат римского числа меньше единицы");
            }
            return intToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    private static boolean isRoman(String s) {
        return s.matches("[IVX]+");
    }

    private static boolean isArabic(String s) {
        return s.matches("\\d+");
    }

    private static int romanToInt(String s) {
        int sum = 0;
        int prevValue = 0;
        for (char c : s.toCharArray()) {
            int value = romanToInt.get(c);
            sum += value;
            if (prevValue < value) {
                sum -= 2 * prevValue;
            }
            prevValue = value;
        }
        return sum;
    }

    private static String intToRoman(int num) {
        StringBuilder str = new StringBuilder();
        int[] values = {10, 9, 5, 4, 1};
        for (int value : values) {
            while (num >= value) {
                str.append(intToRoman.get(value));
                num -= value;
            }
        }
        return str.toString();
    }
}
