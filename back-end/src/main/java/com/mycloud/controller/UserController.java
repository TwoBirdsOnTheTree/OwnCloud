package com.mycloud.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户Controller
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    /**
     * 登录
     *
     * @param request request
     * @return 结果
     */
    @PostMapping("/login")
    public String login(HttpServletRequest request) {

        return "";
    }

}
