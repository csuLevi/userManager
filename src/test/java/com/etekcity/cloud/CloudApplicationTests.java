package com.etekcity.cloud;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.etekcity.cloud.constant.errorCode.ErrorCode;
import com.etekcity.cloud.dto.request.RegisterRequest;
import com.etekcity.cloud.service.impl.handler.UserRegisterHandler;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void userRegisterHandler() {
        UserRegisterHandler ur = new UserRegisterHandler();
        String emailCorrect = "test@correct.com";
        String emailIncorrect = "test@incorrect_com";
        String password = "ACB132acb";

        RegisterRequest rrCor = new RegisterRequest();
        rrCor.setEmail(emailCorrect);
        rrCor.setPassword(password);

        RegisterRequest rrIncor = new RegisterRequest();
        rrIncor.setEmail(emailIncorrect);
        rrIncor.setPassword(password);
        try {
            Assert.assertEquals(ur.userRegisterHandler(rrCor).getCode(), ErrorCode.SUCCESS.getCode());
            Assert.assertEquals(ur.userRegisterHandler(rrIncor).getCode(), ErrorCode.ACCOUNT_EMAIL_FORMAT_ERROR.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
