package com.assets.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.assets.dao.DeviceMapper;
import com.assets.dao.DeviceoutMapper;
import com.assets.dao.DevicereceiveMapper;
import com.assets.model.Device;
import com.assets.model.Deviceout;
import com.assets.model.Devicereceive;
import com.assets.model.Devicetype;
import com.assets.model.Organ;
import com.assets.service.DeviceService;
import com.assets.service.DevicereceiveService;
@Service("DevicereceiveService")
public class DevicereceiveServiceImpl implements DevicereceiveService{

	@Resource
	private DevicereceiveMapper mapper;

	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean save(Devicereceive role) {
		try {
			mapper.save(role);
			role.setStatus(4);
			mapper.setStatus(role);
			return true;
		} catch (Exception e) {
			 e.printStackTrace();
             TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚事务
             return false;
		}
		
	}

	@Override
	public boolean update(Devicereceive role) {
		// TODO Auto-generated method stub
		return mapper.update(role);
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return mapper.delete(id);
	}

	@Override
	public List<Device> getDeviceAll(int oid) {
		// TODO Auto-generated method stub
		return mapper.getDeviceAll(oid);
	}

	@Override
	public Devicereceive findById(int id) {
		// TODO Auto-generated method stub
		return mapper.findById(id);
	}

	@Override
	public List<Devicereceive> findAll(int oid) {
		// TODO Auto-generated method stub
		return mapper.findAll(oid);
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean setreurn(Devicereceive role) {
		try {
			mapper.setreurn(role);
			role=mapper.findById(role.getId());
			role.setStatus(1);
			mapper.setStatus(role);
			return true;
		} catch (Exception e) {
			 e.printStackTrace();
             TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚事务
             return false;
		}
		
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean batchDelete(String[] ids) {
		try{
			for (String id : ids){
	    		mapper.delete(Integer.valueOf(id));
	    	}
			return true;
        }catch(Exception e){
             e.printStackTrace();
             TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚事务
             return false;
        }
	}

}
