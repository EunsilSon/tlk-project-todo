import { getWeekCount, getFirstDay, getLastDay, getLastDayOfPrevMonth } from "../components/calenderForm.js";
export function renderDateInit(year, month) {
    var calDate = document.getElementById("cal-date");
    calDate.innerText = year + "." + month;
}
function renderDate(year, month) {
    var calDate = document.getElementById("cal-date");
    calDate.innerText = year + "." + (month + 1);
}
export function renderCalender(year, month) {
    var firstDayIdx = getFirstDay(year, month);
    var lastDay = getLastDay(year, month);
    var weekCount = getWeekCount(year, month, lastDay);
    var prevDay = getLastDayOfPrevMonth(year, month) - firstDayIdx;
    var nextDay = 1;
    var dayCount = 1;
    renderDate(year, month);
    var calDiv = document.getElementById("calender");
    var calInnerDiv = document.getElementById("cal-inner-div");
    if (!calInnerDiv) {
        calInnerDiv = document.createElement("td");
        calInnerDiv.id = "cal-inner-div";
        calDiv.appendChild(calInnerDiv);
    }
    else {
        calInnerDiv.innerHTML = "";
    }
    for (var i = 0; i < weekCount; i++) {
        var row = document.createElement("tr");
        row.className = "week";
        for (var j = 0; j < 7; j++) {
            var dayItem = document.createElement("td");
            if (i === 0 && j < firstDayIdx) {
                dayItem.textContent = ++prevDay + "";
                dayItem.classList.add("not-now");
            }
            else if (dayCount <= lastDay) {
                dayItem.textContent = dayCount++ + "";
                dayItem.classList.add("now");
            }
            else if (dayCount > lastDay) {
                dayItem.textContent = nextDay++ + "";
                dayItem.classList.add("not-now");
            }
            row.appendChild(dayItem);
        }
        calInnerDiv.appendChild(row);
    }
}
