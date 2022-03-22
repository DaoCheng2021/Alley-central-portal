package com.tts.cp.lib.service;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.tts.cp.lib.common.Product;
import com.tts.cp.lib.visit.bean.ConfPerform;
import com.tts.cp.lib.visit.bean.LibItemsMini;
import com.tts.cp.lib.visit.bean.User;
import com.tts.lib.utils.TextUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Alley zhao created on 2021/8/31.
 */
//所有java方法的单元测试，一些基本方法
@Slf4j
public class DemoTest_01 {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");

    // 静态类变量和类变量会自动设置初始值，用final修饰的类变量或者局部变量必须手动赋值。
    static int in;

    private final ArrayList arrayList = new ArrayList<String>();

    @Test
    public void test01(){

    }

    @Test // HashMap常用方法
    public void test42() {
        Map<String, String> map = new HashMap();
        map.put("", "");
        Set<Map.Entry<String, String>> entries = map.entrySet(); // 遍历循环
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
        }
        map.containsKey(""); // 判断是否包含
        map.size(); // 长度
        map.get(""); //获取values
        map.remove(""); // 删除
        boolean empty = map.isEmpty(); // 判断是否为空

    }
/*
    public static void main(String[] args) {
        System.out.println("创建线程");
        Runnable runnable = () -> {
            System.out.println("创建线程1");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("创建线程2");
        };
        new Thread(runnable).start();
        System.out.println("创建线程3");
//        -----------------
        System.out.println("创建线程");
        new Thread(() -> {
            System.out.println("创建线程1");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("创建线程2");
        }).start();
        System.out.println("创建线程3");
    } */

    // 1.核心线程数（当线程池运行的线程少于CorePoolSize时，创建一个新的线程处理请求。就是最少的线程数量）2.最多可以存在的线程数据9 3.存活时间 4. 5.用于保留任务并移交给工作线程的阻塞队列
    private static ExecutorService executor = new ThreadPoolExecutor(2, 9,
            60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue(10));

    public static void main(String[] args) {
        // 创建线程池
        executor.execute(() -> {
            System.out.println("创建线程池1");
            try {
                Thread.sleep(5000);
                System.out.println("创建线程池1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executor.execute(() -> {
            System.out.println("创建线程池12");
            try {
                Thread.sleep(5000);
                System.out.println("创建线程池12");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executor.execute(() -> {
            System.out.println("创建线程池13");
            try {
                Thread.sleep(5000);
                System.out.println("创建线程池123");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }


    // 计算出String每种字符出现的次数
    @Test
    public void test41() throws IndexOutOfBoundsException {
        Object o;

        String string = "abstractTestParam";
        Map<Character, Integer> map = new HashMap<>();
        int length = string.length();
        for (int i = 0; i < length; i++) {
            char c = string.charAt(i);
            if (map.containsKey(c)) {
                Integer integer = map.get(c) + 1;
                map.put(c, integer);
            } else {
                map.put(string.charAt(i), 1);
            }
        }
        Set<Map.Entry<Character, Integer>> entries = map.entrySet();
        for (Map.Entry<Character, Integer> entry : entries) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    @Test
    public void test40() {
        List<String> list = new ArrayList<>();
        list.add("replace替换");
        list.add("toLowerCase");
        list.add("length");
        list.add("toLowerCase");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (next.equals("toLowerCase")) {
                iterator.remove();
//                break;
            }
        }
        Hashtable hashtable = new Hashtable();
        hashtable.put("a", "s");
        hashtable.put("a2", "s2");
//        LinkedHashMap
        HashMap map;
        arrayList.add("a");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ss");
        stringBuilder.append("ss2");
        String string = stringBuilder.toString();
    }

    @Test //String方法
    public void test39() {
        String string = " Length L";
        String string2 = " Length L2";
        boolean equals = string.equals("");
        boolean b = string.equalsIgnoreCase(""); //易格no儿 K斯
        int indexOf = string.indexOf("Lww"); // 返回指定字符的索引
        char charAt = string.charAt(3); // 返回指定索引的字符
        String[] gs = string.split("g"); // 根据指定字符串分割字符串
        byte[] bytes = string.getBytes(); // 返回字符串byte类型数组
        char[] chars = string.toCharArray(); // 变成数组
        String string1 = Arrays.toString(chars); // 数组变字符串
        String replace = string.replace('L', 'l'); //瑞配斯 替换指定字符
        String trim = string.trim(); // 去除字符两边的空格
        int length = string.length(); // 返回字符串的长度
        String lowerCase = string.toLowerCase(); // 咯魏 把字符串全部小写
        String upperCase = string.toUpperCase(); // 阿配 把字符串全部大写
        String substring = string.substring(2, 4); // 截取字符串
        boolean he = string.contains("HE");// 肯忒斯 判断是否包含
        int i = string.hashCode();
        int i2 = string2.hashCode();
        Vector vector = new Vector();
    }

    /*
    final 修饰类最终类，不能继承 修饰方法不能被重写（可以重载） 修饰变量是常量，必须初始化
    finally 配合try catch使用，有无异常都会走里面的代码。可以写必须要写的代码
    finalize 是Object的一个垃圾回收的方法。
    * */
    @Test
    public void test38() {
        try {
//            int i=1/0;
            System.out.println("开始");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("最终实现的代码");
        }
        System.out.println("结束");
    }

    @Test
    public void test37() {
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + " : " + Thread.currentThread().getId());
        };
    }

    @Test // 两个hashCode相等，equals相等吗
    public void test36() {
        String str1 = "通话";
        String str2 = "重地";
        System.out.println(String.format("str1：%d | str2：%d", str1.hashCode(), str2.hashCode()));
        System.out.println(str1.equals(str2));
        // 因为在散列表中，hashCode相等代表两个键值对的哈希值相等，哈希值相等并一定得出键值对相等
    }

    @Test
    public void test35() {
        StringBuffer sb = new StringBuffer();
        sb.append("2222");
        sb.insert(3, "4");
        System.out.println(sb);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ss");
        List list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("b");
        Set set = new HashSet(list);
        System.out.println();
        List list1 = new ArrayList(set);
        list1.add("b");
    }

    /*
     * 乐观锁实现:主要用到了AtomicInteger来实现了乐观锁，底层用的是CAS机制实现
     * CAS：主要包含了三个操作数->内存位置，预期原值和新值。把内存位置的值和预期原值比较，如果相匹配，处理器自动会把新值放到内存位置上，如果不匹配，不做任何操作。
     * */
//    private static volatile int a = 0;
//    public static void main(String[] args) {
//        AtomicInteger a = new AtomicInteger();
//        Thread[] thread = new Thread[5];
//        for (int i = 0; i < 5; i++) {
//            thread[i] = new Thread(() -> {
//                for (int y = 0; y < 10; y++) {
//                    System.out.println(a.incrementAndGet());
////                    System.out.println(a++);
//                    /*
//                     * 不用AtomicInteger的乐观锁->a++做了三件事情
//                     * 1.从主存中读取a的值
//                     * 2.对a进行加1的操作
//                     * 3.把加1后的a重新刷新到主存中
//                     * 但是刷新之前别的线程已经改变了主存的值，那么就会造成数据错误
//                     * */
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            thread[i].start();
//        }
//    }

    @Test
    public void test33() {
        String string = "111";
        //字符串转int类型方法
        int i = Integer.valueOf(string).intValue();
        int userId = Integer.parseInt(string);
        System.out.println(i + userId);
    }

    @Test // 取json字符串类型的数据
    public void test32() {
        String string = "{\n" +
                "\t\"limitPicklistScoringByStandardBizLevel\": false,\n" +
                "\t\"Picklist\": {\n" +
                "\t\t\"fields\": {\n" +
                "\t\t\t\"desc0\": true,\n" +
                "\t\t\t\"desc1\": true,\n" +
                "\t\t\t\"desc2\": true,\n" +
                "\t\t\t\"answerType\": true,\n" +
                "\t\t\t\"scoring\": true,\n" +
                "\t\t\t\"bizType\": true,\n" +
                "\t\t\t\"photoRequired\": true,\n" +
                "\t\t\t\"photoCommentRequired\": true,\n" +
                "\t\t\t\"photoCommentType\": true,\n" +
                "\t\t\t\"referencePhotos\": true\n" +
                "\t\t},\n" +
                "\t\t\"branchFields\": {\n" +
                "\t\t\t\"desc0\": true,\n" +
                "\t\t\t\"desc1\": true,\n" +
                "\t\t\t\"desc2\": true\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"enableAppliedScopeSelection\": true,\n" +
                "\t\"enableDataBranch\": false\n" +
                "}";
        JSONObject jsonObject = JSONObject.fromObject(string);
        String enable = jsonObject.getString("enableAppliedScopeSelection");
    }

    @Test
    public void test31() {
        int i = 5;
//        while (i > 1) {
//            System.out.println(i);
//            i--;
//        }
        do {   // do while 循环，先执行do里面的代码，然后再进行判断。也就是说不管怎么样都会至少执行一次do里面的代码
            System.out.println(i);
            i--;
            if (i == 3) {
                continue;
            }
        } while (i > 1);
    }

    @Test // 手写冒泡排序，最大的数字放到最后面，对比一次之后每次对比的数据从最末尾的数据往前进一位（nums.length;--）
    public void test30() {
        int nums[] = {1, 34, 56, 8, -32, 7, -9, 236, 235};
        for (int y = 0; y < nums.length; y++) {
            for (int x = 1; x < nums.length - y; x++) {
                if (nums[x - 1] > nums[x]) { // 降序和升序换一下大于小于
//                if (nums[x - 1] < nums[x]) {
                    int temporary = nums[x];
                    nums[x] = nums[x - 1];
                    nums[x - 1] = temporary;
                }
            }
        }
        System.out.println(Arrays.toString(nums));
    }

    @Test
    public void test29() {
        Map<String, Object> map = new HashMap<>();
        //声明类型和方法
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String token = JWT.create()
                .withHeader(map)//头
                .withClaim("SIGN_ID", "signId")//用户id
                .withClaim("LOG_IN_DATE", "logInDate")//登录日期
                .sign(Algorithm.HMAC256("aweasadsadasd"));//签名
        System.out.println("-----token:" + token);
        String s = this.analysisToken(token);
        System.out.println(s);
    }

    //解析token
    private String analysisToken(String token) {
        log.info("AnalysisToken Token:{}", token);
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("aweasadsadasd")).build();
        DecodedJWT jwt;
        try {
            jwt = verifier.verify(token);
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
        Map<String, Claim> claims = jwt.getClaims();
        String signId = claims.get("SIGN_ID").asString();
        return signId;
    }

    @Test
    public void test28() {
        String string = "PIC0000639,PIC0000646,PIC0000645,PIC0000642,PIC0000643,PIC0000648";
        LinkedHashSet<String> imageIdSet = (LinkedHashSet<String>) StringUtils.commaDelimitedListToSet(string);
        Set<String> set = StringUtils.commaDelimitedListToSet(string);
        Set<String> imageIdSet2 = StringUtils.commaDelimitedListToSet(string);
        String[] split = string.split(",");
        Set<String> strings = new LinkedHashSet<>(Arrays.asList(split));
        if (strings.size() >= 3) {
            System.out.println("ss");
        }
        System.out.println(strings.contains("作者3"));
    }

    @Test // 带逗号的字符串，根据逗号分割成多个字符串，删除或者add再生成新的用逗号分割的的数据返回
    public void test27() {
        String string = "AUP,author,作者";
        String[] split = string.split(",");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(split));
        System.out.println("list:" + list);
        list.remove("AUP");
        String string1 = StringUtils.collectionToCommaDelimitedString(list);
        System.out.println("string1:" + string1);
    }

    @Test
    public void test26() { //Optional(阿不神闹)可选择的
        //Optional.ofNullable,如果对象是空的话，就会创建一个空的对象返回，避免了空指针异常。这样不用if判断空指针异常，代码更加的优雅
        List<String> list = null;
        List<String> list1 = new ArrayList<>();
        list1.add("Optional");
        list1.add("Optional2");
        list1.add("Optional3");
        List<String> list2 = new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>()).forEach(li -> list2.add(li));
        for (String s : list2) {
            System.out.println(s);
        }
        //list 如果null 返回空的new ArrayList();
        List<String> list3 = Optional.ofNullable(list).orElse(new ArrayList<>());
        System.out.println(list3);
        Map<String, String> map = new HashMap<>();
//        String name = map.get("name");
        String name = Optional.ofNullable(map.get("name")).orElse("");
        System.out.println(name); // 如果map.get(name)没值，就给"" 空
    }

    @Test // Stream流
    public void test25() {
        String[] string = new String[4];
        string[0] = "s";
        string[1] = "2";
        string[2] = "3";
        string[3] = "4";
        // TextUtil.underscoreName()方法是增加大写字母的下划线 pending_name
        String s2 = TextUtil.underscoreName("pendingName");
        System.out.println(s2);
        List<String> list = Arrays.asList(string); // 数组转集合，集合转数组 数据转List List转数组
        System.out.println(list);
        String[] strings = list.toArray(new String[]{});
        System.out.println(Arrays.toString(strings));

    }

    @Test
    public void test24() throws JsonProcessingException {  // @JsonProperty注解的使用
        Product product = new Product();
        product.setId(3);
        System.out.println(new ObjectMapper().writeValueAsString(product)); // 对象序列化转Json
        // Json转对象或者Map
        Product product1 = new ObjectMapper().readValue("{\"num\":0,\"price\":null,\"name\":null,\"category\":null,\"IdId\":3}", Product.class);
        Map map = new ObjectMapper().readValue("{\"num\":0,\"price\":null,\"name\":null,\"category\":null,\"IdId\":3}", Map.class);
        System.out.println(map.get("num"));
        System.out.println(product1);
    }

    @Test
    public void test23() throws JsonProcessingException {
        Product prod1 = new Product(1, 1, new BigDecimal("15.5"), "面包", "零食");
        Product prod2 = new Product(2, 2, new BigDecimal("20"), "饼干", "零食");
        Product prod3 = new Product(3, 3, new BigDecimal("30"), "月饼", "零食");
        Product prod4 = new Product(4, 3, new BigDecimal("10"), "青岛啤酒", "啤酒");
        Product prod5 = new Product(5, 10, new BigDecimal("15"), "百威啤酒", "啤酒");
        List<Product> prodList = Lists.newArrayList(prod1, prod2, prod3, prod4, prod5);
        Map<Integer, List<Product>> collect = prodList.stream().collect(Collectors.groupingBy(Product::getNum));
        System.out.println(collect);
        Map<String, List<Product>> collect1 = prodList.stream().collect(Collectors.groupingBy(item -> item.getCategory() + "_" + item.getName()));
        System.out.println(collect1);
        Map<String, List<Product>> collect2 = prodList.stream().collect(Collectors.groupingBy(item -> {
            if (item.getNum() > 3) {
                return "num大于3";
            } else {
                return "num小于3";
            }
        }));
        Map<String, Map<String, List<Product>>> collect3 = prodList.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.groupingBy(item -> {
            if (item.getNum() > 3) {
                return "num大于3";
            } else {
                return "num小于3";
            }
        })));
        System.out.println(new ObjectMapper().writeValueAsString(collect3));
        Map<String, Long> collect4 = prodList.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));
        System.out.println(collect4);
        Map<String, Integer> collect5 = prodList.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.summingInt(Product::getNum)));
        System.out.println(collect5);
        System.out.println(new ObjectMapper().writeValueAsString("{\"啤酒\":{\"category\":\"啤酒\",\"id\":5,\"name\":\"百威啤酒\",\"num\":10,\"price\":15},\"零食\":{\"category\":\"零食\",\"id\":3,\"name\":\"月饼\",\"num\":3,\"price\":30}}"));

    }

    @Test
    public void test22() {

        List<String> items =
                Arrays.asList("apple", "apple", "banana",
                        "apple", "orange", "banana", "papaya");

        Map<String, Long> result =
                items.stream().collect(
                        Collectors.groupingBy(
                                Function.identity(), Collectors.counting()
                        )
                );
        Map<String, Long> collect = items.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        System.out.println(result);
    }

    @Test
    public void test21() {
        Map<String, Object> map = new HashMap<>();
        map.put("a,", "a");
        map.put("a2,", "a");
        map.put("a3,", "a");
        map.put("a,4", "a");
        System.out.println(map.size());


    }

    @Test
    public void test20() {
        List<String> list = new ArrayList<>();
        list.add("bb");
        list.add("aa");
        list.add("aa");
        list.add("cc");
        System.out.println("------" + list.size());
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == "aa") {
                list.remove(i--);
            }
        }
    }

    @Test
    public void test19() { //List转String
        Integer[] arr = {1, 2, 3};
        List<Integer> arrList = Arrays.asList(arr); //数组变成list集合
        System.out.println(arrList);

        List<String> list = new ArrayList<>();
        list.add("AA");
        list.add("bb");
        list.add("bb");
        list.add("cc");
        boolean cc = list.remove("cc2");

        String string1 = StringUtils.collectionToCommaDelimitedString(list); //List集合转成String逗号相连用这个
        String string2 = StringUtils.collectionToDelimitedString(list, "。");
        String string3 = StringUtils.collectionToDelimitedString(list, "。", "前缀prefix", "后缀suffix");
        System.out.println(string1);
        System.out.println(list.toString());
        String string = list.toString();
        System.out.println(string.substring(1, string.length() - 1).replaceAll("\\s", ""));
    }

    @Test
    public void test18() {

        switch ("a") {
            case "a":
                System.out.println("aaa");
                break;
            case "b":
                System.out.println("nbbb");
                break;
        }
    }

    @Test
    public void test17() { //list判空
        List<LibItemsMini> list = null;
//        List<LibItemsMini> list= new ArrayList<>();
        if (list.isEmpty()) {
            System.out.println("空");
        } else {
            System.out.println("有值");
        }

    }

    @Test
    public void test16() {
        List<String> list = new ArrayList<>();
        list.add("当你觉得为时已晚，恰恰是最早的时候");
        list.add("二十一天就会养成一种习惯");
        list.add("比亚迪唐，Build Your Dream");
        String s = writeDataToFile(list);
        System.out.println(s);
    }

    File[] files = {new File("E\\text/txt")};

    //这个地址的txt文件如果不存在就删除，如果存在就先清除原有数据，再导入新的数据
    public String writeDataToFile(List<String> list) {
        File file;
        BufferedWriter bw;
        try {
            file = new File("E:\\pdf\\churchroutine\\email\\export\\AttachmentDate.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            fw.write("");
            fw.flush();
            bw = new BufferedWriter(fw);
            for (String str : list) {
                fw.write(str + "\r\n");
            }
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        return "E:\\test.txt";
    }

    @Test
    public void test15() {
        File file = new File("E:\\pdf\\churchroutine\\email\\export\\AttachmentDate.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test14() {
        //1618864583000 毫秒值转时间
        long milli = 1618864583000L;
        Date date = new Date();
        date.setTime(milli);
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-ss HH:mm:ss");
        String format = sdf.format(date);
        System.out.println(format);
        System.out.println(date.toString());
        System.out.println(date);
        try {
            Date parse = sdf.parse("1997-12-07 00:00:00");
            System.out.println(parse);
            System.out.println(parse.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test // JSON 转字符串
    public void test13() {
        System.out.println(String.valueOf(1));
        System.out.println(!StringUtils.hasText(""));
        String jsonStr = "{\"word\":\"这里是json串\"}";
        Map<String, String> map = JSON.parseObject(jsonStr, Map.class);
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            System.out.println(stringStringEntry.getKey() + ":" + stringStringEntry.getValue());
        }
    }

    @Test
    public void test11() {
        System.out.println(" sad wA ".replaceAll("\\s", "").toLowerCase());//去空格，全小写
        System.out.println(" sad wA ".replaceAll("\\s", "").toUpperCase());//全大写
        System.out.println("========");
        //判空，StringUtils.hasText("") 如果是空就是false，如果有值就是true
        if (StringUtils.hasText("  a")) {
            System.out.println("不是空");
        } else {
            System.out.println("是空");
        }
        //&& & 双于和单于对结果没有区别，都是有false就是false。只是双于短路判断
        //|| | 双货和单货对结果没有区别，都是有true就是true。只是双于短路判断
    }

    @Test
    public void test10() {
        //关于List<User>循环遍历取值的问题，List.for循环set每个User的值只会set最后一条的数据
        ConfPerform confPerformParentA = new ConfPerform();
        confPerformParentA.setTemplateId("parent A");
        confPerformParentA.setPerformType("Parent perform A");

        ConfPerform confPerformParentB = new ConfPerform();
        confPerformParentB.setTemplateId("parent B");
        confPerformParentB.setPerformType("Parent perform B");

        String st = "test";
        List<ConfPerform> list = new ArrayList<>();
        list.add(confPerformParentB);
//        list.add(confPerformParentB);
        list.add(confPerformParentA);
        for (ConfPerform confPerform : list) {
            confPerform.setName(st);
            st = st + st;
            System.out.println("引用类型变量的赋值只复制对象的引用：" + confPerform.hashCode() + "=====" + confPerform);
        }//List集合的数据永远会是最后一个set进去的数据
        //原因：引用类型变量的赋值只能复制对象的引用，而不复制对象本身。而将一个值类型赋值给另一个值类型变量时，将复制包含的值
    }

    @Test // String根据逗号转集合的方法
    public void test09() throws Exception { //String数据根据 ， 分割成数组或者Set集合
        String[] strings = StringUtils.commaDelimitedListToStringArray("AUP,EUP,LCP,AJP");
        Set<String> set = StringUtils.commaDelimitedListToSet("AUP,EUP,LCP,AJP");
        String[] split = "AUP,EUP,LCP,AJP".split(","); //
        for (int i = 0; i < strings.length; i++) {
            System.out.println(strings[i]);
        }
        for (int i = 0; i < split.length; i++) {
            System.out.println(strings[i]);
        }
        List<String> list = Arrays.asList(split);
        System.out.println(list); // Set转List集合

        List list1 = new ArrayList(list); // list这玩意好像不是list，需要转义一次成list
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i).equals("EUP")) {
                list1.remove(i);
                i--;
            }
        }
        System.out.println("--" + list1.toString() + "--");
        String listString = list1.toString();
        System.out.println(listString.substring(1, listString.length() - 1)); // List集合转toString再去掉两边的中括号
        List<String> listDistinct = new ArrayList<>(); //List去重
        listDistinct.add("saa");
        listDistinct.add("saaa");
        listDistinct.add("saaa");
        listDistinct.add("saaa");
        listDistinct.add("saaaaaa");
        listDistinct.add("saaaaaa");
        List<String> collect = listDistinct.stream().distinct().collect(Collectors.toList());
        System.out.println("java8 stream进行去重：" + collect);
    }

    @Test
    public void test08() {
        //map遍历 iterator迭代器
        Map<String, String> map = new HashMap<>();
        map.put("1", "a");
        map.put("2", "c");
        map.put("3", "b");
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            System.out.println(stringStringEntry.getKey() + "---" + stringStringEntry.getValue());
        }
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            System.out.println(next.getKey() + "---" + next.getValue());
        }
    }

    @Test
    public void test07() {
        List<User> list = new ArrayList<>();
        list.add(new User("a", "a", 1));
        list.add(new User("b", "b", 2));
        list.add(new User("c", "c", 3));
        list.stream().forEach(user -> System.out.println(user.getId()));
        list.stream().map(user -> user.getId()).forEach(user -> System.out.println(user));
        list.stream().forEach(n -> System.out.println(n));
        for (User user : list) {
            System.out.println(user);
        }

    }

    @Test
    public void Md5Demo() {
        String str = "123456";
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println("MD5加密：------");
        System.out.println(hexString.toString());
    }

    @Test//时间转换+取当前毫秒值+获取绝对值Math.abs()
    public void test06() throws Exception {
        long l = System.currentTimeMillis();
        System.out.println(l); //获取当前毫秒值
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss:SSS");
        Thread.sleep(4000);//暂停4秒钟
        String format = sdf.format(new Date());
        System.out.println(format);
        long time = sdf.parse(format).getTime();
        System.out.println(time);//获取传入的String类型时间的毫秒值
        System.out.println(Math.abs(l - time));// 取绝对值
    }

    //判断本地地址是否有文件
    @Test
    public void test05() {
        File file = new File("E:/RuanJian1110/各种中文软件/111.png");
        if (!file.exists() && !file.isDirectory()) {
            System.out.println("没有图片");
        } else {
            System.out.println("有图片");
        }
    }

    @Test
    public void test04() {
        List<String> list = new ArrayList();
        list.add("a");
        list.add("a2");
        list.add("a3");
        for (String string : list) {

            for (String s : list) {
                if (s.equalsIgnoreCase("a2")) {
//                    break;//直接结束整个for循环
                    continue; //只是结束这层循环，执行下一层循环
                }
            }

        }
//        String str = "http://10.0.0..29:8080/recs/verify/tokan=%1$s=====%2$s---------%3$s";
//        System.out.println(String.format(str, "数据1", "数据2", "数据3")); //format的使用 %3$s
//        System.out.println(switchTest("phi"));
//        System.out.println(Math.abs(-3)); //负数返回绝对值，正数不变
        /*
         * boolean和Boolean的区别：boolean是基本数据类型，存在于栈中，只用true和false。Boolean是包装类型，存在于堆中，有true和false还有null
         八大基本类型：1.byte 2.short 3.int 4.long 5.float 6.double 7.char 8 boolean
         包装类型：Byte Short Integer Long Float Double Character Boolean
         整数(字节)：Byte/8 Short/16 Int/32 Long/64 浮点：Float/32 Double/64 布尔：Boolean/1 字符类型：Char/16
         String被final修饰过，所以不能被继承。Integer、Character等包装类型也不能被继承
         * */
    }

    public String switchTest(String brand) { //根据条件获取
        switch (brand) {
            case "ace":
                return "1";
            case "phi":
                return "2";
            default:
                return "3";
        }
    }

