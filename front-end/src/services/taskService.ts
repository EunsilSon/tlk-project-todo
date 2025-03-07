declare var axios: any;

const instance = axios.create({
    baseURL: `http://127.0.0.1:8080/`,
    withCredentials: true,
})

export const createTask = async (task: Task) => {
    try {
        const response = await instance.post(`task`, task);
        console.log(response);
        return response;
    } catch (error) {
        console.log(error);
        return error;        
    }
}

export const deleteTask = async (id: string) => {
    try {
        const response = await instance.delete(`task/${id}`);
        console.log(response);
        return response;
    } catch (error) {
        console.log(error);
        return error;        
    }
}

// 달력 렌더링 될 때
export const getMonthlyTaskList = async (year: number, month: number, page: number) => {
    try {
        const response = await instance.get(`/tasks/monthly?year=${year}&month=${month}&page=${page}`);
        if (response.data.status == 200) {
            return response.data.data;
        } else {
            return response.data.message;
        }
    } catch (error) {
        console.log(error);
        return error;
    }
}

// 일자 선택할 때
export const getDaliyTaskList = async (year: number, month: number, day: number, page: number) => {
    try {
        const response = await instance.get(`/tasks/daily?year=${year}&month=${month}&day=${day}&page=${page}`);
        if (response.data.status == 200) {
            return response.data.data;
        } else {
            return response.data.message;
        }
    } catch (error) {
        console.log(error);
        return error;
    }
}