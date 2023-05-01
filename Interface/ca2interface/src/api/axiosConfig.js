import axios from "axios";

export default axios.create({
    baseURL: "http://localhost:8080/f",
    header: {'Content-Type': 'multipart/form-data'}
});