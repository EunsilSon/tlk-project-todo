export function renderCalenderDate(year: number, month: number) {
    const calDate = document.getElementById("cal-date") as HTMLElement;
    calDate.innerText = year + "." + (month+1);
}

export function renderCalendar(weekCount: number, firstDayIdx: number, lastDay: number) {
    console.log(weekCount);
    console.log(firstDayIdx);
    console.log(lastDay);

    const calDiv = document.getElementById("calender") as HTMLElement;
    //calDiv.innerHTML = "";

    let dayCount = 1;
    for (let i = 0; i < weekCount; i++) {
        let row = document.createElement("tr");
        row.className = "week";

        for (let j = 0; j < 7; j++) {
            let dayItem = document.createElement("td");
            if (i === 0 && j < firstDayIdx) {
                dayItem.textContent = "";
            } else if (dayCount <= lastDay) {
                dayItem.textContent = dayCount++ + "";
            } 
            row.appendChild(dayItem);
        }
        calDiv.appendChild(row);
    }
}