import axios from "axios";

export default axios.create({
    baseURL: "/api",
    header: {'Content-Type': 'multipart/form-data'}
});