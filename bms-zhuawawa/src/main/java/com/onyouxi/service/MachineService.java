package com.onyouxi.service;

import com.onyouxi.model.dbModel.MachineModel;
import com.onyouxi.repository.manager.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by administrator on 2017/8/21.
 */
@Service
public class MachineService {

    @Autowired
    private MachineRepository machineRepository;

    public Page<MachineModel> findAll(Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(page, size, sort);
        return machineRepository.findAll(pageRequest);
    }

    public MachineModel save(MachineModel machineModel){
        return machineRepository.insert(machineModel);
    }

    public void del(String id){
        machineRepository.delete(id);
    }

    public void update(MachineModel machineModel){
        machineRepository.save(machineModel);
    }

    public void updateStatus(String id,Integer status){
        MachineModel machineModel = machineRepository.findOne(id);
        if( null != machineModel){
            machineModel.setStatus(status);
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
        return  machineRepository.findOne(id);
    }


}
