package cn.neusoft.xuxiao.service.inf;

import cn.neusoft.xuxiao.webapi.entity.PhotoSelecter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface IPhotoService {

    public List<Map<String,String>> getImagesHtml(PhotoSelecter selecter);
}
