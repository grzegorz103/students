package main;

import java.io.Serializable;

public class Student implements Serializable {

    private long pid;
    private int semester;
    private String name, surname, age, major;

    public Student(long pID, String name, String surname, String age, String major, int semester) {
        this.pid = pID;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.major = major;
        this.semester = semester;
    }

    public long getPID() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAge() {
        return age;
    }

    public String getMajor() {
        return major;
    }

    public int getSemester() {
        return semester;
    }
}
