import { createTask, getMonthlyTaskList } from "../services/taskService.js";

document.addEventListener('DOMContentLoaded', () => {
    // Service에서 받아오기
    const response = getMonthlyTaskList(2025, 2, 0);
    console.log(response);
    // render
})


const createBtn = document.getElementById('create');

if (createBtn) {
    createBtn.addEventListener('click', ()=> {
        try {
            let content: string = document.getElementById('task-input')?.innerText || '';
            let date: string = document.getElementById('input-date')?.innerText || '';  
            const task = { content, date };
            console.log(createTask(task));
        } catch (error) {
            console.log(error);
        }
    });
}