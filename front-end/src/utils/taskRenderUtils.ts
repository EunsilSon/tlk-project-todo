import { deleteTask } from "../services/taskService.js";

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

        deleteBtn.addEventListener('click', async () => {
            try {
                const isConfirmed = confirm("삭제하시겠습니까?");
                if (isConfirmed) {
                    await deleteTask(task.id)
                    .then((response: any) => {
                        if (response.status === 200) {
                            alert("삭제 완료되었습니다.");
                            window.location.reload();
                        } else {
                            alert("삭제 실패했습니다.");
                        }
                    })
                }
            } catch (error: any) {
                console.log(error.message);
            }
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
