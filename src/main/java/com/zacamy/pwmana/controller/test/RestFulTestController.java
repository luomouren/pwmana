package com.zacamy.pwmana.controller.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zacamy.pwmana.bean.User;

/**
 * 基于Restful风格架构测试
 */
@Controller
@RequestMapping(value="/test")
public class RestFulTestController {

    /** 日志实例 */
    private static final Logger logger = Logger.getLogger(RestFulTestController.class);

    @RequestMapping(value = "/hello", produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String hello() {
        return "你好！hello";
    }

    @RequestMapping(value = "/say/{msg}", produces = "application/json;charset=UTF-8")
    public @ResponseBody
    String say(@PathVariable(value = "msg") String msg) {
        return "{\"msg\":\"you say:'" + msg + "'\"}";
    }

    @RequestMapping(value = "/person/{id:\\d+}", method = RequestMethod.GET)
    public @ResponseBody
    User getUser(@PathVariable("id") int id) {
        logger.info("获取人员信息id=" + id);
        User person = new User();
        person.setUsername("张三");
        person.setSex(true);
        person.setId(id);
        return person;
    }

    @RequestMapping(value = "/person/{id:\\d+}", method = RequestMethod.DELETE)
    public @ResponseBody
    Object deleteUser(@PathVariable("id") int id) {
        logger.info("删除人员信息id=" + id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "删除人员信息成功");
        return jsonObject;
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public @ResponseBody
    Object addUser(User person) {
        logger.info("注册人员信息成功id=" + person.getId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "注册人员信息成功");
        return jsonObject;
    }

    @RequestMapping(value = "/person", method = RequestMethod.PUT)
    public @ResponseBody
    Object updateUser( User person) {
        logger.info("更新人员信息id=" + person.getId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "更新人员信息成功");
        return jsonObject;
    }

    @RequestMapping(value = "/person", method = RequestMethod.PATCH)
    public @ResponseBody
    List<User> listUser(@RequestParam(value = "name", required = false, defaultValue = "") String name) {

        logger.info("查询人员name like " + name);
        List<User> lstUsers = new ArrayList<User>();

        User person = new User();
        
        person.setUsername("张三");
        person.setSex(true);
        
        person.setId(101);
        lstUsers.add(person);

        User person2 = new User();
        person2.setUsername("李四");
        person2.setSex(false);
        person2.setId(102);
        lstUsers.add(person2);

        User person3 = new User();
        person3.setUsername("王五");
        person3.setSex(true);
        person3.setId(103);
        lstUsers.add(person3);

        return lstUsers;
    }

}