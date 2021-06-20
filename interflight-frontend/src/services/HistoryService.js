import axios from 'axios';

const HISTORY_REST_API = "http://localhost:8080/history/getAllPlanes"
class HistoryService{
    getFlights(){
        console.log(axios.get(HISTORY_REST_API));
        return axios.get(HISTORY_REST_API);
    }
}
export default new HistoryService();