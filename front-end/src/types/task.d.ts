interface Task {
    id: string,
    content: string,
    year: string,
    month: string,
    day: string,
    groupId: string,
}

interface NewTask {
    content: string,
    year: string,
    month: string,
    day: string,
    createdBy: string,
    groupId: string,
}