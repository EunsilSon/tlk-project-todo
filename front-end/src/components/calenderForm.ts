import { renderCalendar } from "../utils/calenderRenderUtils.js";

document.addEventListener('DOMContentLoaded', () => {
    let date = new Date();
    let year = date.getFullYear();
    let month = date.getMonth();

    let firstDayIdx = getFirstDay(year, month);
    let lastDay = getLastDay(year, month);
    let weekCount = getWeekCount(year, month, lastDay);
    renderCalendar(weekCount, firstDayIdx, lastDay);
})


// 주 수
function getWeekCount(year: number, month: number, lastDay: number) {
    return Math.ceil((new Date(year, month, 1).getDay() + lastDay) / 7); // 시작 요일 위치 + 마지막 일자 / 7
}


// 시작 요일
function getFirstDay (year: number, month: number) {
    return new Date(year, month, 1).getDay();
}


// 말일
function getLastDay(year: number, month: number): number {
    let monthLastDays: number[] = [31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

    if (year % 400 == 0 && year % 4 == 0) {
        monthLastDays[1] = 29;
    } else if (year % 100 == 0) {
        monthLastDays[1] = 28;
    } 

    return monthLastDays[month];
}