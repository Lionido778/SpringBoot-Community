package cn.codeprobe.community.service;

import cn.codeprobe.community.dao.UserMapper;
import cn.codeprobe.community.entity.User;
import cn.codeprobe.community.utils.ActivationPag;
import cn.codeprobe.community.utils.DigestHelper;
import cn.codeprobe.community.utils.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements ActivationPag {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private MailClient mailClient;

    @Value("${community.path.domain}")
    public String domain;
    @Value("${server.servlet.context-path}")
    public String contextPath;

    /**
     * 根据id查找用户
     *
     * @param id
     * @return
     */
    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();
        // 空值处理
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "用户名不能为空！");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg", "密码不能为空！");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg", "邮箱不能为空！");
            return map;
        }
        //验证账号
        if (userMapper.selectByName(user.getUsername()) != null) {
            map.put("usernameMsg", "该用户已经被注册");
            return map;
        }
        if (userMapper.selectByEmail(user.getEmail()) != null) {
            map.put("emailMsg", "该邮箱已被占用");
            return map;
        }
        // 注册用户
        user.setType(0);
        user.setStatus(0);
        user.setEmail(user.getEmail());
        user.setCreateTime(new Date());
        user.setHeaderUrl("http://ptlogin2.qq.com/getface?appid=1006102&imgtype=3&uin=1308753047");
        user.setActivationCode(DigestHelper.generateUUID());
        user.setSalt(DigestHelper.generateUUID().substring(0, 5));
        String encryptPwd = DigestHelper.sha512(user.getPassword() + user.getSalt()).substring(4, 12);
        user.setPassword(encryptPwd);
        userMapper.insertUser(user);
        // 发送激活邮件
        Context context = new Context();
        context.setVariable("username", user.getUsername());
        String url = domain + contextPath + "activation" + "/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "激活账户", content);

        return map;
    }

    /**
     * 激活账户
     *
     * @param userId
     * @param code
     * @return
     */
    public int activation(int userId, String code) {
        User user = userMapper.selectById(userId);

        if (user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {
            userMapper.updateStatus(userId,1);
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }

    }

}
