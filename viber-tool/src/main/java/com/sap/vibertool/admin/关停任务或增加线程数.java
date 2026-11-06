package com.sap.vibertool.admin;

import com.alibaba.fastjson2.JSONObject;
import com.sap.vibertool.admin.model.TaskModel;
import com.sap.vibertool.admin.util.AdminUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Slf4j
public class 关停任务或增加线程数 {
    /**
     * 获取当前时间到下一个“5分钟整点”（0/5分 0秒）的毫秒数
     *
     * @return 距离下一个整点的毫秒数
     */
    public static long getMillisToNextFiveMinuteInterval() {
        LocalDateTime now = LocalDateTime.now();

        // 1. 获取当前分钟数
        int minute = now.getMinute();

        // 2. 计算需要增加的分钟数，以到达下一个 5 的倍数
        // 例如: 12 -> 15 (加3), 15 -> 20 (加5, 因为要严格大于当前时刻，若当前秒>0)
        // 注意：如果当前是 10:05:00.000，严格来说下一个整点是 10:10:00.000

        int remainder = minute % 5;
        int minutesToAdd;

        if (remainder == 0) {
            // 当前分钟已经是 5 的倍数 (0, 5, 10...)
            // 检查秒和纳秒是否也为 0
            if (now.getSecond() == 0 && now.getNano() == 0) {
                // 正好在整点上，下一个整点是 5 分钟后
                minutesToAdd = 5;
            } else {
                // 分钟对但秒不对，例如 10:05:30，下一个整点是 10:10:00
                minutesToAdd = 5;
            }
        } else {
            // 当前分钟不是 5 的倍数，例如 10:12:30，下一个整点是 10:15:00
            minutesToAdd = 5 - remainder;
        }

        // 3. 计算目标时间
        LocalDateTime nextInterval = now.plusMinutes(minutesToAdd)
                .withSecond(0)
                .withNano(0);

        // 4. 计算毫秒差值
        return ChronoUnit.MILLIS.between(now, nextInterval);
    }

