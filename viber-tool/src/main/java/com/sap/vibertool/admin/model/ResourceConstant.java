package com.sap.vibertool.admin.model;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

public class ResourceConstant {
    public static String GRIZZLY_SMS = "675e764f21c24b13";
    public static String HERO_SMS = "ff1ad6a6fc7d44b9";
    public static String LAO_MAO = "f7f629fd64d9409b";
    public static String SMS_BOWER = "a740a994692842b7";
    public static String TIGER_SMS = "3ed00ac490ef44d8";
    public static String WORLDCODE = "81036a3e8a6d48fb";

    static void main() {
        JSONObject json = JSON.parseObject("{\n" +
                "    \"code\": 0,\n" +
                "    \"msg\": \"success\",\n" +
                "    \"success\": true,\n" +
                "    \"timeStamp\": 1792854813348,\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"id\": \"fcc6f8d118274c5d\",\n" +
                "            \"provider\": \"FANGYUAN_SMS\",\n" +
                "            \"token\": \"******\",\n" +
                "            \"tokenPreview\": \"6b60bb92ad01...\",\n" +
                "            \"projectId\": \"10085:8818\",\n" +
                "            \"providerParams\": null,\n" +
                "            \"maxPriceUsd\": 0.150000,\n" +
                "            \"balanceType\": \"U\",\n" +
                "            \"balance\": 13.2353,\n" +
                "            \"balanceError\": \"FangyuanSms balance 失败：{\\\"code\\\":\\\"2010\\\",\\\"msg\\\":\\\"\\\\u4f59\\\\u989d\\\\u4e0d\\\\u8db3\\\"}\",\n" +
                "            \"balanceUpdatedAt\": 1791985142897,\n" +
                "            \"enabled\": false,\n" +
                "            \"createdAt\": 1781620657803,\n" +
                "            \"updatedAt\": 1791985166728\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"c9765ffb778f4823\",\n" +
                "            \"provider\": \"FIREFOX\",\n" +
                "            \"token\": \"******\",\n" +
                "            \"tokenPreview\": \"c098adc5c7d2...\",\n" +
                "            \"projectId\": \"1064\",\n" +
                "            \"providerParams\": \"{\\\"firefoxCookie\\\": \\\"cswebclinet_language=chn; cswebclient_select_item=1064; AutoRelMin=%u0031%u0030; BillingModel=%u0031; BlackOwner=%u0031; C_AliImg=%u0030; C_AllowWithdraw=%u0031; C_AutoJoinItem=%u0031; C_Cap=%u0031; C_CapMin=%u0030; C_DockingOpen=%u0031; C_FormText=%u0068%u0074%u0074%u0070%u0073%u003A%u002F%u002F%u0074%u002E%u006D%u0065%u002F%u0066%u0069%u0072%u0065%u0066%u006F%u0078%u005F%u0066%u0075%u006E%u0020%u652F%u6301; C_ItemDelPhone=%u0030; C_LoginText=%u9ED1%u5BA2%u6A2A%u884C%uFF0C%u5982%u679C%u60A8%u975E%u5B98%u7F51%u0028%u0066%u0069%u0072%u0065%u0066%u006F%u0078%u002E%u0066%u0075%u006E%u0029%u4E0B%u8F7D%uFF0C%u5EFA%u8BAE%u7ACB%u5373%u5B89%u88C5%u706B%u7ED2%u0028%u0068%u0075%u006F%u0072%u006F%u006E%u0067%u002E%u0063%u006E%u0029%u4E2A%u4EBA%u7248%uFF0C%u5E76%u91CD%u65B0%u5230%u5B98%u7F51%u4E0B%u8F7D; C_MinWithdraw=%u0031%u0035%u0030; C_Regist=%u0031; C_ShowPrice=%u0031; C_ShowUPrice=%u0031; ClearDataBase=%u0032%u0030%u0031%u0039%u002D%u0030%u0033%u002D%u0030%u0039; CpUrlBase=%u0068%u0074%u0074%u0070%u003A%u002F%u002F%u006B%u0073%u002E%u0066%u0069%u0072%u0065%u0066%u006F%u0078%u002E%u0066%u0075%u006E%u002F; D_Regist=%u0030; DelOldData=%u0039%u0030; DockingPer=%u0030%u002E%u0030%u0030%u0030%u0030; FeesType=%u0030; ForceDocking=%u0030; ForceOrder=%u0030; GGTalk=%u5BA2%u670D%u5728%u7EBF%u0038%u003A%u0030%u0030%u002D%u0032%u0032%u003A%u0030%u0030; GotMode=%u0032; IsAllowBlack=%u0031; IsAllowRepeat=%u0031; ItemAdd=%u0030; KSApiPhoneAddCountMax=%u0033%u0030%u0030%u0030%u0030; M_YunDataApi=; Membership=%u0030; NumberCode=%u0030; OderBy=%u0030; PrivateAdd=%u0031; RechRedPag=%u0030; RelCou2Black=%u0033; RelTimes=%u0031; SmsReplaceString=%u0039%u0035%u0031%u0038%u0038%u002C%u0034%u0030%u0030%u0030%u0039%u0036%u0030%u0039%u0036%u0030%u002C%u0038%u0038%u0031%u0035%u0038%u0031%u0039%u0038%u002C%u0036%u0032%u0038%u0033%u0036%u0036%u0036%u0036%u002C%u0034%u0036%u0030%u0030%u0033%u0037%u0034%u0034%u0031%u0033%u0034%u0030%u002C%u0038%u0038%u0031%u0035%u0038%u0031%u0039%u0038%u002C%u003A%u0031%u0039%u0031%u0032%u0031%u002C%u003F%u002C%u0052%u004D%u0030%u002C%u002D%u002C%u0034%u0030%u0030%u0038%u0038%u0038%u0030%u0039%u0039%u0039%u002C%u0052%u004D%u0030%u002E%u0030%u0030%u002C%u0077%u0068%u0061%u0074%u0073%u0061%u0070%u0070%u6765%u7535%u53F7%u7801%u8F6C%u77ED%u4FE1%u005B%u4E8C%u4E03%u002C%u0077%u0068%u0061%u0074%u0073%u0061%u0070%u0070%u6765%u7535%u53F7%u7801%u8F6C%u77ED%u4FE1%u005B%u96F6%u4E03%u002C%u0077%u0068%u0061%u0074%u0073%u0061%u0070%u0070%u6765%u7535%u53F7%u7801%u8F6C%u77ED%u4FE1%u005B%u96F6%u516D%u002C%u0077%u0068%u0061%u0074%u0073%u0061%u0070%u0070%u6765%u7535%u53F7%u7801%u8F6C%u77ED%u4FE1%u005B%u96F6%u4E00%u96F6%u4E00%u002C%u0077%u0065%u0063%u0068%u0061%u0074%u6765%u7535%u53F7%u7801%u8F6C%u77ED%u4FE1%u002C%u0031%u0038%u0030%u0030%u0038%u0030%u0030%u002C%u0031%u0038%u0030%u0030%u0038%u0030%u0039%u0038%u002C%u004C%u0048%u0020%u0031%u0038%u0030%u0030%u002C%u0076%u0069%u0061%u0020%u0057%u0068%u0061%u0074%u0073%u0041%u0070%u0070%u0020%u0061%u0075%u002C%u003F%u003F%u003F%u0020%u0035%u0020%u003F%u003F%u003F%u002C%u0072%u006F%u0061%u006D%u0069%u006E%u0067%u002C%u002A%u002A%u002A%u002A%u002C%u0074%u0065%u006C%u0065%u0067%u0072%u0061%u006D%u0077%u0068%u0061%u0074%u0073%u0061%u0070%u0070%u62FC%u591A%u591A%u0076%u0069%u0062%u0065%u0072%u0066%u0061%u0063%u0065%u0062%u006F%u006F%u006B%u004C%u0075%u0063%u006B%u0069%u006E%u004D%u0059%u002C%u0054%u0065%u006C%u0065%u0067%u0072%u0061%u006D%u0057%u0068%u0061%u0074%u0073%u0041%u0070%u0070%u62FC%u591A%u591A%u6EF4%u6EF4%u0066%u0061%u0063%u0065%u0062%u006F%u006F%u006B%u963F%u91CC%u5DF4%u5DF4%u6DD8%u5B9D%u006C%u0061%u007A%u0061%u0064%u0061%u0076%u0069%u0062%u0065%u0072%u004E%u0069%u006B%u0065%u002C%u0074%u0065%u006C%u0065%u0067%u0072%u0061%u006D%u0077%u0068%u0061%u0074%u0073%u0061%u0070%u0070%u002C%u0054%u0065%u006C%u0065%u0067%u0072%u0061%u006D%u0057%u0068%u0061%u0074%u0073%u0041%u0070%u0070%u0076%u0069%u0062%u0065%u0072%u62FC%u591A%u591A%u6DD8%u5B9D%u002C%u0074%u0065%u006C%u0065%u0067%u0072%u0061%u006D%u0077%u0068%u0061%u0074%u0073%u0061%u0070%u0070%u963F%u91CC%u5DF4%u5DF4%u0066%u0061%u0063%u0065%u0062%u006F%u006F%u006B%u0076%u0069%u0062%u0065%u0072%u002C%u0064%u0061%u0073%u0073%u0069%u0073%u0074%u0061%u006E%u0063%u0065%u003A%u0020%u0057%u0068%u0061%u0074%u0073%u0041%u0070%u0070%u002C%u0054%u0065%u006C%u0065%u0067%u0072%u0061%u006D%u0057%u0068%u0061%u0074%u0073%u0041%u0070%u0070%u002C%u0074%u0065%u006C%u0065%u0067%u0072%u0061%u006D%u0077%u0068%u0061%u0074%u0073%u0061%u0070%u0070%u002C%u0032%u0034%u0034%u0039%u0032%u0033%u0031%u0031%u0039%u0031%u0039%u0032%u002C; sys_usdt_trc20_address=%u0054%u0058%u0073%u0071%u004A%u0046%u0050%u0064%u0067%u0047%u0052%u0058%u0058%u0033%u0070%u0058%u0065%u0053%u0034%u0077%u0052%u0050%u0076%u0058%u0058%u004A%u0042%u0046%u0067%u0038%u0052%u0050%u0045%u0041; TPhoneSmsTableNameCountry=%u0030; TPhoneTableNameCountry=%u0030; TronNet_ApiKey=%u0035%u0036%u0036%u0036%u0037%u0038%u0038%u0034%u002D%u0066%u0035%u0064%u0034%u002D%u0034%u0031%u0035%u0062%u002D%u0039%u0066%u0038%u0033%u002D%u0033%u0064%u0034%u0031%u0039%u0033%u0064%u0036%u0031%u0030%u0034%u0035; U_AccQuery=%u0031; U_AliName=%u0031; U_AliPic=%u0068%u0074%u0074%u0070%u003A%u002F%u002F%u0064%u006F%u006D%u002E%u0064%u006F%u006D%u006E%u0061%u006D%u0065%u002E%u0066%u0075%u006E%u002F; U_ApiSite=%u0068%u0074%u0074%u0070%u0073%u003A%u002F%u002F%u0077%u0065%u0062%u002E%u0066%u0069%u0072%u0065%u0066%u006F%u0078%u002E%u0066%u0075%u006E%u002F%u0075%u0073%u0065%u0072%u002F%u0061%u0070%u0069%u0073%u002F%u0061%u0070%u0069%u0064%u006F%u0063%u005F%u0063%u0068%u006E%u002E%u0061%u0073%u0070%u0078; U_DockingQQ=%u0030; U_FormText=%u0068%u0074%u0074%u0070%u0073%u003A%u002F%u002F%u0074%u002E%u006D%u0065%u002F%u0066%u0069%u0072%u0065%u0066%u006F%u0078%u005F%u0066%u0075%u006E%u0020%u652F%u6301; U_LoginText=%u9ED1%u5BA2%u6A2A%u884C%uFF0C%u5982%u679C%u60A8%u975E%u5B98%u7F51%u0028%u0066%u0069%u0072%u0065%u0066%u006F%u0078%u002E%u0066%u0075%u006E%u0029%u4E0B%u8F7D%uFF0C%u5EFA%u8BAE%u7ACB%u5373%u5B89%u88C5%u706B%u7ED2%u0028%u0068%u0075%u006F%u0072%u006F%u006E%u0067%u002E%u0063%u006E%u0029%u4E2A%u4EBA%u7248%uFF0C%u5E76%u91CD%u65B0%u5230%u5B98%u7F51%u4E0B%u8F7D; U_Recharge=%u0037; U_RechMin=%u0030; U_Regist=%u0031; U_ShowHelp=%u0068%u0074%u0074%u0070%u003A%u002F%u002F%u0064%u006C%u0065%u002E%u006F%u006F%u0078%u0078%u0030%u0030%u0030%u0030%u0030%u002E%u0063%u006F%u006D%u002F%u0064%u006C%u0065%u0063%u006F%u006E%u0073%u006F%u006C%u0065%u002F%u0072%u0065%u0067%u0069%u0073%u0074%u0065%u0072%u002E%u0068%u0074%u006D%u006C; U_ShowOnlineCount=%u0031; UnDockingPer=%u0030%u002E%u0030%u0030%u0030%u0030; WriteApiLog=%u0031; cswebclient_select_country=ken; cswebbuyer_name=ccckrik194@gmail.com; cswebbuyer_password=640E7A720D1CBAE1D931305F5580AD63; cswebclinet_msgid_click=4901917; cswebclinet_msgid_readed=4902382\\\"}\",\n" +
                "            \"maxPriceUsd\": 0.150000,\n" +
                "            \"balanceType\": \"U\",\n" +
                "            \"balance\": 0.0,\n" +
                "            \"balanceError\": \"\",\n" +
                "            \"balanceUpdatedAt\": 1789626179769,\n" +
                "            \"enabled\": false,\n" +
                "            \"createdAt\": 1781620657803,\n" +
                "            \"updatedAt\": 1789626198291\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"675e764f21c24b13\",\n" +
                "            \"provider\": \"GRIZZLY_SMS\",\n" +
                "            \"token\": \"******\",\n" +
                "            \"tokenPreview\": \"b4c217f0e030...\",\n" +
                "            \"projectId\": \"vi\",\n" +
                "            \"providerParams\": null,\n" +
                "            \"maxPriceUsd\": 0.150000,\n" +
                "            \"balanceType\": \"U\",\n" +
                "            \"balance\": 849.514,\n" +
                "            \"balanceError\": \"\",\n" +
                "            \"balanceUpdatedAt\": 1792854813215,\n" +
                "            \"enabled\": true,\n" +
                "            \"createdAt\": 1781620657803,\n" +
                "            \"updatedAt\": 1781620657803\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"ff1ad6a6fc7d44b9\",\n" +
                "            \"provider\": \"HERO_SMS\",\n" +
                "            \"token\": \"******\",\n" +
                "            \"tokenPreview\": \"d0cf44Ae76A7...\",\n" +
                "            \"projectId\": \"vi\",\n" +
                "            \"providerParams\": null,\n" +
                "            \"maxPriceUsd\": 0.150000,\n" +
                "            \"balanceType\": \"U\",\n" +
                "            \"balance\": 1277.14,\n" +
                "            \"balanceError\": \"\",\n" +
                "            \"balanceUpdatedAt\": 1792854751872,\n" +
                "            \"enabled\": true,\n" +
                "            \"createdAt\": 1781620657803,\n" +
                "            \"updatedAt\": 1781620657803\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"f7f629fd64d9409b\",\n" +
                "            \"provider\": \"LAO_MAO\",\n" +
                "            \"token\": \"******\",\n" +
                "            \"tokenPreview\": \"cfb2bbd3d232...\",\n" +
                "            \"projectId\": \"vi\",\n" +
                "            \"providerParams\": null,\n" +
                "            \"maxPriceUsd\": 0.150000,\n" +
                "            \"balanceType\": \"U\",\n" +
                "            \"balance\": 479.143,\n" +
                "            \"balanceError\": \"\",\n" +
                "            \"balanceUpdatedAt\": 1792854751936,\n" +
                "            \"enabled\": true,\n" +
                "            \"createdAt\": 1783835586751,\n" +
                "            \"updatedAt\": 1783835586751\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"76e24e1269ce441c\",\n" +
                "            \"provider\": \"META_OTP\",\n" +
                "            \"token\": \"******\",\n" +
                "            \"tokenPreview\": \"66c88e9eecbc...\",\n" +
                "            \"projectId\": \"viber\",\n" +
                "            \"providerParams\": null,\n" +
                "            \"maxPriceUsd\": 0.200000,\n" +
                "            \"balanceType\": \"U\",\n" +
                "            \"balance\": 0.0,\n" +
                "            \"balanceError\": \"\",\n" +
                "            \"balanceUpdatedAt\": 1783830959452,\n" +
                "            \"enabled\": false,\n" +
                "            \"createdAt\": 1783314144009,\n" +
                "            \"updatedAt\": 1783830975564\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"fd820353e7f743f6\",\n" +
                "            \"provider\": \"PORN_NUMBER_SUZ\",\n" +
                "            \"token\": \"******\",\n" +
                "            \"tokenPreview\": \"ea90de53-f69...\",\n" +
                "            \"projectId\": \"\",\n" +
                "            \"providerParams\": null,\n" +
                "            \"maxPriceUsd\": 0.150000,\n" +
                "            \"balanceType\": \"U\",\n" +
                "            \"balance\": 1244.93,\n" +
                "            \"balanceError\": \"www.tg-pornumbersuz.shop 网络异常，稍后重试\",\n" +
                "            \"balanceUpdatedAt\": 1792576589165,\n" +
                "            \"enabled\": false,\n" +
                "            \"createdAt\": 1791534821169,\n" +
                "            \"updatedAt\": 1792576650612\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"721c4990a6dd4161\",\n" +
                "            \"provider\": \"SECOND_NUMBER\",\n" +
                "            \"token\": \"******\",\n" +
                "            \"tokenPreview\": \"39q1t9FimXal...\",\n" +
                "            \"projectId\": \"vi\",\n" +
                "            \"providerParams\": null,\n" +
                "            \"maxPriceUsd\": 0.150000,\n" +
                "            \"balanceType\": \"U\",\n" +
                "            \"balance\": 96.85,\n" +
                "            \"balanceError\": \"\",\n" +
                "            \"balanceUpdatedAt\": 1791639009840,\n" +
                "            \"enabled\": false,\n" +
                "            \"createdAt\": 1788868812793,\n" +
                "            \"updatedAt\": 1791639045087\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"e1e598c4b8da42bb\",\n" +
                "            \"provider\": \"SIM_51\",\n" +
                "            \"token\": \"******\",\n" +
                "            \"tokenPreview\": \"eyJhbGciOiJS...\",\n" +
                "            \"projectId\": \"viber\",\n" +
                "            \"providerParams\": null,\n" +
                "            \"maxPriceUsd\": 0.150000,\n" +
                "            \"balanceType\": \"U\",\n" +
                "            \"balance\": 1.7478,\n" +
                "            \"balanceError\": \"\",\n" +
                "            \"balanceUpdatedAt\": 1787018351875,\n" +
                "            \"enabled\": false,\n" +
                "            \"createdAt\": 1783837000577,\n" +
                "            \"updatedAt\": 1787018381607\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"2a3060c94e224b1f\",\n" +
                "            \"provider\": \"SMSBUYS\",\n" +
                "            \"token\": \"******\",\n" +
                "            \"tokenPreview\": \"3122a2c1504e...\",\n" +
                "            \"projectId\": \"10008\",\n" +
                "            \"providerParams\": null,\n" +
                "            \"maxPriceUsd\": 0.150000,\n" +
                "            \"balanceType\": \"U\",\n" +
                "            \"balance\": 78.988,\n" +
                "            \"balanceError\": \"\",\n" +
                "            \"balanceUpdatedAt\": 1791639008939,\n" +
                "            \"enabled\": false,\n" +
                "            \"createdAt\": 1788858537525,\n" +
                "            \"updatedAt\": 1791639051420\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"a740a994692842b7\",\n" +
                "            \"provider\": \"SMS_BOWER\",\n" +
                "            \"token\": \"******\",\n" +
                "            \"tokenPreview\": \"HCxNudqGrl5m...\",\n" +
                "            \"projectId\": \"vi\",\n" +
                "            \"providerParams\": null,\n" +
                "            \"maxPriceUsd\": 0.150000,\n" +
                "            \"balanceType\": \"U\",\n" +
                "            \"balance\": 1177.15,\n" +
                "            \"balanceError\": \"\",\n" +
                "            \"balanceUpdatedAt\": 1792854752137,\n" +
                "            \"enabled\": true,\n" +
                "            \"createdAt\": 1782630003569,\n" +
                "            \"updatedAt\": 1782643699010\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"588237ac77e9466d\",\n" +
                "            \"provider\": \"SMS_MAIN\",\n" +
                "            \"token\": \"******\",\n" +
                "            \"tokenPreview\": \"PhIAIV-criAd...\",\n" +
                "            \"projectId\": \"121\",\n" +
                "            \"providerParams\": null,\n" +
                "            \"maxPriceUsd\": 0.150000,\n" +
                "            \"balanceType\": \"U\",\n" +
                "            \"balance\": 998.12,\n" +
                "            \"balanceError\": \"HTTP connect timed out\",\n" +
                "            \"balanceUpdatedAt\": 1782715529939,\n" +
                "            \"enabled\": false,\n" +
                "            \"createdAt\": 1781620657803,\n" +
                "            \"updatedAt\": 1782715598067\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"bb7c7a2c18b5476d\",\n" +
                "            \"provider\": \"SWIFT_VERIFY\",\n" +
                "            \"token\": \"******\",\n" +
                "            \"tokenPreview\": \"3b3a9cb03531...\",\n" +
                "            \"projectId\": \"vi\",\n" +
                "            \"providerParams\": null,\n" +
                "            \"maxPriceUsd\": 0.150000,\n" +
                "            \"balanceType\": \"U\",\n" +
                "            \"balance\": 79.95,\n" +
                "            \"balanceError\": \"SwiftVerify balance 失败：{\\\"code\\\":\\\"SERVER_ERROR\\\",\\\"message\\\":\\\"Internal server error\\\"}\",\n" +
                "            \"balanceUpdatedAt\": 1784710776223,\n" +
                "            \"enabled\": false,\n" +
                "            \"createdAt\": 1781620657803,\n" +
                "            \"updatedAt\": 1784710838550\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"3ed00ac490ef44d8\",\n" +
                "            \"provider\": \"TIGER_SMS\",\n" +
                "            \"token\": \"******\",\n" +
                "            \"tokenPreview\": \"zGfKIYs8iwR3...\",\n" +
                "            \"projectId\": \"vi\",\n" +
                "            \"providerParams\": null,\n" +
                "            \"maxPriceUsd\": 0.150000,\n" +
                "            \"balanceType\": \"U\",\n" +
                "            \"balance\": 190.171,\n" +
                "            \"balanceError\": \"\",\n" +
                "            \"balanceUpdatedAt\": 1792854752943,\n" +
                "            \"enabled\": true,\n" +
                "            \"createdAt\": 1781620657803,\n" +
                "            \"updatedAt\": 1786975527471\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"73ff86f37b584b47\",\n" +
                "            \"provider\": \"VAK_SMS\",\n" +
                "            \"token\": \"******\",\n" +
                "            \"tokenPreview\": \"cdacf4d7b8f3...\",\n" +
                "            \"projectId\": \"vi\",\n" +
                "            \"providerParams\": null,\n" +
                "            \"maxPriceUsd\": 0.150000,\n" +
                "            \"balanceType\": \"$\",\n" +
                "            \"balance\": 50.0,\n" +
                "            \"balanceError\": \"\",\n" +
                "            \"balanceUpdatedAt\": 1781881600041,\n" +
                "            \"enabled\": false,\n" +
                "            \"createdAt\": 1781620657803,\n" +
                "            \"updatedAt\": 1781881653312\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"81036a3e8a6d48fb\",\n" +
                "            \"provider\": \"WORLDCODE\",\n" +
                "            \"token\": \"******\",\n" +
                "            \"tokenPreview\": \"eb0d0f1b3637...\",\n" +
                "            \"projectId\": \"1049\",\n" +
                "            \"providerParams\": \"{\\\"worldcodeCookie\\\": \\\"cswebclinet_language=chn; cswebbuyer_name=ccckrik194@gmail.com; cswebbuyer_password=800B9BD1E5441D8523C65C121449ABDD\\\"}\",\n" +
                "            \"maxPriceUsd\": 0.150000,\n" +
                "            \"balanceType\": \"U\",\n" +
                "            \"balance\": 1041.86,\n" +
                "            \"balanceError\": \"\",\n" +
                "            \"balanceUpdatedAt\": 1792854752999,\n" +
                "            \"enabled\": true,\n" +
                "            \"createdAt\": 1788868773613,\n" +
                "            \"updatedAt\": 1789897622708\n" +
                "        }\n" +
                "    ]\n" +
                "}");
        JSONArray data = json.getJSONArray("data");
        for (int i = 0; i < data.size(); i++) {
            JSONObject e = data.getJSONObject(i);
            if (e.getBoolean("enabled")) {
                System.out.println("public static String " + e.getString("provider") + "=\"" + e.getString("id") + "\";");
            }
        }
    }
}
