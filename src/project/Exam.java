package project;


import java.time.LocalDate;

public class Exam {

    private String name;
    private LocalDate date;
    private String status;
    private Integer id;

    Exam(String name, LocalDate date, String status, Integer id){
        this.name = name;
        this.date = date;
        this.status = status;
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }
}
