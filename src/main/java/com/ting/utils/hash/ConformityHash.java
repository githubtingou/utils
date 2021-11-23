package com.ting.utils.hash;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * 一致性hash
 *
 * @author lishuang
 * @version 1.0
 * @date 2021/11/22
 */
public class ConformityHash {


    /**
     * 服务器节点
     */
    private static final List<String> serverList = new ArrayList<String>() {{
        add("192.168.1.1:8008");
        add("192.168.1.2:8009");
        add("192.168.101.1:8008");
        add("192.168.95.1:8008");

    }};

    private static final List<String> nodeList = new ArrayList<>();

    /**
     * 虚拟节点数量
     */
    private static final int VIRTUAL_NODES_NUMBER = 10;

    /**
     * hash环
     */
    private static final TreeMap<Integer, String> hashMap = new TreeMap<>();

    static {
        for (String server : serverList) {
            nodeList.add(server);
            hashMap.put(getHash(server), server);
            for (int i = 0; i < VIRTUAL_NODES_NUMBER; i++) {
                String virtual = server + "&" + i;
                nodeList.add(virtual);
                // 添加到hash环中
                hashMap.put(getHash(virtual), virtual);
            }
        }
    }
    /**
     * 获取ip地址
     *
     * @param userId
     * @return
     */
    private static String getAddr(String userId) {

        int hash = getHash(userId);
        Integer integer = hashMap.tailMap(hash).firstKey();
        System.out.println(integer);
        String host = hashMap.get(integer);
        if (host.contains("&")) {
            host = host.substring(0, host.indexOf("&"));
        }
        return host;
    }

    /**
     * 使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别
     */
    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }

        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }

    public static void main(String[] args) {
        System.out.println(getAddr("1235465"));
    }
}
