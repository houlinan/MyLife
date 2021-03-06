package cn.houlinan.mylife.service;

import cn.houlinan.mylife.constant.HPConstant;
import cn.houlinan.mylife.entity.RegistrationCode;
import cn.houlinan.mylife.entity.primary.repository.RegistrationCodeRepository;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.DesEncryptUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/11/9
 * Time : 20:41
 */
@Service
@Slf4j
public class HPShopService {


    @Autowired
    RegistrationCodeRepository registrationCodeRepository ;

    public boolean checkRegistCode(String desRegistCode) {


        if(CMyString.isEmpty(desRegistCode)) {
            log.error("desRegistCode == null");
            return false;
        }

        try{
            String[] data = getRegistDescByDESCode(desRegistCode);
            JSONObject attribute = getRegistCodeJson(data);

            RegistrationCode registrationCode = registrationCodeRepository.findRegistrationCodeById(data[0]);
            if(!registrationCode.getAttribute().equals(attribute.toString())){
                log.error("对比注册码数据错误，registrationCode.getAttribute() = {} ， attribute = {}"
                ,registrationCode.getAttribute() , attribute.toString());
                return false ;
            }
            return true ;

        }catch(Throwable e){
            e.printStackTrace();
        }
        return false ;
    }



    /**
     *DESC:根据二维码加密码获取加密信息
     *@param:  DESCode
     *@return:  net.sf.json.JSONObject
     *@author hou.linan
     *@date:
    */
    public String[] getRegistDescByDESCode(String desRegistCode) throws Throwable{
        String registCode = DesEncryptUtil.decrypt(desRegistCode , HPConstant.DES_KEY);

        registCode =new String(Base64.decodeBase64(registCode.getBytes("UTF-8")),"UTF-8");
        log.info("解析注册码：{} ，解析之后的结果是：{}" , desRegistCode ,registCode );
        //191109BH2F501P94_0_1_20_2_5
        String[] data =  registCode.split("_");
        if(data.length != 6){
            throw new Exception("解析注册码{"+registCode + "}错误，解析长度不一致" );
        }

        RegistrationCode registrationCode = registrationCodeRepository.findRegistrationCodeById(data[0]);

        if(registrationCode == null ) {
            throw new Exception("没有找到对象{" + data[0] + "}" );
        }
        if(registrationCode.getIsUserd() == 1){
            throw new Exception("注册码已经被使用。注册码为：{" + registrationCode.getId() + "}"  );
        }

        return data ;
    }

    public JSONObject getRegistCodeJson(String... data){

        JSONObject attribute = new JSONObject();
        attribute.put("isTop" ,  Integer.valueOf(data[1]) );
        attribute.put("adminNum" ,  Integer.valueOf(data[2]) );
        attribute.put("productNum" ,  Integer.valueOf(data[3])) ;
        attribute.put("picNum" ,  Integer.valueOf(data[4]) );
        attribute.put("productOutTime" , Integer.valueOf( data[5])) ;

        return attribute;
    }


}
