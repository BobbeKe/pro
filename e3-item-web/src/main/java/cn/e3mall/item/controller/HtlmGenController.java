package cn.e3mall.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成静态页面测试
 */
@Controller
public class HtlmGenController {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping("/genhtml")
    @ResponseBody
    public String genHtml() throws Exception {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        //加载模板
        Template template = configuration.getTemplate("hello.ftl");
        //创建数据集
        Map data = new HashMap<>();
        data.put("hello",123345);
        //指定文件输出路径和文件名
        FileWriter out = new FileWriter(new File("E:\\javawork\\freemarker\\fm_hello.html"));
        //输出文件
        template.process(data,out);
        //关闭流
        out.close();
        return "OK";
    }
}
