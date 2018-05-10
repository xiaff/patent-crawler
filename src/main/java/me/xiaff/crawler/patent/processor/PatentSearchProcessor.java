package me.xiaff.crawler.patent.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import me.xiaff.crawler.patent.ProxyTemplate;
import me.xiaff.crawler.patent.entity.Patent;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PatentSearchProcessor {
    public static final Logger logger = LoggerFactory.getLogger(PatentSearchProcessor.class);
    private static final String BASE_URL = "http://www.wanfangdata.com.cn/searchResult/getCoreSearch.do";
    private String paramStrs;
    private String patentType;
    private String classCode;
    private int pageSize;
    private int year;

    private List<String> cookies = null;

    public PatentSearchProcessor(String patentType, String classCode, int pageSize, int year) {
        this.patentType = patentType;
        this.classCode = classCode;
        this.pageSize = pageSize;
        this.year = year;
        this.paramStrs = String.format("(专利—主分类号:(\"%s\"))*$pub_org_code:CN*$patent_type:%s*$common_year:%s", classCode, patentType, year);
    }

    public Pair<List<Patent>, Boolean> download(int page) {
        logger.info("Downloading {} of Class-{} of Year-{} at page-{} ...", patentType, classCode, year, page);
//        RestTemplate restTemplate = ProxyTemplate.getRestTemplate();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/x-www-form-urlencoded; charset=UTF-8"));
        if (this.cookies != null) {
            headers.put(HttpHeaders.COOKIE, this.cookies);
        }

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("paramStrs", paramStrs);
        requestBody.add("startDate", "1900");
        requestBody.add("endDate", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        requestBody.add("classType", "patent-patent_element");
        requestBody.add("pageNum", String.valueOf(page));
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
        HttpHeaders responseHeaders = response.getHeaders();

        if (responseHeaders.containsKey(HttpHeaders.SET_COOKIE)) {
            this.cookies = responseHeaders.get(HttpHeaders.SET_COOKIE);
        }

        JSONObject json = null;
        try {
            json = JSON.parseObject(responseText);
        } catch (JSONException e) {
            logger.info(e.getLocalizedMessage());
        }
        if (json == null) {
            return null;
        }

        JSONArray rows = json.getJSONArray("pageRow");
        List<Patent> patents = new ArrayList<>();
        for (Object row : rows) {
            JSONObject patentJson = (JSONObject) row;
//            System.out.println(patentJson);
            Patent patent = new Patent();
            patent.setId(patentJson.getString("id"));
            patent.setPatentId(patentJson.getString("patent_id"));
            patent.setDataSort(patentJson.getInteger("data_sort"));
            patent.setPageCnt(patentJson.getInteger("page_cnt"));
            patent.setRefdocCnt(patentJson.getLong("refdoc_cnt"));
            patent.setInventorName(getStringOrArrayString(patentJson, "inv_name"));
            patent.setTitle(patentJson.getString("title"));
            patent.setTagNumber(patentJson.getInteger("tag_num"));
            patent.setPublicationDate(patentJson.getDate("pub_date"));
            patent.setPublicationNum(patentJson.getString("pub_num"));
            if (patent.getPublicationNum() != null && patent.getPublicationNum().length() > 40) {
                patent.setPublicationNum("error");
            }
            patent.setApplicationDate(patentJson.getDate("app_date02"));
            patent.setApplicationNum(patentJson.getString("app_num"));
            patent.setApplicationNumOld(patentJson.getString("app_num_old"));
            patent.setLegalStatus(patentJson.getString("legal_status"));
            if (patentJson.getString("prio_date") != null) {
                try {
                    patent.setPriorityDate(new SimpleDateFormat("YYYY.MM.dd").parse(patentJson.getString("prio_date")));
                } catch (ParseException ignore) {
                }
            }
            patent.setSignory(patentJson.getString("signory"));
            patent.setApplicationAreaCode(patentJson.getString("app_area_code"));
            patent.setAgencyPersonName(patentJson.getString("agy_per_name"));
            patent.setAgencyOrgName(patentJson.getString("agy_org_name"));
            patent.setApplicantName(getStringOrArrayString(patentJson, "applicant_name"));
            patent.setClassCode(getStringOrArrayString(patentJson, "class_code"));
            patent.setSummary(patentJson.getString("summary"));
            patent.setApplicantAddress(patentJson.getString("app_address"));
            patent.setPatentType(patentJson.getString("patent_type"));
            patent.setPubOrgCode(patentJson.getString("pub_org_code"));
            patent.setSearchCode(classCode);
//            System.out.println(patent);
            patents.add(patent);
        }

        int totalRow = json.getInteger("totalRow");
        boolean hasMore = true;
        if (totalRow <= page * json.getInteger("pageSize")) {
            hasMore = false;
        }


        logger.info("Progress: {}/{}", page * json.getInteger("pageSize"), totalRow);
        return new ImmutablePair<>(patents, hasMore);
    }

    private String getStringOrArrayString(JSONObject json, String key) {
        if (json.get(key) == null) {
            return "";
        }
        String classCodeStr = json.get(key).toString();
        if (classCodeStr.startsWith("[") && classCodeStr.endsWith("]")) {
            return (StringUtils.collectionToDelimitedString(json.getJSONArray(key).toJavaList(String.class), ";"));
        } else {
            return classCodeStr;
        }
    }


    public static void main(String[] args) {
        Pair<List<Patent>, Boolean> listBooleanPair = new PatentSearchProcessor("实用新型","C07", 20, 2003).download(1);
        for (Patent patent : listBooleanPair.getLeft()) {
            System.out.println(patent);
        }
    }

}
