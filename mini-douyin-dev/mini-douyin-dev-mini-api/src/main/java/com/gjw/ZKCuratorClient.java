package com.gjw;

import com.gjw.pojo.Bgm;
import com.gjw.service.BgmService;
import org.apache.curator.framework.CuratorFramework;

import java.io.File;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import com.gjw.cofig.ResourceConfig;
import com.gjw.enums.BGMOperatorTypeEnum;
import com.gjw.utils.JsonUtils;

/**
 * Created by Administrator on 2018/6/22.
 */
public class ZKCuratorClient {

    // zk客户端
    private CuratorFramework client = null;
    final static Logger log = LoggerFactory.getLogger(ZKCuratorClient.class);

    @Autowired
    private BgmService bgmService;

    public static final String ZOOKEEPER_SERVER = "127.0.0.1:2181";

//	@Autowired
//	private ResourceConfig resourceConfig;

    public void init() {

        if (client != null) {
            return;
        }

        // 重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
        // 创建zk客户端
        client = CuratorFrameworkFactory.builder()
                .connectString(ZOOKEEPER_SERVER)
                .sessionTimeoutMs(10000).retryPolicy(retryPolicy).namespace("admin").build();
        // 启动客户端
        client.start();

        try {
//            String testNodeData = new String(client.getData().forPath("/bgm/180530DXKK4YYGTC"));
//            log.info("测试的节点数据为： {}", testNodeData);
            addChildWatch("/bgm");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addChildWatch(String nodePath) throws Exception {

        final PathChildrenCache cache = new PathChildrenCache(client, nodePath, true);
        cache.start();
        cache.getListenable().addListener(new PathChildrenCacheListener() {

            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
                    throws Exception {
                // 需要进行虚拟路径配置，这个是个坑啊
                /**
                 * 1. https://www.cnblogs.com/gmq-sh/p/6825649.html   idea如何配置虚拟路径，这里选择第一种方式
                 * 2. https://blog.csdn.net/woweipingzui/article/details/52037662  虚拟路径无法使用中文
                 */
                if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {
                    log.info("监听到事件 CHILD_ADDED");

                    // 1. 从数据库查询bgm对象，获取路径path
                    String path = event.getData().getPath();
                    String operatorObjStr = new String(event.getData().getData());
                    Map<String, String> map = JsonUtils.jsonToPojo(operatorObjStr, Map.class);
                    String operatorType = map.get("operType");
                    String songPath = map.get("path");

//					String arr[] = path.split("/");
//					String bgmId = arr[arr.length - 1];

//					Bgm bgm = bgmService.queryBgmById(bgmId);
//					if (bgm == null) {
//						return;
//					}

                    // 1.1 bgm所在的相对路径
//					String songPath = bgm.getPath();

                    // 2. 定义保存到本地的bgm路径
                    String filePath = "G:\\java程序\\mini-douyin\\upload" + songPath;
//					String filePath = resourceConfig.getFileSpace() + songPath;

                    // 3. 定义下载的路径（播放url）
                    // 对/的分隔，在java中目录的斜杠有两个，加上转义有四个
                    String arrPath[] = songPath.split("\\\\");
                    String finalPath = "";
                    // 3.1 处理url的斜杠以及编码
                    for (int i = 0; i < arrPath.length; i++) {
                        if (StringUtils.isNotBlank(arrPath[i])) {
                            finalPath += "/";
                            // 对其编码，防止中文出错
                            finalPath += URLEncoder.encode(arrPath[i], "UTF-8");
                        }
                    }
                    String bgmUrl = "http://127.0.0.1:8080/mvc" + finalPath;
//					String bgmUrl = resourceConfig.getBgmServer() + finalPath;

                    if (operatorType.equals(BGMOperatorTypeEnum.ADD.type)) {
                        // 下载bgm到spingboot服务器
                        URL url = new URL(bgmUrl);
                        File file = new File(filePath);
                        FileUtils.copyURLToFile(url, file);
                        client.delete().forPath(path);
                    } else if (operatorType.equals(BGMOperatorTypeEnum.DELETE.type)) {
                        File file = new File(filePath);
                        // 删除对应文件
                        FileUtils.forceDelete(file);
                        client.delete().forPath(path);
                    }
                }
            }
        });
    }
}
