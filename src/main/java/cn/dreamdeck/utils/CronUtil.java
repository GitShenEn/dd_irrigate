package cn.dreamdeck.utils;


import cn.dreamdeck.model.TaskScheduleModel;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname CronUtil
 * @Description TODO
 */
public class CronUtil {

    /**
     *
     *方法摘要：构建Cron表达式
     *@param  taskScheduleModel
     *@return String
     */
    public static String createCronExpression(TaskScheduleModel taskScheduleModel){
        StringBuffer cronExp = new StringBuffer("");

        if(null == taskScheduleModel.getJobType()) {
            System.out.println("执行周期未配置" );//执行周期未配置
        }

        if (null != taskScheduleModel.getSecond()
                && null == taskScheduleModel.getMinute()
                && null == taskScheduleModel.getHour()){
            //每隔几秒
            if (taskScheduleModel.getJobType().intValue() == 0) {
                cronExp.append("0/").append(taskScheduleModel.getSecond());
                cronExp.append(" ");
                cronExp.append("* ");
                cronExp.append("* ");
                cronExp.append("* ");
                cronExp.append("* ");
                cronExp.append("?");
            }

        }

        if (null != taskScheduleModel.getSecond()
                && null != taskScheduleModel.getMinute()
                && null == taskScheduleModel.getHour()){
            //每隔几分钟
            if (taskScheduleModel.getJobType().intValue() == 4) {
                cronExp.append("* ");
                cronExp.append("0/").append(taskScheduleModel.getMinute());
                cronExp.append(" ");
                cronExp.append("* ");
                cronExp.append("* ");
                cronExp.append("* ");
                cronExp.append("?");
            }

        }

        if (null != taskScheduleModel.getSecond()
                && null != taskScheduleModel.getMinute()
                && null != taskScheduleModel.getHour()) {
            //秒
            cronExp.append(taskScheduleModel.getSecond()).append(" ");
            //分
            cronExp.append(taskScheduleModel.getMinute()).append(" ");
            //小时
            cronExp.append(taskScheduleModel.getHour()).append(" ");

            //每天
            if(taskScheduleModel.getJobType().intValue() == 1){
                cronExp.append("* ");//日
                cronExp.append("* ");//月
                cronExp.append("?");//周
            }else if (taskScheduleModel.getJobType().intValue() == 6){
                cronExp.append("1/").append(taskScheduleModel.getOpenInterval());
                cronExp.append(" ");
                cronExp.append("* ");//月
                cronExp.append("?");//周
            }


        }
        else {
            System.out.println("时或分或秒参数未配置" );//时或分或秒参数未配置
        }
        return cronExp.toString();
    }

    public static Object createConr(String startTime, String endTime){

        return null;
    }

    /**
     *
     *方法摘要：生成计划的详细描述
     *@param  taskScheduleModel
     *@return String
     */
    public static String createDescription(TaskScheduleModel taskScheduleModel){
        StringBuffer description = new StringBuffer("");
        //计划执行开始时间
//      Date startTime = taskScheduleModel.getScheduleStartTime();

        if (null != taskScheduleModel.getSecond()
                && null != taskScheduleModel.getMinute()
                && null != taskScheduleModel.getHour()) {
            //按每天
            if(taskScheduleModel.getJobType().intValue() == 1){
                description.append("每天");
                description.append(taskScheduleModel.getHour()).append("时");
                description.append(taskScheduleModel.getMinute()).append("分");
                description.append(taskScheduleModel.getSecond()).append("秒");
                description.append("执行");
            }

            //按每周
            else if(taskScheduleModel.getJobType().intValue() == 3){
                if(taskScheduleModel.getDayOfWeeks() != null && taskScheduleModel.getDayOfWeeks().length > 0) {
                    String days = "";
                    for(int i : taskScheduleModel.getDayOfWeeks()) {
                        days += "周" + i;
                    }
                    description.append("每周的").append(days).append(" ");
                }
                if (null != taskScheduleModel.getSecond()
                        && null != taskScheduleModel.getMinute()
                        && null != taskScheduleModel.getHour()) {
                    description.append(",");
                    description.append(taskScheduleModel.getHour()).append("时");
                    description.append(taskScheduleModel.getMinute()).append("分");
                    description.append(taskScheduleModel.getSecond()).append("秒");
                }
                description.append("执行");
            }

            //按每月
            else if(taskScheduleModel.getJobType().intValue() == 2){
                //选择月份
                if(taskScheduleModel.getDayOfMonths() != null && taskScheduleModel.getDayOfMonths().length > 0) {
                    String days = "";
                    for(int i : taskScheduleModel.getDayOfMonths()) {
                        days += i + "号";
                    }
                    description.append("每月的").append(days).append(" ");
                }
                description.append(taskScheduleModel.getHour()).append("时");
                description.append(taskScheduleModel.getMinute()).append("分");
                description.append(taskScheduleModel.getSecond()).append("秒");
                description.append("执行");
            }

        }
        return description.toString();
    }

