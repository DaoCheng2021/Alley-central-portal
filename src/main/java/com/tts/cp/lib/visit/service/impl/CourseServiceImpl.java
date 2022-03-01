package com.tts.cp.lib.visit.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tts.cp.lib.common.RedisUtil;
import com.tts.cp.lib.visit.bean.LibItemsMini;
import com.tts.cp.lib.visit.bean.SpValidation;
import com.tts.cp.lib.visit.bean.UserBrandCountry;
import com.tts.cp.lib.visit.dao.CourseDAO;
import com.tts.cp.lib.visit.dao.LibItemsMiniRepository;
import com.tts.cp.lib.visit.dao.UserBrandCountryRepository;
import com.tts.cp.lib.visit.service.CourseService;
import com.tts.lib.web.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Alley zhao created on 2021/9/3.
 */

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private UserBrandCountryRepository userBrandCountryRepository;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LibItemsMiniRepository libItemsMiniRepository;

    @Autowired
    private RedisTemplate redisTemplate;

//    @Scheduled(cron = "0 1/1 * * * *")
//    private void test(){
//        int i =0;
//        System.out.println("第几次运行"+i);
//        i++;
//    }

    @Override
    public StandardResponse getSpTestAlley3(String versionId) {
        log.info("getSpTestAlley3:{}", versionId);
        List<String> list = courseDAO.getSpTestAlley3(versionId);
        StandardResponse sr = new StandardResponse<>();
        sr.setData(list);
        return sr;
    }

    @Override
    public StandardResponse getSpTestAlley3_old(String versionId) {
        log.info("getSpTestAlley3_old:{}", versionId);
        Map map = courseDAO.getSpTestAlley3_old(versionId);
        StandardResponse sr = new StandardResponse<>();
        sr.setData(map);
        return sr;
    }

    //    @Transactional(propagation = Propagation.REQUIRED)//如果当前存在事务，加入该事务，如果当前不存在事务，则创建一个新的事务。
    //一下三种propagation配置是可以造成事务不会回滚。事务失效是因为配置错误
