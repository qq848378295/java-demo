package com.sap.vibertool.sms.hero;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.sap.vibertool.sms.laomao.Laomao.maxPrice;

@Slf4j
//2. 请将恢复码保存在安全地方。如果遗失了安装Google Authenticator的手机，可使用此代码恢复账户访问权限。
//RWRTHQBCQH22HZD74BKJA7QC2IWE374Z
public class Hero {
    static String API_KEY = "b31853d68e2b1c21531ccd809A533354";

    //    b31853d68e2b1c21531ccd809A533354
    static void main() {

        Map<Integer, HeroCountry> countrys = countrys();

        List<HeroPrice> heroPrices = offersSms(countrys);

    }

    private static List<HeroPrice> offersSms(Map<Integer, HeroCountry> countries) {
        HttpRequest request = HttpRequest.get("https://hero-sms.com/api/v1/activations/offers/sms?services=vi&verificationType=sms");
        request.header("Authorization", "ApiKey " + API_KEY);
        List<HeroPrice> list = new ArrayList<>();
        AtomicInteger count = new AtomicInteger();
        try (HttpResponse execute = request.execute()) {
            JSONObject json = JSON.parseObject(execute.body());
            JSONObject jsonObject = json.getJSONObject("data").getJSONObject("vi");
            jsonObject.keySet().forEach(key -> {
                HeroPrice heroPrice = new HeroPrice();
                JSONObject _json = jsonObject.getJSONObject(key);
//                JSON.parseObject(json.getJSONObject(key).getJSONObject("vi").toJSONString(), LaoMaoPrice.class);
                JSONObject prices = _json.getJSONObject("prices");
                JSONObject counts = _json.getJSONObject("counts");
                JSONObject map = _json.getJSONObject("map");

                heroPrice.setCountryId(key);
                heroPrice.setCountry(countries.get(Integer.parseInt(key)));

                Integer physical = counts.getInteger("physical");
                heroPrice.setPhysical(physical);
                Double min = prices.getDouble("min");
                heroPrice.setMin(min);

                if (physical > 0 && min <= maxPrice) {
                    heroPrice.setDefaul(prices.getDouble("default"));
                    heroPrice.setRetail(prices.getDouble("retail"));

                    heroPrice.setTotal(counts.getInteger("total"));
                    System.out.println(count.incrementAndGet() + " heroPrice:" + heroPrice);
                    list.add(heroPrice);
                }
            });
        }
        return list;
    }

    public static Map<Integer, HeroCountry> countrys() {
//        String u = "https://hero-sms.com/stubs/handler_api.php?action=getCountries&api_key=" + API_KEY;
//        String s = HttpUtil.get(u);
        String s = FileUtil.readString("C:\\Users\\84837\\antigravity-code\\java-demo\\viber-tool\\src\\main\\java\\com\\sap\\vibertool\\sms\\hero\\country.txt", Charset.defaultCharset());
        JSONObject json = JSON.parseObject(s);
        Map<Integer, HeroCountry> countries = new HashMap<>();
        json.keySet().forEach(key -> {
            countries.put(Integer.parseInt(key), JSON.parseObject(json.getJSONObject(key).toJSONString(), HeroCountry.class));
        });
        return countries;
    }
}
