package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class EmployeeService {
    private EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
//    public BigDecimal calculateAverageSalary() {
//        Iterable<Employee> allEmployees = employeeRepository.findAll();
//        BigDecimal sumSalary = BigDecimal.ZERO;
//        int count = 0;
//        while (allEmployees.iterator().hasNext()) {
//            Employee employee = allEmployees.iterator().next();
//            sumSalary = sumSalary.add(employee.getSalary());
//        }
//        return null;
//    }
//}

    public BigDecimal calculateAverageSalary() {
        Iterable<Employee> allEmployees = employeeRepository.findAll();

        BigDecimal sumSalary = BigDecimal.ZERO;
        int count = 0;

        java.util.Iterator<Employee> iterator = allEmployees.iterator();

        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            sumSalary = sumSalary.add(employee.getSalary());
            count++;
        }

        if (count == 0) return BigDecimal.ZERO;
        return sumSalary.divide(BigDecimal.valueOf(count), 2, java.math.RoundingMode.HALF_UP);
    }


    public List<Employee> findTopEmployees() {
        Iterable<Employee> allEmployees = employeeRepository.findAll();
        List<Employee> topEmployees = new ArrayList<>();

        Iterator<Employee> iterator = allEmployees.iterator();

        if (!iterator.hasNext()) {
            return topEmployees;  // возврат пустого списка
        }

        Employee first = iterator.next();
        topEmployees.add(first);
        BigDecimal maxSalary = first.getSalary();


        while (iterator.hasNext()) {
            Employee current = iterator.next();
            int comparison = current.getSalary().compareTo(maxSalary);

            if (comparison > 0) {
                maxSalary = current.getSalary();
                topEmployees.clear(); // очистка списка
                topEmployees.add(current);
            } else if (comparison == 0) {
                // если зп такая же
                topEmployees.add(current); // просто добавляем в список к остальным
            }
        }
        return topEmployees;
    }


    public java.util.List<Employee> filterByPosition(String position) {
        Iterable<Employee> allEmployees = employeeRepository.findAll();
        java.util.Iterator<Employee> iterator = allEmployees.iterator();

        java.util.List<Employee> filteredList = new java.util.ArrayList<>();

        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            if (employee.getPosition().equalsIgnoreCase(position)) {
                filteredList.add(employee);
            }
        }
        return filteredList;
    }
}


