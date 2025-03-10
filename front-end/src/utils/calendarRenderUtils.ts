import { getWeekCount, getFirstDay, getLastDay, getLastDayOfPrevMonth } from "../components/calendarForm.js";
import { getTaskCountProcess, getDailyTaskProcess } from "../components/taskForm.js";

function renderTitle(year: number, month: number) {
    const calDate = document.getElementById("cal-date") as HTMLElement;
    calDate.innerText = year + "." + (month + 1);
}

export async function renderCalendar(year: number, month: number) {
    let firstDayIdx = getFirstDay(year, month);
    let lastDay = getLastDay(year, month);
    let weekCount = getWeekCount(year, month, lastDay);
    let prevDay = getLastDayOfPrevMonth(year, month) - firstDayIdx;
    let nextDay = 1;
    let dayCount = 1;

    renderTitle(year, month); // 조회된 날짜
    renderSelectedDate(year, month+1, "1"); // 선택된 날짜

    const calDiv = document.getElementById("calender") as HTMLElement;
    let calInnerDiv = document.getElementById("cal-inner-div") as HTMLElement;

    if (!calInnerDiv) {
        calInnerDiv = document.createElement("td");
        calInnerDiv.id = "cal-inner-div";
        calDiv.appendChild(calInnerDiv);
    } else {
        calInnerDiv.innerHTML = "";
    }

    const countArr: number[] = await getTaskCountProcess(year, month); // 저장된 task 개수
    let countIdx = 1;

    for (let i = 0; i < weekCount; i++) {
        let row = document.createElement("tr");
        row.className = "week";

        for (let j = 0; j < 7; j++) {
            let dayItem = document.createElement("td");
            let day = document.createElement("div");
            let count = document.createElement("div");
            count.className = "count"
            dayItem.appendChild(day);
            
            if (i === 0 && j < firstDayIdx) { // 이전
                day.textContent = ++prevDay + "";
                day.className = "not-now";
            } else if (dayCount <= lastDay) { // 현재
                day.textContent = dayCount++ + "";
                day.className = "now";
                count.textContent = countArr[countIdx++] + "";

                dayItem.appendChild(count);
            } else if (dayCount > lastDay) { // 다음
                day.textContent = nextDay++ + "";
                day.className = "not-now";
            }

            day.addEventListener("click", () => {
                renderSelectedDate(year, month+1, day.innerText); // 선택한 일자 표시
                getDailyTaskProcess(day.innerText || "", 0); // 선택한 일자 task 조회
            });

            row.appendChild(dayItem);
        }
        calInnerDiv.appendChild(row);
    }
}

function renderSelectedDate(year: number, month: number, day: string) {
    const inputDate = document.getElementById('input-date') as HTMLElement;
    inputDate.textContent = `${year}. ${month}. ${day}`;
}