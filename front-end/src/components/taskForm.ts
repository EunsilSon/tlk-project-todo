import { createTask, getMonthlyTaskList, getDailyTaskList, getTaskDetail } from "../services/taskService.js";
import { renderTasks, renderTaskDetail } from "../utils/taskRenderUtils.js"
import { getCurrentCalendar } from "./calendarForm.js";

let taskPage = 1;

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

/* 스크롤을 이용해 task 조회 */
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

/* 특정 일자의 모든 task 조회회 */
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

/* yyyy.mm.dd -> yyyy-mm-dd */
function formatDate(year: number, month: number, day: number): string {
    const date = new Date(year, month-1, day+1);
    return date.toISOString().split("T")[0];
}

const createBtn = document.getElementById('create') as HTMLButtonElement;
const taskInput = document.getElementById('task-input') as HTMLInputElement;
const content = document.getElementById('task-input') as HTMLInputElement;

/* 공백 입력 시 input 비활성화 */
taskInput?.addEventListener('input', function () { 
    createBtn.disabled = taskInput.value.trim() === "";
});

/* task 생성 */
createBtn?.addEventListener('click', async () => {
    try {
        const isConfirmed = confirm("등록하시겠습니까?");
        if (isConfirmed) {
            const date = document.getElementById('input-date') as HTMLElement;
            let splitDate = date.innerText.split(". ");

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
        } else {
            content.value = "";
        }
    } catch (error: any) {
        console.log(error.message);
    }
});