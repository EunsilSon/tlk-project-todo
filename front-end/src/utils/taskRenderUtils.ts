import { getMonthlyTaskList } from "../services/taskService";

export const renderTasks = (taskList: Task[]) => {
    const taskItemDiv = document.getElementById('task-div') as HTMLElement;
    taskItemDiv.innerHTML = '';

    taskList.forEach(task => {
        const taskItem = document.createElement('div');
        taskItem.className = 'task-item';

        const taskDetail = document.createElement('div');
        taskDetail.className = 'task-detail';

        const dateDiv = document.createElement('div');
        dateDiv.className = 'date';
        dateDiv.textContent = task.date;

        const contentDiv = document.createElement('div');
        contentDiv.className = 'content';
        contentDiv.textContent = task.content;

        const deleteBtn = document.createElement('button');
        deleteBtn.className = 'delete';
        deleteBtn.id = task.id.toString();
        deleteBtn.textContent = '삭제';

        deleteBtn.addEventListener('click', () => {
            //removeTask(task.id);
        });


        // 선택한 일자가 있으면 daily, 없으면 monthly
        const monthNextBtn = document.createElement('button');
        monthNextBtn.textContent = '더보기';
        monthNextBtn.addEventListener('click', () => {
            let page = 1;
            //getMonthlyTaskList()
        });

        taskDetail.appendChild(dateDiv);
        taskDetail.appendChild(contentDiv);
        taskItem.appendChild(taskDetail);
        taskItem.appendChild(deleteBtn);
        taskItemDiv.appendChild(taskItem);
    });
};
