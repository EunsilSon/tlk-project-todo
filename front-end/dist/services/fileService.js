const instance = axios.create({
    baseURL: `http://127.0.0.1:8080/images`,
    withCredentials: true,
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
