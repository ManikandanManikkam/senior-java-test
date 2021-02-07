package com.cc.practicaltest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cc.practicaltest.model.CutomerDetail;
import com.cc.practicaltest.model.OrderDetails;
import com.cc.practicaltest.service.OrderDetailsService;

@RestController
public class ReteriveOrderDetailsController {
	final static Logger logger = LoggerFactory.getLogger(ReteriveOrderDetailsController.class);
	
	@Autowired
	OrderDetailsService orderService;
	
	
	/*
	 * @Autowired public ReteriveOrderDetailsController(OrderDetailsService
	 * orderService) { this.orderService=orderService; }
	 */

	@RequestMapping(value = "/orderdetails/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CutomerDetail> getOrderDetails(@PathVariable("id") long id) {
		logger.error("Fetching User with id " + id);

		List<OrderDetails> orderList = null;
		CutomerDetail customerDetailsObj = null;
		
		try {
			customerDetailsObj = orderService.findBycustomerId(id);
			if (customerDetailsObj != null) {
				orderList = orderService.findByorderId(id);
				customerDetailsObj.setOrders(orderList);
			}
		} catch (Exception exp  ) {
			// TODO Auto-generated catch block
			logger.error("SQLException occured-->" + exp.getMessage());
			return new ResponseEntity<CutomerDetail>(customerDetailsObj,HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		
		if (customerDetailsObj == null) {
			logger.error("Order with id " + id + " not found");
			return new ResponseEntity<CutomerDetail>(customerDetailsObj,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CutomerDetail>(customerDetailsObj, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = {"/","/orderdetails/"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> doHandle() {
		String error_message= "Requested URL is Not Found";
		return new ResponseEntity<String>(error_message, HttpStatus.NOT_FOUND);
	}


}
