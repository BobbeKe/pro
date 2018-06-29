package cn.e3mall.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class TestFreeMarker {

    @Test
    public void testFreeMarker() throws Exception {
        //1、创建模板文件   WEB-INF/ftl/hello.ftl
        //2、创建一个configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //3、设置模板文件保存的目录
        configuration.setDirectoryForTemplateLoading(new File("E:\\IdeaProjects\\pro\\e3-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        //4、模板文件的编码格式，一般是utf-8
        configuration.setDefaultEncoding("utf-8");
        //5、加载一个模板文件，创建一个模板对象
        Template template = configuration.getTemplate("hello.ftl");
        //6、创建一个数据集，可以是pojo，也可以是map。推荐使用map
        Map data = new HashMap<>();
        data.put("hello","hello Kobbe");
        //创建一个pojo对象
        Student student = new Student(1,"bobbe","成都");
        data.put("student",student);
        //7、创建一个Writer对象，指定输出文件的路径和文件名
        Writer out = new FileWriter(new File("E:\\JAVAfile\\freemarker\\fm_test.txt"));
        //8、生成对应发静态文件
        template.process(data,out);
        //9、关闭流
        out.close();
    }
}
