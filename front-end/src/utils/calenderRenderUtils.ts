export function renderCalendar(weekCount: number, firstDayIdx: number, lastDay: number) { // 연도, 월, 시작 요일, 말일
    console.log(weekCount);
    console.log(firstDayIdx);
    console.log(lastDay);
    
    const dayDiv = document.getElementById("day-div") as HTMLBodyElement;
    dayDiv.innerHTML = "";

    let dayCount = 1;

    for (let i = 0; i < weekCount; i++) {
        let row = document.createElement("ul");
        row.className = "day-navigation";

        for (let j = 0; j < 7; j++) {
            let dayItem = document.createElement("li");
            if (i === 0 && j < firstDayIdx) { // 첫째주 시작 요일부터 일자 입력
                dayItem.textContent = "";
            } else if (dayCount <= lastDay) {
                dayItem.textContent = dayCount++ + "";
            } 
            row.appendChild(dayItem);
        }
        dayDiv.appendChild(row);
    }
}