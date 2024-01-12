import axios from 'axios';
const BASE_URL = 'https://dal-clubs.onrender.com';

axios.defaults.baseURL = BASE_URL;
axios.defaults.headers.common['Content-Type'] = 'application/json;charset=utf-8';

export default axios;

export const axiosPrivate = axios.create({
    baseURL: BASE_URL,
    withCredentials: true
});