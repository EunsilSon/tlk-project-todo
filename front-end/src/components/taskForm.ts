import { createTask, getMonthlyTaskList, getDailyTaskList, deleteTask } from "../services/taskService.js";
import { renderTasks, renderImgPreview } from "../utils/taskRenderUtils.js"
import { getCurrentCalendar } from "./calendarForm.js";

declare var swal: any;
var MAX_FILE_SIZE = 2 * 1024 * 1024;
let taskPage: number = 1;
let fileList: File[] = [];

document.addEventListener('DOMContentLoaded', async () => {
    scrollForTask();
})

const createBtn = document.getElementById('create') as HTMLButtonElement;
const taskInput = document.getElementById('task-input') as HTMLInputElement;
const content = document.getElementById('task-input') as HTMLInputElement;
const inputDate = document.getElementById('input-date') as HTMLElement;
const fileUploadBtn = document.getElementById('file-upload') as HTMLElement;

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

fileUploadBtn?.addEventListener('change', (event) => {
    const inputFile = event.target as HTMLInputElement;

    if (inputFile.files) {
        const f = inputFile.files[0];
        console.log("업로드 파일 정보: " + typeof f, f);
        /*console.log(f.size);
        console.log(f.name);
        console.log(f.type);*/

        /*const file = {
            originName: f.name,
            size: Number(f.size),
            type: f.type,
            groupId: "img-file-group-id-test",
        };
        fileList.push(file);*/
        
        if (f.size >= MAX_FILE_SIZE) {
            swal({
                position: "top-end",
                icon: "info",
                title: "최대 2MB 크기까지 업로드 가능합니다.",
                timer: 800
            })
            inputFile.value = '';
            return;
        }

        const previewDiv = document.getElementById('preview-div');
        if (previewDiv && previewDiv.children.length < 5) {
            const reader = new FileReader();
            reader.onload = () => {
                renderImgPreview(reader.result as string);
            };
            reader.readAsDataURL(f);
        } else {
            inputFile.value = '';
            swal({
                position: "top-end",
                icon: "info",
                title: "최대 5개까지 업로드 가능합니다.",
                timer: 800
            })
        }
    }
});

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
    let currentCalendar: number[] = getCurrentCalendar();
    let newTasks: Task[] = await getMonthlyTaskList(currentCalendar[0], currentCalendar[1], taskPage++);
    
    if (newTasks.length == 0) {
        swal({
            position: "top-end",
            icon: "info",
            title: "마지막 기록입니다.",
            timer: 650
        })
    } else {
        renderTasks(newTasks);
    }
}

/* 특정 일자의 모든 task 조회 */
async function dailyTaskProcess() {
    const inputDate = document.getElementById('input-date') as HTMLElement;
    let currentCalendar: number[] = getCurrentCalendar();

    let newTasks: Task[] = await getDailyTaskList(currentCalendar[0], currentCalendar[1], inputDate.innerText.substring(9, 11), taskPage++);
    if (newTasks.length == 0) {
        swal({
            position: "top-end",
            icon: "info",
            title: "마지막 기록입니다.",
            timer: 650
        })
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
        year: splitDate[0],
        month: splitDate[1],
        day: splitDate[2],
    };

    await createTask(task)
    .then((response: any) => {
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
    })
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