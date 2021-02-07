package com.cc.practicaltest.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.cc.practicaltest.model.CutomerDetail;
import com.cc.practicaltest.model.OrderDetails;
import com.cc.practicaltest.model.TrimAddonDetails;

public interface OrderDetailsService {
	
	List <OrderDetails> findByorderId(long id) throws SQLException  ;
		
	CutomerDetail findBycustomerId(long id) throws SQLException ;
	
	Map<String,List<TrimAddonDetails>> getTrimAddDetailmap() throws SQLException ;
	
	

}
