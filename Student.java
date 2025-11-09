import java.time.LocalDate;

public class Student {
    private String lastName;
    private String gender; // "Ч" (Чоловік) або "Ж" (Жінка)
    private long libraryCardNumber;
    private LocalDate expiryDate;

    public Student(String lastName, String gender, long libraryCardNumber, LocalDate expiryDate) {
        this.lastName = lastName;
        this.gender = gender;
        this.libraryCardNumber = libraryCardNumber;
        this.expiryDate = expiryDate;
    }

    // Геттери (методи для отримання значень)
    
    // --- ОСЬ ЦЕЙ МЕТОД ПОТРІБНО ДОДАТИ ---
    public String getLastName() {
        return lastName;
    }
    // ------------------------------------

    public String getGender() {
        return gender;
    }

    public long getLibraryCardNumber() {
        return libraryCardNumber;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    @Override
    public String toString() {
        return String.format("Студент: %s, Стать: %s, Квиток №: %d, Дійсний до: %s",
                lastName, gender, libraryCardNumber, expiryDate.toString());
    }
}