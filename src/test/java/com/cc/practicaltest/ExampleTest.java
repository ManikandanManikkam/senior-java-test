package com.cc.practicaltest;

import static org.mockito.Mockito.mock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.cc.practicaltest.controller.ReteriveOrderDetailsController;
import com.cc.practicaltest.dao.Orderdetaildao;
import com.cc.practicaltest.model.CutomerDetail;
import com.cc.practicaltest.model.OrderDetails;
import com.cc.practicaltest.service.OrderDetailsServiceImpl;



@RunWith(MockitoJUnitRunner.class)

public class ExampleTest {

    CutomerDetail custobj;
    
    @Mock
    Orderdetaildao dao;
    @Mock
    DriverManagerDataSource dataSource;
    
    @Mock
    OrderDetailsServiceImpl orderService;
 
   @InjectMocks
   ReteriveOrderDetailsController orderController;
    
    @Before
	public void setUp() {
		custobj = new CutomerDetail();
		custobj.setForename("Joyce");
		custobj.setSurname("Edwards");
		custobj.setCustomer_no(153);
		List<OrderDetails> orders = new ArrayList<OrderDetails>();
		custobj.setOrders(orders);
	}
    
    @Test
    public void TestCasegetOrderDetails() throws SQLException {
         Mockito.when(orderService.findBycustomerId(153)).thenReturn(custobj);
       	 ResponseEntity<CutomerDetail> responseEntity = orderController.getOrderDetails(153);
    	 Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    	 Assert.assertEquals(custobj.getForename(),responseEntity.getBody().getForename());
    	 Assert.assertEquals(custobj.getOrders(),responseEntity.getBody().getOrders());
      }
    
    @Test
    public void TestCasegetOrderDetailsNull() throws SQLException {
    	custobj=null;
         Mockito.when(orderService.findBycustomerId(153)).thenReturn(custobj);
    	 ResponseEntity<CutomerDetail> responseEntity = orderController.getOrderDetails(153);
    	 Assert.assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
    	 Assert.assertEquals(custobj,null);
    	
      }
    @Test
    public void TestCasegetOrderDetailsOrdersNull() throws SQLException {
    	
         Mockito.when(orderService.findBycustomerId(153)).thenReturn(custobj);
         Mockito.when(orderService.findByorderId(153)).thenReturn(null);
    	 ResponseEntity<CutomerDetail> responseEntity = orderController.getOrderDetails(153);
    	 Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    	 Assert.assertEquals(custobj.getForename(),responseEntity.getBody().getForename());
    	 Assert.assertEquals(custobj.getOrders(),null);
    	
      }
    
    @Test
    public void TestCase2doHandle() throws SQLException {
    	
         ResponseEntity<String> responseEntitysource= new ResponseEntity<String>("Requested URL is Not Found", HttpStatus.NOT_FOUND);
         ReteriveOrderDetailsController orderController = mock(ReteriveOrderDetailsController.class);
       	 Mockito.when(orderController.doHandle()).thenReturn(responseEntitysource);
      	 ResponseEntity<String> responseEntity = orderController.doHandle();
    	 Assert.assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
         Assert.assertEquals("Requested URL is Not Found",responseEntity.getBody());
        
    }
    
    @Test
    public void TestCasegetOrderCheckExpection() throws SQLException {
         Mockito.when(orderService.findBycustomerId(153)).thenThrow(SQLException.class);
       	 ResponseEntity<CutomerDetail> responseEntity = orderController.getOrderDetails(153);
    	 Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,responseEntity.getStatusCode());
    	 
      }
    
    
    
  
        
       
}