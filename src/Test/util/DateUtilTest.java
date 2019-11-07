package util;
import com.free4lab.freeRT.manager.ProjectHotValueManager;
import com.free4lab.freeRT.model.ProjectHotValue;
import com.free4lab.freeRT.utils.DateUtil;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

import static com.free4lab.freeRT.utils.DateUtil.findMonday;
import static com.free4lab.freeRT.utils.DateUtil.stringToTimestamp;
import static org.junit.Assert.*;

public class DateUtilTest {




    @Test
    public void ergodicWeekTime() {

//        dateUtil.ergodicWeekTime()
    }
    @Test
    public void stringToTimestampTest(){
        System.out.println(stringToTimestamp("2015-8-15 15:32:11"));

    }
    @Test
    public void findWeekByDateTest() {
        List<ProjectHotValue> projectHotValuelist = new ProjectHotValueManager().findHotValueByPid(126);
        DateUtil dateUtil = new DateUtil();
        for (ProjectHotValue temHotValue : projectHotValuelist) {
            System.out.println("This week " + dateUtil.findWeekByDate(temHotValue.getTime()));


        }
    }
    @Test
    public void findMondayTest(){

        System.out.println(findMonday("2018-5-19 15:32:11"));
    }



}