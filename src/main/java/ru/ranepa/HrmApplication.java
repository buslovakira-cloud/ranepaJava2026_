package ru.ranepa;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.repository.EmployeeRepositoryImpl;
import ru.ranepa.service.EmployeeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class HrmApplication {
    public static void main(String[] args) {

        EmployeeRepository repo = new EmployeeRepositoryImpl();
        EmployeeService service = new EmployeeService(repo);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== HRM System Menu ===");
            System.out.println("1. Show all employees");
            System.out.println("2. Add employee");
            System.out.println("3. Delete by ID");
            System.out.println("4. Show statistics (Average salary / TOP)");
            System.out.println("5. Job search");
            System.out.println("6. Exit");
            System.out.print("Select an item: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("--- list of employees ---");
                    repo.findAll().forEach(System.out::println);
                    break;

//                case "2":
//                    System.out.print("Enter a name: ");
//                    String name = scanner.nextLine();
//                    System.out.print("Enter the position: ");
//                    String pos = scanner.nextLine();
//                    System.out.print("Enter the salary: ");
//                    double salary = Double.parseDouble(scanner.nextLine());

                case "2":
                    // 1. Проверка Имени
                    String name = "";
                    while (true) {
                        System.out.print("Enter a name: ");
                        name = scanner.nextLine().trim();
                        if (name.isEmpty()) {
                            System.out.println("Name can't be empty");
                        } else if (name.matches(".*\\d.*")) {
                            System.out.println("Name shouldn't contain numbers");
                        } else {
                            break;
                        }
                    }

                    // 2. Проверка Должности
                    String pos = "";
                    while (true) {
                        System.out.print("Enter a position: ");
                        pos = scanner.nextLine().trim();
                        if (pos.isEmpty()) {
                            System.out.println("Position can't be empty");
                        } else if (pos.matches(".*\\d.*")) {
                            System.out.println("Position shouldn't contain numbers");
                        } else {
                            break;
                        }
                    }

                    // 3. Проверка Зарплаты
                    BigDecimal salary = BigDecimal.ZERO;
                    while (true) {
                        System.out.print("Enter a salary: ");
                        String input = scanner.nextLine();
                        try {
                            salary = new BigDecimal(input);
                            if (salary.compareTo(BigDecimal.ZERO) <= 0) {
                                System.out.println("Salary should be bigger than 0");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Enter a valid number");
                        }
                    }
                    Employee newEmp = new Employee(name, pos, salary, LocalDate.now());
                    System.out.println(repo.save(newEmp));
                    break;

                case "3":
                    System.out.print("Enter the ID to delete: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    System.out.println(repo.delete(id));
                    break;

                case "4":
                    System.out.println("Average salary: " + service.calculateAverageSalary());
                    List<Employee> topList = service.findTopEmployees();
                    System.out.println("The highest paid:");

                    if (topList.isEmpty()) {
                        System.out.println("No data yet");
                    } else {
                        for (Employee e : topList) {
                            System.out.println(" - " + e);
                        }
                    }
                    break;

                case "5":
                    System.out.print("The position? ");
                    String searchPos = scanner.nextLine();
                    List<Employee> filtered = service.filterByPosition(searchPos);
                    filtered.forEach(System.out::println);
                    break;

                case "6":
                    System.out.println("completed");
                    return;

                default:
                    System.out.println("Error: enter a number between 1 and 6!");
            }
        }
    }
}





//        Employee emp = new Employee(
//                "Sidorov Alex Evgen`evich",
//                "java developer",
//                30_000.0,
//                LocalDate.of(2026, 3,1)
//        );
//        System.out.println(emp.getSalary());
//
//
//        var repo = new EmployeeRepositoryImpl();
//        System.out.println("==============================");
//        System.out.println(repo.save(emp));
//        System.out.println("==============================");
//        var emp1 = repo.findById(1L)
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//        System.out.println("Employee was found:" + emp1);
//    }
//
//}

