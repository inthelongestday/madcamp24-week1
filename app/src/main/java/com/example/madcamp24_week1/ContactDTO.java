package com.example.madcamp24_week1;

public class ContactDTO {
    private String name;
    private String phone;

    public ContactDTO(String name, String phone) {
        this.name = name;
        setPhone(phone);
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

    private String formatPhone(String phone) {
        if (phone.length() == 10) {
            return String.format("%s-%s-%s", phone.substring(0, 3), phone.substring(3, 6), phone.substring(6, 10));
        }
        if (phone.length() == 11) {
            return String.format("%s-%s-%s", phone.substring(0, 3), phone.substring(3, 7), phone.substring(7, 11));
        }
        return phone;
    }
}
