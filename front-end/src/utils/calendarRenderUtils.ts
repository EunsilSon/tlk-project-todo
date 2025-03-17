import { getWeekCount, getFirstDay, getLastDay, getLastDayOfPrevMonth, setScrollTop } from "../components/calendarForm.js";
import { setTaskPage, monthlyTaskProcess } from "../components/taskForm.js";
import { getDailyTaskList, getTaskCount } from "../services/taskService.js";
import { renderNewTasks } from "./taskRenderUtils.js";

export function renderCalendar(year: number, month: number) {
    renderTitle(year, month);
    renderInner(year, month);
}

function renderTitle(year: number, month: number) {
    const calDate = document.getElementById("cal-date") as HTMLElement;
    calDate.innerText = year + "." + (month + 1);

    calDate.addEventListener('click', () => {
        monthlyTaskProcess();
    })
}

async function renderInner(year: number, month: number) {

    let isCurrentYearAndMonth: boolean = false;
    let date = new Date();
    if (date.getFullYear() == year && date.getMonth() == month) {
        isCurrentYearAndMonth = true;
    }

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
            
            if (i === 0 && j < firstDayIdx) { // 이전
                day.textContent = ++prevDay + "";
                day.className = "not-now";
            } else if (dayCount <= lastDay) { // 현재
                day.textContent = dayCount++ + "";
                day.className = "now";

                if (countArr[countIdx] > 0) {
                    count.textContent = countArr[countIdx] + "";
                }
                countIdx++;

                // 현재 날짜 표시
                if (isCurrentYearAndMonth === true && dayCount == date.getDate()+1) {
                    day.id = "current-date";
                }
            } else if (dayCount > lastDay) { // 다음
                day.textContent = nextDay++ + "";
                day.className = "not-now";
            }

            day.addEventListener("click", async () => {
                /* 선택된 날짜 표시 */
                document.querySelectorAll(".now.selected").forEach(d => d.classList.remove("selected")); // 이전에 선택된 요소의 selected 속성 제거
                if (day.classList.contains("now")) {
                    day.classList.add("selected");
                }

                // task 조회 전 page 초기화
                setScrollTop();
                setTaskPage();
                
                renderSelectedDay(year, month+1, day.innerText);
                renderNewTasks(await getDailyTaskList(year, month+1, day.innerText, 0), day.innerText); // 날짜 변경될 때마다 해당 일의 task 조회
            });

            dayItem.appendChild(day);
            dayItem.appendChild(count);
            row.appendChild(dayItem);
        }
        calInnerDiv.appendChild(row);
    }
}

function renderSelectedDay(year: number, month: number, day: string) {
    const inputDate = document.getElementById('input-date') as HTMLElement;
    inputDate.textContent = `${year}. ${month}. ${day}`;
}
