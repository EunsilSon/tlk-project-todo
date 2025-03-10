import { renderCalender } from "../utils/calenderRenderUtils.js";
import { getMonthlyTaskList } from "../services/taskService.js";
import { getMonthlyTaskProcess, getTaskCountProcess } from "../components/taskForm.js";

document.addEventListener('DOMContentLoaded', () => {
    let year: number;
    let month: number;

    if (localStorage.length === 0) {
        let date = new Date();
        year = date.getFullYear();
        month = date.getMonth();
    } else {
        year = Number(localStorage.getItem("year"));
        month = Number(localStorage.getItem("month"))-1;
        localStorage.clear(); 
    }

    renderCalender(year, month);
    getMonthlyTaskProcess(0);
})

// 현재 선택된 날짜
export function getSelectedDate() {
    let calDate = document.getElementById('cal-date') as HTMLElement;
    let year: number = Number(calDate.innerText.substring(0, 4));
    let month: number = Number(calDate.innerText.substring(5, 7));
    return [year, month];
}

// 주 수
export function getWeekCount(year: number, month: number, lastDay: number) {
    return Math.ceil((new Date(year, month, 1).getDay() + lastDay) / 7); // 시작 요일 위치 + 마지막 일자 / 7
}

// 시작 요일
export function getFirstDay (year: number, month: number) {
    return new Date(year, month, 1).getDay();
}

// 말일
export function getLastDay(year: number, month: number) {
    let monthLastDays: number[] = [31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    if (year % 400 == 0 && year % 4 == 0) {
        monthLastDays[1] = 29;
    } else if (year % 100 != 0) {
        monthLastDays[1] = 28;
    } 

    return monthLastDays[month];
}

// 이전 달의 말일
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

// 이전 달
function moveToPrevCalender() {
    let seletedDate: number[] = getSelectedDate();
    let year = seletedDate[0];
    let month = seletedDate[1];
    month--;
    if (month <= 0) {
        year--;
        month = 12;
    }
    renderCalender(year, month-1);
    getMonthlyTaskList(year, month, 0);
}

// 다음 달 {
function moveToNextCalender() {
    let seletedDate: number[] = getSelectedDate();
    let year = seletedDate[0];
    let month = seletedDate[1];
    month++;
    if (month > 12) {
        year++;
        month = 1;
    }
    renderCalender(year, month-1);
    getMonthlyTaskList(year, month, 0);
}

/*
이전, 다음 버튼
*/
const prevBtn = document.getElementById('prev');
const nextBtn = document.getElementById('next');

if (prevBtn) {
    prevBtn.addEventListener('click', function() {
        moveToPrevCalender();
})};

if (nextBtn) {
    nextBtn.addEventListener('click', function() {
        moveToNextCalender();
})};