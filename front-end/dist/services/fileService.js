import { config } from "../config.js";
const instance = axios.create({
    baseURL: config.API_BASE_URL + 'attaches',
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
