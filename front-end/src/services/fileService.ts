declare var axios: any;

const instance = axios.create({
    baseURL: `http://127.0.0.1:8080/attaches`,
    withCredentials: true,
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