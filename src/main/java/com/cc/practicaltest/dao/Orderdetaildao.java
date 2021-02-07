package com.cc.practicaltest.dao;

import java.sql.SQLException;
import java.util.List;

import com.cc.practicaltest.model.CutomerDetail;
import com.cc.practicaltest.model.OrderDetails;
import com.cc.practicaltest.model.TrimAddonDetails;

public interface Orderdetaildao {

	CutomerDetail getCustomerDetails(long custId) throws SQLException;

	List<OrderDetails> getOrderDetails(long custId) throws SQLException;

	List<TrimAddonDetails> getTrimAddDetail() throws SQLException;

}