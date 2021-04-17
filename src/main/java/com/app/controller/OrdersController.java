package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ResponseDTO;
import com.app.service.IOrdersService;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrdersController {

	@Autowired
	private IOrdersService orderService;

	public OrdersController() {
		super();
		System.out.println("in ctor of " + getClass().getName() + " " + orderService);
	}

	// add Rest clnt request handling method : for getting all Orders
	@GetMapping("/all")
	public ResponseEntity<?> getAllOrders() {
		orderService.getAllOrders().forEach(System.out::println);
		return ResponseEntity.ok(orderService.getAllOrders());
	}

	// add Rest clnt request handling method : for getting all Orders of the
	// Customer By ID
	@GetMapping("/customer/{custId}")
	public ResponseEntity<?> getCustomerOrders(@PathVariable String custId) {
		System.out.println("in get customer Orders : " + custId);
		return ResponseEntity.ok(new ResponseDTO<>(orderService.getCustomerOrders(Integer.parseInt(custId))));
	}

	// add Rest clnt request handling method : for getting all Orders of the
	// HomeMaker By ID
	@GetMapping("/homeMaker/{hmId}")
	public ResponseEntity<?> getHomeMakerOrders(@PathVariable String hmId) {
		System.out.println("in get homemaker Orders : " + hmId);
		return ResponseEntity.ok(new ResponseDTO<>(orderService.getHomeMakerOrders(Integer.parseInt(hmId))));
	}

}