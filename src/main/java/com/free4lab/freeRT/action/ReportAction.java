package com.free4lab.freeRT.action;

import com.free4lab.freeRT.dao.ProjectDAO;
import com.free4lab.freeRT.dao.ProjectPermissionDAO;
import com.free4lab.freeRT.dao.ReportPermissionDAO;
import com.free4lab.freeRT.manager.ProjectHotValueManager;
import com.free4lab.freeRT.manager.ProjectManager;
import com.free4lab.freeRT.manager.ReportManager;
import com.free4lab.freeRT.manager.UserManager;
import com.free4lab.freeRT.model.*;
import com.free4lab.freeRT.utils.Constants;
import com.free4lab.freeRT.utils.FileUtil;
import com.free4lab.freeRT.utils.PushMailUtil;
import com.free4lab.freeRT.utils.ReportTitleUtil;
import com.free4lab.utils.group.Result;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;




/**
 * Created by yph on 17-4-12.
 */
public class ReportAction {

    private static final int PAGE_SIZE = 10;//用于分页
    private int pageSum;//用于分页
    private int page;//用于分页

    private int projectId;//用于project_report.jsp/my_report.jsp
    private Project project;//用于project_report.jsp
    private List<ProjectPermission> pp;//用于project_report.jsp
    private Integer uid;//用于project_report.jsp
    private List<Project> projectList;//用于my_report.jsp
    private boolean draftBox;//用于my_report.jsp
    private boolean trashCan;//用于my_report.jsp
    private boolean list;
    private boolean calendar;
    private List<Report> reportList;//用于report_content.jsp


    private String formatReportName="";//用于show_report.jsp 的title输出

    private List<String> weekList;//用于new_report.jsp
    private List<String> monthList;//用于new_report.jsp
    private List<String> yearList;//用于new_report.jsp

    private Report report;//用于新建、编辑、显示报告
    private ProjectHotValueManager projectHotValueManager;


    private List<Attachment> attachmentList;//用于显示、编辑报告

    private int id;//用于显示、编辑、删除报告

    private String selectedProjectIdString;//用于新建、编辑报告
    private String searchProjectKeyword;//用于新建、编辑报告

    private String sendMailException;//发送邮件时报错的标记

//    private int year;//用于搜索选项联动
//    private int month;//用于搜索选项联动
//    private List<String> searchWeekList;//用于搜索选项联动

    private long numOfReport;//用于导航栏显示报告数
    private Boolean auth = false;

    public String execute() {
        return Result.SUCCESS;
    }

    public String findAll(){
        reportList = new ArrayList<Report>();
        List<Report> tempList = ReportManager.findAll();
        for(Report report:tempList){
            Report temp = new Report();
            temp.setId(report.getId());
            temp.setName(report.getName());
            temp.setTime(report.getTime());
            reportList.add(temp);
        }
        return Result.SUCCESS;
    }

    public String calendar(){
        return Result.SUCCESS;
    }

    public String userReport() {
        projectList = ProjectManager.findProjectByUser((Integer) ActionContext.getContext().getSession().get(Constants.USER_ID));
        draftBox = true;
        trashCan = true;
        return Result.SUCCESS;
    }
    public String userReportNotLeave() {
        projectList = ProjectManager.findProjectNotLeaveByUser((Integer) ActionContext.getContext().getSession().get(Constants.USER_ID));
        draftBox = true;
        trashCan = true;
        return Result.SUCCESS;
    }

    public String listUserReport() {
        int userId = UserManager.findUserById((Integer) ActionContext.getContext().getSession().get(Constants.USER_ID)).getId();
        if(projectId == 0) {
            pageSum = (int) Math.ceil((double)ReportManager.count(userId) / (double)PAGE_SIZE);
            reportList = ReportManager.find(userId, page, PAGE_SIZE);
        } else {
            pageSum = (int) Math.ceil((double)ReportManager.count(userId, projectId) / (double)PAGE_SIZE);
            reportList = ReportManager.find(userId, projectId, page, PAGE_SIZE);
        }
        return Result.SUCCESS;
    }

