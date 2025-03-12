import { deleteTask, getTaskDetail } from "../services/taskService.js";
import { getCurrentCalendar } from "../components/calendarForm.js"

export const renderNewTasks = (taskList: Task[], day: string) => {
    const taskDiv = document.getElementById('task-div') as HTMLElement;
    let currentCalendar: number[] = getCurrentCalendar();
    
    if (day === "none") {
        if (localStorage.getItem("currentMonth") != currentCalendar[1]+"") { // 달력 넘겼을 때
            localStorage.setItem("currentMonth", currentCalendar[1]+"");
            taskDiv.innerHTML = '';
        }
    } else {
        if (localStorage.getItem("currentDay") != day) { // 다른 날짜 선택했을 때
            localStorage.setItem("currentDay", day);
            taskDiv.innerHTML = '';
        }
    }

    renderTasks(taskList);
};

export const renderTasks = (taskList: Task[]) => {
    const taskDiv = document.getElementById('task-div') as HTMLElement;

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
        const shortContent = task.content.length > 20 ? task.content.substring(0, 20) + ' ------- 최대 100자' : task.content; // 글자 수 넘침 처리
        contentDiv.textContent = shortContent; 

        const deleteBtn = document.createElement('button');
        deleteBtn.className = 'delete';
        deleteBtn.id = task.id.toString();
        deleteBtn.textContent = '삭제';

        taskDetail.appendChild(dateDiv);
        taskDetail.appendChild(contentDiv);
        taskItem.appendChild(taskDetail);
        taskItem.appendChild(deleteBtn);
        taskDiv.appendChild(taskItem);

        deleteBtn.addEventListener('click', async () => {
            try {
                const isConfirmed = confirm("삭제하시겠습니까?");
                if (isConfirmed) {
                    await deleteTask(task.id)
                    .then((response: any) => {
                        if (response.status === 200) {
                            alert("삭제 완료되었습니다.");
                            let currentCalendar: number[] = getCurrentCalendar();
                            localStorage.setItem("updatedYear", currentCalendar[0] + "");
                            localStorage.setItem("updatedMonth", currentCalendar[1] + "");
                            localStorage.setItem("updated", "true");
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
    
        taskDetail.addEventListener('click', async () => {
            const isConfirmed = confirm("이동하시겠습니까?");
            if (isConfirmed) {
                window.location.href = `/html/detail.html?id=${task.id}`;
            } 
        })
    });

}

export const renderTaskDetail = (task: Task) => {
    console.log("단일 task");
}