package cn.neusoft.xuxiao.webapi;

import cn.neusoft.xuxiao.service.inf.IPhotoService;
import cn.neusoft.xuxiao.utils.ImageUtils;
import cn.neusoft.xuxiao.utils.TimeTool;
import cn.neusoft.xuxiao.webapi.entity.PhotoSelecter;
import org.bouncycastle.util.encoders.UrlBase64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.channels.Selector;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.FileHandler;

@Controller
public class PhotoController {

    @Resource(name="iPhotoServiceImpl")
    private IPhotoService photoService;

    @RequestMapping("/photo/getOnePhoto")
    public void getOnePhoto(HttpServletResponse response,String week,String name) throws IOException {
        String rootPath1 = "/home/ubuntu/ncqa_dev/registerImages/"+week+"/"+name;
        ImageUtils.imageFiletoClient(response,rootPath1);
    }

    @RequestMapping("/photo/index")
    public String getImagesHtml(PhotoSelecter reqMsg, ModelMap map){
        List<Map<String, String>> imagesHtml = photoService.getImagesHtml(reqMsg);

        int weekOfYear = TimeTool.getWeekOfYear();
        map.put("week",weekOfYear);
        map.put("list",imagesHtml);

        return "photo";
    }
}
