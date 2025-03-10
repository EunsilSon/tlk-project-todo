import { getTaskCount, createTask, getMonthlyTaskList, getDaliyTaskList } from "../services/taskService.js";
import { getSelectedDate } from "./calenderForm.js";

export async function getTaskCountProcess(year: number, month: number) {
    return await getTaskCount(year, month+1);
}

export async function getMonthlyTaskProcess(page: number) {
    let seletedDate: number[] = getSelectedDate();
    await getMonthlyTaskList(seletedDate[0], seletedDate[1], page);
}

export function getDailyTaskProcess(day: string, page: number) {
    let seletedDate: number[] = getSelectedDate();
    getDaliyTaskList(seletedDate[0], seletedDate[1], day, page);
}

function formatDate(year: number, month: number, day: number): string {
    const date = new Date(year, month-1, day+1);
    return date.toISOString().split("T")[0];
}

const createBtn = document.getElementById('create');
if (createBtn) {
    createBtn.addEventListener('click', async () => {
        try {
            const content = document.getElementById('task-input') as HTMLInputElement;
            const date = document.getElementById('input-date') as HTMLElement;
            let splitDate = date.innerText.split(". ");

            const task = {
                content: content.value,
                date: formatDate(Number(splitDate[0]), Number(splitDate[1]), Number(splitDate[2]))
            };

            await createTask(task);

            alert("등록이 완료 되었습니다.");
            localStorage.setItem("year", splitDate[0]);
            localStorage.setItem("month", splitDate[1]);
            window.location.reload();
        } catch (error) {
            console.log(error);
        }
    });
}