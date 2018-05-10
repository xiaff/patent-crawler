package me.xiaff.crawler.patent.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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

    @Column(length = 1000)
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

    @Column(columnDefinition = "longtext")
    private String signory;

    @Column(length = 50)
    private String applicationAreaCode;

    @Column(length = 50)
    private String agencyPersonName;

    private String agencyOrgName;

    private String applicantName;

    private String classCode;

    @Column(columnDefinition = "longtext")
    private String summary;

    private String applicantAddress;

    @Column(length = 50)
    private String patentType;

    @Column(length = 50)
    private String pubOrgCode;

    @Column(length = 50)
    private String searchCode;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;

    public Patent() {
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

    public String getAgencyOrgName() {
        return agencyOrgName;
    }

    public void setAgencyOrgName(String agencyOrgName) {
        this.agencyOrgName = agencyOrgName;
    }

    public String getPatentType() {
        return patentType;
    }

    public void setPatentType(String patentType) {
        this.patentType = patentType;
    }

    public String getPubOrgCode() {
        return pubOrgCode;
    }

    public void setPubOrgCode(String pubOrgCode) {
        this.pubOrgCode = pubOrgCode;
    }

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
                ", signory='" + signory + '\'' +
                ", applicationAreaCode='" + applicationAreaCode + '\'' +
                ", agencyPersonName='" + agencyPersonName + '\'' +
                ", agencyOrgName='" + agencyOrgName + '\'' +
                ", applicantName='" + applicantName + '\'' +
                ", classCode='" + classCode + '\'' +
                ", summary='" + summary + '\'' +
                ", applicantAddress='" + applicantAddress + '\'' +
                ", patentType='" + patentType + '\'' +
                ", pubOrgCode='" + pubOrgCode + '\'' +
                ", searchCode='" + searchCode + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
