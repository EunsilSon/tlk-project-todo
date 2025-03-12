import { renderDateInit, renderCalender } from "../utils/calenderRenderUtils.js";
document.addEventListener('DOMContentLoaded', function () {
    var date = new Date();
    renderDateInit(date.getFullYear(), date.getMonth());
    renderCalender(date.getFullYear(), date.getMonth()); // 처음 로드될 때는 현재 날짜의 달력
});
// 주 수
export function getWeekCount(year, month, lastDay) {
    return Math.ceil((new Date(year, month, 1).getDay() + lastDay) / 7); // 시작 요일 위치 + 마지막 일자 / 7
}
// 시작 요일
export function getFirstDay(year, month) {
    return new Date(year, month, 1).getDay();
}
// 말일
export function getLastDay(year, month) {
    var monthLastDays = [31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    if (year % 400 == 0 && year % 4 == 0) {
        monthLastDays[1] = 29;
    }
    else if (year % 100 != 0) {
        monthLastDays[1] = 28;
    }
    return monthLastDays[month];
}
// 이전 달의 말일
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
// 이전 달
function moveToPrevCalender() {
    var calDate = document.getElementById('cal-date');
    var year = Number(calDate.innerText.substring(0, 4));
    var month = Number(calDate.innerText.substring(5, 7));
    month--;
    if (month <= 0) {
        year--;
        month = 12;
    }
    renderCalender(year, month - 1);
}
// 다음 달
function moveToNextCalender() {
    var calDate = document.getElementById('cal-date');
    var year = Number(calDate.innerText.substring(0, 4));
    var month = Number(calDate.innerText.substring(5, 7));
    month++;
    if (month > 12) {
        year++;
        month = 1;
    }
    renderCalender(year, month - 1);
}
// 버튼
var prevBtn = document.getElementById('prev');
var nextBtn = document.getElementById('next');
if (prevBtn) {
    prevBtn.addEventListener('click', moveToPrevCalender);
}
if (nextBtn) {
    nextBtn.addEventListener('click', moveToNextCalender);
}
