package courses.innotech;

import courses.innotech.utils.Cache;
import courses.innotech.utils.Mutator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class Student implements Studentable {
  @Getter @Setter
  private String name;
  private List<Integer> grades = new ArrayList<>();

  public Student(String name) {
    this.name = name;
  }

  public List<Integer> getGrades() {
    return new ArrayList<>(grades);
  }

  @Mutator
  public void addGrade(Integer grade) {
    this.grades.add(grade);
  }

  @Cache
  public Double calcAvgGrades(){
    return this.grades
        .stream()
        .mapToInt(a -> a)
        .average()
        .orElse(0);
  }
}
