package com.javagpt.back.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javagpt.back.entity.Career;
import com.javagpt.back.mapper.CareerMapper;
import com.javagpt.back.service.CareerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zqy
 * @since 2023-07-28
 */
@Service
public class CareerServiceImpl extends ServiceImpl<CareerMapper, Career> implements CareerService {

    @Autowired
    private CareerMapper careerMapper;

    @Override
    public JSONArray getData() {
        QueryWrapper<Career> qw = new QueryWrapper<>();
        List<Career> careers = careerMapper.selectList(qw);
        JSONArray result = resolve(0, careers, null);
        return result;
    }

    public JSONArray resolve(Integer level, List<Career> careers, Integer parentId) {
        JSONArray curResult = new JSONArray();
        System.out.println(parentId + "," + level);
        for (Career career : careers) {
            JSONObject obj = new JSONObject();
            obj.put("value", career.getId());
            obj.put("label", career.getName());
            System.out.println(career.getName() + "," + career.getParentId() + "," + parentId);
//            // 根节点
            if (career.getParentId() == null && parentId == null) {
                JSONArray children = resolve(level+1, careers, career.getId());
                System.out.println(children + "," + career.getName() + "," + career.getId());
                if (children != null && children.size() > 0) {
                    System.out.println(career.getName() + " children:" + children.toJSONString());
                    obj.put("children", children);
                }
                curResult.add(obj);
            } else if (career.getParentId() != null && parentId != null && career.getParentId().equals(parentId)){
                JSONArray children = resolve(level+1, careers, career.getId());
                if (children != null && children.size() > 0) {
                    System.out.println(career.getName() + " children:" + children.toJSONString());
                    obj.put("children", children);
                }
                curResult.add(obj);
            }
        }
        return curResult;
    }
}
