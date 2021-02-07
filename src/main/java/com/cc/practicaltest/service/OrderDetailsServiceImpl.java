package com.cc.practicaltest.service;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.cc.practicaltest.dao.Orderdetaildao;
import com.cc.practicaltest.model.CutomerDetail;
import com.cc.practicaltest.model.OrderDetails;
import com.cc.practicaltest.model.TrimAddonDetails;

@Component
@Service("orderServiceImpl")
@Transactional
public class OrderDetailsServiceImpl implements OrderDetailsService {

	@Autowired
	private Orderdetaildao dao;


	@Override
	public List<OrderDetails> findByorderId(long id) throws SQLException {
		// TODO Auto-generated method stub
		List<OrderDetails> order_List = dao.getOrderDetails(id);

		Map<String, List<TrimAddonDetails>> trimDetails = getTrimAddDetailmap();

		mapTrimDetails(trimDetails, order_List);

		return order_List;
	}

	@Override
	public CutomerDetail findBycustomerId(long id) throws SQLException {
		// TODO Auto-generated method stub
		return dao.getCustomerDetails(id);
	}

	@Override
	public Map<String, List<TrimAddonDetails>> getTrimAddDetailmap() throws SQLException {

		List<TrimAddonDetails> trim_List = dao.getTrimAddDetail();

		Map<String, List<TrimAddonDetails>> trimDetails = trim_List.stream()
				.collect(Collectors.groupingBy(TrimAddonDetails::getTrim_name));

		return trimDetails;
	}

	protected void mapTrimDetails(Map<String, List<TrimAddonDetails>> trimDetail, List<OrderDetails> order_List) {
		Map<String, List<TrimAddonDetails>> trimDetail2 = trimDetail;
		if (CollectionUtils.isEmpty(trimDetail2) || CollectionUtils.isEmpty(order_List)) {
			return;
		} else {
			order_List.forEach(orderobj -> {
				List<TrimAddonDetails> trimDetailsList = trimDetail2.get(orderobj.getTrim_name());
				TrimAddonDetails latesttrim = Collections.max(trimDetailsList,
						Comparator.comparing(TrimAddonDetails::getEffective_date));
				TrimAddonDetails oldttrim = Collections.min(trimDetailsList,
						Comparator.comparing(TrimAddonDetails::getEffective_date));
				Date order_date = Date.valueOf(LocalDate.parse(orderobj.getOrder_date()));
				if (order_date.after(latesttrim.getEffective_date())) {
					populateTrimDetails(orderobj, latesttrim);
				} else {
					populateTrimDetails(orderobj, oldttrim);
				}
			});
		}
	}

	protected void populateTrimDetails(OrderDetails orderobj, TrimAddonDetails latesttrim) {
		orderobj.setHeadLight_type(latesttrim.getHeadlight_type());
		orderobj.setInfoentainment_type(latesttrim.getInfotainment_type());
		orderobj.setTrim_WheelType(latesttrim.getWheel_type());
		orderobj.setUpholstery_type(latesttrim.getUpholstery_type());
	}

}
