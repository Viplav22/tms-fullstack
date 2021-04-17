package com.app.service;

import java.util.List;

import com.app.pojos.Orders;

public interface IOrdersService {
	List <Orders> getAllOrders();

	List <Orders> getCustomerOrders(int cutId);
	
	List<Orders> getHomeMakerOrders(int hmId);

	 void saveOrderDetails(Orders tiffinOrders);
}
