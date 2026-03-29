package ru.ranepa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.repository.EmployeeRepositoryImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService service;
    private EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        // Перед каждым тестом создаем пустой репозиторий и сервис
        repository = new EmployeeRepositoryImpl();
        service = new EmployeeService(repository);
    }


    @Test
    void testCalculateAverageSalary() {
        repository.save(new Employee("Kira", "Dev", new BigDecimal(2000), LocalDate.now()));
        repository.save(new Employee("Nastya", "Dev", new BigDecimal(3000), LocalDate.now()));
        repository.save(new Employee("Ivan", "Dev", new BigDecimal(1000), LocalDate.now()));

        BigDecimal average = service.calculateAverageSalary();

        assertEquals(0, new BigDecimal(2000).compareTo(average), "Average salary should be 2000");
    }

    @Test
    void testFindTopEmployees_2Winners() {
        // Двое с одинаковой зп
        repository.save(new Employee("Twin1", "Developer", new BigDecimal(500), LocalDate.now()));
        repository.save(new Employee("Twin2", "Analyst", new BigDecimal(500), LocalDate.now()));
        repository.save(new Employee("Different", "HR", new BigDecimal(100), LocalDate.now()));

        List<Employee> top = service.findTopEmployees();

        assertEquals(2, top.size(), "There must be 2 top employees");
        assertTrue(top.stream().anyMatch(e -> e.getName().equals("Twin1")));
        assertTrue(top.stream().anyMatch(e -> e.getName().equals("Twin2")));
    }

    @Test
    void test_EmptyList() {
        List<Employee> top = service.findTopEmployees();

        assertNotNull(top, "The method shouldn't return null");
        assertTrue(top.isEmpty(), "The list should be empty");
    }
}