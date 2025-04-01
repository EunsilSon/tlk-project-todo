import { getCurrentCalendar } from "../components/calendarForm.js";
<<<<<<< HEAD
import { deleteTaskProcess } from "../components/taskForm.js";
/* 달력을 넘기거나 다른 날을 선택했을 때 지우고 새로 그리기 */
export const renderNewTasks = (taskList, day) => {
    const taskDiv = document.getElementById('task-div');
    let currentCalendar = getCurrentCalendar();
    if (day === "none") { // 다른 달력
=======
import { deleteTaskProcess, deleteImageProcess, removeFileInArray } from "../components/taskForm.js";
import { config } from "../config.js";
/* 달력을 넘기거나 다른 날을 선택했을 때 지우고 새로 그리기 */
export const renderNewTasks = (taskList, isDifferentCal) => {
    const taskDiv = document.getElementById('task-div');
    let currentCalendar = getCurrentCalendar();
    if (isDifferentCal) { // 다른 달력
>>>>>>> front
        if (localStorage.getItem("currentMonth") != currentCalendar[1] + "") {
            localStorage.setItem("currentMonth", currentCalendar[1] + "");
            taskDiv.innerHTML = '';
        }
    }
    else { // 일자 변경
        taskDiv.innerHTML = '';
    }
    renderTasks(taskList);
};
/* 기존의 달력, 날짜에서 이어서 그리기 */
export const renderTasks = (taskList) => {
    const taskDiv = document.getElementById('task-div');
    taskList.forEach(task => {
        const taskItem = document.createElement('div');
<<<<<<< HEAD
        taskItem.className = 'task-item';
        const taskDetail = document.createElement('div');
        taskDetail.className = 'task-detail';
        const dateDiv = document.createElement('div');
        dateDiv.className = 'date';
        dateDiv.textContent = task.year + ". " + task.month + ". " + task.day;
        const contentDiv = document.createElement('div');
        contentDiv.className = 'content';
        const shortContent = task.content.length > 17 ? task.content.substring(0, 17) + ' ------- 최대 100자' : task.content; // 글자 수 넘침 처리
        contentDiv.textContent = shortContent;
=======
        const contentDiv = document.createElement('div');
        const imgDiv = document.createElement('div');
        taskItem.className = 'task-item';
        const taskDetail = document.createElement('div');
        taskDetail.className = 'task-detail';
        const date = document.createElement('div');
        date.className = 'date';
        date.textContent = task.year + ". " + task.month + ". " + task.day;
        const content = document.createElement('div');
        content.className = 'content';
        content.textContent = task.content.length > 30 ? task.content.substring(0, 30) : task.content;
>>>>>>> front
        const deleteBtn = document.createElement('button');
        deleteBtn.className = 'delete';
        deleteBtn.id = task.id.toString();
        deleteBtn.textContent = '삭제';
<<<<<<< HEAD
        taskDetail.appendChild(dateDiv);
        taskDetail.appendChild(contentDiv);
        taskItem.appendChild(taskDetail);
        taskDiv.appendChild(taskItem);
        deleteBtn.addEventListener('click', async () => {
=======
        deleteBtn.addEventListener('click', () => {
>>>>>>> front
            try {
                swal({
                    title: "삭제하시겠습니까?",
                    icon: "info",
                    buttons: true,
                })
                    .then(async (confirm) => {
                    if (confirm) {
<<<<<<< HEAD
                        deleteTaskProcess(task.id);
=======
                        await deleteTaskProcess(task.id);
>>>>>>> front
                    }
                });
            }
            catch (error) {
                console.log(error.message);
            }
        });
<<<<<<< HEAD
    });
};
export const renderImgPreview = (src, file, fileArray) => {
=======
        task.attaches.forEach(attach => {
            const imgItem = document.createElement("div");
            const img = document.createElement('img');
            const imgDeleteBtn = document.createElement('img');
            imgItem.className = "image-item";
            img.src = config.FILE_BASE_URL + attach.targetName;
            img.id = attach.id;
            img.alt = attach.originName;
            imgDeleteBtn.src = "/assets/remove.png";
            imgDeleteBtn.className = "image-delete-btn";
            imgDeleteBtn.addEventListener('click', () => {
                try {
                    swal({
                        title: "삭제하시겠습니까?",
                        icon: "info",
                        buttons: true,
                    })
                        .then(async (confirm) => {
                        if (confirm) {
                            await deleteImageProcess(attach.id);
                        }
                    });
                }
                catch (error) {
                    console.log(error.message);
                }
            });
            imgItem.appendChild(img);
            imgItem.appendChild(imgDeleteBtn);
            imgDiv.appendChild(imgItem);
        });
        taskDetail.appendChild(date);
        taskDetail.appendChild(content);
        contentDiv.appendChild(taskDetail);
        contentDiv.appendChild(deleteBtn);
        taskItem.appendChild(contentDiv);
        taskItem.appendChild(imgDiv);
        taskDiv.appendChild(taskItem);
    });
};
export const renderImgPreview = (src, file) => {
>>>>>>> front
    const preview = document.getElementById("preview-div");
    const img = document.createElement('img');
    img.src = src;
    img.style.display = "block";
    img.alt = file.name;
    img.addEventListener('click', () => {
        swal({
            title: "삭제하시겠습니까?",
            icon: "info",
            buttons: true,
        })
            .then((confirm) => {
            if (confirm) {
<<<<<<< HEAD
                const index = fileArray.indexOf(file);
                if (index !== -1) {
                    fileArray.splice(index, 1);
                }
                img.remove();
=======
                removeFileInArray(file);
                img.remove();
                checkImageLimit();
>>>>>>> front
            }
        });
    });
    preview.appendChild(img);
<<<<<<< HEAD
=======
    checkImageLimit();
};
function checkImageLimit() {
    const preview = document.getElementById("preview-div");
    const imgCount = preview.getElementsByTagName("img").length;
    const fileUploadInput = document.getElementById("file-upload");
    const fileUploadNotice = document.getElementById("file-upload-notice");
    if (imgCount >= 5) {
        fileUploadInput.disabled = true;
        fileUploadInput.style.color = "transparent";
        fileUploadNotice.innerText = "5개까지 첨부 가능합니다.";
        fileUploadNotice.style.display = "block";
    }
    else {
        fileUploadInput.disabled = false;
        fileUploadInput.style.color = "initial";
        fileUploadNotice.innerText = "";
        fileUploadNotice.style.display = "notice";
    }
}
export const showLastDataNotice = () => {
    const taskDiv = document.getElementById("task-div");
    const taskItems = taskDiv?.getElementsByClassName("task-item");
    if (taskItems && taskItems.length > 0) {
        const lastTaskItem = taskItems[taskItems.length - 1];
        let noticeP = lastTaskItem.querySelector("#no-data-notice");
        if (!noticeP) {
            const noticeP = document.createElement('p');
            noticeP.id = "no-data-notice";
            noticeP.textContent = "마지막 기록입니다.";
            lastTaskItem.appendChild(noticeP);
        }
    }
>>>>>>> front
};
