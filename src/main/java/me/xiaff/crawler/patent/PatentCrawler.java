package me.xiaff.crawler.patent;

import me.xiaff.crawler.patent.entity.Patent;
import me.xiaff.crawler.patent.entity.PatentCrawlRecord;
import me.xiaff.crawler.patent.processor.PatentSearchProcessor;
import me.xiaff.crawler.patent.repo.PatentCrawlRecordRepo;
import me.xiaff.crawler.patent.repo.PatentRepo;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

@Component
public class PatentCrawler implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(PatentCrawler.class);

    private static BlockingQueue<PatentCrawlRecord> recordQueue;

    @Autowired
    private PatentRepo patentRepo;

    @Autowired
    private PatentCrawlRecordRepo patentCrawlRecordRepo;

    @Value(value = "${crawl:false}")
    private boolean crawl;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("crawl={}", crawl);
        if (!crawl) {
            return;
        }
        List<PatentCrawlRecord> records = patentCrawlRecordRepo.findAllByFinished(false);
        records = records.stream()
                .filter(r -> r.getTotalNum() == null || r.getTotalNum() > 0)
                .collect(Collectors.toList());
        recordQueue = new LinkedBlockingDeque<>(records);
        int threadNum = 1;

        List<CrawlRunner> crawlRunners = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            CrawlRunner crawlRunner = new CrawlRunner();
            crawlRunners.add(crawlRunner);
            crawlRunner.start();
        }
        for (CrawlRunner crawlRunner : crawlRunners) {
            crawlRunner.join();
        }
    }

    @Transactional
    private void saveRecord(PatentCrawlRecord record) {
        patentCrawlRecordRepo.save(record);
    }

    @Transactional
    private void savePatents(List<Patent> patentList) {
        patentRepo.save(patentList);
//        logger.info("Saved {} patents.", patentList.size());
    }

    class CrawlRunner extends Thread {

        @Override
        public void run() {
            while (!recordQueue.isEmpty()) {
                PatentCrawlRecord record;
                try {
                    record = recordQueue.take();
                } catch (InterruptedException ignore) {
                    return;
                }
                logger.info("--Next record: {}--", record.toString());
                String patentType = record.getPatentType();
                String code = record.getClassCode();
                Integer year = record.getYear();
                Integer page = record.getPage();

                PatentSearchProcessor processor = new PatentSearchProcessor(patentType, code, 200, year);
                while (true) {
                    Pair<List<Patent>, Boolean> result = processor.download(page++);
                    if (result == null) {
                        page -= 1;
                        logger.warn("Network Error, Retry after 5s ......");
                        try {
                            Thread.sleep(5 * 1000);
                        } catch (InterruptedException ignore) {
                        }
                        continue;
                    }
                    savePatents(result.getKey());
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    boolean hasMore = result.getRight();
                    if (!hasMore) {
                        record.setFinished(true);
                        saveRecord(record);
                        break;
                    }
                    record.setPage(page - 1);
                    saveRecord(record);
                }
            }

        }
    }
}
