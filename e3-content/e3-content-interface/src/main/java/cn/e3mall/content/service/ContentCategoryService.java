package cn.e3mall.content.service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUITreeNode;

import java.util.List;

public interface ContentCategoryService {

    List<EasyUITreeNode> getContentCatList(long parentId);
    E3Result addContentCategory(Long parentId, String name);
}
