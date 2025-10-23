package service;

import exceptions.FileLoadException;
import model.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    //Сохраняет список сотрудников в текстовый файл.
    public void saveEmployeesToFile(List<Employee> employees, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            for(Employee employee : employees) {
                writer.write(empToString(employee));
                writer.newLine();
            }
            System.out.println("Данные сохранены в файл: " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e);
        }
    }

    //Читает данные о сотрудниках из файла того же формата и возвращает список объектов Employee.
    public List<Employee> loadEmployeesFromFile(String fileName) throws FileLoadException {

        List<Employee> employees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    if(line.trim().isEmpty()) {
                        continue;
                    }
                    String[] parts = line.split(",");
                    if(parts.length < 4) {
                        throw new IllegalArgumentException("Недостаточно данных в строке." +
                                " Ожидается: id, firstName, lastName, salary");
                    }
                    int id = Integer.parseInt(parts[0].trim());
                    String firstName = parts[1];
                    String lastName = parts[2];
                    int salary= Integer.parseInt(parts[3].trim());

                    if(id <= 0) {
                        throw new IllegalArgumentException("ID должен быть положительным числом");
                    }
                    if(salary < 0) {
                        throw new IllegalArgumentException("Зарплата не может быть отрицательной");
                    }
                    if(firstName.isEmpty() || lastName.isEmpty()) {
                        throw new IllegalArgumentException("Имя и фамилия не могут быть пустыми");
                    }

                    Employee employee = new Employee(id, firstName, lastName, salary);
                    employees.add(employee);
                } catch (Exception e) {
                    System.out.println("Ошибка в строке " + lineNumber + ": " + line);
                    System.out.println("Причина: " + e.getMessage());
                    System.out.println("Строка пропущена, продолжаем обработку...\n");
                }
            }
            System.out.println("Загрузка завершенаю. Успешно загружено: " + employees.size() + " сотрудников");

        } catch (IOException e) {
            throw new FileLoadException("Файл не найден или невозможно прочитать: " + fileName, e);
        }

        return employees;
    }

    private static String empToString(Employee employee) {
        return String.format("%d,%s,%s,%d",
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getSalary());
    }
}
