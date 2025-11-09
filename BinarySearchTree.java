import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class BinarySearchTree {

    private static final Random rand = new Random();

    // Внутрішній клас для вузла дерева
    class Node {
        Student student;
        Node left, right;
        double priority; // Потрібно для 3-го рівня (Рандомізація/Treap)

        Node(Student student) {
            this.student = student;
            // Призначаємо випадковий пріоритет для Treap (Рівень 3)
            this.priority = rand.nextDouble(); 
        }
    }

    private Node root;

    // --- Методи 2-го рівня (Вставка в корінь) ---

    public void insertLevel2(Student student) {
        root = insertAtRoot(root, student);
    }

    private Node insertAtRoot(Node node, Student student) {
        if (node == null) {
            return new Node(student);
        }

        // Ключ - дата дії квитка 
        if (student.getExpiryDate().isBefore(node.student.getExpiryDate())) {
            node.left = insertAtRoot(node.left, student);
            node = rightRotate(node); // Ротація вправо
        } else {
            node.right = insertAtRoot(node.right, student);
            node = leftRotate(node); // Ротація вліво
        }
        return node;
    }

    // --- Методи 3-го рівня (Балансування "Рандомізація" - Treap) ---

    public void insertLevel3(Student student) {
        root = insertTreap(root, student);
    }

    private Node insertTreap(Node node, Student student) {
        if (node == null) {
            return new Node(student);
        }

        // 1. Звичайна вставка BST (за ключем - датою)
        if (student.getExpiryDate().compareTo(node.student.getExpiryDate()) < 0) {
            node.left = insertTreap(node.left, student);
            // 2. Перевірка властивості "купи" (heap property) за пріоритетом
            if (node.left != null && node.left.priority > node.priority) {
                node = rightRotate(node);
            }
        } else {
            node.right = insertTreap(node.right, student);
            // 2. Перевірка властивості "купи" (heap property) за пріоритетом
            if (node.right != null && node.right.priority > node.priority) {
                node = leftRotate(node);
            }
        }
        return node;
    }


    // --- Спільні методи (Ротації, Пошук, Друк) ---

    // Ротація вправо
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        return x;
    }

    // Ротація вліво
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        return y;
    }

    // Пошук за ключем (дата)
    public Student search(LocalDate expiryDate) {
        Node result = searchRecursive(root, expiryDate);
        return (result != null) ? result.student : null;
    }

    private Node searchRecursive(Node current, LocalDate date) {
        if (current == null) {
            return null;
        }
        if (date.equals(current.student.getExpiryDate())) {
            return current;
        }
        return date.isBefore(current.student.getExpiryDate())
                ? searchRecursive(current.left, date)
                : searchRecursive(current.right, date);
    }

    // Друк дерева (обхід у ширину)
    public void printBFS() {
        if (root == null) {
            System.out.println("Дерево порожнє.");
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        System.out.println("Обхід дерева у ширину (корінь: " + root.student + "):");
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            System.out.println("  -> " + node.student);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }
    
    // Метод для очищення дерева між демонстраціями
    public void clear() {
        root = null;
    }
}