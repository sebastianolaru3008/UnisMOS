package project;

public class Grade {

    private Float grade;
    private String subject;

    Grade(String subject, float grade){
        this.grade = grade;
        this.subject = subject;
    }

    public Float getGrade() {
        return grade;
    }

    public String getSubject() {
        return subject;
    }
}