//    public static void main(String[] args) {
//        //互斥锁 synchronized关键字。作用就是为了多个线程之间访问资源的同步性，synchronized关键字修饰的方法代码块在任何时候只能有一个线程执行
//        DemoTest_01 test02 = new DemoTest_01();
//        Runnable runnable1 = () -> test02.Demo1();
//        Runnable runnable2 = () -> test02.Demo2();
//        new Thread(runnable1).start();
//        new Thread(runnable2).start();
//    }
//
//    public synchronized void Demo1() {
//        System.out.println("synchronized 1 运行");
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("synchronized 1 结束");
//    }
//
//    public synchronized void Demo2() {
//        System.out.println("synchronized 2 运行");
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("synchronized 2 结束");
//    }

    @Test//
    public void test02() {
        String substring = ",ww a qw w".substring(1);//删除对应下标的字符
        System.out.println(substring.substring(1, substring.length() - 1));//删除第一个字符和最后一个
        System.out.println(substring.replace(" ", ""));//把字符串里第一个字符替换成第二个字符
        System.out.println(substring.replaceAll("\\s", ""));//去空格
        System.out.println("大写" + substring.toUpperCase()); // 大写
        System.out.println("小写" + substring.toLowerCase()); // 小写
        System.out.println(substring);
        String string = "AAP,ACC,ACB,TES,BCD,CCE";
        String[] split = string.split(",");
        List<String> temporaryList = Arrays.asList(split);//这里的list不是真的list集合，按照list集合操作会报错。需要再转义一次
        List<String> list = new ArrayList<>(temporaryList);
        list.remove("ACC");
        String string1 = StringUtils.collectionToCommaDelimitedString(list);//把集合数据通过 ，相连变成字符串
        System.out.println();
    }

}
