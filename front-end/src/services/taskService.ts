declare var axios: any;

const instance = axios.create({
    baseURL: `http://127.0.0.1:8080/`,
    withCredentials: true,
})

export const createTask = async (task: NewTask) => {
    try {
        const response = await instance.post(`task`, task);
        console.log(response.data);
        return response.data;
    } catch (error: any) {
        console.log(error.response);
        return error;
    }
}

export const updateTask = async (task: Task) => {
    try {
        const response = await instance.put(`task`, task);
        console.log(response);
        return response;
    } catch (error: any) {
        console.log(error.response);
        return error;
    }
}

export const deleteTask = async (id: string) => {
    try {
        const response = await instance.delete(`task/${id}`);
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
        const response = await instance.get(`/tasks/monthly?year=${year}&month=${month}&page=${page}`);
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
export const getDaliyTaskList = async (year: number, month: number, day: string, page: number) => {
    try {
        const response = await instance.get(`/tasks/daily?year=${year}&month=${month}&day=${day}&page=${page}`);
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

/*  단일 task 조회 */
export const getTaskDetail = async (id: string) => {
    try {
        const response = await instance.get(`/task/${id}`);
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
        const response = await instance.get(`/task/count?year=${year}&month=${month}`);
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