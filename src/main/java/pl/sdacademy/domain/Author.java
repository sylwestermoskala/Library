package pl.sdacademy.domain;

public class Author {
    private String name;
    private String surname;
    private Integer authorid;

    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Author(Integer authorid, String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.authorid = authorid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAuthorid() {
        return authorid;
    }

    public void setAuthorid(Integer authorid) {
        this.authorid = authorid;
    }


    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", authorid=" + authorid +
                '}';
    }
}
