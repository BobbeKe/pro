package cn.e3mall.testService;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.content.service.ContentCategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})
public class TestService {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @Test
    public void testcontentCategoryService() {
        List<EasyUITreeNode> contentCatList = contentCategoryService.getContentCatList(87L);
        System.out.println(contentCatList.size());
    }
}
