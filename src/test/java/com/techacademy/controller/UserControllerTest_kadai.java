package com.techacademy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.techacademy.entity.User;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserControllerTest_kadai {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        // Spring Securityを有効にする
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    @DisplayName("User一覧画面")
    @WithMockUser
    void testGetUser() throws Exception {
        // HTTPリクエストに対するレスポンスの検証
        MvcResult result = mockMvc.perform(get("/user/list")) // URLにアクセス
            .andExpect(status().isOk()) // HTTPステータスが200OKであること
            .andExpect(model().attributeExists("userlist")) // Modelにuserlistが含まれていること
            .andExpect(model().hasNoErrors()) // Modelにエラーが無いこと
            .andExpect(view().name("user/list")) // viewの名前が user/list であること
            .andReturn(); // 内容の取得

        // userlistの検証
        // Modelからuserlistを取り出す
        List<User> userList = (List<User>) result.getModelAndView().getModel().get("userlist");

     // 最初のユーザーを取得
     User user = userList.get(0);

     assertEquals(user.getId(), 1);
     assertEquals(user.getName(), "キラメキ太郎");

     // 2番目のユーザーを取得
     user = userList.get(1);

     assertEquals(user.getId(), 2);
     assertEquals(user.getName(), "キラメキ次郎");

     // 3番目のユーザーを取得
     user = userList.get(2);

     assertEquals(user.getId(), 3);
     assertEquals(user.getName(), "キラメキ花子");

    }
}
