package me.xiaff.crawler.patent;

import me.xiaff.crawler.patent.entity.PatentCrawlRecord;
import me.xiaff.crawler.patent.processor.TotalNumProcessor;
import me.xiaff.crawler.patent.repo.PatentCrawlRecordRepo;
import me.xiaff.crawler.patent.repo.PatentRepo;
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

@Component
public class TotalNumChecker implements ApplicationRunner {
    public static final Logger logger = LoggerFactory.getLogger(TotalNumChecker.class);

    private static BlockingQueue<PatentCrawlRecord> recordQueue;

    @Value("${check:false}")
    private boolean check;

    @Autowired
    private PatentCrawlRecordRepo patentCrawlRecordRepo;

    @Autowired
    private PatentRepo patentRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("check={}", check);
        if (!check) {
            return;
        }
        List<PatentCrawlRecord> records = patentCrawlRecordRepo.findAllByTotalNumIsNull();
        recordQueue = new LinkedBlockingDeque<>(records);
        int threadNum = 2;

        List<RecordCheckRunner> recordCheckRunners = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            RecordCheckRunner checkRunner = new RecordCheckRunner();
            recordCheckRunners.add(checkRunner);
            checkRunner.start();
            Thread.sleep(1000L);
        }

        for (RecordCheckRunner checkRunner : recordCheckRunners) {
            checkRunner.join();
        }
    }

    class RecordCheckRunner extends Thread {

        @Override
        public void run() {

            while (!recordQueue.isEmpty()) {
                PatentCrawlRecord record;
                try {
                    record = recordQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                String classCode = record.getClassCode();
                Integer year = record.getYear();
                TotalNumProcessor totalNumProcessor = new TotalNumProcessor(record.getPatentType(), classCode, 10, year);
                Long totalNum;
                while (true) {
                    totalNum = totalNumProcessor.getTotalNum();
                    if (totalNum == null) {
                        try {
                            Thread.sleep(5 * 1000);
                        } catch (InterruptedException ignore) {
                        }
                    } else {
                        break;
                    }
                }
                record.setTotalNum(totalNum);
                logger.info("Type={}, ClassCode={}, Year={}, Finished={}, Downloaded={}, TotalNum={}",
                        record.getPatentType(), classCode, year, record.isFinished(), record.getDownloadedNum(), totalNum);
                updateRecord(record);

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException ignore) {
                }

            }
        }
    }

    @Transactional
    private void updateRecord(PatentCrawlRecord record) {
        patentCrawlRecordRepo.save(record);
    }
}
