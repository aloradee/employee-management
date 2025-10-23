package service;

import exceptions.EmployeeNotFoundException;
import model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeService {

    // Находит сотрудника по его ID в переданном списке.
    public Employee getEmployeeById(int id, List<Employee> employees) throws EmployeeNotFoundException {
        for(Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        throw new EmployeeNotFoundException("Сотрудник с ID " + id + " не найден");
    }

    //Возвращает список сотрудников, чья зарплата больше или равна переданной.
    public List<Employee> getEmployeesBySalaryGreaterThan(int targetSalary, List<Employee> employees) {
        List<Employee> employees1 = new ArrayList<>();

        for(Employee employee : employees) {
            if(employee.getSalary() >= targetSalary) {
                employees1.add(employee);
            }
        }
        return employees1;
    }

    //Преобразует список сотрудников в Map (HashMap), где ключом будет строка "id+" + id (например, "id5"), а значением - сам сотрудник.
    public Map<String, Employee> getEmployeeMap(List<Employee> employees) {
        HashMap<String, Employee> employeeHashMap = new HashMap<>();

        for(Employee employee : employees) {
            employeeHashMap.put("id" + employee.getId(), employee);
        }

        return employeeHashMap;
    }

}
