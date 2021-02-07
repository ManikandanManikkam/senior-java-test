package com.cc.practicaltest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cc.practicaltest.config.AppConfig;
import com.cc.practicaltest.config.MvcWebApplicationInitializer;
import com.cc.practicaltest.controller.ReteriveOrderDetailsController;
import com.cc.practicaltest.dao.OrderdetaildaoImpl;
import com.cc.practicaltest.model.CutomerDetail;
import com.cc.practicaltest.model.OrderDetails;
import com.cc.practicaltest.service.OrderDetailsServiceImpl;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MvcWebApplicationInitializer.class, ReteriveOrderDetailsController.class,
		OrderDetailsServiceImpl.class, OrderdetaildaoImpl.class,
		AppConfig.class }, loader = AnnotationConfigWebContextLoader.class)

public class ExampleIntergrationTest {
	
private MockMvc mockMvc;
	
	private CutomerDetail custobj;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		custobj = new CutomerDetail();
		custobj.setForename("Joyce");
		custobj.setSurname("Edwards");
		custobj.setCustomer_no(153);
		List<OrderDetails> orders = new ArrayList<OrderDetails>();
		custobj.setOrders(orders);
	}

	

	@Test
	public void testCreationOfOrderSucceeds() throws Exception {
		CutomerDetail custobj = new CutomerDetail();

		String json = new ObjectMapper().writeValueAsString(custobj);

		mockMvc.perform(get("/orderdetails/153").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(json)).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
	}

	@Test
	public void testCreationOfOrderSucceedResponse() throws Exception {

		String json = new ObjectMapper().writeValueAsString(custobj);

		MvcResult result = (MvcResult) mockMvc
				.perform(get("/orderdetails/153").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(json)).andDo(MockMvcResultHandlers.print())
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
		;
		String content = result.getResponse().getContentAsString();
		assertThat(json).isEqualTo(content);
	}

	@Test
	public void testCreationOfOrderFailureResponse() throws Exception {
		CutomerDetail cusobj =setupdata();
		String json_string = new ObjectMapper().writeValueAsString(cusobj);

		MvcResult result = (MvcResult) mockMvc
				.perform(get("/orderdetails/10153").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(json_string))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
		;
		String content = result.getResponse().getContentAsString();
		assertThat(json_string).isNotEqualTo(content);
	}
	
	@Test
	public void testCreationOfOrderSuccessResponse() throws Exception {
		CutomerDetail cusobj = setupdataValid();
		String json_string = new ObjectMapper().writeValueAsString(cusobj);

		MvcResult result = (MvcResult) mockMvc
				.perform(get("/orderdetails/1153").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(json_string))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
		;
		String content = result.getResponse().getContentAsString();
		
		assertThat(json_string).isEqualTo(content);
	}
	
	



	@Test
	public void testCreationOrderdetailNotfound() throws Exception {

		String json = new ObjectMapper().writeValueAsString(custobj);

		mockMvc.perform(get("/orderdetails/").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(json)).andExpect(status().isNotFound());
	}

	@Test
	public void testCreationProjectNotfound() throws Exception {
	
		String json = new ObjectMapper().writeValueAsString(custobj);

		mockMvc.perform(
				get("/").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound());
	}


	@Test
	public void testCreationOrderdetailwithText() throws Exception {

		String json = new ObjectMapper().writeValueAsString(custobj);

		mockMvc.perform(get("/orderdetails/dddd").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(json)).andExpect(status().isBadRequest());
	}

		
	private CutomerDetail setupdata() {
		String json_String = "{\"forename\":\"Barbara\",\"surname\":\"Johnson\",\"customer_no\":1153,\"orders\":[{\"order_no\":1847,\"employee_number\":2,\"sale_price\":\"32746.01\",\"order_date\":\"2019-07-07\",\"deposit\":\"2862.03\",\"vechile_no\":1847,\"model_name\":\"Luxury\",\"trim_name\":\"Ultra\",\"trim_WheelType\":\"19 inch\",\"infoentainment_type\":\"8 inch Bluetooth/GPS\",\"headLight_type\":\"LED\",\"trim_additional_cost\":null,\"engine_model\":\"2.0d\",\"engine_capacity\":\"2000\",\"vechile_fuelType\":\"Diesel\",\"expected_delivery_type\":\"2019-08-21\",\"actual_delivery_type\":\"2019-08-21\",\"vechileColour\":\"White\",\"upholstery_type\":\"Leather\"},{\"order_no\":3294,\"employee_number\":2,\"sale_price\":\"20039.71\",\"order_date\":\"2019-07-07\",\"deposit\":\"1291.52\",\"vechile_no\":3294,\"model_name\":\"Family\",\"trim_name\":\"Plus\",\"trim_WheelType\":\"17 inch\",\"infoentainment_type\":\"6 inch Bluetooth/GPS\",\"headLight_type\":\"Halogen\",\"trim_additional_cost\":null,\"engine_model\":\"2.0d\",\"engine_capacity\":\"2000\",\"vechile_fuelType\":\"Diesel\",\"expected_delivery_type\":\"2019-08-11\",\"actual_delivery_type\":\"2019-08-11\",\"vechileColour\":\"Grey\",\"upholstery_type\":\"Cloth\"},{\"order_no\":3603,\"employee_number\":2,\"sale_price\":\"18727.80\",\"order_date\":\"2019-07-07\",\"deposit\":\"925.41\",\"vechile_no\":3603,\"model_name\":\"Family\",\"trim_name\":\"Plus\",\"trim_WheelType\":\"17 inch\",\"infoentainment_type\":\"6 inch Bluetooth/GPS\",\"headLight_type\":\"Halogen\",\"trim_additional_cost\":null,\"engine_model\":\"1.8i\",\"engine_capacity\":\"1800\",\"vechile_fuelType\":\"Petrol\",\"expected_delivery_type\":\"2019-08-18\",\"actual_delivery_type\":\"2019-08-18\",\"vechileColour\":\"Blue\",\"upholstery_type\":\"Cloth\"},{\"order_no\":22716,\"employee_number\":2,\"sale_price\":\"22810.32\",\"order_date\":\"2019-07-07\",\"deposit\":\"69.45\",\"vechile_no\":22716,\"model_name\":\"Family\",\"trim_name\":\"Ultra\",\"trim_WheelType\":\"19 inch\",\"infoentainment_type\":\"8 inch Bluetooth/GPS\",\"headLight_type\":\"LED\",\"trim_additional_cost\":null,\"engine_model\":\"2.0d\",\"engine_capacity\":\"2000\",\"vechile_fuelType\":\"Diesel\",\"expected_delivery_type\":\"2019-08-20\",\"actual_delivery_type\":\"2019-08-20\",\"vechileColour\":\"Grey\",\"upholstery_type\":\"Leather\"},{\"order_no\":24964,\"employee_number\":2,\"sale_price\":\"34925.67\",\"order_date\":\"2019-07-07\",\"deposit\":\"372.83\",\"vechile_no\":24964,\"model_name\":\"Luxury\",\"trim_name\":\"Ultra\",\"trim_WheelType\":\"19 inch\",\"infoentainment_type\":\"8 inch Bluetooth/GPS\",\"headLight_type\":\"LED\",\"trim_additional_cost\":null,\"engine_model\":\"2.0d\",\"engine_capacity\":\"2000\",\"vechile_fuelType\":\"Diesel\",\"expected_delivery_type\":\"2019-08-19\",\"actual_delivery_type\":\"2019-08-19\",\"vechileColour\":\"Black\",\"upholstery_type\":\"Leather\"}]}\"";
		CutomerDetail cusobj = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			cusobj = objectMapper.readValue(json_String, CutomerDetail.class);
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cusobj;
	}

	

	private CutomerDetail setupdataValid() {
		// TODO Auto-generated method stub
		String json_String="{\"forename\":\"Barbara\",\"surname\":\"Johnson\",\"customer_no\":1153,\"orders\":[{\"order_no\":1847,\"employee_number\":2,\"sale_price\":\"32746.01\",\"order_date\":\"2019-07-07\",\"deposit\":\"2862.03\",\"vechile_no\":1847,\"model_name\":\"Luxury\",\"trim_name\":\"Ultra\",\"trim_WheelType\":\"19 inch\",\"infoentainment_type\":\"8 inch Bluetooth/GPS\",\"headLight_type\":\"LED\",\"trim_additional_cost\":null,\"engine_model\":\"2.0d\",\"engine_capacity\":\"2000\",\"vechile_fuelType\":\"Diesel\",\"expected_delivery_type\":\"2019-08-21\",\"actual_delivery_type\":\"2019-08-21\",\"vechileColour\":\"White\",\"upholstery_type\":\"Leather\"},{\"order_no\":3294,\"employee_number\":2,\"sale_price\":\"20039.71\",\"order_date\":\"2019-07-07\",\"deposit\":\"1291.52\",\"vechile_no\":3294,\"model_name\":\"Family\",\"trim_name\":\"Plus\",\"trim_WheelType\":\"17 inch\",\"infoentainment_type\":\"6 inch Bluetooth/GPS\",\"headLight_type\":\"Halogen\",\"trim_additional_cost\":null,\"engine_model\":\"2.0d\",\"engine_capacity\":\"2000\",\"vechile_fuelType\":\"Diesel\",\"expected_delivery_type\":\"2019-08-11\",\"actual_delivery_type\":\"2019-08-11\",\"vechileColour\":\"Grey\",\"upholstery_type\":\"Cloth\"},{\"order_no\":3603,\"employee_number\":2,\"sale_price\":\"18727.80\",\"order_date\":\"2019-07-07\",\"deposit\":\"925.41\",\"vechile_no\":3603,\"model_name\":\"Family\",\"trim_name\":\"Plus\",\"trim_WheelType\":\"17 inch\",\"infoentainment_type\":\"6 inch Bluetooth/GPS\",\"headLight_type\":\"Halogen\",\"trim_additional_cost\":null,\"engine_model\":\"1.8i\",\"engine_capacity\":\"1800\",\"vechile_fuelType\":\"Petrol\",\"expected_delivery_type\":\"2019-08-18\",\"actual_delivery_type\":\"2019-08-18\",\"vechileColour\":\"Blue\",\"upholstery_type\":\"Cloth\"},{\"order_no\":22716,\"employee_number\":2,\"sale_price\":\"22810.32\",\"order_date\":\"2019-07-07\",\"deposit\":\"69.45\",\"vechile_no\":22716,\"model_name\":\"Family\",\"trim_name\":\"Ultra\",\"trim_WheelType\":\"19 inch\",\"infoentainment_type\":\"8 inch Bluetooth/GPS\",\"headLight_type\":\"LED\",\"trim_additional_cost\":null,\"engine_model\":\"2.0d\",\"engine_capacity\":\"2000\",\"vechile_fuelType\":\"Diesel\",\"expected_delivery_type\":\"2019-08-20\",\"actual_delivery_type\":\"2019-08-20\",\"vechileColour\":\"Grey\",\"upholstery_type\":\"Leather\"},{\"order_no\":24964,\"employee_number\":2,\"sale_price\":\"34925.67\",\"order_date\":\"2019-07-07\",\"deposit\":\"372.83\",\"vechile_no\":24964,\"model_name\":\"Luxury\",\"trim_name\":\"Ultra\",\"trim_WheelType\":\"19 inch\",\"infoentainment_type\":\"8 inch Bluetooth/GPS\",\"headLight_type\":\"LED\",\"trim_additional_cost\":null,\"engine_model\":\"2.0d\",\"engine_capacity\":\"2000\",\"vechile_fuelType\":\"Diesel\",\"expected_delivery_type\":\"2019-08-19\",\"actual_delivery_type\":\"2019-08-19\",\"vechileColour\":\"Black\",\"upholstery_type\":\"Leather\"}]}";
		CutomerDetail cusobj = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			cusobj = objectMapper.readValue(json_String, CutomerDetail.class);
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cusobj;
		
	}

}
