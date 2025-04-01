import { createTask, getMonthlyTaskList, getDailyTaskList, deleteTask } from "../services/taskService.js";
import { deleteImage } from "../services/fileService.js";
import { renderTasks, renderImgPreview, showLastDataNotice, renderNewTasks} from "../utils/taskRenderUtils.js"
import { getCurrentCalendar } from "./calendarForm.js";


declare var swal: any;
const MAX_FILE_SIZE = 2 * 1024 * 1024;
let taskPage: number = 1;
let fileArray: File[] = [];

const createBtn = document.getElementById('create') as HTMLButtonElement;
const taskInput = document.getElementById('task-input') as HTMLInputElement;
const content = document.getElementById('task-input') as HTMLInputElement;
const inputDate = document.getElementById('input-date') as HTMLElement;
const fileUploadBtn = document.getElementById('file-upload') as HTMLButtonElement;

document.addEventListener('DOMContentLoaded', async () => {
    scrollForTask();
})

taskInput?.addEventListener('input', function () {
    if (taskInput.value.trim() === "" || inputDate.innerText === "") {
        inputDate.innerText = "날짜를 선택하세요.";
        taskInput.value = "";
    } else {
        createBtn.disabled = false;
        fileUploadBtn.disabled = false;
    }
});

createBtn?.addEventListener('click', async () => {
    await createTaskProcess();
});

fileUploadBtn?.addEventListener('change', (event) => {
    const inputFile = event.target as HTMLInputElement;

    if (inputFile.files) {
        const uploadFile = inputFile.files[0];

        if (uploadFile.size >= MAX_FILE_SIZE) {
            swal({
                position: "top-end",
                icon: "info",
                title: "최대 2MB 크기까지 업로드 가능합니다.",
                timer: 800
            })
            return;
        }

        // 파일 첨부
        const reader = new FileReader();
        reader.onload = () => {
            renderImgPreview(reader.result as string, uploadFile);
        };
        reader.readAsDataURL(uploadFile);

        // 첨부한 파일들
        Array.from(inputFile.files).forEach((file) => {
            fileArray.push(file);
        })
    }
});

export function removeFileInArray(file: File) {
    const index = fileArray.indexOf(file);
    if (index !== -1) {
        fileArray.splice(index, 1);
    }
}

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
export async function monthlyTaskProcess() {
    const taskDiv = document.getElementById('task-div') as HTMLElement;

    let currentCalendar: number[] = getCurrentCalendar();
    let newTasks: Task[] = await getMonthlyTaskList(currentCalendar[0], currentCalendar[1], taskPage++);

    if (taskDiv.scrollHeight > taskDiv.clientHeight && newTasks.length == 0) {
        showLastDataNotice();
    } else {
        renderTasks(newTasks);
    }
}

/* 특정 일자의 모든 task 조회 */
async function dailyTaskProcess() {
    const taskDiv = document.getElementById('task-div') as HTMLElement;
    const inputDate = document.getElementById('input-date') as HTMLElement;

    let currentCalendar: number[] = getCurrentCalendar();
    let newTasks: Task[] = await getDailyTaskList(currentCalendar[0], currentCalendar[1], inputDate.innerText.substring(9, 11), taskPage++);

    if (taskDiv.scrollHeight > taskDiv.clientHeight && newTasks.length == 0) {
        showLastDataNotice();
    } else {
        renderTasks(newTasks);
    }
}

export async function reloadMonthlyTask() {
    let currentCalendar: number[] = getCurrentCalendar();
    let newTasks: Task[] = await getMonthlyTaskList(currentCalendar[0], currentCalendar[1], 0);
    const taskDiv = document.getElementById('task-div') as HTMLElement;
    renderNewTasks(newTasks, false);
    taskDiv.scrollTop = 0;
}

/* UUID v4 규격의 UUID 직접 생성 */
function generateUUID(): string {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, (c) => {
        const r = Math.random() * 16 | 0;
        const v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

/* task 생성 */
async function createTaskProcess() {
    const inputDate = document.getElementById('input-date') as HTMLElement;
    let splitDate = inputDate.innerText.split(". ");

    const formData = new FormData();

    if (fileArray.length > 0) {
        fileArray.forEach((file) => {
            formData.append("attaches", file);
        });
        formData.append("groupId",  generateUUID());
    }
    formData.append("content", content.value);
    formData.append("year", splitDate[0]);
    formData.append("month", splitDate[1]);
    formData.append("day", splitDate[2]);
    formData.append("createdBy", "1");

    const response = await createTask(formData);
    if (response.status === 200) {
        swal({
            position: "top-end",
            icon: "success",
            title: "등록 완료",
            timer: 750
        })
            .then(() => {
                localStorage.setItem("updatedYear", Number(splitDate[0]) + "");
                localStorage.setItem("updatedMonth", Number(splitDate[1]) + "");
                localStorage.setItem("updated", "true");
                window.location.reload();
            })
    } else {
        swal({
            position: "top-end",
            icon: "fail",
            title: "등록 실패",
            timer: 650
        })
    }
}

export async function deleteTaskProcess(taskId: string) {
    await deleteTask(taskId)
        .then((response: any) => {
            if (response.status === 200) {
                swal({
                    position: "top-end",
                    icon: "success",
                    title: "삭제 완료",
                    timer: 750
                })
                .then(() => {
                    let currentCalendar: number[] = getCurrentCalendar();
                    localStorage.setItem("updatedYear", currentCalendar[0] + "");
                    localStorage.setItem("updatedMonth", currentCalendar[1] + "");
                    localStorage.setItem("updated", "true");
                    window.location.reload();
                })
            } else {
                swal({
                    position: "top-end",
                    icon: "fail",
                    title: "삭제 실패",
                    timer: 650
                })
            }
        });
}

export async function deleteImageProcess(imageId: string) {
    await deleteImage(imageId)
        .then((response: any) => {
            if (response.status === 200) {
                swal({
                    position: "top-end",
                    icon: "success",
                    title: "삭제 완료",
                    timer: 750
                })
                    .then(() => {
                        let currentCalendar: number[] = getCurrentCalendar();
                        localStorage.setItem("updatedYear", currentCalendar[0] + "");
                        localStorage.setItem("updatedMonth", currentCalendar[1] + "");
                        localStorage.setItem("updated", "true");
                        window.location.reload();
                    })
            } else {
                swal({
                    position: "top-end",
                    icon: "fail",
                    title: "삭제 실패",
                    timer: 650
                })
            }
        });
}