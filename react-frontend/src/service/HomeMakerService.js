import axios from 'axios';
import Base_URL from "./Base_Url"

const HOMEMAKER_API_BASE_URL = Base_URL+'/homeMaker';

class HomeMakerService {

    authenticateUser(email, password){
        return axios.get(HOMEMAKER_API_BASE_URL+ '/login/' + email + '/' + password)
    }

    addUser(user){
        return axios.post(HOMEMAKER_API_BASE_URL + '/signup/',user)
    }

    getUser(userId){
        return axios.get(HOMEMAKER_API_BASE_URL+'/getHomeMaker/'+userId)
    }

    deleteUser(userId) {
        return axios.delete(HOMEMAKER_API_BASE_URL + '/deleteHomeMaker/' + userId);
    }

    updateUserDetails(user) {
        return axios.put(HOMEMAKER_API_BASE_URL + '/updateUserDetails', user);
    }

    getAllHomeMakers() {
        return axios.get(HOMEMAKER_API_BASE_URL + "/get-all-home-makers")
    }

    getMyCustomers(id){
        return axios.get(HOMEMAKER_API_BASE_URL+'/getMyCustomers/'+id)
    }

    getAllCities(){
        return axios.get(HOMEMAKER_API_BASE_URL+'/cities')
    }

    homeMakersByCity(city){
        return axios.get(HOMEMAKER_API_BASE_URL+'/homeMakersByCity/'+city)
    }
}

export default new HomeMakerService();