    //参考例子
    public static void main(String[] args) {
        System.out.println(getCron("2020-01-18","10:15:00",4));
    }


    public static String  getCron(String startYtd,String startHms,Integer openInterval){
        try {
            Date date =  DateUtil.getDateTime(startYtd+" "+startHms);
            System.out.println(DateUtil.getTime(date));
            TaskScheduleModel taskScheduleModel = new TaskScheduleModel();
            if (openInterval==1){
                taskScheduleModel.setJobType(1); //每天
            }else{
                taskScheduleModel.setJobType(6); //按照几天执行
            }
            taskScheduleModel.setHour(DateUtil.getHour(date));
            taskScheduleModel.setMinute(DateUtil.getMin(date));
            taskScheduleModel.setSecond(0);
            taskScheduleModel.setOpenInterval(openInterval);
            String   cronExp  = createCronExpression(taskScheduleModel);
            List list = null;
            list = getRecentTriggerTime(cronExp);
            if (list!=null&&list.size()>0){
                return  cronExp;
            }else{
                return  null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }


    public static String  getCron2(String time,Integer openInterval,Integer openTime){

        Date date =  DateUtil.getDateTime(time);
        System.out.println(DateUtil.getTime(date));
        TaskScheduleModel taskScheduleModel = new TaskScheduleModel();
        if (openInterval==1){
            taskScheduleModel.setJobType(1); //每天
        }else{
            taskScheduleModel.setJobType(6); //按照几天执行
        }
        taskScheduleModel.setHour(DateUtil.getHour(date));
        taskScheduleModel.setMinute(DateUtil.getMin(date));
        taskScheduleModel.setSecond(0);
        taskScheduleModel.setOpenInterval(openInterval);
        return createCronExpression(taskScheduleModel);
    }

    public static List<String> getRecentTriggerTime(String cron) {
        List<String> list = new ArrayList<String>();
        try {
            CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
            cronTriggerImpl.setCronExpression(cron);
            // 这个是重点，一行代码搞定
            List<Date> dates = TriggerUtils.computeFireTimes(cronTriggerImpl, null, 20);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Date date : dates) {
                list.add(dateFormat.format(date));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static String  getWaterPumpCron(String time, String startHms){
        Date date =  DateUtil.getDateTime(time+" "+startHms);
        if (date==null){
            return  null;
        }
        int hour = DateUtil.getHour(date);
        int min = DateUtil.getMin(date);
        StringBuffer cronExp = new StringBuffer("");
            cronExp.append("0 ");
            cronExp.append(hour+" ");
            cronExp.append(min+" ");
            cronExp.append("? ");
            cronExp.append("* ");
            cronExp.append(startHms);
        List list = null;
        try {
            list = getRecentTriggerTime(cronExp.toString());
            if (list!=null&&list.size()>0){
                return  cronExp.toString();
            }else{
                return  null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }


}
