package com.cc.practicaltest.contstant;

public interface SQLConstant {
	
	public static StringBuilder ORDER_DETAILS_QUERY= new StringBuilder("SELECT ords.order_number as 'order_number',sale_price,deposit,order_date,vehicle_number,"
			  + " model_name,ordv.engine_designation as 'engine_designation' ,colour," +
			  " expected_delivery_date,actual_delivery_date , employee_number, capacity,fuel_type,trim_name"
			  + " FROM orders ords INNER JOIN ordered_vehicles ordv ON ords.order_number = ordv.order_number"
			  + " INNER JOIN engines en on ordv.engine_designation=en.engine_designation" +
			  " where ords.customer_number =?");

	public static String CUSTOMER_DETAILS_QUERY ="select customer_number,forename, surname from customers where customer_number= ?";
	
	public static String TRIM_DETAILS_QUERY= "select trim_name,effective_date,wheel_type,infotainment_type,headlight_type,upholstery_type from trim_equipment";

}
