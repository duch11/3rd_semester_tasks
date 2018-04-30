package tech.holm.third_semester_tasks.streams.exercises;

public class Student {
    String name;
    String email;
    int yearStarted;
    boolean isActive;

    public Student(String name, String email, int yearStarted, boolean isActive) {

        this.name = name;

        this.email = email;

        this.yearStarted = yearStarted;

        this.isActive = isActive;

    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
