package me.xiaff.crawler.patent.repo;

import me.xiaff.crawler.patent.entity.PatentCrawlRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PatentCrawlRecordRepoTest {

    @Autowired
    private PatentCrawlRecordRepo patentCrawlRecordRepo;

    @Test
    @Transactional
    @Rollback(value = false)
    public void insertRecords() {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        String html = restTemplate.getForObject("http://www.wanfangdata.com.cn/navigations/patent.do", String.class);
        Document document = Jsoup.parse(html);
        Elements urlElements = document.select(".xueweilist2 li a");
        for (Element urlElement : urlElements) {
            String link = urlElement.attr("href");
            System.out.println(link);
            String classCode = link.substring(link.lastIndexOf(':') + 1);
            System.out.println(classCode);
            List<PatentCrawlRecord> records = new ArrayList<>();
            for (int year = 1984; year <= 2018; year++) {
                PatentCrawlRecord patentCrawlRecord = new PatentCrawlRecord("实用新型", classCode, year);
                records.add(patentCrawlRecord);
            }
            patentCrawlRecordRepo.save(records);
        }
    }
}