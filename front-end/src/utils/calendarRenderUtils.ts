import { getWeekCount, getFirstDay, getLastDay, getLastDayOfPrevMonth } from "../components/calendarForm.js";
import { getDaliyTaskList, getTaskCount } from "../services/taskService.js";
import { renderTasks } from "./taskRenderUtils.js";

export function renderCalendar(year: number, month: number) {
    renderTitle(year, month);
    renderCalendarInner(year, month);
}

function renderTitle(year: number, month: number) {
    const calDate = document.getElementById("cal-date") as HTMLElement;
    calDate.innerText = year + "." + (month + 1);
}

async function renderCalendarInner(year: number, month: number) {
    let firstDayIdx = getFirstDay(year, month);
    let lastDay = getLastDay(year, month);
    let weekCount = getWeekCount(year, month, lastDay);
    let prevDay = getLastDayOfPrevMonth(year, month) - firstDayIdx;
    let nextDay = 1;
    let dayCount = 1;

    const calDiv = document.getElementById("calender") as HTMLElement;
    let calInnerDiv = document.getElementById("cal-inner-div") as HTMLElement;

    if (!calInnerDiv) {
        calInnerDiv = document.createElement("td");
        calInnerDiv.id = "cal-inner-div";
        calDiv.appendChild(calInnerDiv);
    } else {
        calInnerDiv.innerHTML = "";
    }

    const countArr: number[] = await getTaskCount(year, month+1); // 저장된 task 개수
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

            day.addEventListener("click", async () => {
                // 선택된 날짜 css표시
                document.querySelectorAll(".now.selected").forEach(d => d.classList.remove("selected")); // 이전에 선택된 요소의 selected 속성 제거거
                if (day.classList.contains("now")) {
                    day.classList.add("selected");
                }

                renderSelectedDay(year, month+1, day.innerText);
                renderTasks(await getDaliyTaskList(year, month+1, day.innerText || "", 0));
            });
    
            row.appendChild(dayItem);
        }
        calInnerDiv.appendChild(row);
    }
}

function renderSelectedDay(year: number, month: number, day: string) {
    const inputDate = document.getElementById('input-date') as HTMLElement;
    inputDate.textContent = `${year}. ${month}. ${day}`;
}