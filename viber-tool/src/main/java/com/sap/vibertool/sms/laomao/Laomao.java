package com.sap.vibertool.sms.laomao;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Laomao {

    public static String API_KEY = "d3e2b2f617116f8f1d710d274e5115cf";
    public static Double maxPrice = 0.15D;

    public static Map<Integer, LaoMaoCountry> getCountries() {
        String u = "https://api.laomao.io/stubs/handler_api.php?api_key=" + API_KEY + "&action=getCountries";
        String r = HttpUtil.get(u);
//        log.info("r:{}", r);
        JSONObject json = JSON.parseObject(r);
        Map<Integer, LaoMaoCountry> countries = new HashMap<>();
        json.keySet().forEach(key -> {
            countries.put(Integer.parseInt(key), JSON.parseObject(json.getJSONObject(key).toJSONString(), LaoMaoCountry.class));
        });
        return countries;
    }


    public static List<LaoMaoPrice> getPrices(Map<Integer, LaoMaoCountry> countries) {
        List<LaoMaoPrice> list = new ArrayList<>();
        String u = "https://api.laomao.io/stubs/handler_api.php?api_key=" + API_KEY + "&action=getPrices&service=vi&country=";
        String r = HttpUtil.get(u);
        JSONObject json = JSON.parseObject(r);
        json.keySet().forEach(key -> {
            LaoMaoPrice laoMaoPrice = JSON.parseObject(json.getJSONObject(key).getJSONObject("vi").toJSONString(), LaoMaoPrice.class);
            laoMaoPrice.setCountryId(key);
            laoMaoPrice.setCountry(countries.get(Integer.parseInt(key)));
            if (laoMaoPrice.getCost() <= maxPrice) {
//           log.info("laoMaoPrice:{}", laoMaoPrice);
                list.add(laoMaoPrice);
            }
        });
        return list;
    }


    public static void main(String[] args) {
        Map<Integer, LaoMaoCountry> countries = getCountries();
        List<LaoMaoPrice> priceList = getPrices(countries);
//        log.info("countries:{}", JSON.toJSONString(countries));


    }
}
