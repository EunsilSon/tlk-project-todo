import { createTask, getMonthlyTaskList, getDailyTaskList, getTaskDetail } from "../services/taskService.js";
import { renderTasks, renderTaskDetail } from "../utils/taskRenderUtils.js"
import { getCurrentCalendar } from "./calendarForm.js";

let taskPage: number = 1;

const createBtn = document.getElementById('create') as HTMLButtonElement;
const taskInput = document.getElementById('task-input') as HTMLInputElement;
const content = document.getElementById('task-input') as HTMLInputElement;
const inputDate = document.getElementById('input-date') as HTMLElement;

taskInput?.addEventListener('input', function () { 
    if (taskInput.value.trim() === "" || inputDate.innerText === "") {
        createBtn.disabled = true; // 공백 입력 시 input 비활성화
        inputDate.innerText = "날짜를 선택하세요.";
        taskInput.value = "";
    } else {
        createBtn.disabled = false;
    }
});

createBtn?.addEventListener('click', async () => {
    createTaskProcess();
});

document.addEventListener('DOMContentLoaded', async () => {
    const path = window.location.pathname;
    
    if (path.endsWith('detail.html')) {
        const taskId: string = new URLSearchParams(window.location.search).get('id') || '';
        renderTaskDetail(await getTaskDetail(taskId));
    }

    scrollForTask();
})

export function setTaskPage() {
    taskPage = 1;
}

/* task 조회를 위한 스크롤 */
function scrollForTask() {
    const taskDiv = document.getElementById('task-div') as HTMLElement;
    taskDiv.addEventListener("scroll", () => {
        if (Math.ceil(taskDiv.scrollTop) + taskDiv.clientHeight >= taskDiv.scrollHeight) { // 현재 스크롤 위치 + 화면 요소의 높이 = 전체 스크롤 높이
            const inputDate = document.getElementById('input-date') as HTMLElement;
            if (inputDate.innerText === "") {
                monthlyTaskProcess();
            } else {
                dailyTaskProcess();
            }
        }
      });
}

/* 특정 월의 모든 task 조회 */
async function monthlyTaskProcess() {
    let currentCalendar: number[] = getCurrentCalendar();
    let newTasks: Task[] = await getMonthlyTaskList(currentCalendar[0], currentCalendar[1], taskPage++);
    
    if (newTasks.length == 0) {
        alert('마지막 기록입니다.');
    } else {
        renderTasks(newTasks);
    }
}

/* 특정 일자의 모든 task 조회 */
async function dailyTaskProcess() {
    const inputDate = document.getElementById('input-date') as HTMLElement;
    let currentCalendar: number[] = getCurrentCalendar();
    let newTasks: Task[] = await getDailyTaskList(currentCalendar[0], currentCalendar[1], inputDate.innerText.substring(9, 10), taskPage++);
    
    if (newTasks.length == 0) {
        alert('마지막 기록입니다.');
    } else {
        renderTasks(newTasks);
    }
}

/* task 생성 */
async function createTaskProcess() {
    const inputDate = document.getElementById('input-date') as HTMLElement;
    let splitDate = inputDate.innerText.split(". ");

    const task = {
        content: content.value,
        date: formatDate(Number(splitDate[0]), Number(splitDate[1]), Number(splitDate[2]))
    };

    await createTask(task)
    .then((response: any) => {
        if (response.status === 200) {
            alert("등록이 완료되었습니다.");
            localStorage.setItem("updatedYear", Number(splitDate[0]) + "");
            localStorage.setItem("updatedMonth", Number(splitDate[1]) + "");
            localStorage.setItem("updated", "true");
            window.location.reload();
        } else {
            alert(response.message);
        }
    })
}

/* yyyy.mm.dd -> yyyy-mm-dd */
function formatDate(year: number, month: number, day: number): string {
    const date = new Date(year, month-1, day+1);
    return date.toISOString().split("T")[0];
}