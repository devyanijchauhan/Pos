package org.pgs.postp.dto;

public class CustomerDTO {
    private Long id; // Updated field name
    private String name;
    private String email;
    private Number phone;

    public CustomerDTO() {
    }

    public CustomerDTO(Long id, String name, String email, Number phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() { // Updated getter name
        return id;
    }

    public void setId(Long id) { // Updated setter name
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Number getPhone() {
        return phone;
    }

    public void setPhone(Number phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
