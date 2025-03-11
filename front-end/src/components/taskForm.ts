import { getTaskCount, createTask, getMonthlyTaskList, getDaliyTaskList, getTaskDetail } from "../services/taskService.js";
import { renderTaskDetail } from "../utils/taskRenderUtils.js"

document.addEventListener('DOMContentLoaded', async () => {
    const path = window.location.pathname;
    
    if (path.endsWith('detail.html')) {
        const taskId: string = new URLSearchParams(window.location.search).get('id') || '';
        renderTaskDetail(await getTaskDetail(taskId));
    }

    const taskDiv = document.getElementById("task-div");
    taskDiv?.addEventListener('scroll', () => {
        if (taskDiv.scrollTop + taskDiv.clientHeight >= taskDiv.scrollHeight) {
            const inputDate = document.getElementById("input-date") as HTMLParagraphElement;
            if (inputDate.innerText === "") {
                //getMonthlyTaskProcess();
                console.log("없음");
            } else {
                //getDailyTaskProcess();
                console.log("있음");
            }
        }
    })
})

/* 
DB 호출을 FORM에서 해야하는지, RENDERING CODE에서 바로 해도 되는지 불확실함

export async function getTaskCountProcess(year: number, month: number) {
    return await getTaskCount(year, month+1);
}

export async function getMonthlyTaskProcess(page: number) {
    let seletedDate: number[] = getSelectedDate();
    return await getMonthlyTaskList(seletedDate[0], seletedDate[1], page);
}

export function getDailyTaskProcess(year: number, month: number, day: string, page: number) {
    getDaliyTaskList(year, month, day, page);
}

async function getTaskDetailProcess(taskId: string) {
    return await getTaskDetail(taskId);
}
*/

// yyyy.mm.dd -> yyyy-mm-dd
function formatDate(year: number, month: number, day: number): string {
    const date = new Date(year, month-1, day+1);
    return date.toISOString().split("T")[0];
}

const createBtn = document.getElementById('create') as HTMLButtonElement;
const taskInput = document.getElementById('task-input') as HTMLInputElement;
const content = document.getElementById('task-input') as HTMLInputElement;

if (taskInput) {
    taskInput.addEventListener('input', function () {
            createBtn.disabled = taskInput.value.trim() === "";
    });
}

if (createBtn) {
    createBtn.addEventListener('click', async () => {
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
                        localStorage.setItem("year", splitDate[0]);
                        localStorage.setItem("month", splitDate[1]);
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
}