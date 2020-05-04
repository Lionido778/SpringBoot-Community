package cn.codeprobe.community.controller;

import cn.codeprobe.community.entity.DiscussPost;
import cn.codeprobe.community.service.DiscussPostService;
import cn.codeprobe.community.service.UserService;
import cn.codeprobe.community.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class homeController {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;

    @RequestMapping(path = "index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page) {
        //方法调用之前，handlerAdapter 会自动实例化Model、Page,并将page注入model
        //所以在Thymeleaf中可以直接访问Page里面的数据
        page.setRows(discussPostService.findDiscussRows(0));
        page.setPath("/index");

        List<DiscussPost> discussPostList = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (discussPostList != null) {
            for (DiscussPost discussPost : discussPostList) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", discussPost);
                map.put("user", userService.findUserById(discussPost.getUserId()));
                mapList.add(map);
            }
        }
        model.addAttribute("discussPosts", mapList);
        return "/index";
    }
}
