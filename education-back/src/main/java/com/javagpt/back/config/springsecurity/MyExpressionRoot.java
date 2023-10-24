package com.javagpt.back.config.springsecurity;

import com.javagpt.back.entity.UserEntity;
import com.javagpt.back.mapper.UserMapper;
import com.javagpt.back.service.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author 千祎来了
 * @date 2023/9/24 19:26
 */
@Component("ss") // SpringSecurity 缩写
public class MyExpressionRoot {

    private final Logger logger = LoggerFactory.getLogger(MyExpressionRoot.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleService userRoleService;

    public boolean hasRole(String roles){
        List<String> needRoles = Arrays.asList(roles.split(","));
        //获取当前用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            if (authentication.getPrincipal() instanceof String) {
                String  principal = (String) authentication.getPrincipal();
                if (principal.equals("anonymousUser")) {
                    /**
                     * 对于未登录用户，也给设置一个token，要不然异常处理类捕获不到未授权异常
                     */
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken("",null,null);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    return false;
                }
            }
            /**
             * 如果用户未登录，则 authentication.getPrincipal 值为 anonymousUser ，不能转为 userId，那么直接拒绝就行
             */
            Integer userId = (Integer) authentication.getPrincipal();
            UserEntity userEntity = userMapper.selectById(userId);
            if (userEntity == null) {
                return false;
            }
            List<String> rolePermissions = userRoleService.getByUserId(userEntity.getId());
            logger.info("当前用户角色：{}", rolePermissions.toString());
            logger.info("所需用户角色：{}", needRoles.toString());
            boolean permit = false;
            /**
             * 遍历用户当前角色，只要有一个角色在所需用户角色中，即可通过授权
             */
            for (String rolePermission : rolePermissions) {
                if (needRoles.contains(rolePermission)) {
                    permit = true;
                    break;
                }
            }
            return permit;
        } catch (Exception e){
            return false;
        }
    }
}
