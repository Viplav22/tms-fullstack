package com.app.controller;

import java.security.Principal;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dao.OrdersRepository;
import com.app.dto.ResponseDTO;
import com.app.pojos.Orders;
import com.app.service.IOrdersService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrdersController {

	@Autowired
	private IOrdersService orderService;
	
	@Autowired
	private OrdersRepository orderRepo;

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
	
	// add req handing method for adding order & payment details in orders table
	@PostMapping("/create_order/{custId}/{homeMakerId}") // we want only to send string not viewname so use
															// @ResponseBody
	public String createOrder(@PathVariable String custId, @PathVariable String homeMakerId,
			@RequestBody Map<String, Object> data, Principal principal) throws Exception {
		System.out.println("in createOrder : " + data);

		int amt = Integer.parseInt(data.get("amount").toString()); // taking data

		// razor pay
		RazorpayClient client = new RazorpayClient("rzp_test_wUqZzqgwq0QV5o", "67tidmwHy3GcfbvGBRlyqKYE");

		JSONObject ob = new JSONObject();
		ob.put("amount", amt * 100); // converting amount into paise
		ob.put("currency", "INR");
		ob.put("receipt", "txn_123456");

		// creating new order
		Order order = client.Orders.create(ob); // Order for razorpayAPI
		System.out.println(order);

		// save order to db

		Orders tiffinOrders = new Orders(); // Orders from pojos

		tiffinOrders.setAmount(order.get("amount") + ""); // order -->razorpay ,+ "" for making string
		tiffinOrders.setOrderId(order.get("id")); // order_no_id
		tiffinOrders.setPaymentId(null);
		tiffinOrders.setDateTime();
		tiffinOrders.setStatus("unsuccessful");
		tiffinOrders.setReceipt(order.get("receipt"));
		tiffinOrders.setCustomerId(Integer.parseInt(custId));
		tiffinOrders.setHomeMakerId(Integer.parseInt(homeMakerId));
		// for customer
		// tiffinOrders.setCustomerId(this.customerRepository.get

		orderService.saveOrderDetails(tiffinOrders); // saving details

		// sending this order to client (converting order(json) to string)
		return order.toString();
	}

	// add req handing method for updating order status
	@PostMapping("/update_order")
	public ResponseEntity<?> updateOrder(@RequestBody Map<String, Object> data) {

		System.out.println("in updateOrder ");

		// taking updated data from frontend server updating data
		Orders tiffinOrders = orderRepo.findByOrderId(data.get("order_id").toString());
		// getting order(tiffinOrders) from orderId

		tiffinOrders.setPaymentId(data.get("payment_id").toString());
		tiffinOrders.setStatus(data.get("status").toString());

		orderService.saveOrderDetails(tiffinOrders);

		System.out.println(data);

		return ResponseEntity.ok("done"); // Message
	}

}