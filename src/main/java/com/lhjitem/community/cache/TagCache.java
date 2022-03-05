package com.lhjitem.community.cache;

import com.lhjitem.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lhj
 * @create 2022/3/1 18:29
 * 这里就是直接用java的方式来简便的代替标签数据库
 */
public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO language = new TagDTO();
        language.setCategoryName("开发语言");
        language.setTags(Arrays.asList("js","php","css","html","java","python","c++","c","c#","golang","shell","node.js"));

        TagDTO frame = new TagDTO();
        frame.setCategoryName("开发框架");
        frame.setTags(Arrays.asList("sprig","springboot","springmvc","mybatis","springCloud"));


        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("腾讯云","阿里云"));

        TagDTO dataBase = new TagDTO();
        dataBase.setCategoryName("数据库和缓存");
        dataBase.setTags(Arrays.asList("mysql","h5","sql-server","redis","Oracle","DB2"));


        tagDTOS.add(language);
        tagDTOS.add(frame);
        tagDTOS.add(server);
        tagDTOS.add(dataBase);
        return tagDTOS;
    }


    /**
     * 这个方法就是来校验用户不用过选取标签，而是自己随便输入字符，我们以此来替换或删除非法标签
     * @param tags
     * @return
     */
    public static String filterValid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();

        //flatMap的作用是将二维数组的每一个给遍历出来，而不是只遍历每行的一维数组
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());

        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));

        return invalid;

    }
}
