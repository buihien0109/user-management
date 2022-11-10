import axios from "axios";

const instance = axios.create({
    baseURL: process.env.REACT_APP_API_DOMAIN,
    headers: { 'Content-Type': 'application/json' }
});

instance.interceptors.response.use(function (response) {
    return response.data;
}, function (error) {
    return Promise.reject(error.response.data);
});

export default instance;