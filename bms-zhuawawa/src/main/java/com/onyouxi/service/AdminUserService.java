package com.onyouxi.service;

import com.onyouxi.constant.Const;
import com.onyouxi.model.dbModel.AdminUserModel;
import com.onyouxi.repository.manager.AdminUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuwei on 16/3/1.
 */
@Service
@Slf4j
public class AdminUserService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    private Map<String, AdminUserModel> adminSessionMap = Collections.synchronizedMap(new HashMap<>());


    /**
     * 创建一个管理员账号
     * @param userName
     * @param password
     * @param nick
     * @return
     */
    public AdminUserModel createAdminUser(String userName,String password,String nick){
        if( isExistUserName(userName)){
            throw new IllegalArgumentException("用户名不能重复");
        }
        if(isExistNick(nick)){
            throw new IllegalArgumentException("姓名不能重复");
        }
        AdminUserModel adminUser = new AdminUserModel();
        userName = userName.trim();
        adminUser.setUserName(userName.toLowerCase());
        //密码进行加密处理
        password = BCrypt.hashpw(password,BCrypt.gensalt());
        adminUser.setPassword(password);
        adminUser.setNick(nick);
        return save(adminUser);
    }

    public Boolean isExistUserName(String userName){
        userName = userName.trim();
        AdminUserModel adminUser = adminUserRepository.findByUserName(userName.toLowerCase());
        if( null ==adminUser){
            return false;
        }else{
            return true;
        }
    }

    public Boolean isExistNick(String nick){
        nick = nick.trim();
        AdminUserModel adminUser = adminUserRepository.findByNick(nick);
        if( null ==adminUser){
            return false;
        }else{
            return true;
        }
    }

    public AdminUserModel save(AdminUserModel adminUser){
        return  adminUserRepository.insert(adminUser);
    }

    public void deleteByUserName(String userName) {
        AdminUserModel adminUserModel = this.findByUserName(userName);
        adminUserRepository.delete(adminUserModel);
    }

    public AdminUserModel updateById(AdminUserModel adminUserModel) {

        return null;
    }

    /**
     * 用户登录
     *
     * @param userName
     * @param password
     * @return
     */
    public AdminUserModel login(String userName, String password) {
        return adminUserRepository.findByUserNameAndPassword(userName, password);
    }


    public AdminUserModel findByUserName(String userName) {
        return adminUserRepository.findByUserName(userName);
    }


    public Page<AdminUserModel> findAll(int page, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(page, size, sort);
        return adminUserRepository.findAll(pageRequest);
    }


    public void addSessionTime(String cookieValue, HttpServletResponse response) {
        String key = Const.ADMIN_USER_CACHE_KEY + cookieValue;
        AdminUserModel adminUserModel = adminSessionMap.get(key);
        Date now = new Date();
        long time = now.getTime() + 3600 * 3 * 1000;
        adminUserModel.setLastLoginTime(new Date(time));
        adminSessionMap.put(key,adminUserModel);
        Cookie authKeyCookie = new Cookie(Const.COOKIE_AUTH_KEY, cookieValue);
        authKeyCookie.setMaxAge(3600 * 3);
        authKeyCookie.setPath("/");
        response.addCookie(authKeyCookie);
    }

    public Boolean checkAuthKey(String authKey) {
        String key = Const.ADMIN_USER_CACHE_KEY + authKey;
        AdminUserModel adminUserModel = adminSessionMap.get(key);
        if (null == adminUserModel) {
            return false;
        } else {
            long a = adminUserModel.getLastLoginTime().getTime() - new Date().getTime();
            if (a > 3600 * 3 * 1000) {
                adminSessionMap.remove(key);
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 管理员登陆
     * @param userName
     * @param password
     * @param ip
     * @return
     */
    public AdminUserModel login(String userName,String password,String ip,HttpServletResponse response) throws UnsupportedEncodingException {
        AdminUserModel adminUser = adminUserRepository.findByUserName(userName);
        if( null ==adminUser){
            return null;
        }
        if( !BCrypt.checkpw(password,adminUser.getPassword())){
            return null;
        }

        String key = this.createAuthKey(adminUser);
        Cookie authKeyCookie = new Cookie(Const.COOKIE_AUTH_KEY, key);
        authKeyCookie.setMaxAge(3600*3);
        authKeyCookie.setPath("/");

        Cookie nickCookie = new Cookie(Const.COOKIE_NICK, URLEncoder.encode(adminUser.getNick(), "UTF-8") );
        nickCookie.setMaxAge(3600*24*30);
        nickCookie.setPath("/");

        response.addCookie(authKeyCookie);
        response.addCookie(nickCookie);


        return adminUser;
    }

    public String createAuthKey(AdminUserModel adminUser){
        String hashKey = BCrypt.hashpw(adminUser.getId(),BCrypt.gensalt());
        String key = Const.ADMIN_USER_CACHE_KEY+hashKey;
        adminSessionMap.put(key,adminUser);
        return hashKey;
    }

    public void logout(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if( null != cookies && cookies.length > 0){
            for(Cookie cookie : cookies){
                if(Const.COOKIE_AUTH_KEY.equals(cookie.getName())){
                    adminSessionMap.remove(Const.ADMIN_USER_CACHE_KEY+cookie.getValue());
                }
            }
        }

    }

     /*
     * 根据authKey获得管理员
     * @param authKey
     * @return
     */
    public AdminUserModel getAdminUser(String authKey){
        String key = Const.ADMIN_USER_CACHE_KEY+authKey;
        return adminSessionMap.get(key);
    }

    public AdminUserModel updateAdminInfoById(AdminUserModel adminUser){
        if( null == adminUser || StringUtils.isEmpty(adminUser.getId())){
            throw new IllegalArgumentException("对象或者主键不能为空");
        }
        AdminUserModel au = adminUserRepository.findOne(adminUser.getId());
        if( null != au){
            if(!StringUtils.isEmpty(adminUser.getNick())){
                au.setNick(adminUser.getNick());
            }
            if(!StringUtils.isEmpty(adminUser.getUserName())){
                au.setUserName(adminUser.getUserName());
            }
            if(!StringUtils.isEmpty(adminUser.getPassword())){
                String password = BCrypt.hashpw(adminUser.getPassword(),BCrypt.gensalt());
                au.setPassword(password);
            }
        }
        return adminUserRepository.save(au);
    }

    public void deleteById(String id){
        adminUserRepository.delete(id);
    }

    public Boolean checkPassword(String id,String password){
        AdminUserModel adminUser = adminUserRepository.findOne(id);
        if( null == adminUser){
            return false;
        }
        return BCrypt.checkpw(password,adminUser.getPassword());

    }

    public AdminUserModel findById(String id){
        return adminUserRepository.findOne(id);
    }

}