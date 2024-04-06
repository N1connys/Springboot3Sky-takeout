package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j

public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;
    @Scheduled(cron = "0 * * * * ?")
    public void ScheduledTasks() {
        //每隔一分钟查看订单是否超时
        //select * from order where status=已付款 and localdatetime-15min>ordertime
        LocalDateTime LimitTime = LocalDateTime.now().plusMinutes(-15);
        List<Orders> ordersList = orderMapper.OrderTimeout(Orders.PENDING_PAYMENT, LimitTime);
        if (ordersList != null && ordersList.size() > 0) {
            ordersList.forEach(orders -> {
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelTime(LocalDateTime.now());
                orders.setCancelReason("订单取消，已超时");
                orderMapper.update(orders);
            });
        }
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void ScheduledDeliveryTasks() {
        //凌晨一点查看是否未点击完成订单
        //select * from order where status=已付款 and localdatetime-15min>ordertime
        LocalDateTime time= LocalDateTime.now().plusMinutes(-30);
        List<Orders> ordersList = orderMapper.OrderTimeout(Orders.DELIVERY_IN_PROGRESS, time);
        if (ordersList != null && ordersList.size() > 0) {
            ordersList.forEach(orders -> {
                orders.setStatus(Orders.COMPLETED);
                orderMapper.update(orders);
            });
        }
    }
}
