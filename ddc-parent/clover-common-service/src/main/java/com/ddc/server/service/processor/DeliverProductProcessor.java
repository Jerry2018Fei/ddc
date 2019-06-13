package com.ddc.server.service.processor;

import com.ddc.server.entity.Order;
import com.ddc.server.mapper.OrderMapper;
import com.ddc.server.model.OrderModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 订单发货处理器
 * @author dingpengfei
 * @since 2019-05-09
 */
@Component("deliverProductProcessor")
public class DeliverProductProcessor extends ActionProcessor{

	@Override
	public Order process(OrderModel orderDef, Order currentOrder) throws Exception {
		return this.deliver(orderDef,currentOrder);
	}

	@Resource

    OrderMapper orderMapper;
	/**
	 * 订单发货
	 * @param currentOrder
	 * @return
	 */
	 private Order deliver(OrderModel orderModel, Order currentOrder) throws Exception {
		//更新订单信息
		currentOrder.setOrderStatus(orderModel.getOrderStatus());
		currentOrder.setCreateTime(System.currentTimeMillis());
		 orderMapper.updateById(currentOrder);
		return currentOrder;
	}
}
