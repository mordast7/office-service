package model;

import lombok.*;

import java.util.List;

@Data
public class Employee {
    private Long id;
    private String name;
    private List<PhoneNumber> phoneNumbers;
}
