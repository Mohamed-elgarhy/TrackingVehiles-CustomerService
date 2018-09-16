package com.server.services;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.ws.rs.core.MediaType;

import org.hamcrest.core.IsNull;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc()
@SpringBootTest()
// @ContextHierarchy(@ContextConfiguration)
public class CustomerServiceApplicationTests {

	@Autowired
	private MockMvc mvc;

	@InjectMocks
	private CustomerController customerController;

	@Mock
	CustomerRepository customerRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldReturnAllCustomersAsJson() throws Exception {
		this.mvc.perform(get("/customers")).andDo(print()).andExpect(status().isOk())
				// ;
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].customerName", is("Kalles Grustransporter")))
				.andExpect(jsonPath("$[0].customerAddress", containsString("Cement"))); // string(containsString((("Hello
																						// World")))));
	}

	@Test
	public void shouldReturnOneCustomersAsJson() throws Exception {
		this.mvc.perform(get("/customer").param("id", "1")).andDo(print()).andExpect(status().isOk())
				// ;
				.andExpect(jsonPath("$.entity.id", is(1)))
				.andExpect(jsonPath("$.entity.customerName", is("Kalles Grustransporter")))
				.andExpect(jsonPath("$.entity.customerAddress", containsString("Cement"))); // string(containsString((("Hello
																							// World")))));
	}

	@Test
	public void shouldReturnNotFoundStatusAndNullObject() throws Exception {
		this.mvc.perform(get("/customer").param("id", "7")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is(404))).andExpect(jsonPath("$.entity").value(IsNull.nullValue()));
	}

	@Test
	public void shouldReturnBadRequest() throws Exception {
		this.mvc.perform(get("/customer").param("id", "r")).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void shouldUpdateCustomerDataSuccessfully() throws Exception {

		createCustomer("99", "Ema", "Hype Park");

		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("id", "99");
		jsonObject.accumulate("customerName", "Salem");
		jsonObject.accumulate("customerAddress", "Garden city");

		this.mvc.perform(post("/customer").accept(MediaType.APPLICATION_JSON).content(jsonObject.toString())
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

		this.mvc.perform(get("/customer").param("id", "99")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.entity.id", is(99))).andExpect(jsonPath("$.entity.customerName", is("Salem")))
				.andExpect(jsonPath("$.entity.customerAddress", is("Garden city")));

	}

	@Test
	public void shouldUpdateCustomerNameSuccessfully() throws Exception {

		createCustomer("88", "Jack", "seinfie lopk");

		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("id", "88");
		jsonObject.accumulate("customerName", "Mariano");
		jsonObject.accumulate("customerAddress1", "address 123"); // address is
																	// wrongly
																	// typed

		this.mvc.perform(post("/customer").accept(MediaType.APPLICATION_JSON).content(jsonObject.toString())
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

		this.mvc.perform(get("/customer").param("id", "88")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.entity.id", is(88))).andExpect(jsonPath("$.entity.customerName", is("Mariano")))
				.andExpect(jsonPath("$.entity.customerAddress", containsString("seinfie lopk")));

	}

	@Test
	public void shouldCreateCustomerSuccessfully() throws Exception {

		createCustomer("66", "Klaus", "adress 123");

	}

	private void createCustomer(String id, String name, String address) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("id", id);
		jsonObject.accumulate("customerName", name);
		jsonObject.accumulate("customerAddress", address);

		this.mvc.perform(put("/customer").accept(MediaType.APPLICATION_JSON).content(jsonObject.toString())
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

		this.mvc.perform(get("/customer").param("id", id)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.entity.id", is(Integer.valueOf(id))))
				.andExpect(jsonPath("$.entity.customerName", is(name)))
				.andExpect(jsonPath("$.entity.customerAddress", containsString(address)));
	}

}
