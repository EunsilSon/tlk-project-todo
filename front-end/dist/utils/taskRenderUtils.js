import { getCurrentCalendar } from "../components/calendarForm.js";
import { deleteTaskProcess, deleteImageProcess, removeFileInArray } from "../components/taskForm.js";
/* 달력을 넘기거나 다른 날을 선택했을 때 지우고 새로 그리기 */
export const renderNewTasks = (taskList, isDifferentCal) => {
    showNoDataNotice("none");
    const taskDiv = document.getElementById('task-div');
    let currentCalendar = getCurrentCalendar();
    if (isDifferentCal) { // 다른 달력
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
    showNoDataNotice("none");
    const taskDiv = document.getElementById('task-div');
    taskList.forEach(task => {
        const taskItem = document.createElement('div');
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
        const shortContent = task.content.length > 30 ? task.content.substring(0, 30) : task.content; // 글자 수 넘침 처리
        content.textContent = shortContent;
        const deleteBtn = document.createElement('button');
        deleteBtn.className = 'delete';
        deleteBtn.id = task.id.toString();
        deleteBtn.textContent = '삭제';
        deleteBtn.addEventListener('click', () => {
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
        task.attaches.forEach(attach => {
            const imgItem = document.createElement("div");
            const img = document.createElement('img');
            const imgDeleteBtn = document.createElement('img');
            imgItem.className = "image-item";
            img.src = `http://127.0.0.1:3000/` + attach.targetName;
            img.id = attach.id;
            console.log(attach.targetName);
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
                            deleteImageProcess(attach.id);
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
export const renderImgPreview = (src, file, fileArray) => {
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
                removeFileInArray(file);
                img.remove();
                checkImageLimit();
            }
        });
    });
    preview.appendChild(img);
    checkImageLimit();
};
function checkImageLimit() {
    const preview = document.getElementById("preview-div");
    const imgCount = preview.getElementsByTagName("img").length;
    const fileUploadInput = document.getElementById("file-upload");
    const fileUploadNotice = document.getElementById("file-upload-notice");
    if (imgCount >= 5) {
        fileUploadInput.disabled = true;
        fileUploadNotice.innerText = "5개까지 첨부 가능합니다.";
        fileUploadNotice.style.display = "block";
    }
    else {
        fileUploadInput.disabled = false;
        fileUploadNotice.innerText = "";
        fileUploadNotice.style.display = "notice";
    }
}
export const showNoDataNotice = (status) => {
    const noticeP = document.getElementById('no-data-notice');
    noticeP.style.display = status;
};
