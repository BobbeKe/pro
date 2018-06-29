package cn.e3mall.testService;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemCatService;
import cn.e3mall.service.ItemService;
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
    private ItemService itemService;

    @Autowired
    private ItemCatService itemCatService;

    @Test
    public void  testService() {
        TbItem tbItem = itemService.getItemById(536563L);
        System.out.println(tbItem.getTitle());
    }

    @Test
    public void  testitemCatService() {
        List<EasyUITreeNode> itemCatList = itemCatService.getItemCatlist(2L);
        System.out.println(itemCatList.size());
    }
}
