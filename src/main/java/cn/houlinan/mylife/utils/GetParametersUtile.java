package cn.houlinan.mylife.utils;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * DESC：根据前端请求获取参数转成json
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/5/23
 * Time : 12:49
 */
public class GetParametersUtile {


    public static void main(String[] args) throws Exception{
        String path = "C:\\Users\\houli\\Desktop\\appPram.txt" ;
        String result = readTxt(path).toString() ;
        System.out.println(StringEscapeUtils.escapeJson(result));
//        System.out.println(result);
        String str = "%E4%BA%A7%E5%93%81%E7%A0%94%E5%8F%91%E4%B8%AD%E5%BF%83";



//        System.out.println(URLEncoder.encode("aaaaaa" , "UTF-8"));
//        JSONObject jsonObject = JSONObject.fromObject(result);
//        Map<String, String> mapJson = JSONObject.fromObject(jsonObject);
//        System.out.println("111" + mapJson.toString());
        //resultMap = JSON.parseObject(jsonObjectData.toString(), Map.class);
           }




/*    public void testGaojiandangan()throws Exception{

        User loginUser = User.findByName("weibo@qq.com");
        ContextHelper.clear();
        ContextHelper.initContext(loginUser);
        String str=

        JSONObject jsonObject = JSONObject.fromObject(str
        );
        Map<String, String> mapJson = JSONObject.fromObject(jsonObject);
        HashMap hParameters  = new HashMap <>();
        hParameters.putAll(mapJson);
        System.out.println(hParameters.toString());
        hParameters.remove("serviceid");
        hParameters.remove("methodname");
        JSPRequestProcessor processor = new JSPRequestProcessor();
        Object oReport = (Object) processor.excute(mapJson.get("SERVICEID") + "", "" +mapJson.get("METHODNAME"), hParameters);
        System.err.println(WCMJSONHelper.toJSON(oReport)
        );


    }*/

    public static JSONObject readTxt(String filePath) {

        JSONObject result = new JSONObject();

        try {
            File file = new File(filePath);
            if(file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
//                    System.out.println("当前行的文字为“："+ lineTxt);
                    String[] strs = lineTxt.split(":");
                    if(!StringUtils.isEmpty(strs[0])){
                        StringBuffer sb = new StringBuffer( );
                        if(strs.length == 2){
                            sb.append(strs[1].trim());
                        }else{
                            for(int a = 1 ;a <strs.length  ; a++){
                                sb.append(strs[a].trim()).append(":");
                            }
                            sb.setLength(sb.length()-1);
                        }
                        String cuttValue = strs[1];
                        if (StringUtils.isEmpty(sb.toString())){
                            result.put(strs[0].trim().toUpperCase(),"");
                        }else{
                            result.put(strs[0].trim().toUpperCase() , sb.toString());
                        }
                    }
                }
                br.close();
            } else {
                System.out.println("文件不存在!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件读取错误!");
        }

        return result ;
    }



}