    static void main() throws Exception {
//        https://www.viber.mom/api/risk-analysis/task-dimensions?taskId=21cf8aba008841e2&minSamples=1&limit=5000
        long millisToNextFiveMinuteInterval =0L;
//         millisToNextFiveMinuteInterval = getMillisToNextFiveMinuteInterval();
//        if (millisToNextFiveMinuteInterval != 0) {
//            long second = millisToNextFiveMinuteInterval / 1000;
//            long min = second / 60;
//            long se = second % 60;
//            log.info("等待{}分{}秒", min, se);
//            Thread.sleep(millisToNextFiveMinuteInterval);
//        }
        for (int i = 0; i < 30 * 24 * 12; i++) {
            try {
                log.info("{} 开始执行", i);
                abcd();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(5000L);
            millisToNextFiveMinuteInterval = getMillisToNextFiveMinuteInterval();
            if (millisToNextFiveMinuteInterval != 0) {
                long second = millisToNextFiveMinuteInterval / 1000;
                long min = second / 60;
                long se = second % 60;
                log.info("休眠{}分{}秒", min, se);
                Thread.sleep(millisToNextFiveMinuteInterval);
            }
        }

        //完成
        System.exit(0);
    }

    private static void abcd() {
        AdminUtil.checkAuthorization();
        List<TaskModel> taskList = AdminUtil.registerTaskList();

        try {
            Boolean warning = true;
//            pauseTask(taskList, warning);
            stopTask(taskList, warning);

            plusThreads(taskList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void plusThreads(List<TaskModel> taskList) {
        int i = 0;

        int ii = 0;

        boolean change = true;

        for (TaskModel taskModel : taskList) {
            if (!Objects.equals("RUNNING", taskModel.getStatus())) {
                continue;
            }
            if (taskModel.getSuccessCount() >= 2) {
//
                Integer recentCodeArrivalRate = taskModel.getRecentCodeArrivalRate();
                int thread = taskModel.getThreadCount();
                if (recentCodeArrivalRate > 2) {
                    int newThreads = getNewThreads(recentCodeArrivalRate);
                    if (newThreads > thread) {
                        ++i;
                        log.info("{}：需要加线程数量 {} rate:{} thread:{}》{} {}", i, taskModel.getId(), recentCodeArrivalRate, thread, newThreads, taskModel);
                        if (change) {
                            changeThreads(taskModel, newThreads);
                        }
                        continue;
                    }
                }

                Double totalRate = taskModel.getSuccessCount() * 100.0 / taskModel.getFetchCount();
                int newThreads = getNewThreads(recentCodeArrivalRate);
                if (totalRate > recentCodeArrivalRate && newThreads != thread) {
                    ii++;
                    //成功率下降
                    log.info("{}：需要减少线程数量 {} rate:{} thread:{}》{} {}", ii, taskModel.getId(), recentCodeArrivalRate, thread, newThreads, taskModel);
                    if (change) {
                        changeThreads(taskModel, newThreads);
                    }
                    continue;
                }
            }
        }
        System.out.println("加线程:" + i);
        System.out.println("减线程:" + ii);
        System.out.println("停止的" + stopTaskCount);

    }

    private static int getNewThreads(Integer recentCodeArrivalRate) {
        if (recentCodeArrivalRate <= 2) {
            return 1;
        }
        if (recentCodeArrivalRate <= 5) {
            return 2;
        }
        if (recentCodeArrivalRate <= 9) {
            return 3;
        }
        if (recentCodeArrivalRate <= 15) {
            return 4;
        }
        if (recentCodeArrivalRate <= 20) {
            return 5;
        }

        if (recentCodeArrivalRate <= 30) {
            return 6;
        }

        if (recentCodeArrivalRate <= 40) {
            return 7;
        }
        if (recentCodeArrivalRate <= 45) {
            return 8;
        }
        if (recentCodeArrivalRate <= 50) {
            return 9;
        }

        if (recentCodeArrivalRate <= 100) {
            return 10;
        }
        return 1;
    }

    private static void changeThreads(TaskModel taskModel, int threadCount) {
        /*if (taskModel.getResourceId().equalsIgnoreCase(ResourceConstant.HERO_SMS)) {
            log.info("hero 不自动变更线程数:   {}    {}",taskModel.getId(),taskModel);
            System.err.println("hero 不自动变更线程数:" + taskModel);
            return;
        }*/
        JSONObject json = new JSONObject();
        json.put("taskCount", taskModel.getTaskCount());
        json.put("threadCount", threadCount);
        json.put("mncList", taskModel.getMncList());
        json.put("language", taskModel.getLanguage());
        json.put("maxPriceUsd", taskModel.getMaxPriceUsd());
        json.put("proxyCountry", taskModel.getProxyCountry());
        json.put("resourceParams", new JSONObject());
        System.out.println(taskModel.getId() + "\t修改线程数:" + json);
        JSONObject jsonObject = AdminUtil.requestPost("https://www.viber.mom/api/register-tasks/" + taskModel.getId() + "/update", json);
        System.out.println("修改结果:" + jsonObject);
    }

    static int stopTaskCount = 0;

    private static void pauseTask(List<TaskModel> taskList, boolean stop) {
        for (TaskModel taskModel : taskList) {
            Long createdAt = taskModel.getCreatedAt();
            //超过10分钟，
            if (!Objects.equals("RUNNING", taskModel.getStatus())) {
                continue;
            }
            if (System.currentTimeMillis() - createdAt < 1000 * 60 * 10) {
                continue;
            }
            if (stop) {
                if ((taskModel.getSuccessCount() <= 0 && (taskModel.getFetchCount() > 150 || taskModel.getFetchCount() == 0))) {
                    System.out.println(++stopTaskCount + "\t" + taskModel.getId() + " 效果不好的\t" + taskModel);
//                    if (RandomUtil.randomBoolean())
                    AdminUtil.pause(taskModel.getId());
                    continue;
                }
            }
        }
        System.out.println("需要关闭的" + stopTaskCount);
    }

    private static void stopTask(List<TaskModel> taskList, boolean stop) {
        for (TaskModel taskModel : taskList) {
            Long createdAt = taskModel.getCreatedAt();
            //超过10分钟，
            if (!Objects.equals("RUNNING", taskModel.getStatus())) {
                continue;
            }
            if (System.currentTimeMillis() - createdAt < 1000 * 60 * 10) {
                continue;
            }
            if (stop) {
                if ((taskModel.getSuccessCount() <= 0 && (taskModel.getFetchCount() > 150 || taskModel.getFetchCount() == 0))) {
                    System.out.println(++stopTaskCount + "\t" + taskModel.getId() + " 效果不好的\t" + taskModel);
//                    if (RandomUtil.randomBoolean())
                    AdminUtil.stop(taskModel.getId());
                    continue;
                }
            }
        }
        System.out.println("需要关闭的" + stopTaskCount);
    }

}
