import { createTask } from "../services/taskService.js";

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