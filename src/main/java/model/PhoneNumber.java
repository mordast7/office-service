package model;

import lombok.Data;

@Data
public class PhoneNumber {
    private Long id;
    private Long employeeId;
    private String phoneNumber;
}
