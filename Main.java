import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        runLevel1();
        runLevel2();
        runLevel3();
    }

    // --- РІВЕНЬ 1 ---
    public static void runLevel1() {
        System.out.println("--- РІВЕНЬ 1: Масив та Інтерполяційний пошук ---");

        // 1. Створення масиву
        Student[] students = createStudentArray();

        // 2. Сортування згідно з правилом (Упорядкований за статтю) 
        Arrays.sort(students, Comparator.comparing(Student::getGender));

        // 3. Виведення масиву (за статтю)
        System.out.println("Вміст масиву (відсортовано за статтю):");
        for (Student s : students) {
            System.out.println(s);
        }

        // 4. Виконання завдання
        
        // ВАЖЛИВО: Інтерполяційний (як і бінарний) пошук вимагає,
        // щоб масив був відсортований за КЛЮЧЕМ ПОШУКУ (номером квитка)[cite: 26, 27].
        // Тому ми ПЕРЕСОРТОВУЄМО масив перед пошуком.
        Arrays.sort(students, Comparator.comparingLong(Student::getLibraryCardNumber));
        System.out.println("\nМасив пересортовано за номером квитка для пошуку:");
        for (Student s : students) {
            System.out.println(s);
        }

        long cardNumberToFind = 10005L;
        taskLevel1(students, cardNumberToFind);
        
        long cardNotFound = 99999L;
        taskLevel1(students, cardNotFound);
    }

    // Метод для завдання 1-го рівня
    public static void taskLevel1(Student[] students, long cardNumberToFind) {
        // Шукаємо студента за номером квитка 
        Student found = interpolationSearch(students, cardNumberToFind);

        System.out.printf("\n--- Результат пошуку для квитка №%d ---\n", cardNumberToFind);
        if (found == null) {
            System.out.println("Студента з таким номером квитка не знайдено.");
            return;
        }

        // Якщо знайдено, перевіряємо, чи це студент-чоловік 
        if (!found.getGender().equals("Ч")) {
            System.out.printf("Знайдено студента (%s), але це не чоловік.\n", found.getGender());
            return;
        }

        // Перевіряємо, чи завершився термін дії
        System.out.println("Знайдено студента-чоловіка: " + found.getLastName());
        if (found.getExpiryDate().isBefore(LocalDate.now())) {
            System.out.println("РЕЗУЛЬТАТ: Так, термін дії квитка ЗАВЕРШИВСЯ.");
        } else {
            System.out.println("РЕЗУЛЬТАТ: Ні, квиток ще ДІЙСНИЙ.");
        }
    }

    // Реалізація інтерполяційного пошуку 
    public static Student interpolationSearch(Student[] arr, long key) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high && key >= arr[low].getLibraryCardNumber() && key <= arr[high].getLibraryCardNumber()) {
            if (low == high) {
                if (arr[low].getLibraryCardNumber() == key) return arr[low];
                return null;
            }

            // Формула інтерполяції
            // (key - arr[low]) * (high - low) / (arr[high] - arr[low])
            long arrLow = arr[low].getLibraryCardNumber();
            long arrHigh = arr[high].getLibraryCardNumber();
            
            // Запобігання діленню на нуль, якщо всі елементи в діапазоні однакові
            if (arrHigh == arrLow) return null;

            int pos = low + (int) ((((double) (high - low) / (arrHigh - arrLow)) * (key - arrLow)));

            // Якщо індекс вийшов за межі (через нерівномірний розподіл)
            if (pos < low || pos > high) {
                return null; // Або перейти на бінарний/лінійний
            }

            if (arr[pos].getLibraryCardNumber() == key) {
                return arr[pos];
            }

            if (arr[pos].getLibraryCardNumber() < key) {
                low = pos + 1;
            } else {
                high = pos - 1;
            }
        }
        return null;
    }

    // --- РІВЕНЬ 2 ---
    public static void runLevel2() {
        System.out.println("\n--- РІВЕНЬ 2: BST (Вставка в корінь) ---");
        BinarySearchTree bst = new BinarySearchTree();
        
        // 1. Створення та ініціація BST
        System.out.println("Додавання вузлів (вставка в корінь):");
        Student s1 = new Student("Петренко", "Ч", 10001L, LocalDate.of(2025, 12, 31));
        Student s2 = new Student("Іванова", "Ж", 10002L, LocalDate.of(2026, 5, 20));
        Student s3 = new Student("Сидоренко", "Ч", 10003L, LocalDate.of(2025, 6, 15));
        
        bst.insertLevel2(s1);
        bst.printBFS();
        bst.insertLevel2(s2);
        bst.printBFS();
        bst.insertLevel2(s3);
        bst.printBFS();

        // 2. Пошук вузла за ключем 
        LocalDate keyToSearch = LocalDate.of(2026, 5, 20);
        System.out.printf("\nПошук студента з датою квитка %s...\n", keyToSearch);
        Student foundStudent = bst.search(keyToSearch);
        System.out.println("Знайдено: " + (foundStudent != null ? foundStudent : "Нікого"));
    }

    // --- РІВЕНЬ 3 ---
    public static void runLevel3() {
        System.out.println("\n--- РІВЕНЬ 3: BST (Балансування 'Рандомізація' - Treap) ---");
        BinarySearchTree treap = new BinarySearchTree();

        // 1. Створення та ініціація BST (з балансуванням)
        System.out.println("Додавання вузлів (Treap-балансування):");
        Student s1 = new Student("Петренко", "Ч", 10001L, LocalDate.of(2025, 12, 31));
        Student s2 = new Student("Іванова", "Ж", 10002L, LocalDate.of(2026, 5, 20));
        Student s3 = new Student("Сидоренко", "Ч", 10003L, LocalDate.of(2025, 6, 15));

        treap.insertLevel3(s1);
        treap.printBFS();
        treap.insertLevel3(s2);
        treap.printBFS();
        treap.insertLevel3(s3);
        treap.printBFS();
        
        // 2. Пошук вузла за ключем
        LocalDate keyToSearch = LocalDate.of(2025, 6, 15);
        System.out.printf("\nПошук студента з датою квитка %s...\n", keyToSearch);
        Student foundStudent = treap.search(keyToSearch);
        System.out.println("Знайдено: " + (foundStudent != null ? foundStudent : "Нікого"));
    }
    
    // Допоміжний метод для створення масиву (мінімум 20 елементів)
    private static Student[] createStudentArray() {
        // LocalDate.now() - поточна дата, для перевірки завдання
        LocalDate today = LocalDate.now(); 
        
        return new Student[]{
            new Student("Петренко", "Ч", 10001L, LocalDate.of(2025, 12, 31)),
            new Student("Іванова", "Ж", 10002L, LocalDate.of(2026, 5, 20)),
            new Student("Сидоренко", "Ч", 10003L, LocalDate.of(2025, 6, 15)),
            new Student("Коваль", "Ж", 10004L, LocalDate.of(2027, 1, 10)),
            new Student("Мельник", "Ч", 10005L, today.minusDays(10)), // Протермінований
            new Student("Шевченко", "Ж", 10006L, LocalDate.of(2026, 8, 30)),
            new Student("Бондар", "Ч", 10007L, today.plusYears(1)),
            new Student("Ткаченко", "Ж", 10008L, LocalDate.of(2025, 11, 11)),
            new Student("Коваленко", "Ч", 10009L, LocalDate.of(2027, 2, 28)),
            new Student("Захаров", "Ж", 10010L, today.minusMonths(2)), // Протермінований
            new Student("Мороз", "Ч", 10011L, LocalDate.of(2026, 3, 5)),
            new Student("Павленко", "Ж", 10012L, LocalDate.of(2025, 7, 19)),
            new Student("Лисенко", "Ч", 10013L, LocalDate.of(2028, 1, 1)),
            new Student("Григоренко", "Ж", 10014L, today.plusDays(5)),
            new Student("Савченко", "Ч", 10015L, LocalDate.of(2026, 10, 22)),
            new Student("Романенко", "Ж", 10016L, LocalDate.of(2025, 9, 14)),
            new Student("Кравченко", "Ч", 10017L, LocalDate.of(2027, 4, 18)),
            new Student("Пономаренко", "Ж", 10018L, today.minusYears(1)), // Протермінований
            new Student("Василенко", "Ч", 10019L, LocalDate.of(2026, 12, 1)),
            new Student("Семененко", "Ж", 10020L, LocalDate.of(2025, 8, 8))
        };
    }
}