package me.xiaff.crawler.patent.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import me.xiaff.crawler.patent.ProxyTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Calendar;

public class TotalNumProcessor {
    public static final Logger logger = LoggerFactory.getLogger(TotalNumProcessor.class);
    private static final String BASE_URL = "http://www.wanfangdata.com.cn/searchResult/getCoreSearch.do";
    private String paramStrs;
    private String patentType;
    private String classCode;
    private int pageSize;
    private int year;

    public TotalNumProcessor(String patentType, String classCode, int pageSize, int year) {
        this.patentType = patentType;
        this.classCode = classCode;
        this.pageSize = pageSize;
        this.year = year;
        this.paramStrs = String.format("(专利—主分类号:(\"%s\"))*$pub_org_code:CN*$patent_type:%s*$common_year:%d", classCode, patentType, year);
    }


    public Long getTotalNum() {
        logger.info("Downloading patents of Class-{} of Year-{} at page-1 ...", classCode, year);
//        RestTemplate restTemplate = ProxyTemplate.getRestTemplate();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/x-www-form-urlencoded; charset=UTF-8"));

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("paramStrs", paramStrs);
        requestBody.add("startDate", "1900");
        requestBody.add("endDate", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        requestBody.add("classType", "patent-patent_element");
        requestBody.add("pageNum", "1");
        requestBody.add("pageSize", String.valueOf(pageSize));
        requestBody.add("sortFiled", "");
        requestBody.add("isSearchSecond", "true");
        requestBody.add("updateDate", "");

        HttpEntity<String> response;
        try {
            response = restTemplate.postForEntity(BASE_URL, new HttpEntity<>(requestBody, headers), String.class);
        } catch (ResourceAccessException | HttpServerErrorException e) {
            logger.info(e.getLocalizedMessage());
            return null;
        }
        String responseText = response.getBody();

        JSONObject json = null;
        try {
            json = JSON.parseObject(responseText);
        } catch (JSONException e) {
            logger.info(e.getLocalizedMessage());
        }
        if (json == null) {
            return null;
        }

        return json.getLong("totalRow");
    }

    public static void main(String[] args) {
        TotalNumProcessor processor = new TotalNumProcessor("实用新型", "A01", 10, 1985);
        System.out.println(processor.getTotalNum());
    }
}
