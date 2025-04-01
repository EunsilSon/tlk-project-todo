<<<<<<< HEAD
const instance = axios.create({
    baseURL: `http://127.0.0.1:8080/images`,
    withCredentials: true,
=======
import { config } from "../config.js";
const instance = axios.create({
    baseURL: config.API_BASE_URL + 'attaches',
>>>>>>> front
});
export const deleteImage = async (id) => {
    try {
        const response = await instance.delete(`/${id}`);
        console.log(response);
        return response;
    }
    catch (error) {
        console.log(error.response);
        return error;
    }
};
