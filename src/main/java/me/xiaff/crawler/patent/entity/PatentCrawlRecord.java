package me.xiaff.crawler.patent.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "patent_crawl_record")
public class PatentCrawlRecord {
    @Id
    @GeneratedValue
    private Long id;

    private String patentType;

    private String classCode;

    private Integer year;

    private Integer page;

    private boolean finished;

    private Long downloadedNum;

    private Long totalNum;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;

    public PatentCrawlRecord() {
    }

    public PatentCrawlRecord(String patentType, String classCode, Integer year) {
        this.patentType = patentType;
        this.classCode = classCode;
        this.year = year;
        this.page = 1;
        this.finished = false;
    }

    public PatentCrawlRecord(Long id, String classCode, Integer year, Integer page, boolean finished) {
        this.id = id;
        this.classCode = classCode;
        this.year = year;
        this.page = page;
        this.finished = finished;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatentType() {
        return patentType;
    }

    public void setPatentType(String patentType) {
        this.patentType = patentType;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Long getDownloadedNum() {
        return downloadedNum;
    }

    public void setDownloadedNum(Long downloadedNum) {
        this.downloadedNum = downloadedNum;
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
        return "PatentCrawlRecord{" +
                "id=" + id +
                ", patentType='" + patentType + '\'' +
                ", classCode='" + classCode + '\'' +
                ", year=" + year +
                ", page=" + page +
                ", finished=" + finished +
                ", totalNum=" + totalNum +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