//    @Transactional(propagation = Propagation.NOT_SUPPORTED) //给整个类加事务这个方法不用加事务
//    @Transactional(propagation = Propagation.SUPPORTS)//如果当前存在事务，则加入该事务；如果当前不存在事务，则以非事务的方式继续运行
//    @Transactional(propagation = Propagation.NEVER)//以非事务方式进行，如果当前存在事务，则抛出异常
//    @Transactional(propagation = Propagation.NEVER,rollbackFor =IOException.class)//以非事务方式进行，如果当前存在事务，则抛出异常
//    @Transactional(rollbackFor = EOFException.class)
    @Transactional
    @Override
    public StandardResponse getAvailableMarketsByUserAndBrand(String userId, String brand) {
        log.info("getAvailableMarketsByUserAndBrand:{}", userId, brand);
        List<UserBrandCountry> userBrandList = userBrandCountryRepository.getUserBrandList(userId, brand);
        Map<String, List<UserBrandCountry>> collect = userBrandList.stream().collect(Collectors.groupingBy(UserBrandCountry::getBmu));
        //groupingBy 根据Bmu分类，组装成map数据
        List list = new ArrayList();
        collect.forEach((k, y) -> {
            Map map = new HashMap();
            map.put("name", k);
            map.put("bmu", k);
            map.put("items", y);
            map.put("enable", false);
            list.add(map);
        });
        StandardResponse sr = new StandardResponse();
        sr.setData(list);
        return sr;
    }

    @Override
    public StandardResponse getSpValidation(String templateId, String validationId, String vGroup) {
        log.info("--getSpValidation:{},{},{}", templateId, validationId, vGroup);
        List<SpValidation> validationList = courseDAO.getSpValidation(templateId, validationId, vGroup);
        LinkedHashMap<String, List<SpValidation>> collect = validationList.stream().collect(Collectors.groupingBy(SpValidation::getSubVGroup, LinkedHashMap::new, Collectors.toList()));
        Map<String, List<SpValidation>> collect2 = validationList.stream().collect(Collectors.groupingBy(SpValidation::getSubVGroup));
        Map map = new HashMap();
        map.put("collect1", collect);
        map.put("collect2", collect2);
        StandardResponse sr = new StandardResponse();
        sr.setData(map);
        return sr;
    }


    /*
     * 根据id去redis取values数据，如果没有就去数据库拿，再顺便给redis赋值。方便下次redis拿数据
     * */
    @Override
    public StandardResponse getRedisData(String versionId, String itemId) {
        log.info("getRedisData:{},{}", versionId, itemId);
        StandardResponse sr = new StandardResponse();
        String redisId = versionId + ":" + itemId;
        String valueJSON = (String) redisUtil.get(redisId);
        if (null == valueJSON || valueJSON.isEmpty()) {
            LibItemsMini libItemsMini = libItemsMiniRepository.findByVersionIdAndItemId("PHI.MST.T1129.INFO.3B238794B0", "INF0000001");
            try {
                String libItemsMiniJSON = new ObjectMapper().writeValueAsString(libItemsMini);
                boolean set = redisUtil.set(redisId, libItemsMiniJSON);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            sr.setData(libItemsMini);
            return sr;
        } else {
            LibItemsMini libItemsMini = null;
            try {
                libItemsMini = new ObjectMapper().readValue(valueJSON, LibItemsMini.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            sr.setData(libItemsMini);
            return sr;
        }
    }

    /*
     *   这条id的数据先给数据库更新之后，再更新同一个id的redis数据
     * */
    @Override
    public StandardResponse updateRedisData(String versionId, String itemId) {
        log.info("--updateRedisData:{},{}", versionId, itemId);
        LibItemsMini libItemsMini = libItemsMiniRepository.findByVersionIdAndItemId(versionId, itemId);
        libItemsMini.setItemId("newItemId");
        libItemsMini.setVersionId("newVersionId"); //假装给数据库更新的数据
        String libItemsMiniJSON = null;
        try {
            libItemsMiniJSON = new ObjectMapper().writeValueAsString(libItemsMini);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        boolean set = redisUtil.set(versionId + ":" + itemId, libItemsMiniJSON);
        return null;
    }

    /*
     * 点赞功能
     * redis结构 key(UPId 点赞的文章的作者id) : value( HashMap(String(文章的id),Set(给这篇文章点赞用户的id))
     * 先取出老的数据，如果没有新建再存进去
     * 有老数据把点赞用户id加进去，再存进redis
     * */
    @Override
    public StandardResponse addPraise(String articleId, String userId, String authorId) {
        log.info("--addPraise:{},{}", articleId, userId);
        String addAuthorId = "AddPraise:" + authorId; // authorId
        String praiseAllKey = "praiseAll:" + authorId;   // author获取总赞数的Id
        double praiseCount = 0; // author总赞数
        Set resultSet = (Set) redisUtil.hget(addAuthorId, articleId);
        if (null == resultSet) {
            Set<String> valueSet = new HashSet<>();
            valueSet.add(userId);
            redisUtil.hset(addAuthorId, articleId, valueSet);
            praiseCount = redisUtil.hincr(addAuthorId, praiseAllKey, 1);
        } else {
            long count = resultSet.stream().filter(set -> set.equals(userId)).count(); // 判断这个userId给这篇文章点赞过没有
            if (count == 0) {    // 此userId没有点赞
                resultSet.add(userId);
                redisUtil.hset(addAuthorId, articleId, resultSet);
                praiseCount = redisUtil.hincr(addAuthorId, praiseAllKey, 1); // 给计总点赞数的value +1
            } else {
                praiseCount = (int) redisUtil.hget(addAuthorId, praiseAllKey);  // 此用户给这篇文章点过赞，返回这个author文章的总点赞量
            }
        }
        Map userPraiseMap = userPraiseArticle(userId, authorId, articleId);
        Set<String> finalPraise = (Set<String>) redisUtil.hget(addAuthorId, articleId);
        StandardResponse sr = new StandardResponse();
        Map<String, Object> map = new HashMap();
        map.put("finalPraise", finalPraise.size());
        map.put("praiseCount", (int) praiseCount);
        map.putAll(userPraiseMap);
        sr.setData(map);
        return sr;
    }

    @Override // cancel 点赞
    public StandardResponse cancelPraise(String userId, String articleId, String authorId) {
        log.info("--cancelPraise:{},{},{}", userId, articleId, authorId);
        String cancelAuthorId = "AddPraise:" + authorId; // authorId
        String praiseAllKey = "praiseAll:" + authorId;   // author获取总赞数的Id
        String userPraiseId = "Praise:" + userId;
        String authorOFArticle = authorId + ":" + articleId;
        Set praiseUserSet = (Set) redisUtil.hget(cancelAuthorId, articleId);
        if (praiseUserSet.size() == 1) { // 如果author此articleId只有一个人点赞，就直接删除
            redisUtil.hdel(cancelAuthorId, articleId);
        } else {
            praiseUserSet.remove(userId); // author此articleId有多人点赞，删除对应取消点赞的userId
            redisUtil.hset(cancelAuthorId, articleId, praiseUserSet);
        }
        redisUtil.hdecr(cancelAuthorId, praiseAllKey, 1); // 给此author总点赞的计数减一
        // user统计点赞的数据删除此articleId

        Set userPraiseArticleSet = (Set) redisUtil.hget(userId, userPraiseId);
        if (userPraiseArticleSet.size() == 1) {
            redisUtil.hdel(userId, userPraiseId);
        } else {
            userPraiseArticleSet.remove(authorOFArticle);
            redisUtil.hset(userId, userPraiseId, userPraiseArticleSet);
        }
        return null;
    }

    @Override // user Vote
    public String addVote(String userSoleId, String authorSoleId) {
        //先判断这个用户近十分钟投过票没有
        String userSoleIdFinal = "userDate:" + userSoleId; // 用户投票唯一id
        String authorSoleIdFinal = "authorCount:" + authorSoleId; // 作者唯一id
        if (redisUtil.hasKey(userSoleIdFinal)) {
            // 这个用户十分钟内投过票
            return "user Vote No Time To!";
        } else {
            redisUtil.incr(authorSoleIdFinal, 1);
            redisUtil.set(userSoleIdFinal, "Vote", 30);
            return "user Vote Success";
        }
    }

    @Override
    public String test(String string) {
        System.out.println("test:方法体");
        return string;
    }

    // 用户点赞的文章统计
    public Map userPraiseArticle(String userId, String authorId, String articleId) {
        log.info("--userPraiseArticle:{},{},{}", userId, authorId, articleId);
        String userPraiseId = "Praise:" + userId;
        String authorOFArticle = authorId + ":" + articleId;
        Set userPraiseArticle = (Set) redisUtil.hget(userId, userPraiseId);
        if (null == userPraiseArticle) {
            Set set = new HashSet();
            set.add(authorOFArticle);
            redisUtil.hset(userId, userPraiseId, set);
            Map map = new HashMap();
            map.put("userPraiseCount", 1);
            map.put("userPraiseArticleId", set);
            return map;
        } else {
            userPraiseArticle.add(authorOFArticle);
            redisUtil.hset(userId, userPraiseId, userPraiseArticle);
            Map map = new HashMap();
            map.put("userPraiseCount", userPraiseArticle.size());
            map.put("userPraiseArticleId", userPraiseArticle);
            return map;
        }
    }
}
