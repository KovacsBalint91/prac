package balint.kovacs.first_project.model;

import java.time.LocalDate;

public class Income {

    private long value;
    private LocalDate date;

    public Income(){
    }

    public Income(long value, LocalDate date) {
        this.value = value;
        this.date = date;
    }

    public Income(long value) {
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
}