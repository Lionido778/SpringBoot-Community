package cn.codeprobe.community.dao;

import cn.codeprobe.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DiscussPostMapper {

    //采用动态sql，当传入userId = 0,则表示查询所有用户的帖子数量，如果userId != 0,表示查询某个用户帖子数量
    List<DiscussPost> selectDiscussPost(int userId, int offset, int limit);

    //@Param注解用于给参数取别名
    //如果只有一个参数，并且在<if>里使用，则必须加别名
    int selectDiscussRows(@Param("userId") int userId);
}
