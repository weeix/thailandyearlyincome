package com.example.thailandyearlyincome;

public class ProvinceInfo {
    private String province;
    private String family;
    private String person;
    private String income;

    public ProvinceInfo(String province, String family, String person, String income) {
        this.province = province;
        this.family = family;
        this.person = person;
        this.income = income;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }
}
