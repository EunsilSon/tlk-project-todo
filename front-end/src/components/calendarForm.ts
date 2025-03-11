import { renderCalendar } from "../utils/calendarRenderUtils.js";
import { renderTasks } from "../utils/taskRenderUtils.js";
import { getMonthlyTaskList } from "../services/taskService.js";

document.addEventListener('DOMContentLoaded', async () => {
    let year: number;
    let month: number;

    if (localStorage.length === 1) { // task 페이징을 위해 localStorage에 item이 항상 1개 있음
        let date = new Date();
        year = date.getFullYear();
        month = date.getMonth();
    } else { // task 생성 후 + item 2
        year = Number(localStorage.getItem("year"));
        month = Number(localStorage.getItem("month"))-1;
        localStorage.clear(); 
    }

    console.log(year);
    console.log(month);
    renderCalendar(year, month);
    renderTasks(await getMonthlyTaskList(year, month+1, 0), "none");
})

/* 달력 넘길 때 선택된 날짜 지우기 */
function clearSelectedDate() {
    const selectedDate = document.getElementById('input-date') as HTMLInputElement;
    selectedDate.innerText = "";
}

/* 현재 선택된 달력 */
export function getCurrentCalendar() {
    const calDate = document.getElementById('cal-date') as HTMLElement;
    let year: number = Number(calDate.innerText.substring(0, 4));
    let month: number = Number(calDate.innerText.substring(5, 7));
    return [year, month];
}

/* 주 수 */
export function getWeekCount(year: number, month: number, lastDay: number) {
    return Math.ceil((new Date(year, month, 1).getDay() + lastDay) / 7); // 시작 요일 위치 + 마지막 일자 / 7
}

/* 시작 요일 */
export function getFirstDay (year: number, month: number) {
    return new Date(year, month, 1).getDay();
}

/* 말일 */
export function getLastDay(year: number, month: number) {
    let monthLastDays: number[] = [31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    if (year % 400 == 0 && year % 4 == 0) {
        monthLastDays[1] = 29;
    } else if (year % 100 != 0) {
        monthLastDays[1] = 28;
    } 

    return monthLastDays[month];
}

/* 이전 달의 말일 */
export function getLastDayOfPrevMonth(year: number, month: number) {
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

/* 이전 달 */
async function moveToPrevCalendar() {
    let seletedDate: number[] = getCurrentCalendar();
    let year = seletedDate[0];
    let month = seletedDate[1];

    month--;
    if (month <= 0) {
        year--;
        month = 12;
    }

    renderCalendar(year, month-1);
    renderTasks(await getMonthlyTaskList(year, month, 0), "none");
}

/* 다음 달 */
    async function moveToNextCalendar() {
    let seletedDate: number[] = getCurrentCalendar();
    let year = seletedDate[0];
    let month = seletedDate[1];

    month++;
    if (month > 12) {
        year++;
        month = 1;
    }
    
    renderCalendar(year, month-1);
    renderTasks(await getMonthlyTaskList(year, month, 0), "none");
}

const prevBtn = document.getElementById('prev');
const nextBtn = document.getElementById('next');

if (prevBtn) {
    prevBtn.addEventListener('click', function() {
    clearSelectedDate();
    moveToPrevCalendar();
})};

if (nextBtn) {
    nextBtn.addEventListener('click', function() {
    clearSelectedDate();
    moveToNextCalendar();
})};