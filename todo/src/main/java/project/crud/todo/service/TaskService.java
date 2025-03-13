package project.crud.todo.service;

import project.crud.todo.domain.dto.TaskDTO;
import project.crud.todo.domain.vo.TaskUpdateVO;
import project.crud.todo.domain.vo.TaskVO;

import java.util.List;

public interface TaskService {
    // 보니까 이거 사용하는 구간에서 응답에 따른 처리 해주려고 구현한 것으로 보임
    // 자바스크립트 스타일인데, 우리쪽 컨벤션은 void를 기본 정책으로 삼고, 반환해주지 않으면 모르는 값이 생길 경우에만 값을 반환해주고 있음
    boolean create(TaskVO taskVO);

    boolean update(TaskUpdateVO taskUpdateVO);

    boolean delete(Long id);

    List<TaskDTO> getMonthlyTask(int page, int year, int month);

    List<TaskDTO> getDailyTask(int page, int year, int month, int day);

    //이거 왜 int[]인지 궁금함
    int[] getTaskCount(int year, int month);

    // 이거 무슨 기능 ??
    //만약 controller같은 곳에서 사용하지 않을 계획이라면, private int 메소드로 변경 필요
    int getLastDay(int year, int month);
}