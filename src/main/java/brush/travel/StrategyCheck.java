package brush.travel;

import java.util.Date;
import java.util.List;

import brush.entity.DateStrategy;
import brush.entity.SendStrategy;
import brush.entity.Strategy;
import brush.util.DateUtils;

public class StrategyCheck {
	/**
	 * 每天需要发包数。
	 * @param strategy
	 */
	public void setTotalPerDay(){
		Strategy strategy=DataInfo.strategy;
		DateStrategy dateStrategy=strategy.getDateStrategy();
		Date now = new Date();
		int day=(int) ((dateStrategy.getEndTime().getTime()-now.getTime())/(24*60*60*1000)); 
		dateStrategy.setTotalPerDay(dateStrategy.getTotal()/day);
	}
	
	/**
	 * 设置休眠时间。
	 * @param strategy
	 */
	public void setSleepTime(){
		Strategy strategy=DataInfo.strategy;
		long totalPerDay=strategy.getDateStrategy().getTotalPerDay();
		List<SendStrategy> list=strategy.getSendStrategys();
		String fromTime=null;
		String toTime=null;
		String nowDate=DateUtils.getTodayYMD();
		Date dateFromTime=null;
		Date dateToTime=null;
		long sleeptime=0;
		for(SendStrategy sendStrategy:list){
			fromTime=nowDate+" "+sendStrategy.getFromTime();
			toTime=nowDate+" "+sendStrategy.getToTime();
			dateFromTime=DateUtils.getTime(fromTime);
			dateToTime=DateUtils.getTime(toTime);
			sleeptime=(dateToTime.getTime()-dateFromTime.getTime())/(long)(totalPerDay*sendStrategy.getPercent());
			sendStrategy.setSleepTime(sleeptime);
			
		}
	}
	
	/**
	 * 获取当前休眠时间。
	 * @return
	 */
	public long getCurrSleepTime(){
		Date now = new Date();
		Strategy strategy=DataInfo.strategy;
		List<SendStrategy> list=strategy.getSendStrategys();
		String fromTime=null;
		String toTime=null;
		String nowDate=DateUtils.getTodayYMD();
		Date dateFromTime=null;
		Date dateToTime=null;
		for(SendStrategy sendStrategy:list){
			fromTime=nowDate+" "+sendStrategy.getFromTime();
			toTime=nowDate+" "+sendStrategy.getToTime();
			dateFromTime=DateUtils.getTime(fromTime);
			dateToTime=DateUtils.getTime(toTime);
			if(now.getTime()>dateFromTime.getTime()&&now.getTime()<=dateToTime.getTime()){
				return sendStrategy.getSleepTime();
			}else{
				continue;
			}
			
		}
		return 10*1000;
	}
	
	/**
	 * 在时间范围内发包。
	 * @return
	 */
	public boolean isSendData(){
		Date now = new Date();
		Strategy strategy=DataInfo.strategy;
		DateStrategy dateStrategy=strategy.getDateStrategy();
		return now.getTime()>dateStrategy.getStartTime().getTime()&&now.getTime()<=dateStrategy.getEndTime().getTime();
	}
}
