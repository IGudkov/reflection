package courses.innotech;

import courses.innotech.utils.Utils;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

class StudentTest {

  @Test
  @DisplayName("Инкапсуляция. Нет возможности получить ссылку на массив для последующей модификации")
  public void checkModifyGrades(){
    Student student = new Student("Ivan");
    Studentable proxyStudentable = Utils.cache(student);

    List<Integer> grades = student.getGrades();
    grades.add(2);

    Assertions.assertTrue(student.getGrades().isEmpty());
  }

  @Test
  @DisplayName("Метод вызывается впервые после создания кэшируемого объекта: вызывается обычным образом")
  public void methodFirstRunNoCache(){
    Student student = new Student("Ivan");
    Studentable proxyStudentable = Utils.cache(student);

    for (int i = 0; i < 10000000; i++) {
      proxyStudentable.addGrade((int)(Math.random()*(4)) +2);
    }

    Instant start = Instant.now();
    Double d = proxyStudentable.calcAvgGrades();
    Instant finish = Instant.now();

    Assertions.assertTrue(Duration.between(start, finish).toMillis() > 1);
  }

  @Test
  @DisplayName("Метод вызывается повторно и с момента прошлого вызова внесены изменения в состояние объекта: вызывается обычным образом")
  public void methRepeatChangesSetNoCache(){
    Student student = new Student("Ivan");
    Studentable proxyStudentable = Utils.cache(student);

    for (int i = 0; i < 10000000; i++) {
      proxyStudentable.addGrade((int)(Math.random()*(4)) +2);
    }

    Double d1 = proxyStudentable.calcAvgGrades();
    proxyStudentable.addGrade(5);

    Instant start = Instant.now();
    Double d2 = proxyStudentable.calcAvgGrades();
    Instant finish = Instant.now();

    Assertions.assertTrue(Duration.between(start, finish).toMillis() > 1);
  }

  @Test
  @DisplayName("Метод вызывается повторно и объект не был изменен с момента прошлого вызова: вместо вызова метода возвращается то же значение, что возвращал метод при предыдущем вызове")
  public void methRepeatGetCache(){
    Student student = new Student("Ivan");
    Studentable proxyStudentable = Utils.cache(student);

    for (int i = 0; i < 10000000; i++) {
      proxyStudentable.addGrade((int)(Math.random()*(4)) +2);
    }

    Double d1 = proxyStudentable.calcAvgGrades();

    Instant start = Instant.now();
    Double d2 = proxyStudentable.calcAvgGrades();
    Instant finish = Instant.now();

    Assertions.assertFalse(Duration.between(start, finish).toMillis() > 1);
  }
}