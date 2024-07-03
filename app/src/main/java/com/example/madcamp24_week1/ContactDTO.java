package com.example.madcamp24_week1;

public class ContactDTO implements Comparable<ContactDTO> {

    private int id;
    private String name;
    private String phone;

    public ContactDTO(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        setPhone(phone);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = formatPhone(phone);
    }

    public void setName(String name) {
        this.name = name;
    }

    private String formatPhone(String phone) {
        if (phone.length() == 10) {
            return String.format("%s-%s-%s", phone.substring(0, 3), phone.substring(3, 6), phone.substring(6, 10));
        }
        if (phone.length() == 11) {
            return String.format("%s-%s-%s", phone.substring(0, 3), phone.substring(3, 7), phone.substring(7, 11));
        }
        return phone;
    }

    public int compareTo(ContactDTO other) {
        return this.name.compareTo(other.name);
    }
}
