package cc.xmist;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = {"cc.xmist.mistchat"})
@MapperScan("cc.xmist.mistchat.**.mapper")
@ServletComponentScan
public class MistChatServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MistChatServerApplication.class, args);
    }

}