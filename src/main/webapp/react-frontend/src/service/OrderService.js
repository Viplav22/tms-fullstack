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
}

export default new OrderService();