package com.cc.practicaltest.service;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.cc.practicaltest.dao.Orderdetaildao;
import com.cc.practicaltest.model.CutomerDetail;
import com.cc.practicaltest.model.OrderDetails;
import com.cc.practicaltest.model.TrimAddonDetails;

@RunWith(MockitoJUnitRunner.class)
public class OrderDetailsServiceImplTest {

	CutomerDetail custobj;
	List<OrderDetails> orders;
	
	
	@Mock
    DriverManagerDataSource dataSource;
    
	@Mock
    Orderdetaildao dao;
   
    @InjectMocks
	OrderDetailsServiceImpl orderService;

	@Before
	public void setUp() {

		custobj = new CutomerDetail();
		custobj.setForename("Joyce");
		custobj.setSurname("Edwards");
		custobj.setCustomer_no(153);
		orders = new ArrayList<OrderDetails>();
	     for(int i =0; i<4; i++) {
	    	 int order_no=1000;
	    	 OrderDetails order = new OrderDetails(); 
	    	 order.setOrder_no(order_no+1);
	    	 order.setDeposit("550");
	    	 order.setSale_price("15000");
	    	 order.setOrder_date("2020-10-10");
	    	 order.setTrim_name("Basic");
	    	 orders.add(order);
	     }
		
		custobj.setOrders(orders);
	}

	@Test
	public void testFindBycustomerId() throws SQLException {

		Mockito.when(dao.getCustomerDetails(153)).thenReturn(custobj);
		CutomerDetail cusobj = orderService.findBycustomerId(153);
		Assert.assertEquals(cusobj.getForename(), custobj.getForename());
	}

	@Test
	public void testFindByOrderId() throws SQLException {
		
		    List<TrimAddonDetails> trimdetailList=setupDataTrimDetails();
		 	Mockito.when(dao.getTrimAddDetail()).thenReturn(trimdetailList);
			Mockito.when(dao.getOrderDetails(153)).thenReturn(orders);
			List<OrderDetails> returnobj=orderService.findByorderId(153);
			Assert.assertEquals(returnobj.size(),orders.size());
	}

	@Test
	public void testGetTrimAddDetail() throws SQLException {
		Map<String, List<TrimAddonDetails>> trimdetails= setupData();
		 List<TrimAddonDetails> trimdetailList=setupDataTrimDetails();
		Mockito.when(dao.getTrimAddDetail()).thenReturn(trimdetailList);
		Map<String, List<TrimAddonDetails>> returnobj=orderService.getTrimAddDetailmap();
		Assert.assertEquals(trimdetails.size(),returnobj.size());
	}

	
	public Map<String, List<TrimAddonDetails>>  setupData() {
		
		Map<String, List<TrimAddonDetails>> trimdetails = new HashMap<String, List<TrimAddonDetails>>();
		
		 for(int i =0; i<3; i++) {
			 
			 List<TrimAddonDetails> temp = new ArrayList<TrimAddonDetails>();
			 for(int j =0; j<2; j++) {
				 TrimAddonDetails obj = new TrimAddonDetails();
				 obj.setEffective_date(Date.valueOf(LocalDate.parse("2019-10-10")));
				 obj.setTrim_name("Basic");
				 obj.setHeadlight_type("Halogen");
				 temp.add(obj);
			 }
			 
			 trimdetails.put("Basic", temp);
		 }
		 
		 return trimdetails;
	}
	
	
	public List<TrimAddonDetails> setupDataTrimDetails() {
		
		 List<TrimAddonDetails> trimdetailList= new ArrayList<TrimAddonDetails>();
			
			 for(int i =0; i<3; i++) {
					 TrimAddonDetails obj = new TrimAddonDetails();
					 obj.setTrim_name("Basic");
					 obj.setHeadlight_type("Halogen");
					 obj.setEffective_date(Date.valueOf(LocalDate.parse("2019-10-10")));
					 trimdetailList.add(obj);
 			 
			 }
			 return trimdetailList;
			
		}

	

}
