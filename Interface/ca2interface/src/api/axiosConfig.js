import axios from "axios";

export default axios.create({
    baseURL: "http://localhost/api",
    header: {'Content-Type': 'multipart/form-data'}
});