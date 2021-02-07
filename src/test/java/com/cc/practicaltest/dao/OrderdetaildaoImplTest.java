package com.cc.practicaltest.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.cc.practicaltest.config.AppConfig;
import com.cc.practicaltest.model.CutomerDetail;
import com.cc.practicaltest.model.OrderDetails;
import com.cc.practicaltest.model.TrimAddonDetails;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class OrderdetaildaoImplTest {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    Orderdetaildao dao;
   
    private static final String EXAMPLE_SQL = "SELECT 1";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void shouldPass() {
        Long result = jdbcTemplate.queryForObject(EXAMPLE_SQL, Long.class);
        assertThat(result).isEqualTo(1);

    }

    
    @Test
    public void getCustomerDetailsTest() throws SQLException {
    	CutomerDetail obj=dao.getCustomerDetails(153);
        assertThat(obj.getForename()).isEqualTo("Joyce");

    }

    @Test
    public void getOrderDetailsTest() throws SQLException {
    	List<OrderDetails> obj=dao.getOrderDetails(153);
        assertThat(obj.size()).isEqualTo(0);

    }

    @Test
    public void getOrderDetailsWithOrderTest() throws SQLException {
    	List<OrderDetails> obj=dao.getOrderDetails(1153);
        assertThat(obj.size()).isEqualTo(5);
        assertThat(obj.get(0).getOrder_no()).isEqualTo(1847);

    }

    @Test
    public void getTrimAddDetaiTest() throws SQLException {
    	List<TrimAddonDetails> obj=dao.getTrimAddDetail();
        assertThat(obj.size()).isEqualTo(5);
        assertThat(obj.get(0).getTrim_name()).isEqualTo("Basic");

    }
    
}


