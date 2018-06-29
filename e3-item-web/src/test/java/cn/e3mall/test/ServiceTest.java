package cn.e3mall.test;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/*.xml")
public class ServiceTest {
    @Autowired
    private ItemService  itemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${HTML_GEN_PATH}")
    private String  HTML_GEN_PATH;

    @Test
    public void testService() {
        TbItem tbItem = itemService.getItemById(152725824608773L);
        System.out.println(tbItem.getTitle());
    }

    @Test
    public void testFree() {
        System.out.println(freeMarkerConfigurer.getConfiguration().getDefaultEncoding());

    }

    @Test
    public void testFreeMarker() throws Exception {
        //1、创建模板文件   WEB-INF/ftl/hello.ftl
        //3、设置模板文件保存的目录
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("hello.ftl");
        //6、创建一个数据集，可以是pojo，也可以是map。推荐使用map
        TbItem tbItem = itemService.getItemById(15274303739249L);
        Item item = new Item(tbItem);
        TbItemDesc itemDesc = itemService.getItemDescById(15274303739249L);
        //创建一个数据集
        Map data = new HashMap<>();
        //将商品数据封装进去
        data.put("item",item);
        data.put("itemDesc",itemDesc);
        //7、创建一个Writer对象，指定输出文件的路径和文件名
        Writer out = new FileWriter(new File(HTML_GEN_PATH+"fm_test.html"));
        //8、生成对应发静态文件
        template.process(data,out);
        //9、关闭流
        out.close();
    }


}
