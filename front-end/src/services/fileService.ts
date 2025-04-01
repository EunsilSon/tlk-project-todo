declare var axios: any;
import { config } from "../config.js";

const instance = axios.create({
    baseURL: config.API_BASE_URL + 'attaches',
})

export const deleteImage = async (id: string) => {
    try {
        const response = await instance.delete(`/${id}`);
        console.log(response);
        return response;
    } catch (error: any) {
        console.log(error.response);
        return error;
    }
}