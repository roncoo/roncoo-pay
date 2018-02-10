package com.fast.pay.trade.utils.alipay.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class AliPayUtil {

    public static Map<String , String> parseNotifyMsg(Map<String, String[]> requestParams){

        Map<String,String> params = new HashMap<String,String>();

        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        return params;
    }

}
