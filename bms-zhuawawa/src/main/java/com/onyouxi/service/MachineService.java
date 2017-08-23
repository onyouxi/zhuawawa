package com.onyouxi.service;

import com.onyouxi.model.dbModel.MachineModel;
import com.onyouxi.model.pageModel.MachineResult;
import com.onyouxi.repository.manager.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by administrator on 2017/8/21.
 */
@Service
public class MachineService {

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private WechatUserService wechatUserService;

    private String[] letter = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    private String[] number = {"0","1","2","3","4","5","6","7","8","9"};

    private String getCode(){
        Date now = new Date();
        Random r = new Random(now.toString().hashCode());
        int maxLetter = r.nextInt(20);
        List<String> temp = new ArrayList<String>();
        for( int i=maxLetter;i<maxLetter+6;i++){
            temp.add(letter[i]);
        }
        int maxNum = r.nextInt(5);
        for( int i=maxNum;i<maxNum+6;i++){
            temp.add(number[i]);
        }
        Collections.shuffle(temp);
        String c = String.valueOf(Math.abs(now.toString().hashCode()));
        int subC = r.nextInt(4);
        c = c.substring(subC,subC+6);
        String code = "";
        for( int i=0;i<c.length();i++){
            Integer d = Integer.valueOf(c.substring(i,i+1));
            code += temp.get(d);
        }
        return code;
    }

    public Page<MachineModel> findAll(Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(page, size, sort);
        return machineRepository.findAll(pageRequest);
    }

    public MachineModel save(MachineModel machineModel){
        if( null == machineModel){
            return null;
        }
        machineModel.setCanUse(1);
        machineModel.setStatus(0);
        machineModel.setCode(getCode());
        return machineRepository.insert(machineModel);
    }

    public void del(String id){
        machineRepository.delete(id);
    }

    public void update(MachineModel machineModel){
        if( null != machineModel){
            MachineModel oldMachineModel = this.findById(machineModel.getId());
            if( null != oldMachineModel){
                oldMachineModel.setName(machineModel.getName());
                oldMachineModel.setDes(machineModel.getDes());
                machineRepository.save(oldMachineModel);
            }
        }
    }

    public void updateStatus(String id,Integer status,String wechatId){
        MachineModel machineModel = machineRepository.findOne(id);
        if( null != machineModel){
            machineModel.setStatus(status);
            machineModel.setCurrentWechatId(wechatId);
            machineRepository.save(machineModel);
        }
    }

    public void updateCanUse(String id,Integer canUse){
        MachineModel machineModel = machineRepository.findOne(id);
        if( null != machineModel){
            machineModel.setCanUse(canUse);
            machineRepository.save(machineModel);
        }
    }

    public MachineModel findById(String id){
        if(StringUtils.isEmpty(id)){
            return null;
        }
        return  machineRepository.findOne(id);
    }

    public MachineResult findByMachineId(String machineId){
        MachineResult machineResult = new MachineResult();
        MachineModel machineModel = this.findById(machineId);
        machineResult.setMachineModel(machineModel);
        if( !StringUtils.isEmpty(machineModel.getCurrentWechatId())){
            machineResult.setWechatUserModel(wechatUserService.findById(machineModel.getCurrentWechatId()));
        }
        return machineResult;
    }


}
