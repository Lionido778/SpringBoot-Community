package cn.codeprobe.community;

import cn.codeprobe.community.utils.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailTest {

    @Autowired
    private MailClient mailClient;
    @Test
    public void sendMailTest(){
        mailClient.sendMail("1308753047@qq.com","test","this is a test mail !");
    }
}
