package me.xiaff.crawler.patent.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "r_patent")
public class Patent {
    @Id
    @Column(length = 50)
    private String id;

    @Column(length = 50)
    private String patentId;

    private Integer dataSort;
    private Integer pageCnt;
    private Long refdocCnt;
    private String inventorName;
    private String title;
    private Integer tagNumber;

    private Date publicationDate;
    @Column(length = 50)
    private String publicationNum;

    private Date applicationDate;
    @Column(length = 50)
    private String applicationNum;
    @Column(length = 50)
    private String applicationNumOld;

    @Column(length = 50)
    private String legalStatus;

    private Date priorityDate;
    @Column(length = 50)
    private String priorityNum;

    @Column(columnDefinition = "text")
    private String signory;

    @Column(length = 50)
    private String applicationAreaCode;

    @Column(length = 50)
    private String agencyPersonName;

    private String applicantName;

    private String classCode;

    @Column(columnDefinition = "text")
    private String summary;

    private String applicantAddress;

    public Patent() {
    }

    public Patent(String id, String patentId, Integer dataSort, Integer pageCnt, Long refdocCnt, String inventorName, String title, Integer tagNumber, Date publicationDate, String publicationNum, Date applicationDate, String applicationNum, String applicationNumOld, String legalStatus, Date priorityDate, String priorityNum, String signory, String applicationAreaCode, String agencyPersonName, String applicantName, String classCode, String summary, String applicantAddress) {
        this.id = id;
        this.patentId = patentId;
        this.dataSort = dataSort;
        this.pageCnt = pageCnt;
        this.refdocCnt = refdocCnt;
        this.inventorName = inventorName;
        this.title = title;
        this.tagNumber = tagNumber;
        this.publicationDate = publicationDate;
        this.publicationNum = publicationNum;
        this.applicationDate = applicationDate;
        this.applicationNum = applicationNum;
        this.applicationNumOld = applicationNumOld;
        this.legalStatus = legalStatus;
        this.priorityDate = priorityDate;
        this.priorityNum = priorityNum;
        this.signory = signory;
        this.applicationAreaCode = applicationAreaCode;
        this.agencyPersonName = agencyPersonName;
        this.applicantName = applicantName;
        this.classCode = classCode;
        this.summary = summary;
        this.applicantAddress = applicantAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatentId() {
        return patentId;
    }

    public void setPatentId(String patentId) {
        this.patentId = patentId;
    }

    public Integer getDataSort() {
        return dataSort;
    }

    public void setDataSort(Integer dataSort) {
        this.dataSort = dataSort;
    }

    public Integer getPageCnt() {
        return pageCnt;
    }

    public void setPageCnt(Integer pageCnt) {
        this.pageCnt = pageCnt;
    }

    public Long getRefdocCnt() {
        return refdocCnt;
    }

    public void setRefdocCnt(Long refdocCnt) {
        this.refdocCnt = refdocCnt;
    }

    public String getInventorName() {
        return inventorName;
    }

    public void setInventorName(String inventorName) {
        this.inventorName = inventorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(Integer tagNumber) {
        this.tagNumber = tagNumber;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPublicationNum() {
        return publicationNum;
    }

    public void setPublicationNum(String publicationNum) {
        this.publicationNum = publicationNum;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getApplicationNum() {
        return applicationNum;
    }

    public void setApplicationNum(String applicationNum) {
        this.applicationNum = applicationNum;
    }

    public String getApplicationNumOld() {
        return applicationNumOld;
    }

    public void setApplicationNumOld(String applicationNumOld) {
        this.applicationNumOld = applicationNumOld;
    }

    public String getLegalStatus() {
        return legalStatus;
    }

    public void setLegalStatus(String legalStatus) {
        this.legalStatus = legalStatus;
    }

    public Date getPriorityDate() {
        return priorityDate;
    }

    public void setPriorityDate(Date priorityDate) {
        this.priorityDate = priorityDate;
    }

    public String getPriorityNum() {
        return priorityNum;
    }

    public void setPriorityNum(String priorityNum) {
        this.priorityNum = priorityNum;
    }

    public String getSignory() {
        return signory;
    }

    public void setSignory(String signory) {
        this.signory = signory;
    }

    public String getApplicationAreaCode() {
        return applicationAreaCode;
    }

    public void setApplicationAreaCode(String applicationAreaCode) {
        this.applicationAreaCode = applicationAreaCode;
    }

    public String getAgencyPersonName() {
        return agencyPersonName;
    }

    public void setAgencyPersonName(String agencyPersonName) {
        this.agencyPersonName = agencyPersonName;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getApplicantAddress() {
        return applicantAddress;
    }

    public void setApplicantAddress(String applicantAddress) {
        this.applicantAddress = applicantAddress;
    }

    @Override
    public String toString() {
        return "Patent{" +
                "id='" + id + '\'' +
                ", patentId='" + patentId + '\'' +
                ", dataSort=" + dataSort +
                ", pageCnt=" + pageCnt +
                ", refdocCnt=" + refdocCnt +
                ", inventorName='" + inventorName + '\'' +
                ", title='" + title + '\'' +
                ", tagNumber=" + tagNumber +
                ", publicationDate=" + publicationDate +
                ", publicationNum='" + publicationNum + '\'' +
                ", applicationDate=" + applicationDate +
                ", applicationNum='" + applicationNum + '\'' +
                ", applicationNumOld='" + applicationNumOld + '\'' +
                ", legalStatus='" + legalStatus + '\'' +
                ", priorityDate=" + priorityDate +
                ", priorityNum='" + priorityNum + '\'' +
                ", signory='" + signory + '\'' +
                ", applicationAreaCode='" + applicationAreaCode + '\'' +
                ", agencyPersonName='" + agencyPersonName + '\'' +
                ", applicantName='" + applicantName + '\'' +
                ", classCode='" + classCode + '\'' +
                ", summary='" + summary + '\'' +
                ", applicantAddress='" + applicantAddress + '\'' +
                '}';
    }
}
