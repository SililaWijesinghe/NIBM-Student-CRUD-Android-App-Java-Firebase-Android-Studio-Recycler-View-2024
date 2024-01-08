package com.example.myapplication9777;

public class DataClass {

    private String stdRegNo;
    private String stdName;
    private Integer stdAge;
    private String stdGender;
    private Integer stdTeleNo;
    private Integer stdParTeleNo;
    private String dataImage;
    private String key;

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getStdRegNo() {
        return stdRegNo;
    }

    public String getStdName() {
        return stdName;
    }

    public Integer getStdAge() {
        return stdAge;
    }

    public String getStdGender() {
        return stdGender;
    }

    public Integer getStdTeleNo() {
        return stdTeleNo;
    }

    public Integer getStdParTeleNo() {
        return stdParTeleNo;
    }

    public String getDataImage() {return dataImage;}

    public DataClass(String stdRegNo, String stdName, Integer stdAge, String stdGender, Integer stdTeleNo, Integer stdParTeleNo,
                     String dataImage) {
        this.stdRegNo = stdRegNo;
        this.stdName = stdName;
        this.stdAge = stdAge;
        this.stdGender = stdGender;
        this.stdTeleNo = stdTeleNo;
        this.stdParTeleNo = stdParTeleNo;
        this.dataImage = dataImage;
    }

    public DataClass() {

    }
}
