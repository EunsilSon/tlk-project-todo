import { getCurrentCalendar } from "../components/calendarForm.js";
import { deleteTaskProcess } from "../components/taskForm.js";
export const renderNewTasks = (taskList, day) => {
    const taskDiv = document.getElementById('task-div');
    let currentCalendar = getCurrentCalendar();
    if (day === "none") {
        if (localStorage.getItem("currentMonth") != currentCalendar[1] + "") { // 달력 넘겼을 때
            localStorage.setItem("currentMonth", currentCalendar[1] + "");
            taskDiv.innerHTML = '';
        }
    }
    else {
        if (localStorage.getItem("currentDay") != day) { // 다른 날짜 선택했을 때
            localStorage.setItem("currentDay", day);
            taskDiv.innerHTML = '';
        }
    }
    renderTasks(taskList);
};
export const renderTasks = (taskList) => {
    const taskDiv = document.getElementById('task-div');
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
                swal({
                    title: "삭제하시겠습니까?",
                    icon: "info",
                    buttons: true,
                })
                    .then(async (confirm) => {
                    if (confirm) {
                        deleteTaskProcess(task.id);
                    }
                });
            }
            catch (error) {
                console.log(error.message);
            }
        });
        taskDetail.addEventListener('click', async () => {
            const isConfirmed = confirm("이동하시겠습니까?");
            if (isConfirmed) {
                window.location.href = `/html/detail.html?id=${task.id}`;
            }
        });
    });
};
export const renderTaskDetail = (task) => {
    console.log("단일 task");
};
