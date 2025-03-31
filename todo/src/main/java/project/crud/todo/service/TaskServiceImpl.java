package project.crud.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.crud.todo.domain.dto.AttachDTO;
import project.crud.todo.domain.dto.TaskDTO;
import project.crud.todo.domain.entity.Attach;
import project.crud.todo.domain.entity.Task;
import project.crud.todo.domain.vo.TaskVO;
import project.crud.todo.repository.AttachRepository;
import project.crud.todo.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final int DEFAULT_TASK_SIZE = 20;
    private final String DEFAULT_TASK_SORT_BY = "scheduledDate";

    private final TaskRepository taskRepository;
    private final AttachService attachService;
    private final AttachRepository attachRepository;
    private final S3UploadService s3UploadService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, AttachService attachService, AttachRepository attachRepository, S3UploadService s3UploadService) {
        this.taskRepository = taskRepository;
        this.attachService = attachService;
        this.attachRepository = attachRepository;
        this.s3UploadService = s3UploadService;
    }

    @Override
    @Transactional
    public boolean create(List<MultipartFile> files, TaskVO taskVO) {
        try {
            if (files != null) { // TODO: List 가 없을 수도 있고, List 는 있지만 비어있을 수도 있음. 현재는 List 가 비어있을 때에도 내부 로직을 타게 됨
                attachService.save(files, taskVO.getGroupId(), taskVO.getCreatedBy());
            }
            taskRepository.save(Task.from(taskVO));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Task not found"));
        attachRepository.deleteByGroupId(task.getGroupId()); // TODO: 반환 값이 void 이면 이 메서드가 정말 성공해서 true 인지 모름. 실무에서는 nativeQuery 만들어서 int 반환하고 있음
        taskRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getMonthlyTask(int page, int year, int month) {
        Pageable pageable = PageRequest.of(page, DEFAULT_TASK_SIZE, Sort.by(DEFAULT_TASK_SORT_BY)); // TODO: Pageable 컨트롤러에서 자동 매핑
        Page<Task> tasks = taskRepository.findAllByYearAndMonth(year, month, pageable);
        return getTasks(tasks);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getDailyTask(int page, int year, int month, int day) {
        Pageable pageable = PageRequest.of(page, DEFAULT_TASK_SIZE, Sort.by(DEFAULT_TASK_SORT_BY));  // TODO: Pageable 컨트롤러에서 자동 매핑
        Page<Task> tasks = taskRepository.findAllByYearAndMonthAndDay(year, month, day, pageable);
        return getTasks(tasks);
    }

    private List<TaskDTO> getTasks(Page<Task> tasks) {
        List<TaskDTO> taskDTOList = new ArrayList<>();
        for (Task task : tasks) {
            List<AttachDTO> attachDTOS = getAttaches(task);
            taskDTOList.add(new TaskDTO(task, attachDTOS));
        }
        return taskDTOList;
    }

    private List<AttachDTO> getAttaches(Task task) {
        List<Attach> attaches = attachRepository.findByGroupId(task.getGroupId());
        return attaches.stream()
                .map(attach -> new AttachDTO(
                        attach.getId(),
                        attach.getOriginName(),
                        s3UploadService.generatePreSignedUrl(attach.getS3Key()) // TODO: 느려짐 / 퍼블릭 액세스 + 특정 IP만 허용
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public int[] getTaskCount(int year, int month) {
        int lastDay = getLastDay(year, month);
        int[] countArr = new int[lastDay + 1];
        countArr[0] = -1;
        for (int i = 1; i <= lastDay; i++) {
            countArr[i] = taskRepository.countByYearAndMonthAndDay(year, month, i);
        }
        return countArr;
    }

    private int getLastDay(int year, int month) {
        int[] lastDayByMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month == 2) {
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                lastDayByMonth[1] = 29;
            }
        }
        return lastDayByMonth[month - 1];
    }
}