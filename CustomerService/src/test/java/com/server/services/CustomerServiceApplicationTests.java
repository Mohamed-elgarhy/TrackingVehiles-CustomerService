package com.server.services;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.isNull;
import static org.mockito.Matchers.matches;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.Response;

import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc()
@SpringBootTest()
//@ContextHierarchy(@ContextConfiguration)
public class CustomerServiceApplicationTests {

	   @Autowired
	   private MockMvc mvc;

	  /*@MockBean
	   private CustomerController customerController;*/

	   @Test
	    public void shouldReturnAllCustomersAsJson() throws Exception {
	        this.mvc.perform(get("/customers")).andDo(print()).andExpect(status().isOk())
	        //;
	        .andExpect(jsonPath("$[0].id", is(1)))
	        .andExpect(jsonPath("$[0].customerName", is("Kalles Grustransporter")))
	        .andExpect(jsonPath("$[0].customerAddress", containsString("Cement"))); //string(containsString((("Hello World")))));
	    }
	   
	   @Test
	    public void shouldReturnOneCustomersAsJson() throws Exception {
	        this.mvc.perform(get("/customer").param("id", "1")).andDo(print()).andExpect(status().isOk())
	        //;
	        .andExpect(jsonPath("$.entity.id", is(1)))
	        .andExpect(jsonPath("$.entity.customerName", is("Kalles Grustransporter")))
	        .andExpect(jsonPath("$.entity.customerAddress", containsString("Cement"))); //string(containsString((("Hello World")))));
	    }
	   
	   @Test
	    public void shouldReturnNotFoundStatusAndNullObject() throws Exception {
	        this.mvc.perform(get("/customer").param("id", "7")).andDo(print()).andExpect(status().isOk())
	        .andExpect(jsonPath("$.status", is(404)))
	        .andExpect(jsonPath("$.entity").value(IsNull.nullValue())); //string(containsString((("Hello World")))));
	    }
	   
	  /* @Test
	    public void shouldReturnOneCustomersAsJson1() throws Exception {
		   assertThat(this.customerController).isNotNull();
		    when(customerController.retrieveVechileBean(("1")).thenReturn(Response.ok().entity(new Customer(1L, "abc", "street")).build());
		 
		    MvcResult result= mockMvc.perform(get("/product/{id}/", 1))
		            .andExpect(status().isOk())
		            .andExpect(view().name("productshow"))
		            .andExpect(MockMvcResultMatchers.model().attributeExists("product"))
		            .andExpect(model().attribute("product", hasProperty("id", is(1))))
		            .andExpect(model().attribute("product", hasProperty("productId", is("235268845711068308"))))
		            .andExpect(model().attribute("product", hasProperty("description", is("Spring Framework Guru Shirt"))))
		            .andExpect(model().attribute("product", hasProperty("price", is(new BigDecimal("18.95")))))
		            .andExpect(model().attribute("product", hasProperty("imageUrl", is("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_shirt-rf412049699c14ba5b68bb1c09182bfa2_8nax2_512.jpg"))))
		            .andReturn();
	    }*/
	   
	   
	   /*@Test
	   public void getArrivals() throws Exception {
	       Customer customer = new Customer();
	       customer.setCustomerName("Yerevan");

	       List<Customer> allCustomers = Collections.singletonList(customer);

	       given(customerController.retrieveVechileBeanList()).willReturn(allCustomers);

	       mvc.perform(get("/customers"))
	               .andExpect(status().isOk())
	               .andExpect(jsonPath("$", hasSize(1)))
	               .andExpect(jsonPath("$[0].city", is(customer.getCustomerName())));
	   }*/

	   /*@Test
	   public void getArrivalsById() throws Exception {
	       Arrival arrival = new Arrival();
	       arrival.setCity("Yerevan");

	       given(arrivalController.getArrivalById(arrival.getId())).willReturn(arrival);

	       mvc.perform(get(VERSION + ARRIVAL + arrival.getId())
	               .with(user("blaze").password("Q1w2e3r4"))
	               .contentType(APPLICATION_JSON))
	               .andExpect(status().isOk())
	               .andExpect(jsonPath("city", is(arrival.getCity())));
	   }*/
    

}
