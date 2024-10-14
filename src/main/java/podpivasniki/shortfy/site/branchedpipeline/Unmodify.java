package podpivasniki.shortfy.site.branchedpipeline;

import javax.swing.text.StringContent;
import java.util.*;
import java.util.stream.Collectors;

public class Unmodify {

    private List<String> answer = new ArrayList<>();

    public List<String> getAnswer() {
        return List.copyOf(answer);
    }
    public void print(){
        for(String s:answer){
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("Alice", 90),
                new Student("Bob", 85),
                new Student("Charlie", 90)
        ).stream().sorted(Comparator.comparing(Student::getGrade)
                .reversed()
                .thenComparing(Student::getName)).collect(Collectors.toList());
    }
}
class Student {
    private String name;
    private int grade;

    public Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
