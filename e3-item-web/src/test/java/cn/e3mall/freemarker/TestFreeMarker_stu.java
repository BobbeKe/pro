package cn.e3mall.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

public class TestFreeMarker_stu {

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
        Template template = configuration.getTemplate("student.ftl");
        //6、创建一个数据集，可以是pojo，也可以是map。推荐使用map
        Map data = new HashMap<>();


        data.put("hello","hello Kobbe");
        //创建一个pojo对象
        Student student = new Student(1,"bobbe","成都");
        data.put("student",student);
        //添加一个List
        List<Student> studentList = new ArrayList<Student>();
        studentList.add(new Student(1,"bobbe1","成都"));
        studentList.add(new Student(2,"bobbe2","武汉"));
        studentList.add(new Student(3,"bobbe3","宁波"));
        studentList.add(new Student(4,"bobbe4","北京"));
        studentList.add(new Student(5,"bobbe5","杭州"));
        data.put("studentList",studentList);

        //添加日期类型
        data.put("date",new Date());
        //空值
        data.put("val",null);
        //7、创建一个Writer对象，指定输出文件的路径和文件名
        Writer out = new FileWriter(new File("E:\\javawork\\freemarker\\fm_student.html"));
        //8、生成对应发静态文件
        template.process(data,out);
        //9、关闭流
        out.close();
    }

}
