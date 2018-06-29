package cn.e3mall.solrj;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-*.xml"})
public class ServiceTest {

    @Autowired
    private SearchService searchService;

    @Test
    public void test() throws Exception {
        SearchResult searchResult = searchService.search("手机", 1, 20);
        System.out.println(searchResult.getRecordCount());
    }
}
