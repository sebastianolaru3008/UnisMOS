package project;

public class Fee {
    private Integer id;
    private Tax tax;
    private Student student;

    Fee(Tax tax, Student student, Integer id){
        this.tax = tax;
        this.student = student;
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public Tax getTax() {
        return tax;
    }

    public Integer getId() {
        return id;
    }
}
