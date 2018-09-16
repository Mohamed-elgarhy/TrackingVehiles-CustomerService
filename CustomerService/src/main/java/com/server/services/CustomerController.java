package com.server.services;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/customers")
	public List<Customer> retrieveCustomerList() {

		
		return customerRepository.findAll();
	}

	@GetMapping("/customer")
	public Response retrieveCustomer(@RequestParam("id") Long customerId) {

		Optional<Customer> customer = customerRepository.findById(customerId);

		if (customer.isPresent())
			return Response.ok().entity(customer.get()).build();
		else
			return Response.status(Status.NOT_FOUND).build();

	}

	@RequestMapping(value = "/customer", method = RequestMethod.PUT)
	public Response createCustomer(@RequestBody Customer customer) {

		customerRepository.save(customer);

		return Response.ok("success").build();
	}

	@RequestMapping(value = "/customer", method = RequestMethod.POST)
	public Response updateCustomer(@RequestBody Customer customer) {

		Optional<Customer> objectToUpdate = customerRepository.findById(customer.getId());

		if (objectToUpdate.isPresent()) {
			Customer updatedObject = objectToUpdate.get();

			if (customer.getCustomerAddress() != null
					&& !updatedObject.getCustomerAddress().equalsIgnoreCase(customer.getCustomerAddress()))
				updatedObject.setCustomerAddress(customer.getCustomerAddress());

			if (customer.getCustomerName() != null
					&& !updatedObject.getCustomerName().equalsIgnoreCase(customer.getCustomerName()))
				updatedObject.setCustomerName(customer.getCustomerName());

			customerRepository.save(updatedObject);
		} else {
			return Response.status(Status.NOT_FOUND).entity("Object not found").build();

		}

		return Response.ok("success").build();
	}
}