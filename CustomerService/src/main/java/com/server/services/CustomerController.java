package com.server.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/customers")
	public List<Customer> retrieveVechileBeanList() {

		List<Customer> customers = new ArrayList<>();
		customers = customerRepository.findAll();

		return customers;
	}
	
	@GetMapping("/customer")
	public Customer retrieveVechileBean() {

		
		Optional<Customer> customer = customerRepository.findById(1L);

		return customer.get();
	}
}