    public String listUserReportForCalendar() {
        int userId = UserManager.findUserById((Integer) ActionContext.getContext().getSession().get(Constants.USER_ID)).getId();
        reportList = new ArrayList<Report>();
        List<Report> tempList;
        if(projectId == 0) {
            tempList = ReportManager.findForSend(userId);
        } else {
            tempList = ReportManager.find(userId, projectId);
        }
        for(Report report:tempList){
            Report temp = new Report();
            temp.setId(report.getId());
            temp.setName(report.getName());
            temp.setTime(report.getTime());
            reportList.add(temp);
        }
        return Result.SUCCESS;
    }

    public String managerReport() {
        projectList = ProjectManager.findUserProjectAsManager((Integer) ActionContext.getContext().getSession().get(Constants.USER_ID));
        list = true;
        calendar = true;
        return Result.SUCCESS;
    }

    public String listManagerReport() {
        if(projectId == 0) {
            projectList = ProjectManager.findUserProjectAsManager((Integer) ActionContext.getContext().getSession().get(Constants.USER_ID));
            if(projectList.isEmpty()) {
                pageSum = 0;
                reportList = null;
            } else {
                pageSum = (int) Math.ceil((double) ReportManager.count(projectList) / (double) PAGE_SIZE);
                reportList = ReportManager.find(projectList, page, PAGE_SIZE);
            }
            return Result.SUCCESS;
        } else {
            return listProjectReport();
        }
    }

    public String listManagerReportForCalendar() {
        if(projectId == 0) {
            projectList = ProjectManager.findUserProjectAsManager((Integer) ActionContext.getContext().getSession().get(Constants.USER_ID));
            if(projectList.isEmpty()) {
                reportList = null;
                return Result.SUCCESS;
            } else {
                return findAll();
            }
        } else {
            return listProjectReportForCalendar();
        }
    }

    public Integer getSessionUID() {
        try {
            Integer id = (Integer) ActionContext.getContext().getSession()
                    .get(Constants.USER_ID);
            return id;
        } catch (NullPointerException e) {
            return null;
        }
    }
    public String projectReport() {
        uid = getSessionUID();
        Boolean auth = ProjectManager.isMember(projectId,uid);
        if(auth){
            project = ProjectManager.findProject(projectId);
            return Result.SUCCESS;
        }else {
            return Result.ERROR;
        }

    }

    public String listProjectReport() {
        uid = getSessionUID();
        pp = ProjectPermissionDAO.getInstance().findRelation(projectId,uid);
        if (pp.size()!=0){
            Project temp = new Project();
            temp.setId(projectId);
            pageSum = (int) Math.ceil((double)ReportManager.count(temp) / (double)PAGE_SIZE);
            reportList = ReportManager.find(temp, page, PAGE_SIZE);
            return Result.SUCCESS;
        }else{
            return Result.ERROR;
        }

    }

    public String listProjectReportForCalendar() {
        uid = getSessionUID();
        pp = ProjectPermissionDAO.getInstance().findRelation(projectId,uid);
        if (pp.size()!=0){
            Project ptemp = new Project();
            ptemp.setId(projectId);
            reportList = new ArrayList<Report>();
            List<Report> tempList = ReportManager.find(ptemp);
            for(Report report:tempList){
                Report rtemp = new Report();
                rtemp.setId(report.getId());
                rtemp.setName(report.getName());
                rtemp.setTime(report.getTime());
                reportList.add(rtemp);
            }
            return Result.SUCCESS;
        }else{
            return Result.ERROR;
        }

    }

    public String listTrashReport() {
        int userId = UserManager.findUserById((Integer) ActionContext.getContext().getSession().get(Constants.USER_ID)).getId();
        pageSum = (int) Math.ceil((double)ReportManager.countTrash(userId) / (double)PAGE_SIZE);
        reportList = ReportManager.findTrash(userId, page, PAGE_SIZE);
        return Result.SUCCESS;
    }

    public String listDraftReport() {
        int userId = UserManager.findUserById((Integer) ActionContext.getContext().getSession().get(Constants.USER_ID)).getId();
        pageSum = (int) Math.ceil((double)ReportManager.countDraft(userId) / (double)PAGE_SIZE);
        reportList = ReportManager.findDraft(userId, page, PAGE_SIZE);
        return Result.SUCCESS;
    }

