interface Task {
    id: string,
    content: string,
    year: string,
    month: string,
    day: string,
    groupId: string,
    attaches: Attach[]
}

interface NewTask {
    content: string,
    year: string,
    month: string,
    day: string,
    createdBy: string,
    groupId: string,
}