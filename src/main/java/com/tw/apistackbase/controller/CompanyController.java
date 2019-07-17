package com.tw.apistackbase.controller;

import io.micrometer.core.instrument.Meter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jxzhong on 18/08/2017.
 */
@RestController
@RequestMapping("/companies")
public class CompanyController {

    private List<Company> companies = new ArrayList<>();
    private List<Employee> employeesone = new ArrayList<>();
    private List<Employee> employeestwo = new ArrayList<>();

    private void addEmployeeOne() {
        employeesone.add(new Employee(0, "employeeone", 20, "F"));
        employeesone.add(new Employee(1, "employeetwo", 21, "M"));
    }

    private void addEmployeeTwo() {
        employeestwo.add(new Employee(0, "employeethree", 20, "F"));
        employeestwo.add(new Employee(1, "employeefour", 21, "M"));
    }

    private void addCompany() {
        companies.add(new Company(0, "companyone", 1000, employeesone));
        companies.add(new Company(1, "companytwo", 2000, employeestwo));
    }

    private void initializationData() {
        addEmployeeOne();
        addEmployeeTwo();
        addCompany();
    }

    @GetMapping()
    public ResponseEntity getAll() {
        initializationData();
        return ResponseEntity.ok().body(companies);
    }

    @GetMapping("{companyId}")
    public ResponseEntity getOne(@PathVariable Integer companyId) {
        initializationData();
        for (Company value : companies) {
            if (value.getCompanyId() == companyId) {
                return ResponseEntity.ok().body(value);
            }
        }
        return null;
    }

    @GetMapping("{companyId}/employees")
    public ResponseEntity getCompanyEmployees(@PathVariable Integer companyId) {
        initializationData();
        for (Company value : companies) {
            if (value.getCompanyId() == companyId) {
                return ResponseEntity.ok().body(value.getEmployees());
            }
        }
        return null;
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody Company company) {
        companies.add(company);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("{companyId}")
    public ResponseEntity update(@RequestBody Company company, @PathVariable Integer companyId) {
        initializationData();
        for (Company value : companies) {
            if (value.getCompanyId() == companyId) {
                value.setCompanyName(company.getCompanyName());
                value.setEmployees(company.getEmployees());
                value.setEmployeesNumber(company.getEmployeesNumber());
                return ResponseEntity.ok().body(value);
            }
        }
        return null;
    }

    @DeleteMapping("{companyId}")
    public ResponseEntity Delete(@PathVariable Integer companyId){
        initializationData();
        for (Company value : companies) {
            if (value.getCompanyId() == companyId) {
                companies.remove(value);
                return ResponseEntity.ok().body(value);
            }
        }
        return null;
    }

}
