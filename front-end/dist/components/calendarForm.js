import { renderCalendar } from "../utils/calendarRenderUtils.js";
import { renderNewTasks } from "../utils/taskRenderUtils.js";
import { getMonthlyTaskList } from "../services/taskService.js";
import { setTaskPage } from "./taskForm.js";
document.addEventListener('DOMContentLoaded', async () => {
    let year = 0;
    let month = 0;
    if (localStorage.getItem("updated") === "true") { // 삭제 후 reload
        year = Number(localStorage.getItem("updatedYear"));
        month = Number(localStorage.getItem("updatedMonth")) - 1;
        localStorage.clear();
    }
    else {
        let date = new Date();
        year = date.getFullYear();
        month = date.getMonth();
    }
    renderCalendar(year, month);
<<<<<<< HEAD
    renderNewTasks(await getMonthlyTaskList(year, month + 1, 0), "none");
});
=======
    renderNewTasks(await getMonthlyTaskList(year, month + 1, 0), true);
});
const prevBtn = document.getElementById('prev');
const nextBtn = document.getElementById('next');
if (prevBtn) {
    prevBtn.addEventListener('click', function () {
        clearSelectedDate();
        moveToPrevCalendar();
        setScrollTop();
        setTaskPage();
    });
}
;
if (nextBtn) {
    nextBtn.addEventListener('click', function () {
        clearSelectedDate();
        moveToNextCalendar();
        setScrollTop();
        setTaskPage();
    });
}
;
>>>>>>> front
/* 주 수 */
export function getWeekCount(year, month, lastDay) {
    return Math.ceil((new Date(year, month, 1).getDay() + lastDay) / 7); // 시작 요일 위치 + 마지막 일자 / 7
}
/* 시작 요일 */
export function getFirstDay(year, month) {
    return new Date(year, month, 1).getDay();
}
/* 말일 */
export function getLastDay(year, month) {
    let monthLastDays = [31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    if (year % 400 == 0 && year % 4 == 0) {
        monthLastDays[1] = 29;
    }
    else if (year % 100 != 0) {
        monthLastDays[1] = 28;
    }
    return monthLastDays[month];
}
/* 이전 달의 말일 */
export function getLastDayOfPrevMonth(year, month) {
    month--;
    if (month < 0) { // 이전 연도
        year--;
        month = 12;
    }
    if (month > 11) { // 다음 연도
        year++;
        month = 1;
    }
    return getLastDay(year, month);
}
/* 달력 넘길 때 스크롤 상단 고정 */
export function setScrollTop() {
    const taskDiv = document.getElementById('task-div');
    taskDiv.scrollTop = 0;
}
/* 달력 넘길 때 선택된 날짜 지우기 */
function clearSelectedDate() {
    const selectedDate = document.getElementById('input-date');
    selectedDate.innerText = "";
}
/* 현재 선택된 달력 */
export function getCurrentCalendar() {
    const calDate = document.getElementById('cal-date');
    let year = Number(calDate.innerText.substring(0, 4));
    let month = Number(calDate.innerText.substring(5, 7));
    return [year, month];
}
/* 이전 달 */
async function moveToPrevCalendar() {
<<<<<<< HEAD
    let seletedDate = getCurrentCalendar();
    let year = seletedDate[0];
    let month = seletedDate[1];
=======
    let selectedDate = getCurrentCalendar();
    let year = selectedDate[0];
    let month = selectedDate[1];
>>>>>>> front
    month--;
    if (month <= 0) {
        year--;
        month = 12;
    }
    renderCalendar(year, month - 1);
<<<<<<< HEAD
    renderNewTasks(await getMonthlyTaskList(year, month, 0), "none");
}
/* 다음 달 */
async function moveToNextCalendar() {
    let seletedDate = getCurrentCalendar();
    let year = seletedDate[0];
    let month = seletedDate[1];
=======
    renderNewTasks(await getMonthlyTaskList(year, month, 0), true);
}
/* 다음 달 */
async function moveToNextCalendar() {
    let selectedDate = getCurrentCalendar();
    let year = selectedDate[0];
    let month = selectedDate[1];
>>>>>>> front
    month++;
    if (month > 12) {
        year++;
        month = 1;
    }
    renderCalendar(year, month - 1);
<<<<<<< HEAD
    renderNewTasks(await getMonthlyTaskList(year, month, 0), "none");
}
const prevBtn = document.getElementById('prev');
const nextBtn = document.getElementById('next');
if (prevBtn) {
    prevBtn.addEventListener('click', function () {
        clearSelectedDate();
        moveToPrevCalendar();
        setScrollTop();
        setTaskPage();
    });
}
;
if (nextBtn) {
    nextBtn.addEventListener('click', function () {
        clearSelectedDate();
        moveToNextCalendar();
        setScrollTop();
        setTaskPage();
    });
}
;
=======
    renderNewTasks(await getMonthlyTaskList(year, month, 0), true);
}
>>>>>>> front
