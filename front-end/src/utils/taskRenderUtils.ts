import { getCurrentCalendar } from "../components/calendarForm.js"
import { deleteTaskProcess, deleteImageProcess, removeFileInArray } from "../components/taskForm.js";
import { config } from "../config.js";

declare var swal: any;

/* 달력을 넘기거나 다른 날을 선택했을 때 지우고 새로 그리기 */
export const renderNewTasks = (taskList: Task[], isDifferentCal: boolean) => {
    const taskDiv = document.getElementById('task-div') as HTMLElement;
    let currentCalendar: number[] = getCurrentCalendar();

    if (isDifferentCal) { // 다른 달력
        if (localStorage.getItem("currentMonth") != currentCalendar[1] + "") {
            localStorage.setItem("currentMonth", currentCalendar[1] + "");
            taskDiv.innerHTML = '';
        }
    } else { // 일자 변경
        taskDiv.innerHTML = '';
    }

    renderTasks(taskList);
};

/* 기존의 달력, 날짜에서 이어서 그리기 */
export const renderTasks = (taskList: Task[]) => {
    const taskDiv = document.getElementById('task-div') as HTMLElement;

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
        content.textContent = task.content.length > 30 ? task.content.substring(0, 30) : task.content;

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
                    .then(async (confirm: any) => {
                        if (confirm) {
                            await deleteTaskProcess(task.id);
                        }
                    })
            } catch (error: any) {
                console.log(error.message);
            }
        });

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
                        .then(async (confirm: any) => {
                            if (confirm) {
                                await deleteImageProcess(attach.id);
                            }
                        })
                } catch (error: any) {
                    console.log(error.message);
                }
            })
            imgItem.appendChild(img);
            imgItem.appendChild(imgDeleteBtn);
            imgDiv.appendChild(imgItem);
        })

        taskDetail.appendChild(date);
        taskDetail.appendChild(content);

        contentDiv.appendChild(taskDetail);
        contentDiv.appendChild(deleteBtn);

        taskItem.appendChild(contentDiv);
        taskItem.appendChild(imgDiv);

        taskDiv.appendChild(taskItem);
    });
}

export const renderImgPreview = (src: string, file: File) => {
    const preview = document.getElementById("preview-div") as HTMLImageElement;
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
            .then((confirm: any) => {
                if (confirm) {
                    removeFileInArray(file);
                    img.remove();
                    checkImageLimit();
                }
            })
    })

    preview.appendChild(img);
    checkImageLimit();
}

function checkImageLimit() {
    const preview = document.getElementById("preview-div") as HTMLImageElement;
    const imgCount = preview.getElementsByTagName("img").length;
    const fileUploadInput = document.getElementById("file-upload") as HTMLInputElement;
    const fileUploadNotice = document.getElementById("file-upload-notice") as HTMLElement;

    if (imgCount >= 5) {
        fileUploadInput.disabled = true;
        fileUploadInput.style.color = "transparent";
        fileUploadNotice.innerText = "5개까지 첨부 가능합니다.";
        fileUploadNotice.style.display = "block";
    } else {
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
            const noticeP = document.createElement('p') as HTMLElement;
            noticeP.id = "no-data-notice";
            noticeP.textContent = "마지막 기록입니다."

            lastTaskItem.appendChild(noticeP);
        }
    }
}