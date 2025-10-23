package com.example;

import exceptions.EmployeeNotFoundException;
import exceptions.FileLoadException;
import model.Employee;
import service.EmployeeService;
import service.FileService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        EmployeeService employeeService = new EmployeeService();
        FileService fileService = new FileService();

        System.out.println("=== Демострация работы приложения ===");

        // 1. Создаем список сотрудников
        List<Employee> employees = createTestEmployees();
        System.out.println("Создан список сотрудников");
        printEmployees(employees);

        // 2. Демонстрация работы EmployeeService
        demonstrateEmployeeService(employeeService, employees);

        // 3. Демонстрация работы FileService
        demonstrateFileService(fileService, employees);

        System.out.println(" === Демонстрация завершена === ");
    }



    private static List<Employee> createTestEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Кирилл", "Христич", 100_000));
        employees.add(new Employee(2, "Иван", "Иванов", 90_000));
        employees.add(new Employee(3, "Михаил", "Гончаров", 180_000));
        employees.add(new Employee(4, "Марина", "Чебискова", 50_000));
        employees.add(new Employee(5, "Дмитрий", "Морозов", 159_000));
        employees.add(new Employee(6, "Елизавета", "Дроздова", 70_000));
        return employees;
    }

    private static void demonstrateEmployeeService(EmployeeService service, List<Employee> employees) {
        System.out.println("\n === Демонстрация работы EmployeeService ===");
        // 2.1 Поиск сотрудника по id (успешный случай)
        System.out.println("Поиск существующего сотрудника по id:");
        try {
            Employee foundEmployee = service.getEmployeeById(3, employees);
            System.out.println("Найден сотрудник: " + foundEmployee);
        } catch (EmployeeNotFoundException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        // 2.2 Поиск сотрудника по id (несуществующий id)
        System.out.println("Поиск несуществующего сотрудника по id:");
        try {
            Employee notFoundEmployee = service.getEmployeeById(999, employees);
        } catch (EmployeeNotFoundException e) {
            System.out.println("Ошибка: " + e.getMessage());
            System.out.println("Исключение успешно обработано!");
        }

        // 2.3 Фильтрация сотрудников по зарплате
        System.out.println("Сотрудники с зарплатой >= 60_000:");
        List<Employee> highSalaryEmployees = service.getEmployeesBySalaryGreaterThan(60_000, employees);
        printEmployees(highSalaryEmployees);

        // 2.4 Преобразование в Map
        System.out.println("Преобразование сотрудников в Map:");
        Map<String, Employee> employeeMap = service.getEmployeeMap(employees);
        for(Map.Entry<String, Employee> entry : employeeMap.entrySet()) {
            System.out.println("Ключ: " + entry.getKey() + " -> " + entry.getValue());
        }

        // 2.5 Демонстрация работы equals() и hashCode()
        System.out.println("Демонстрация equals() и hashCode():");
        Employee emp1 = new Employee(1, "Иван", "Петров", 50000);
        Employee emp2 = new Employee(1, "Иван", "Петров", 50000);
        Employee emp3 = new Employee(2, "Мария", "Сидорова", 75000);

        System.out.println("emp1.equals(emp2): " + emp1.equals(emp2) + " (должно быть true - одинаковые ID)");
        System.out.println("emp1.equals(emp3): " + emp1.equals(emp3) + " (должно быть false - разные ID)");
        System.out.println("emp1.hashCode() == emp2.hashCode(): " + (emp1.hashCode() == emp2.hashCode()));
    }

    private static void demonstrateFileService(FileService service, List<Employee> employees) {
        System.out.println("\n === Демонстрация FileService:");

        String fileName = "employees.txt";

        // 3.1 Сохранение сотрудников в файл;
        System.out.println("Сохранение сотрудников в файл:");
        service.saveEmployeesToFile(employees, fileName);
        System.out.println("Файл '" + fileName + "' успешно создан");

        // 3.2 Загрузка сотрудников из файла (успешный случай)
        System.out.println("\n3.2 Загрузка сотрудников из файла:");
        try {
            List<Employee> loadedEmployees = service.loadEmployeesFromFile(fileName);
            System.out.println("Успешно загружено " + loadedEmployees.size() + " сотрудников");
            printEmployees(loadedEmployees);
        } catch (FileLoadException e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
        }

        // 3.3 Попытка загрузки из несуществующего файла (обработка исключения)
        System.out.println("\n Попытка загрузки из несуществующего файла:");
        try {
            List<Employee> notFoundEmployees = service.loadEmployeesFromFile("nonexistent_file.txt");
        } catch (FileLoadException e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
            System.out.println("Исключение успешно обработано!");
        }

        // 3.4 Тестирование с файлом, содержащим ошибки
        System.out.println("\n Тестирование обработки ошибок в файле:");
        testErrorHandling(service);
    }

    //Тестирование обработки ошибок в файле
    private static void testErrorHandling(FileService service) {
        String errorFilename = "employees_with_errors.txt";

        // Создаем файл с ошибками
        List<String> errorLines = new ArrayList<>();
        errorLines.add("1,Иван,Петров,50000");        // корректная строка
        errorLines.add("2,Мария,Сидорова,abc");       // ошибка: зарплата не число
        errorLines.add("3,Алексей,Иванов");           // ошибка: не хватает полей
        errorLines.add("abc,Ольга,Смирнова,90000");   // ошибка: ID не число
        errorLines.add("5,Дмитрий,Козлов,60000");     // корректная строка
        errorLines.add("");                           // пустая строка
        errorLines.add("6,,Васильева,55000");         // ошибка: пустое имя
        errorLines.add("7,Сергей,Попов,-1000");       // ошибка: отрицательная зарплата

        // Сохраняем тестовый файл с ошибками
        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(errorFilename))) {
            for (String line : errorLines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Создан тестовый файл с ошибками: " + errorFilename);
        } catch (java.io.IOException e) {
            System.out.println("Ошибка создания тестового файла: " + e.getMessage());
        }

        // Пытаемся загрузить файл с ошибками
        try {
            List<Employee> errorLoadedEmployees = service.loadEmployeesFromFile(errorFilename);
            System.out.println("Успешно загружено сотрудников из файла с ошибками: " + errorLoadedEmployees.size());
            printEmployees(errorLoadedEmployees);
        } catch (FileLoadException e) {
            System.out.println("Ошибка загрузки файла с ошибками: " + e.getMessage());
        }
    }

    // Вспомогательный метод для вывода списка сотрудников
    private static void printEmployees(List<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("Список сотрудников пуст");
            return;
        }

        for (int i = 0; i < employees.size(); i++) {
            System.out.println((i + 1) + ". " + employees.get(i));
        }
    }
}