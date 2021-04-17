package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.OrdersRepository;
import com.app.pojos.Orders;

@Service
@Transactional
public class OrdersSerivceImpl implements IOrdersService {

	@Autowired
	private OrdersRepository orderRepo;

	@Override
	public List<Orders> getAllOrders() {
		return orderRepo.findAll();
	}

	@Override
	public List<Orders> getCustomerOrders(int cutId) {
		return orderRepo.getAllCustomerById(cutId);
	}
	
	@Override
	public List<Orders> getHomeMakerOrders(int hmId) {
		return orderRepo.getAllHomeMakerById(hmId);
	}
}
