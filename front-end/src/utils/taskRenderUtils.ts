import { getCurrentCalendar } from "../components/calendarForm.js"
import { deleteTaskProcess, deleteImageProcess } from "../components/taskForm.js";

declare var swal: any;

/* 달력을 넘기거나 다른 날을 선택했을 때 지우고 새로 그리기 */
export const renderNewTasks = (taskList: Task[], day: string) => {
    const taskDiv = document.getElementById('task-div') as HTMLElement;
    let currentCalendar: number[] = getCurrentCalendar();

    if (day === "none") { // 다른 달력
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
        const shortContent = task.content.length > 17 ? task.content.substring(0, 17) + ' ------- 최대 100자' : task.content; // 글자 수 넘침 처리
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
                .then(async (confirm: any) => {
                    if (confirm) {
                        deleteTaskProcess(task.id);
                    }
                })
            } catch (error: any) {
                console.log(error.message);
            }
        });

        task.files.forEach(file => {
            const img = document.createElement("img");
            img.src = file.path;
            img.id = file.id;
            img.alt = file.originName;
            imgDiv.appendChild(img);

            img.addEventListener('click', () => {
                console.log(file.id);
                try {
                    swal({
                        title: "삭제하시겠습니까?",
                        icon: "info",
                        buttons: true,
                    })
                    .then(async (confirm: any) => {
                        if (confirm) {
                            deleteImageProcess(file.id);
                        }
                    })
                } catch (error: any) {
                    console.log(error.message);
                }
            })
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

export const renderImgPreview = (src: string, file: File, fileArray: File[]) => {
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
                    const index = fileArray.indexOf(file);
                    if (index !== -1) {
                        fileArray.splice(index, 1);
                    }
                    img.remove();
                }
            })
    })

    preview.appendChild(img);
}