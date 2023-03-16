package repository;

import model.Employee;
import model.PhoneNumber;
import util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    public List<Employee> findAll() {
        String selectQuery = "SELECT e.id, p.id phone_number_id, name, phone_number "
                + "FROM employees e "
                + "LEFT JOIN phone_numbers p ON e.id = p.employee_id";
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getAllEmployeesStatement =
                     connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = getAllEmployeesStatement.executeQuery();
            while (resultSet.next()) {
                employees.add(parseEmployeeFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all employees", e);
        }
        return employees;
    }

    public List<PhoneNumber> findNumbersByName(String name) {
        String selectQuery = "SELECT p.id, p.phone_number " +
                "FROM phone_numbers p " +
                "JOIN employees e ON e.id = p.employee_id " +
                "WHERE name = ? ;";
        List<PhoneNumber> phoneNumbers = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getNumbersByEmployeeNameStatement =
                     connection.prepareStatement(selectQuery)) {
            getNumbersByEmployeeNameStatement.setString(1, name);

            ResultSet resultSet = getNumbersByEmployeeNameStatement.executeQuery();
            while (resultSet.next()) {
                phoneNumbers.add(parsePhoneNumberFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get phone numbers by employee name", e);
        }
        return phoneNumbers;
    }

    private PhoneNumber parsePhoneNumberFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String phoneNumberValue = resultSet.getString("phone_number");

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setId(id);
        phoneNumber.setPhoneNumber(phoneNumberValue);

        return phoneNumber;
    }

    private Employee parseEmployeeFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String phoneNumberValue = resultSet.getString("phone_number");
        Long phoneNumberId = resultSet.getObject("phone_number_id", Long.class);

        Employee employee = new Employee();
        employee.setId(id);
        employee.setName(name);

        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        PhoneNumber phoneNumber = new PhoneNumber();

        phoneNumber.setPhoneNumber(phoneNumberValue);
        phoneNumber.setEmployeeId(id);
        phoneNumber.setId(phoneNumberId);

        phoneNumbers.add(phoneNumber);
        employee.setPhoneNumbers(phoneNumbers);

        return employee;
    }
}
