package com.tts.cp.lib.visit.service.impl;

import com.tts.cp.lib.visit.bean.UserBrandCountry;
import com.tts.cp.lib.visit.dao.CourseDAO;
import com.tts.cp.lib.visit.dao.UserBrandCountryRepository;
import com.tts.cp.lib.visit.service.CourseService;
import com.tts.lib.web.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
}
