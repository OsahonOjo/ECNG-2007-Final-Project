package com.osahonojo.sharedpreferencesactivity;

public class Person {
    private String fname;
    private String lname;
    private String school;

    public Person(String fname, String lname, String school) {
        this.fname = fname;
        this.lname = lname;
        this.school = school;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