    public String showReport() {
        int userId = getSessionUID();
        List<Integer> pidList =new ArrayList<Integer>();
        report = ReportManager.find(id);
        String nameTemper[]=report.getName().split("-");
        for(int i=0;i<nameTemper.length;i++){
            if(i==0)
                formatReportName=formatReportName+nameTemper[i];
            else
            {
                formatReportName=formatReportName+" - "+nameTemper[i];
            }
        }
        Iterator<ReportPermission> pps = report.getReportPermissions().iterator();
        while(pps.hasNext()){
            Project p = pps.next().getProject();
            pidList.add(p.getId());
        }
        for(Integer pid:pidList){
            if(!auth){
                auth = ProjectManager.isMember(pid,userId);
            }else {
                break;
            }
        }
        if(auth){
            attachmentList = getAttachmentList(report);
            return "success";
        }else {
            return Result.ERROR;
        }
    }

    public String previousReport() {
        report = ReportManager.findPervious(id);
        return Result.SUCCESS;
    }

    public String nextReport() {
        report = ReportManager.findNext(id);
        return Result.SUCCESS;
    }

    public String newReport() {
        weekList = ReportTitleUtil.getWeekStringList();
        monthList = ReportTitleUtil.getMonthStringList();
        yearList = ReportTitleUtil.getYearStringList();
        return Result.SUCCESS;
    }

    public String saveReport() {
        report.setUser(UserManager.findUserById((Integer) ActionContext.getContext().getSession().get(Constants.USER_ID)));
        report.setTime(new Timestamp(System.currentTimeMillis()));
        report.setState(2);
        List<Project> projectList = new ArrayList<Project>();
        String[] selectedProjectId = selectedProjectIdString.split(",");
        for (String projectId : selectedProjectId) {
            if (!projectId.equals("")) {
                projectList.add(ProjectManager.findProject(Integer.parseInt(projectId)));
            }
        }
        ReportManager.save(report, projectList);
        return Result.SUCCESS;
    }

    public String submitReport() {
        projectHotValueManager = new ProjectHotValueManager();
        report.setUser(UserManager.findUserById((Integer) ActionContext.getContext().getSession().get(Constants.USER_ID)));
        report.setTime(new Timestamp(System.currentTimeMillis()));
        report.setState(0);
        List<Project> projectList = new ArrayList<Project>();
        String[] selectedProjectId = selectedProjectIdString.split(",");
        for(String projectId : selectedProjectId) {
            if(!projectId.equals("")) {
                projectList.add(ProjectManager.findProject(Integer.parseInt(projectId)));
            }
        }

        //每提交一次周报，增加一条热力值记录
        for(Project temProject: projectList){
            projectHotValueManager.addHotValue("report",temProject.getId());
        }
        if(report.getId() == 0) {
            ReportManager.save(report, projectList);
        } else {
            ReportManager.update(report, projectList);
        }


        sendMail(report);
        return Result.SUCCESS;
    }

    public String submitDraftReport() {
        Report temp = ReportManager.find(id);


        List<Project> projectList;
        projectList = new ProjectDAO().findProByReportId(id);
        for (  Project temProject: projectList){
            new ProjectHotValueManager().addHotValue("report",temProject.getId());
        }

        temp.setUser(UserManager.findUserById((Integer) ActionContext.getContext().getSession().get(Constants.USER_ID)));
        temp.setTime(new Timestamp(System.currentTimeMillis()));
        temp.setState(0);
        ReportManager.update(temp);
        sendMail(temp);

        return Result.SUCCESS;
    }

    public String editReport() {
        report = ReportManager.find(id);
        if(report.getState() == 1) {
            return Result.ERROR;
        }
        if(!report.getUser().getUserid().equals(ActionContext.getContext().getSession().get(Constants.USER_ID))) {
            return Result.ERROR;
        }
        attachmentList = getAttachmentList(report);
        return Result.SUCCESS;
    }

