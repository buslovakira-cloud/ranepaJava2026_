package ru.ranepa.repository;

import ru.ranepa.model.Employee;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private Map<Long, Employee> employees = new HashMap<>();
    private static Long nextId = 1L;

    @Override
    public String save(Employee employee) {
        employee.setId(nextId++);
        employees.put(employee.getId(), employee);
        return "Employee" + employee.getId() + "was saved successful";
    }

    @Override
    public Optional<Employee> findById(Long id) {

        return Optional.of(employees.get(id));
    }

    @Override
    public Iterable<Employee> findAll() {

        return employees.values();
    }

    @Override
    public String delete(Long id) {
        if (employees.containsKey(id)) {
            employees.remove(id);
            return "Employee successfully deleted";
        } else {
            return "Employee with ID " + id + " not found";
        }
    }
}
