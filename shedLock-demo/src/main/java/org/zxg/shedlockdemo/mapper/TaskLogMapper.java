package org.zxg.shedlockdemo.mapper;
// com.example.mapper.TaskLogMapper.java
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.zxg.shedlockdemo.entity.TaskLog;

@Mapper
public interface TaskLogMapper {

    @Insert("INSERT INTO t_task_log (task_name, status, message, execute_time) " +
            "VALUES (#{taskName}, #{status}, #{message}, #{executeTime})")
    int insert(TaskLog log);
}