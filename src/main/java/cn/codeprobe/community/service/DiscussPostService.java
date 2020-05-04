package cn.codeprobe.community.service;

import cn.codeprobe.community.dao.DiscussPostMapper;
import cn.codeprobe.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    public List<DiscussPost> findDiscussPosts(int userId,int offset,int limit){
        return discussPostMapper.selectDiscussPost(userId,offset,limit);
    }

    public int findDiscussRows(int userId){
        return discussPostMapper.selectDiscussRows(userId);
    }
}
