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