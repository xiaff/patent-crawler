package me.xiaff.crawler.patent.repo;

import me.xiaff.crawler.patent.entity.PatentCrawlRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatentCrawlRecordRepo extends JpaRepository<PatentCrawlRecord, Long> {
    List<PatentCrawlRecord> findAllByFinished(boolean finished);

    List<PatentCrawlRecord> findAllByTotalNumIsNull();
}
