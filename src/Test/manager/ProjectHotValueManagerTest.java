package manager;

import com.free4lab.freeRT.manager.ProjectHotValueManager;
import com.free4lab.freeRT.model.Project;
import com.free4lab.freeRT.model.ProjectHotValue;
import com.free4lab.freeRT.utils.DateUtil;
import org.junit.Test;

import java.util.List;

public class ProjectHotValueManagerTest {
    ProjectHotValueManager projectHotValueManager = new ProjectHotValueManager();

    @Test
    public void findProjectWeekHotValueTest() {
        System.out.println(projectHotValueManager.findProjectWeekHotValue(126));
        System.out.println("-------------------------------------");
//        System.out.println(projectHotValueManager.findProjectWeekHotValue(126));
    }




}



