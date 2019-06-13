package com.ddc.server.service.handler;

import com.ddc.server.base.BusinessException;
import com.ddc.server.entity.Order;
import com.ddc.server.enums.OrderAction;
import com.ddc.server.enums.OrderStatus;
import com.ddc.server.enums.OrderType;
import com.ddc.server.mapper.OrderMapper;
import com.ddc.server.model.OrderModel;
import com.ddc.server.service.SpringContextBeanService;
import com.ddc.server.util.ComUtil;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import javax.annotation.Resource;

/**
 * @author dingpengfei
 * @since 2019-05-09
 */
public abstract class OrderHandler {

  public static OrderHandler getHandler(OrderType orderType) throws Exception {
    if (ComUtil.isEmpty(orderType)) {
      throw new BusinessException("订单类型未找到");
    }
    String beanName = orderType.name() + OrderHandler.class.getSimpleName();
    OrderHandler handler = null;
    try {
      handler = SpringContextBeanService.getBean(beanName);
    } catch (NoSuchBeanDefinitionException e) {
      throw new BusinessException("未找到对应的订单处理器:" + beanName);
    }
    return handler;
  }

  public static Order handle(OrderAction action, OrderType orderType, OrderModel orderDef)
      throws Exception {
    return getHandler(orderType).doHandle(action, orderType, orderDef);
  }

  @Resource
  private OrderMapper orderMapper;

  public Order doHandle(OrderAction action, OrderType orderType, OrderModel orderModel)
      throws Exception {
    if (ComUtil.isEmpty(action)
        || ComUtil.isEmpty(orderType)
        || ComUtil.isEmpty(orderModel)
        || ComUtil.isEmpty(orderModel.getOrderNo())) {
      throw new BusinessException("数据验证失败");
    }
    Order order = new Order();
    order.setOrderNo(orderModel.getOrderNo());
    Order currentOrder = orderMapper.selectOne(order);
    if (ComUtil.isEmpty(currentOrder)) {
        throw new BusinessException("未查询到数据");
    }
    OrderStatus nextStatus = orderType.getStatusHolder().getByAction(action);
    OrderStatus prevStatus = orderType.getStatusHolder().getByName(currentOrder.getOrderStatus());

    if (!prevStatus.canTransformTo(nextStatus)) {
        throw new BusinessException("订单不能由" + prevStatus.getName() + "转变为" + nextStatus.getName());
    }
    orderModel.setOrderStatus(nextStatus.getName());
    return this.handleInternal(action, orderType, orderModel, currentOrder);
  }

  public abstract Order handleInternal(
      OrderAction action, OrderType orderType, OrderModel orderDef, Order currentOrder)
      throws Exception;
}
