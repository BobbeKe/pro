package cn.e3mall.search.dao;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品搜索
 */
@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;

    /**
     * 根据查询条件查询
     * @param query
     * @return
     */
    public SearchResult search(SolrQuery query) throws Exception {
        //根据查询条件查询
        QueryResponse queryResponse = solrServer.query(query);
        //取得查询结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        //取总记录数
        long numFound = solrDocumentList.getNumFound();
        SearchResult result = new SearchResult();
        result.setRecordCount(numFound);
        //取商品列表，需要取高亮显示
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        List<SearchItem> itemList = new ArrayList<>();

        for (SolrDocument solrDocument:solrDocumentList) {
            SearchItem searchItem = new SearchItem();
            searchItem.setId((String)solrDocument.get("id"));
            searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
            searchItem.setImage((String) solrDocument.get("item_image"));
            searchItem.setPrice((long) solrDocument.get("item_price"));
            searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
            //取高亮显示
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String title = "";
            if (list != null && list.size() > 0) {
                title = list.get(0);
            } else {
                title = (String) solrDocument.get("item_title");
            }
            searchItem.setTitle(title);
            //添加到商品列表
            itemList.add(searchItem);
        }
        result.setItemList(itemList);
        //返回结果
        return result;
    }

}
