package com.sky.controller.admin;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminOrderController")
@Slf4j
@Api(tags = "管理端订单接口")
@RequestMapping("admin/order")
public class OrderConrtoller {
    @Autowired
    private OrderService orderService;

    @GetMapping("conditionSearch")
    @ApiOperation("订单条件查询")
    public Result<PageResult> orderPage(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageResult pageResult = orderService.orderPageSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("订单状态统计")
    @GetMapping("/statistics")
    public Result<OrderStatisticsVO> orderStatus() {
        OrderStatisticsVO orderStatisticsVO = new OrderStatisticsVO();
        orderStatisticsVO = orderService.statistics();
        return Result.success(orderStatisticsVO);
    }
    @ApiOperation("查询订单详情")
    @GetMapping("/details/{id}")
    public Result<OrderVO> queryOrderDetail(@PathVariable("id") Long id)
    {
       OrderVO orderVO=orderService.queryOrderDetails(id);
       return Result.success(orderVO);
    }
    @ApiOperation("接单")
    @PutMapping("/confirm")
    public Result confirmOrder(@RequestBody OrdersConfirmDTO ordersConfirmDTO)
    {
        orderService.confirmOrder(ordersConfirmDTO);
        return Result.success();
    }
    @ApiOperation("拒单")
    @PutMapping("rejection")
    public Result rejectOrders(@RequestBody OrdersRejectionDTO ordersRejectionDTO) throws Exception {
        orderService.rejectOrders(ordersRejectionDTO);
        return Result.success();
    }
    @PutMapping("/cancel")
    @ApiOperation("取消订单")
    public Result cancel(@RequestBody OrdersCancelDTO ordersCancelDTO) throws Exception {
        orderService.cancel(ordersCancelDTO);
        return Result.success();
    }
    @PutMapping("/delivery/{id}")
    @ApiOperation("派送订单")
    public Result delivery(@PathVariable("id") Long id) {
        orderService.delivery(id);
        return Result.success();
    }
    @PutMapping("/complete/{id}")
    @ApiOperation("完成订单")
    public Result complete(@PathVariable("id") Long id) {
        orderService.complete(id);
        return Result.success();
    }
}
