import axios from 'axios';

import Base_URL from "./Base_Url"

const ORDER_API_BASE_URL = Base_URL+'/order';

class OrderService {
    all(){
        return axios.get(ORDER_API_BASE_URL+'/all')
    }
    ofCustomer(userId){
        return axios.get(ORDER_API_BASE_URL+'/customer/'+userId)
    }
    ofHomeMaker(userId){
        return axios.get(ORDER_API_BASE_URL+'/homeMaker/'+userId)
    }

    createOrder(userId,homeMakerId,amount){
        return axios.post(ORDER_API_BASE_URL+'/create_order/'+userId+'/'+homeMakerId,{amount: amount})
    }

    updateOrder(payment_id,order_id,status){
        return axios.post(ORDER_API_BASE_URL+'/update_order',{payment_id: payment_id ,order_id: order_id ,status: status })
    }
}

export default new OrderService();