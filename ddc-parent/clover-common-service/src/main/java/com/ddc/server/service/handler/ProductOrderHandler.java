package com.ddc.server.service.handler;

import com.ddc.server.entity.Order;
import com.ddc.server.enums.OrderAction;
import com.ddc.server.enums.OrderType;
import com.ddc.server.model.OrderModel;
import com.ddc.server.service.processor.ActionProcessor;
import org.springframework.stereotype.Component;

/**
 * @author dingpengfei
 * @since 2019-05-09
 */
@Component("ProductOrderHandler")
public class ProductOrderHandler extends OrderHandler {

	@Override
	public Order handleInternal(OrderAction action, OrderType orderType, OrderModel orderDef, Order currentOrder) throws Exception {
		return  ActionProcessor.process(action,orderType,orderDef,currentOrder);
	}
}
