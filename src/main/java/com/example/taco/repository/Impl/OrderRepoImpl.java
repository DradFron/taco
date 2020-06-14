package com.example.taco.repository.Impl;

import com.example.taco.pojo.Order;
import com.example.taco.pojo.Taco;
import com.example.taco.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepoImpl implements OrderRepository {
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public OrderRepoImpl(JdbcTemplate jdbcTemplate) {
        this.orderInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");
        this.orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Taco_Order_Tacos");
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        for(Taco taco : tacos){
            saveTacoToOrder(taco,orderId);
        }
        return order;
    }

    private long saveOrderDetails(Order order){
        @SuppressWarnings("unchecked")
        Map<String,Object> values = objectMapper.convertValue(order,Map.class);
        values.put("placed_at",order.getPlacedAt());
        values.put("phone_number",order.getPhoneNumber());
        values.put("delivery_name",order.getDeliveryName());
        values.put("delivery_street",order.getDeliveryStreet());
        values.put("delivery_city",order.getDeliveryCity());
        values.put("cc_number",order.getCcNumber());
        long orderId = orderInserter
                .executeAndReturnKey(values)
                .longValue();
        return orderId;
    }

    private void saveTacoToOrder(Taco taco,long orderId) {
        Map<String,Object> values = new HashMap<>();
        values.put("taco_order",orderId);
        values.put("taco",taco.getId());
        orderTacoInserter.execute(values);
    }
}
