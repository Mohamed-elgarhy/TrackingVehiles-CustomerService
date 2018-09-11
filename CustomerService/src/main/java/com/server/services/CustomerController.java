package com.server.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public Response retrieveVechileBean(@RequestParam("id") Long customerId) {

		
		Optional<Customer> customer = customerRepository.findById(customerId);

		if (customer.isPresent())
			return Response.ok().entity(customer.get()).build();
		else
			return Response.status(Status.NOT_FOUND).build();
		
	}
}