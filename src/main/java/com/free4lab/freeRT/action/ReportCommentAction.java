package com.free4lab.freeRT.action;

import com.free4lab.freeRT.dao.ProjectDAO;
import com.free4lab.freeRT.manager.ProjectHotValueManager;
import com.free4lab.freeRT.manager.ReportCommentManager;
import com.free4lab.freeRT.manager.ReportManager;
import com.free4lab.freeRT.manager.UserManager;
import com.free4lab.freeRT.model.Project;
import com.free4lab.freeRT.model.Report;
import com.free4lab.freeRT.model.ReportComment;
import com.free4lab.freeRT.model.User;
import com.free4lab.freeRT.utils.Constants;
import com.free4lab.freeRT.utils.PushMailUtil;
import com.free4lab.utils.group.Result;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.free4lab.freeRT.manager.ProjectHotValueManager.getProjectHotValueManagerInstance;

/**
 * Created by yph on 17-4-27.
 */
public class ReportCommentAction {

    private static final int PAGE_SIZE = 6;//用于分页
    private int pageSum;//用于分页
    private int page;//用于分页

    private Report report;
    private List<ReportComment> reportCommentList;

    private ReportComment reportComment;

    private int replyToUserId;

    private boolean sendMailException;

    public String listReportComment() {
        pageSum = (int) Math.ceil((double)ReportCommentManager.count(report, false) / (double)PAGE_SIZE);
        reportCommentList = ReportCommentManager.find(report, page, PAGE_SIZE, false);
        return Result.SUCCESS;
    }

    public String listReportCommentIgnoreOperation() {
        pageSum = (int) Math.ceil((double)ReportCommentManager.count(report, true) / (double)PAGE_SIZE);
        reportCommentList = ReportCommentManager.find(report, page, PAGE_SIZE, true);
        return Result.SUCCESS;
    }

    public String saveReportComment() {

//        getProjectHotValueManagerInstance().addHotValue("newComment",)
        reportComment.setUser(UserManager.findUserById((Integer) ActionContext.getContext().getSession().get(Constants.USER_ID)));
        reportComment.setTime(new Timestamp(System.currentTimeMillis()));
        ReportCommentManager.save(reportComment);
        HttpServletRequest request = ServletActionContext.getRequest();
        String address = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/report/show?id=" + reportComment.getReport().getId();
        reportComment.setReport(ReportManager.find(reportComment.getReport().getId()));
        if(replyToUserId == 0) {//评论
            if(reportComment.getDescription().startsWith("#操作#")) {
                //#操作# 编辑此资源 不用发邮件
            } else {
                User to = ReportManager.find(reportComment.getReport().getId()).getUser();
                StringBuffer content = new StringBuffer();
                content.append(to.getName()).append("，您好：<br><br>");
                content.append(reportComment.getUser().getName()).append("对您的项目报告“");
                content.append(reportComment.getReport().getName()).append("”发表了评论，请点击下面的链接查看。<br>");
                content.append("<a href='").append(address).append("'>");
                content.append(reportComment.getReport().getName());
                content.append("</a>（").append(address).append("）<br><br>");
                content.append("此邮件由系统自动生成，请勿直接回复。<br><br><hr>用轻项目记录我的极客时光<br><br>轻项目<br>");
                content.append(new SimpleDateFormat("yyyy.M.d").format(new Date()));
                try {
                    PushMailUtil.sendMail(to.getEmail(), reportComment.getUser().getName() + "评论了您的项目报告", content.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    sendMailException = true;
                }
            }
        } else {//回复
            User to = UserManager.findUserById(replyToUserId);
            StringBuffer content = new StringBuffer();
            content.append(to.getName()).append("，您好：<br><br>");
            content.append(reportComment.getUser().getName()).append("回复了您对项目报告“");
            content.append(reportComment.getReport().getName()).append("”的评论，请点击下面的链接查看。<br>");
            content.append("<a href='").append(address).append("'>");
            content.append(reportComment.getReport().getName());
            content.append("</a>（").append(address).append("）<br><br>");
            content.append("此邮件由系统自动生成，请勿直接回复。<br><br><hr>用轻项目记录我的极客时光<br><br>轻项目<br>");
            content.append(new SimpleDateFormat("yyyy.M.d").format(new Date()));
            try {
                PushMailUtil.sendMail(to.getEmail(), reportComment.getUser().getName() + "回复了您对项目报告的评论", content.toString());
            } catch (Exception e) {
                e.printStackTrace();
                sendMailException = true;
            }
        }
    //评论添加热力值
        List<Project> projectList;
        projectList = new ProjectDAO().findProByReportId(reportComment.getReport().getId());
        for (  Project temProject: projectList){
            new ProjectHotValueManager().addHotValue("newComment",temProject.getId());
        }

        return Result.SUCCESS;
    }

    public String deleteReportComment() {
        ReportCommentManager.delete(reportComment);
        return Result.SUCCESS;
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

    public Report getReport() {
        return report;
    }
    public void setReport(Report report) {
        this.report = report;
    }
    public List<ReportComment> getReportCommentList() {
        return reportCommentList;
    }
    public void setReportCommentList(List<ReportComment> reportCommentList) {
        this.reportCommentList = reportCommentList;
    }

    public ReportComment getReportComment() {
        return reportComment;
    }
    public void setReportComment(ReportComment reportComment) {
        this.reportComment = reportComment;
    }
    public int getReplyToUserId() {
        return replyToUserId;
    }
    public void setReplyToUserId(int replyToUserId) {
        this.replyToUserId = replyToUserId;
    }

    public boolean isSendMailException() {
        return sendMailException;
    }
    public void setSendMailException(boolean sendMailException) {
        this.sendMailException = sendMailException;
    }

}
