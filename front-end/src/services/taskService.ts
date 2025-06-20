declare var axios: any;
import { config } from "../config.js";

const instance = axios.create({
    baseURL: config.API_BASE_URL + 'tasks',
})

export const createTask = async (formData: FormData) => {
    try {
        const response = await instance.post("", formData, {
            headers: { "Content-Type": "multipart/form-data" },
        });
        return response.data;
    } catch (error: any) {
        console.log(error.response);
        return error;
    }
}

export const deleteTask = async (id: string) => {
    try {
        const response = await instance.delete(`/${id}`);
        console.log(response);
        return response;
    } catch (error: any) {
        console.log(error.response);
        return error;
    }
}

/* 특정 달력의 모든 task 조회 */
export const getMonthlyTaskList = async (year: number, month: number, page: number) => {
    try {
        const response = await instance.get(`/monthly?year=${year}&month=${month}&page=${page}`);
        if (response.status == 200) {
            console.log(response.data.data);
            return response.data.data;
        } else {
            console.log(response.data.message);
            return response.data.message;
        }
    } catch (error: any) {
        console.log(error.response);
        return error;
    }
}

/* 특정 날짜의 task 조회 */
export const getDailyTaskList = async (year: number, month: number, day: string, page: number) => {
    try {
        const response = await instance.get(`/daily?year=${year}&month=${month}&day=${day}&page=${page}`);
        if (response.data.status == 200) {
            console.log(response.data.data);
            return response.data.data;
        } else {
            return response.data.message;
        }
    } catch (error: any) {
        console.log(error.response);
        return error;
    }
}

/* task 개수 조회 */
export const getTaskCount = async (year: number, month: number) => {
    try {
        const response = await instance.get(`/count?year=${year}&month=${month}`);
        if (response.data.status == 200) {
            return response.data.data;
        } else {
            return response.data.message;
        }
    } catch (error: any) {
        console.log(error.response);
        return error;
    }
}