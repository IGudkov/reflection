package courses.innotech;

import courses.innotech.utils.Utils;

public class Start {
  public static void main(String[] args) {
    Student student = new Student("ivan");

    Studentable proxyStudentable = Utils.cache(student);

    proxyStudentable.addGrade(3);
    proxyStudentable.addGrade(5);
    proxyStudentable.addGrade(5);

    proxyStudentable.calcAvgGrades();
    proxyStudentable.calcAvgGrades();

    System.out.println(student);
  }
}
