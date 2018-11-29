package cn.neusoft.xuxiao.service.impl;

import cn.neusoft.xuxiao.constants.ClassIdConstants;
import cn.neusoft.xuxiao.service.inf.IPhotoService;
import cn.neusoft.xuxiao.utils.StringUtil;
import cn.neusoft.xuxiao.utils.TimeTool;
import cn.neusoft.xuxiao.webapi.entity.PhotoSelecter;
import org.junit.Test;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("iPhotoServiceImpl")
public class PhotoServiceImpl implements IPhotoService {

    private static String rootPath = "/home/ubuntu/ncqa_dev/registerImages/";

    @Override
    public List<Map<String,String>> getImagesHtml(PhotoSelecter selecter) {

        if(StringUtil.isEmpty(selecter.getClass_name())){
            selecter.setClass_name("all");
        }

        if(StringUtil.isEmpty(selecter.getWeek())){
            selecter.setWeek(String.valueOf(TimeTool.getWeekOfYear()));
        }

        File file=  new File(rootPath+selecter.getWeek()+"/");

        if(!file.exists()){
            return null;
        }
        System.out.println(file.getPath());
        List<Map<String,String>> path = new ArrayList<>();

        String[] fileName = file.list();
        for(String one : fileName){
            Map<String, String> map = new HashMap<>();
            if(!StringUtil.isEmpty(selecter.getName())){
                String student_name = one.substring(12,15);
                if(student_name.endsWith("2")){
                    student_name = student_name.substring(0, 2);
                }
                if(student_name.equals(selecter.getName())){
                    select(selecter, one, map);
                }
            }else{
                select(selecter, one, map);
            }
            path.add(map);
        }

        return path;
    }

    private void select(PhotoSelecter selecter, String one, Map<String, String> map) {
        if(selecter.getClass_name().equals("all")){
            map.put(selecter.getWeek(),one);
        }else{
            System.out.println(one);
            String class_index = one.substring(0,9);
            if(selecter.getClass_name().equals(ClassIdConstants.map.get(class_index))){
                map.put(selecter.getWeek(),one);
            }
        }
    }

}
