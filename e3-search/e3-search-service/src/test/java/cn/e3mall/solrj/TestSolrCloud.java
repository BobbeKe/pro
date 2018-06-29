package cn.e3mall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrCloud {

    @Test
    public void testAddDocument() throws Exception {
        //创建一个集群的连接，应该使用CloudSolrServer创建
        CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.25.133:2182,192.168.25.133:2183,192.168.25.133:2184");
        //zkHost:zookeeper地址列表
        //设置默认的defaultCollection
        cloudSolrServer.setDefaultCollection("collection2");
        //创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        //向文档中添加域
        document.addField("item_title", "测试商品");
        document.addField("item_price", "100");
        document.addField("id", "test001");
        //把文档写入索引库
        cloudSolrServer.add(document);
        //提交
        cloudSolrServer.commit();
    }

    @Test
    //查询
    public void testQueryDocument() throws Exception {
        //创建一个CloudSolrServer对象
        CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.25.133:2182,192.168.25.133:2183,192.168.25.133:2184");
        //设置默认的collection
        cloudSolrServer.setDefaultCollection("collection2");
        //设置一个查询对象
        SolrQuery query = new SolrQuery("*:*");
        //执行查询
        QueryResponse  queryResponse= cloudSolrServer.query(query);
        //取查询结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        //打印结果
        System.out.println("总记录数"+solrDocumentList.getNumFound());
        for (SolrDocument solrDocument:solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("item_price"));
        }

    }
}
