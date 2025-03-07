import { getWeekCount, getFirstDay, getLastDay, getLastDayOfPrevMonth } from "../components/calenderForm.js";

export function renderDateInit(year: number, month: number) {
    const calDate = document.getElementById("cal-date") as HTMLElement;
    calDate.innerText = year + "." + month;
}

function renderDate(year: number, month: number) {
    const calDate = document.getElementById("cal-date") as HTMLElement;
    calDate.innerText = year + "." + (month + 1);
}

export function renderCalender(year: number, month: number) {
    let firstDayIdx = getFirstDay(year, month);
    let lastDay = getLastDay(year, month);
    let weekCount = getWeekCount(year, month, lastDay);
    let prevDay = getLastDayOfPrevMonth(year, month) - firstDayIdx;
    let nextDay = 1;
    let dayCount = 1;

    renderDate(year, month);

    const calDiv = document.getElementById("calender") as HTMLElement;
    let calInnerDiv = document.getElementById("cal-inner-div") as HTMLElement;

    if (!calInnerDiv) {
        calInnerDiv = document.createElement("td");
        calInnerDiv.id = "cal-inner-div";
        calDiv.appendChild(calInnerDiv);
    } else {
        calInnerDiv.innerHTML = "";
    }


    for (let i = 0; i < weekCount; i++) {
        let row = document.createElement("tr");
        row.className = "week";

        for (let j = 0; j < 7; j++) {
            let dayItem = document.createElement("td");
            if (i === 0 && j < firstDayIdx) {
                dayItem.textContent = ++prevDay + "";
                dayItem.classList.add("not-now");
            } else if (dayCount <= lastDay) {
                dayItem.textContent = dayCount++ + "";
                dayItem.classList.add("now");
            } else if (dayCount > lastDay) {
                dayItem.textContent = nextDay++ + "";
                dayItem.classList.add("not-now");
            }
            row.appendChild(dayItem);
        }
        calInnerDiv.appendChild(row);
    }
}
