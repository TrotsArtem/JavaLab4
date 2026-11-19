import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    // відкриття вхідного файлу (повторний запит, поки файл не знайдено)
    static BufferedReader openInput(Scanner in) {
        BufferedReader reader = null;
        while (reader == null) {
            System.out.print("Введіть назву вхідного файлу: ");
            String fileName = in.nextLine().trim();
            try {
                reader = new BufferedReader(new FileReader(fileName));
            } catch (IOException ex) {
                System.out.println("Файл не знайдено. Спробуйте ще раз.");
            }
        }
        return reader;
    }

    // відкриття вихідного файлу: створення, перезапис або дописування
    static BufferedWriter openOutput(Scanner in) {
        BufferedWriter writer = null;
        while (writer == null) {
            System.out.print("Введіть назву вихідного файлу: ");
            String fileName = in.nextLine().trim();
            File f = new File(fileName);
            boolean append = false;
            if (!f.exists()) {
                System.out.print("Файлу не існує. Створити? (y/n): ");
                if (!in.nextLine().trim().equalsIgnoreCase("y"))
                    continue;
            } else {
                System.out.print("Файл існує. Перезаписати (r) чи дописати в кінець (a)? ");
                String answer = in.nextLine().trim();
                if (answer.equalsIgnoreCase("a"))
                    append = true;
                else if (!answer.equalsIgnoreCase("r")) {
                    System.out.println("Невірний вибір.");
                    continue;
                }
            }
            try {
                writer = new BufferedWriter(new FileWriter(f, append));
            } catch (IOException ex) {
                System.out.println("Не вдалося відкрити файл для запису.");
            }
        }
        return writer;
    }

    // Завдання 1: вилучити повтори рядків і відсортувати їх за алфавітом
    static void task1(Scanner in) throws IOException {
        BufferedReader reader = openInput(in);

        // колекція для збереження рядків, прочитаних з файлу
        ArrayList<String> lines = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();

        // вилучення повторень рядків
        int i = 0;
        while (i < lines.size()) {
            if (lines.indexOf(lines.get(i)) != i)
                lines.remove(i);
            else
                i++;
        }

        // сортування рядків в алфавітному порядку
        Collections.sort(lines);

        BufferedWriter writer = openOutput(in);
        for (i = 0; i < lines.size(); i++) {
            writer.write(lines.get(i));
            writer.newLine();
        }
        writer.close();
        System.out.println("Готово");
    }

    // Завдання 2: видалити зайві пропуски
    static void task2(Scanner in) throws IOException {
        BufferedReader reader = openInput(in);
        BufferedWriter writer = openOutput(in);

        String line;
        while ((line = reader.readLine()) != null) {
            // trim() прибирає пропуски на початку і в кінці,
            // replaceAll стискає кілька пропусків/табуляцій до одного пробілу;
            // порожні рядки залишаються порожніми
            line = line.trim().replaceAll("[ \\t]+", " ");
            writer.write(line);
            writer.newLine();
        }
        reader.close();
        writer.close();
        System.out.println("Готово");
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean run = true;
        while (run) {
            System.out.println();
            System.out.println("===== МЕНЮ =====");
            System.out.println("1 - Завдання 1 (унікальні рядки, сортування)");
            System.out.println("2 - Завдання 2 (видалення зайвих пропусків)");
            System.out.println("0 - Вихід");
            System.out.print("Ваш вибір: ");
            String choice = in.nextLine().trim();
            try {
                switch (choice) {
                    case "1":
                        task1(in);
                        break;
                    case "2":
                        task2(in);
                        break;
                    case "0":
                        run = false;
                        break;
                    default:
                        System.out.println("Невірний пункт меню.");
                }
            } catch (IOException ex) {
                System.out.println("Помилка вводу-виводу: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Помилка!!!");
            }
        }
        in.close();
        System.out.println("Програму завершено.");
    }
}
