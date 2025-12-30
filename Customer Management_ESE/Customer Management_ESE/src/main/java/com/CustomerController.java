package com;

import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin(origins = "*")   // ⭐ THIS FIXES WEBPAGE CONNECTION
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository repo;

    public CustomerController(CustomerRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public String addCustomer(@RequestBody Customer customer) {
        repo.save(customer);
        return "Customer added";
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }

    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable int id, @RequestBody Customer updatedCustomer) {
        return repo.findById(id).map(c -> {
            c.setName(updatedCustomer.getName());
            c.setAddress(updatedCustomer.getAddress());
            repo.save(c);
            return "Customer updated";
        }).orElse("Customer not found");
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable int id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return "Customer deleted";
        }
        return "Customer not found";
    }
}
