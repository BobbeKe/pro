package cn.e3mall.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class JedisTest {

    @Test
    public void testJedis() throws Exception {

        //创建一个连接Jedis对象，参数：host、port
        Jedis jedis = new Jedis("192.168.25.133", 6379);
        //直接使用jedis操作redis。所有jedis的命令都对应一个方法。
        jedis.set("testdb","myfirst jedis test value");
        String db = jedis.get("testdb");
        System.out.println(db);
        //关闭连接
        jedis.close();
    }
    @Test
    public void testJedisPool() throws Exception {
        //创建连接池对象
        JedisPool jedisPool = new JedisPool("192.168.25.133", 6379);
        //从连接池中获取一个连接
        Jedis jedis = jedisPool.getResource();
        String db = jedis.get("testdb");
        System.out.println(db);
        //关闭连接
        jedis.close();
        //关闭连接
        jedisPool.close();
    }
    @Test
    public  void testJedisCluster() throws Exception {
        //JedisCluster ,有一个参数nodes 是set类型。set中包含若干个HostAndPort对象
        //直接用JedisCluster操作数据库
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("192.168.25.133",7001));
        nodes.add(new HostAndPort("192.168.25.133",7002));
        nodes.add(new HostAndPort("192.168.25.133",7003));
        nodes.add(new HostAndPort("192.168.25.133",7004));
        nodes.add(new HostAndPort("192.168.25.133",7005));
        nodes.add(new HostAndPort("192.168.25.133",7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("testdb","testvalue");
        System.out.println(jedisCluster.get("testdb"));
        jedisCluster.close();
    }
}