    public String updateReport() {
        Report temp = ReportManager.find(report.getId());
        if(!temp.getUser().getUserid().equals(ActionContext.getContext().getSession().get(Constants.USER_ID))) {
            return Result.ERROR;
        }
        temp.setDescription(report.getDescription());
        temp.setAttachment(report.getAttachment());
        List<Project> projectList = new ArrayList<Project>();
        String[] selectedProjectId = selectedProjectIdString.split(",");
        for(String projectId : selectedProjectId) {
            if(!projectId.equals("")) {
                projectList.add(ProjectManager.findProject(Integer.parseInt(projectId)));
            }
        }
        ReportManager.update(temp, projectList);
        return Result.SUCCESS;
    }

    public String trashReport() {
        Report temp = ReportManager.find(id);
        if(!temp.getUser().getUserid().equals(ActionContext.getContext().getSession().get(Constants.USER_ID))) {
            return Result.ERROR;
        }
        ReportManager.trash(temp);
        return Result.SUCCESS;
    }

    public String revertReport() {
        Report temp = ReportManager.find(id);
        if(!temp.getUser().getUserid().equals(ActionContext.getContext().getSession().get(Constants.USER_ID))) {
            return Result.ERROR;
        }
        ReportManager.revert(temp);
        return Result.SUCCESS;
    }

    public String deleteReport() {
        Report temp = ReportManager.find(id);
        if(!temp.getUser().getUserid().equals(ActionContext.getContext().getSession().get(Constants.USER_ID))) {
            return Result.ERROR;
        }
        ReportManager.delete(temp);
        return Result.SUCCESS;
    }

    public String reportNumber() {
        int userId = UserManager.findUserById((Integer) ActionContext.getContext().getSession().get(Constants.USER_ID)).getId();
        numOfReport = ReportManager.count(userId);
        return Result.SUCCESS;
    }

