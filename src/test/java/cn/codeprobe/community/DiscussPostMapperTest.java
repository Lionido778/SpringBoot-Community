package cn.codeprobe.community;

import cn.codeprobe.community.dao.DiscussPostMapper;
import cn.codeprobe.community.entity.DiscussPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DiscussPostMapperTest {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    public void selectDiscussPostTest(){
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPost(0, 0, 10);
        for (DiscussPost discussPost: discussPosts) {
            System.out.println(discussPost);
        }

        int rows = discussPostMapper.selectDiscussRows(0);
        System.out.println(rows);
    }
}
