package com.cc.practicaltest.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.cc.practicaltest.contstant.SQLConstant;
import com.cc.practicaltest.model.CutomerDetail;
import com.cc.practicaltest.model.OrderDetails;
import com.cc.practicaltest.model.TrimAddonDetails;

@Repository("Orderdetaildao")
public class OrderdetaildaoImpl extends JdbcDaoSupport implements Orderdetaildao {

	@Autowired
	public OrderdetaildaoImpl(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	@Override
	public CutomerDetail getCustomerDetails(long custId) throws SQLException {
		CutomerDetail cutomerDetailObj = null;
		String queryString = SQLConstant.CUSTOMER_DETAILS_QUERY;
		RowMapper<CutomerDetail> rowMapper = rowmapperCustomerDetails();
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		if (jdbcTemplate != null) {
			cutomerDetailObj = jdbcTemplate.queryForObject(queryString, new Object[] { custId }, rowMapper);
		}
		return cutomerDetailObj;

	}

	private RowMapper<CutomerDetail> rowmapperCustomerDetails() {
		RowMapper<CutomerDetail> rowMapper = (rs, rowNum) -> {
			CutomerDetail data = new CutomerDetail();
			data.setCustomer_no(rs.getInt("customer_number"));
			data.setForename(rs.getString("forename"));
			data.setSurname(rs.getString("surname"));
			return data;
		};
		return rowMapper;
	}

	@Override
	public List<OrderDetails> getOrderDetails(long custId) throws SQLException {

		String order_query = String.valueOf(SQLConstant.ORDER_DETAILS_QUERY);
		List<OrderDetails> orderDetailList = new ArrayList<OrderDetails>();

		RowMapper<OrderDetails> rowMapper = rowmapperOrderDetails();

		PreparedStatementSetter obj = new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setLong(1, custId);
			}
		};

		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		if (jdbcTemplate != null) {
			orderDetailList = jdbcTemplate.query(order_query, obj, rowMapper);
		}
		return orderDetailList;

	}

	
	@Override
	public List<TrimAddonDetails> getTrimAddDetail() throws SQLException {
		List<TrimAddonDetails> trimAddonDetailsList = new ArrayList<TrimAddonDetails>();
		String order_query = SQLConstant.TRIM_DETAILS_QUERY;
		RowMapper<TrimAddonDetails> rowMapper = rowmapperTrimDetails();
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		if (jdbcTemplate != null) {
			trimAddonDetailsList = jdbcTemplate.query(order_query, rowMapper);
		}

		return trimAddonDetailsList;

	}
	
	
	private RowMapper<OrderDetails> rowmapperOrderDetails() {
		RowMapper<OrderDetails> rowMapper = (rs, rowNum) -> {
			OrderDetails data = new OrderDetails();
			data.setActual_delivery_type(rs.getString("actual_delivery_date"));
			data.setEmployee_number(rs.getInt("employee_number"));
			data.setExpected_delivery_type(rs.getString("expected_delivery_date"));
			data.setDeposit(rs.getString("deposit"));
			data.setSale_price(rs.getString("sale_price"));
			data.setVechile_no(rs.getLong("vehicle_number"));
			data.setModel_name(rs.getString("model_name"));
			data.setEngine_model(rs.getString("engine_designation"));
			data.setTrim_name(rs.getString("trim_name"));
			data.setOrder_no(rs.getInt("order_number"));
			data.setOrder_date(rs.getString("order_date"));
			data.setVechileColour(rs.getString("colour"));
			data.setEngine_capacity(rs.getString("capacity"));
			data.setVechile_fuelType(rs.getString("fuel_type"));
			return data;
		};
		return rowMapper;
	}


	private RowMapper<TrimAddonDetails> rowmapperTrimDetails() {
		RowMapper<TrimAddonDetails> rowMapper = (rs, rowNum) -> {
			TrimAddonDetails data = new TrimAddonDetails();
			data.setEffective_date(rs.getDate("effective_date"));
			data.setInfotainment_type(rs.getString("infotainment_type"));
			data.setUpholstery_type(rs.getString("upholstery_type"));
			data.setWheel_type(rs.getString("wheel_type"));
			data.setTrim_name(rs.getString("trim_name"));
			data.setHeadlight_type(rs.getString("headlight_type"));
			return data;

		};
		return rowMapper;
	}

}
