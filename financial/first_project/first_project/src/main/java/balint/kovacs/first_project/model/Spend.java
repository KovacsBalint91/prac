package balint.kovacs.first_project.model;

import java.time.LocalDate;

public class Spend {
    private long value;
    private LocalDate date;
    private long category_id;

    public Spend() {
    }

    public Spend(long value, LocalDate date, long category_id) {
        this(value, date);
        this.category_id = category_id;
    }

    public Spend(long value, LocalDate date) {
        this.value = value;
        this.date = date;
    }

    public Spend(long value, long category_id) {
        this.value = value;
        this.category_id = category_id;
    }

    public Spend(long value) {
        this.value = value;
        this.date = LocalDate.now();
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "Spend{" +
                "value=" + value +
                ", date=" + date +
                ", category_id=" + category_id +
                '}';
    }
}