    public String searchProject() {
        List<Project> tempList = ProjectManager.findProjectByUser((Integer) ActionContext.getContext().getSession().get(Constants.USER_ID));
        projectList = ProjectManager.findProject(searchProjectKeyword);
        Iterator<Project> projectIterator = projectList.iterator();
        while(projectIterator.hasNext()) {
            Project project = projectIterator.next();
            boolean flag = false;
            for(Project temp : tempList) {
                if(temp.getId().equals(project.getId())) {
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                projectIterator.remove();
            }
        }
        return Result.SUCCESS;
    }

//    public String listWeek() {
//        searchWeekList = ReportManager.findWeek(year, month);
//        return Result.SUCCESS;
//    }

    private List<Attachment> getAttachmentList(Report report) {
        List<Attachment> attachmentList = null;
        if(report.getAttachment() != null) {
            String[] attachmentUuids = report.getAttachment().split(",");
            if (attachmentUuids.length > 0) {
                attachmentList = new ArrayList<Attachment>();
                for (String attachmentUuid : attachmentUuids) {
                    if (!attachmentUuid.equals("")) {
                        Attachment temp = new Attachment();
                        temp.setUuid(attachmentUuid);
                        temp.setName(FileUtil.getFileName(attachmentUuid));
                        temp.setName(FileUtil.getFileName(attachmentUuid));
                        attachmentList.add(temp);
                    }
                }
            }
        }
        return attachmentList;
    }

    public String refreshTime(){
        return Result.SUCCESS;
    }


    private void sendMail(Report report) {
        Report temp = ReportManager.find(report.getId());
        List<Project> projectList = new ArrayList<Project>();
        for (ReportPermission reportPermission : temp.getReportPermissions()) {
            projectList.add(reportPermission.getProject());
        }
        StringBuffer projectNameString = new StringBuffer();
        for(Project project : projectList) {
            projectNameString.append("“").append(project.getName()).append("”");
        }
        Set<Integer> projectManagerIdSet = new HashSet<Integer>();
        for(Project project : projectList) {
            List<User> managerList = ProjectManager.findProjectManager(project.getId());
            for(User manager : managerList) {
                projectManagerIdSet.add(manager.getUserid());
            }
        }
        HttpServletRequest request = ServletActionContext.getRequest();
        sendMailException = "";
        for(int projectManagerId : projectManagerIdSet) {
            User to = UserManager.findUserById(projectManagerId);
            String address = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/report/show?id=" + report.getId();
            StringBuffer content = new StringBuffer();
            content.append(to.getName()).append("，您好：<br><br>");
            content.append(report.getUser().getName()).append("向");
            content.append(projectNameString);
            content.append("提交了新的项目报告，请点击下面的链接查看。<br>");
            content.append("<a href='").append(address).append("'>");
            content.append(report.getName());
            content.append("</a>（").append(address).append("）<br><br>");
            content.append("此邮件由系统自动生成，请勿直接回复。<br><br><hr>用轻项目记录我的极客时光<br><br>轻项目<br>");
            content.append(new SimpleDateFormat("yyyy.M.d").format(new Date()));
            try {
                PushMailUtil.sendMail(to.getEmail(), report.getUser().getName() + "提交了新的项目报告", content.toString());
            } catch (Exception e) {
                e.printStackTrace();
                sendMailException += to.getEmail() + ",";
            }
        }
    }

    public int getPageSum() {
        return pageSum;
    }
    public void setPageSum(int pageSum) {
        this.pageSum = pageSum;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }

    public int getProjectId() {
        return projectId;
    }
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }
    public List<Project> getProjectList() {
        return projectList;
    }
    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }
    public boolean isDraftBox() {
        return draftBox;
    }
    public void setDraftBox(boolean draftBox) {
        this.draftBox = draftBox;
    }
    public boolean isTrashCan() {
        return trashCan;
    }
    public void setTrashCan(boolean trashCan) {
        this.trashCan = trashCan;
    }
    public boolean isList() {
        return list;
    }
    public void setList(boolean list) {
        this.list = list;
    }
    public boolean isCalendar() {
        return calendar;
    }
    public void setCalendar(boolean calendar) {
        this.calendar = calendar;
    }

    public List<Report> getReportList() {
        return reportList;
    }
    public void setReportList(List<Report> reportList) {
        this.reportList = reportList;
    }

    public List<String> getWeekList() {
        return weekList;
    }
    public void setWeekList(List<String> weekList) {
        this.weekList = weekList;
    }
    public List<String> getMonthList() {
        return monthList;
    }
    public void setMonthList(List<String> monthList) {
        this.monthList = monthList;
    }
    public List<String> getYearList() {
        return yearList;
    }
    public void setYearList(List<String> yearList) {
        this.yearList = yearList;
    }

    public Report getReport() {
        return report;
    }
    public void setReport(Report report) {
        this.report = report;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }
    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getSelectedProjectIdString() {
        return selectedProjectIdString;
    }
    public void setSelectedProjectIdString(String selectedProjectIdString) {
        this.selectedProjectIdString = selectedProjectIdString;
    }
    public String getSearchProjectKeyword() {
        return searchProjectKeyword;
    }
    public void setSearchProjectKeyword(String searchProjectKeyword) {
        this.searchProjectKeyword = searchProjectKeyword;
    }

    public String getSendMailException() {
        return sendMailException;
    }
    public void setSendMailException(String sendMailException) {
        this.sendMailException = sendMailException;
    }

//    public int getYear() {
//        return year;
//    }
//    public void setYear(int year) {
//        this.year = year;
//    }
//    public int getMonth() {
//        return month;
//    }
//    public void setMonth(int month) {
//        this.month = month;
//    }
//    public List<String> getSearchWeekList() {
//        return searchWeekList;
//    }
//    public void setSearchWeekList(List<String> searchWeekList) {
//        this.searchWeekList = searchWeekList;
//    }

    public long getNumOfReport() {
        return numOfReport;
    }
    public void setNumOfReport(long numOfReport) {
        this.numOfReport = numOfReport;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }
    public String getFormatReportName() { return formatReportName; }

    public void setFormatReportName(String formatReportName) { this.formatReportName = formatReportName; }

    class Attachment {
        private String uuid;
        private String name;
        public String getUuid() {
            return uuid;
        }
        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

}
