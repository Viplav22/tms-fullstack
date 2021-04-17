package com.app.controller;

import java.security.Principal;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dao.OrdersRepository;
import com.app.dto.CustomerDTO;
import com.app.dto.ResponseDTO;
import com.app.pojos.Customer;
import com.app.pojos.Orders;
import com.app.service.ICustomerService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@CrossOrigin
@RestController // Mandatory class level annotation
//@RestController => @Controller on the cls level + @ResponseBody on the ret types of 
//all req handling methods, annotated with @RequestMapping / @GetMapping.....
@RequestMapping("/customer") // optional : Resource
public class CustomerController {
	// dependency
	@Autowired // autowire=byType}
	private ICustomerService customerService;

	@Autowired // used in razorpay(payment method)
	private OrdersRepository ordersRepository;

	public CustomerController() {
		super();
		System.out.println("in ctor of " + getClass().getName() + " " + customerService);
	}

	// add REST clnt request handling method : for authenticating customer
	@GetMapping("/login/{email}/{password}")
	public ResponseEntity<?> authenticateCustomer(@PathVariable String email, @PathVariable String password) {
		System.out.println("in authenticate customer " + email + " " + password);
		Customer customer = customerService.authenticateCustomer(email, password);
		if (customer == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return ResponseEntity.ok(customer);
	}

	// add REST clnt request handling method : for signing up customer
	@PostMapping("/signup")
	public ResponseEntity<?> signUpCustomer(@RequestBody Customer customer) {
		System.out.println("In customer signup" + customer);
		CustomerDTO dto = customerService.signUpCustomer(customer);
		if (dto == null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return ResponseEntity.ok(new ResponseDTO<>(dto));
	}

	// Add REST request handling method to update user details
	@PutMapping("/updateUserDetails")
	public ResponseEntity<?> updateUserDetails(@RequestBody CustomerDTO customerDTO) {
		System.out.println("in  update details " + customerDTO);
		return ResponseEntity.ok(new ResponseDTO<>(customerService.updateUserDetails(customerDTO)));
	}

	// REST request handling method to delete user details
	@DeleteMapping("/deleteCustomer/{custId}")
	public ResponseEntity<?> deleteUserDetails(@PathVariable int custId) {
		System.out.println("in del user dtls " + custId);
		return ResponseEntity.ok(new ResponseDTO<>(customerService.deleteUserDetails(custId)));
	}

	// add Rest clnt request handling method : for getting all Customers
	@GetMapping("/getAllCustomers")
	public ResponseEntity<?> getAllCustomers() {
		System.out.println("in get All Customers");
		return ResponseEntity.ok(new ResponseDTO<>(customerService.getAllCustomers()));
	}

	// add req handing method for adding homeMaker to customer
	@PutMapping("/select-homeMaker/{hmId}/{custId}")
	public ResponseEntity<?> addMyHomeMaker(@PathVariable String hmId, @PathVariable String custId) {
		System.out.println("In addHomeMaker" + " " + hmId + " " + custId);
		return ResponseEntity.ok(
				new ResponseDTO<>(customerService.addMyHomeMaker(Integer.parseInt(hmId), Integer.parseInt(custId))));
	}

	// add req handing method for getting selected homeMaker
	@GetMapping("/getMyHomeMaker/{custId}")
	public ResponseEntity<?> getMyHomeMaker(@PathVariable String custId) {
		System.out.println("in getMyHomeMaker " + custId);
		return ResponseEntity.ok(new ResponseDTO<>(customerService.getMyHomeMaker(Integer.parseInt(custId))));

	}

	// add req handing method for removing homeMaker linked to customer
	@DeleteMapping("/removeMyHomeMaker/{custId}")
	public ResponseEntity<?> removeMyHomeMaker(@PathVariable String custId) {
		System.out.println("in removeMyHomeMaker " + custId);
		return ResponseEntity.ok(customerService.removeMyHomeMaker(Integer.parseInt(custId)));
	}

	// add req handing method for updating food Plans
	@PutMapping("/updatePackage/{planType}/{pack}")
	public ResponseEntity<?> updatePackage(@PathVariable String planType, @PathVariable String pack,
			@RequestBody Customer customer) {
		System.out.println("in updatePackage : " + planType + " : " + pack + " : " + customer);
		return ResponseEntity.ok(new ResponseDTO<>(customerService.updatePackage(planType, pack, customer)));
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

		this.ordersRepository.save(tiffinOrders); // saving details

		// sending this order to client (converting order(json) to string)
		return order.toString();
	}

	// add req handing method for updating order status
	@PostMapping("/update_order")
	public ResponseEntity<?> updateOrder(@RequestBody Map<String, Object> data) {

		System.out.println("in updateOrder ");

		// taking updated data from frontend server updating data
		Orders tiffinOrders = this.ordersRepository.findByOrderId(data.get("order_id").toString());
		// getting order(tiffinOrders) from orderId

		tiffinOrders.setPaymentId(data.get("payment_id").toString());
		tiffinOrders.setStatus(data.get("status").toString());

		this.ordersRepository.save(tiffinOrders);

		System.out.println(data);

		return ResponseEntity.ok("done"); // Message
	}

	// add Rest clnt request handling method : for getting all Orders of the
	// Customer By ID
	@GetMapping("/getAllOrders/{custId}")
	public ResponseEntity<?> getAllOrders(@PathVariable String custId) {
		System.out.println("in get All Orders : " + custId);
		return ResponseEntity.ok(new ResponseDTO<>(ordersRepository.getAllCustomerById(Integer.parseInt(custId))));
	}
}