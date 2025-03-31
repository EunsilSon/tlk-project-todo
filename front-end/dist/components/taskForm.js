import { createTask, getMonthlyTaskList, getDailyTaskList, deleteTask } from "../services/taskService.js";
import { deleteImage } from "../services/fileService.js";
import { renderTasks, renderImgPreview, showNoDataNotice, renderNewTasks } from "../utils/taskRenderUtils.js";
import { getCurrentCalendar } from "./calendarForm.js";
var MAX_FILE_SIZE = 2 * 1024 * 1024;
let taskPage = 1;
let fileArray = [];
const createBtn = document.getElementById('create');
const taskInput = document.getElementById('task-input');
const content = document.getElementById('task-input');
const inputDate = document.getElementById('input-date');
const fileUploadBtn = document.getElementById('file-upload');
document.addEventListener('DOMContentLoaded', async () => {
    scrollForTask();
});
taskInput?.addEventListener('input', function () {
    if (taskInput.value.trim() === "" || inputDate.innerText === "") {
        inputDate.innerText = "날짜를 선택하세요.";
        taskInput.value = "";
    }
    else {
        createBtn.disabled = false;
        fileUploadBtn.disabled = false;
    }
});
createBtn?.addEventListener('click', async () => {
    createTaskProcess();
});
fileUploadBtn?.addEventListener('change', (event) => {
    const inputFile = event.target;
    if (inputFile.files) {
        const uploadFile = inputFile.files[0];
        if (uploadFile.size >= MAX_FILE_SIZE) {
            swal({
                position: "top-end",
                icon: "info",
                title: "최대 2MB 크기까지 업로드 가능합니다.",
                timer: 800
            });
            inputFile.value = '';
            return;
        }
        const previewDiv = document.getElementById('preview-div');
        if (previewDiv && previewDiv.children.length < 5) {
            const reader = new FileReader();
            reader.onload = () => {
                renderImgPreview(reader.result, uploadFile, fileArray);
            };
            reader.readAsDataURL(uploadFile);
            Array.from(inputFile.files).forEach((file) => {
                fileArray.push(file);
            });
        }
        else {
            inputFile.value = '';
            swal({
                position: "top-end",
                icon: "info",
                title: "최대 5개까지 업로드 가능합니다.",
                timer: 800
            });
        }
    }
});
export function removeFileInArray(file) {
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
    const taskDiv = document.getElementById('task-div');
    taskDiv.addEventListener("scroll", () => {
        if (Math.ceil(taskDiv.scrollTop) + taskDiv.clientHeight >= taskDiv.scrollHeight) { // 현재 스크롤 위치 + 화면 요소의 높이 = 전체 스크롤 높이
            const inputDate = document.getElementById('input-date');
            if (inputDate.innerText === "") {
                monthlyTaskProcess();
            }
            else {
                dailyTaskProcess();
            }
        }
    });
}
/* 특정 월의 모든 task 조회 */
export async function monthlyTaskProcess() {
    const taskDiv = document.getElementById('task-div');
    let currentCalendar = getCurrentCalendar();
    let newTasks = await getMonthlyTaskList(currentCalendar[0], currentCalendar[1], taskPage++);
    if (taskDiv.scrollHeight > taskDiv.clientHeight && newTasks.length == 0) {
        showNoDataNotice("block");
    }
    else {
        showNoDataNotice("none");
        renderTasks(newTasks);
    }
}
/* 특정 일자의 모든 task 조회 */
async function dailyTaskProcess() {
    const taskDiv = document.getElementById('task-div');
    const inputDate = document.getElementById('input-date');
    let currentCalendar = getCurrentCalendar();
    let newTasks = await getDailyTaskList(currentCalendar[0], currentCalendar[1], inputDate.innerText.substring(9, 11), taskPage++);
    if (taskDiv.scrollHeight > taskDiv.clientHeight && newTasks.length == 0) {
        showNoDataNotice("block");
    }
    else {
        showNoDataNotice("none");
        renderTasks(newTasks);
    }
}
export async function reloadMonthlyTask() {
    let currentCalendar = getCurrentCalendar();
    let newTasks = await getMonthlyTaskList(currentCalendar[0], currentCalendar[1], 0);
    const taskDiv = document.getElementById('task-div');
    renderNewTasks(newTasks, false);
    taskDiv.scrollTop = 0;
}
/* task 생성 */
async function createTaskProcess() {
    const inputDate = document.getElementById('input-date');
    let splitDate = inputDate.innerText.split(". ");
    const formData = new FormData();
    if (fileArray.length > 0) {
        fileArray.forEach((file) => {
            formData.append("attaches", file);
        });
    }
    formData.append("content", content.value);
    formData.append("year", splitDate[0]);
    formData.append("month", splitDate[1]);
    formData.append("day", splitDate[2]);
    formData.append("createdBy", "1");
    formData.append("groupId", crypto.randomUUID());
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
        });
    }
    else {
        swal({
            position: "top-end",
            icon: "fail",
            title: "등록 실패",
            timer: 650
        });
    }
}
export async function deleteTaskProcess(taskId) {
    await deleteTask(taskId)
        .then((response) => {
        if (response.status === 200) {
            swal({
                position: "top-end",
                icon: "success",
                title: "삭제 완료",
                timer: 750
            })
                .then(() => {
                let currentCalendar = getCurrentCalendar();
                localStorage.setItem("updatedYear", currentCalendar[0] + "");
                localStorage.setItem("updatedMonth", currentCalendar[1] + "");
                localStorage.setItem("updated", "true");
                window.location.reload();
            });
        }
        else {
            swal({
                position: "top-end",
                icon: "fail",
                title: "삭제 실패",
                timer: 650
            });
        }
    });
}
export async function deleteImageProcess(imageId) {
    await deleteImage(imageId)
        .then((response) => {
        if (response.status === 200) {
            swal({
                position: "top-end",
                icon: "success",
                title: "삭제 완료",
                timer: 750
            })
                .then(() => {
                let currentCalendar = getCurrentCalendar();
                localStorage.setItem("updatedYear", currentCalendar[0] + "");
                localStorage.setItem("updatedMonth", currentCalendar[1] + "");
                localStorage.setItem("updated", "true");
                window.location.reload();
            });
        }
        else {
            swal({
                position: "top-end",
                icon: "fail",
                title: "삭제 실패",
                timer: 650
            });
        }
    });
}
