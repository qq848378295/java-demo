package com.sap.vibertool.sms.smsbower;

public class SmsBower {
    static void main() {
        String API_KEY = "nH0k4ZQf6ROowP3qy9cX7RnZhVnZ0Rj7";
        String code = "vi";
//        String u="https://smsbower.page/stubs/handler_api.php?api_key="+API_KEY+"&action=getServicesList";
//        System.out.println(u);


        String countryU = "https://smsbower.page/stubs/handler_api.php?api_key=" + API_KEY + "&action=getCountries";
        System.out.println(countryU);


        String priceU = "https://smsbower.page/stubs/handler_api.php?api_key=" + API_KEY + "&action=getPrices&service=" + code + "&country=";
        System.out.println(priceU);


    }
}
