package com.sky.controller.user;

import com.sky.constant.MessageConstant;
import com.sky.dto.OrdersDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
@Slf4j
@RequestMapping("user/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/submit")
    public Result<OrderSubmitVO> submit (@RequestBody OrdersSubmitDTO ordersSubmitDTO)
    {
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);

    }
    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        return Result.success(orderPaymentVO);
    }
    @GetMapping("/historyOrders")
    @ApiOperation("历史订单数据查询")
    public Result<PageResult> pageList(int page,int pageSize,Integer status)
    {
        PageResult pageResult=orderService.pageQuery(page,pageSize,status);
        return Result.success(pageResult);
    }
    @GetMapping("orderDetail/{id}")
    @ApiOperation("订单详情查询")
    public Result<OrderVO> queryOrders(@PathVariable("id") Long id) {
        OrderVO orderVO = orderService.selectById(id);
        return Result.success(orderVO);
    }
    @ApiOperation("用户取消下单")
    @PutMapping("cancel/{id}")

    public Result cancelOrders(@PathVariable("id") Long id) throws Exception {
        orderService.cancelOrders(id);
        return Result.success();
    }

    @ApiOperation("再来一单")
    @PostMapping("repetition/{id}")
    public Result orderRepetion(@PathVariable("id") Long id)
    {
        orderService.orderRepetion(id);
        return Result.success();
    }
}
