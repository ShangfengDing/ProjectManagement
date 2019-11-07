package dao;

import com.free4lab.freeRT.dao.ProjectHotValueDAO;
import com.free4lab.freeRT.model.ProjectHotValue;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

import static com.free4lab.freeRT.utils.DateUtil.findMonday;
import static com.free4lab.freeRT.utils.DateUtil.stringToTimestamp;
import static org.junit.Assert.*;

public class ProjectHotValueDAOTest {
    ProjectHotValueDAO projectHotValueDAO =  new ProjectHotValueDAO();
    @Test
    public void findHotValueByWeek() {
        Timestamp monday= stringToTimestamp("2018-5-17 12:21:15");

        List<ProjectHotValue> list = projectHotValueDAO.findHotValueByWeek(monday,126);
        for(ProjectHotValue projectHotValue:list){
            System.out.println("时间"+projectHotValue.getTime()+"热力值："+projectHotValue.getHotvalue());
        }
    }
}