package cn.partytime.init;

import cn.partytime.service.ServerStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

@Service
public class MainService {
    @Autowired
    private ServerStartService serverStartService;
    /**
     * 启动系统加载项目
     */
    @PostConstruct
    public void init() {
        serverStartService.projectInit();
    }
}
