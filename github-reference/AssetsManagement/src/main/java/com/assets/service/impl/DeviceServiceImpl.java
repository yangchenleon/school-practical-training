package com.assets.service.impl;

import java.beans.IntrospectionException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.assets.dao.DeviceMapper;
import com.assets.model.Device;
import com.assets.model.Deviceout;
import com.assets.model.Devicetype;
import com.assets.model.ExcelBean;
import com.assets.model.Organ;
import com.assets.service.DeviceService;
import com.assets.tool.ExcelUtil;
import com.assets.tool.Utils;
@Service("DeviceService")
public class DeviceServiceImpl implements DeviceService{
	@Resource
	private DeviceMapper mapper;

	@Override
	public void save(Device role) {
		// TODO Auto-generated method stub
		mapper.save(role);
	}

	@Override
	public boolean update(Device role) {
		// TODO Auto-generated method stub
		return mapper.update(role);
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return mapper.delete(id);
	}

	@Override
	public Device findById(int id) {
		return mapper.findById(id);
	}

	@Override
	public List<Device> findAll(int oid) {
		return mapper.findAll(oid);
	}

	@Override
	public List<Device> select_brand(Device role) {
		// TODO Auto-generated method stub
		return mapper.select_core(role);
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
             TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//????????????
             return false;
        }
	}

	@Override
	public List<Organ> getOrgan() {
		// TODO Auto-generated method stub
		return mapper.findOrganALL();
	}

	@Override
	public List<Devicetype> getDeviceType() {
		// TODO Auto-generated method stub
		return mapper.findAllDtype();
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void savaout(Deviceout deviceout) {
		try{
			mapper.savaout(deviceout);
			Device d=mapper.findById(deviceout.getDid());
			d.setStatus(2);
			mapper.update(d);
        }catch(Exception e){
             e.printStackTrace();
             TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//????????????
        }
		
	}
	
	//????????
	@Transactional(rollbackFor = Exception.class)
	public boolean importExcelInfo(InputStream in, MultipartFile file, String salaryDate,int adminId,String user) {
	    List<List<Object>> listob;
		try {
			listob = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());
			List<Device> salaryList = new ArrayList<Device>();
		    //????listob????????????????List??
		    for (int i = 0; i < listob.size(); i++) {
		        List<Object> ob = listob.get(i);
		        Device salarymanage = new Device();
		        //????????
		       // salarymanage.setSerial(SerialUtil.salarySerial());
		        //??????????????????????????????model??????????????model??List????????
		       // salarymanage.setAdminId(adminId);
		        salarymanage.setDtid(Integer.valueOf(ob.get(1).toString()));
		        salarymanage.setOid(adminId);
		        salarymanage.setCode("S"+Utils.getCurrentYear()+Utils.generateShortUuid());
		        salarymanage.setResidual(String.valueOf(ob.get(7)));
		        salarymanage.setOriginal(String.valueOf(ob.get(8)));
		        salarymanage.setStatus(1);
		        salarymanage.setProddate(String.valueOf(ob.get(10)));
		        salarymanage.setCreator(user);
		        salarymanage.setCreatetime(String.valueOf(ob.get(12)));
		        salarymanage.setBuyer(String.valueOf(ob.get(13)));
		        salarymanage.setBugdate(String.valueOf(ob.get(14)));
		        salarymanage.setSno(String.valueOf(ob.get(15)));
		        salarymanage.setCrtm(String.valueOf(ob.get(16)));
		        salarymanage.setMdtm(salaryDate);
		        salaryList.add(salarymanage);
		        System.err.println(salarymanage.toString());
		        mapper.save(salarymanage);
		    }
		    return true;
		    //????????
		    //
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//????????????
			 return false;
		}
	    
	}
	
	
	//????????
	
	public XSSFWorkbook exportExcelInfo(int oid,String salaryDate) {
	    //??????????????????????????????????list??
	    List<Device> list = mapper.findAll(oid);
	    List<ExcelBean> excel=new ArrayList<>();
	    Map<Integer,List<ExcelBean>> map=new LinkedHashMap<>();
	    XSSFWorkbook xssfWorkbook=null;
	    //??????????
	    excel.add(new ExcelBean("????","did",0));
	    excel.add(new ExcelBean("????????","dtid",0));
	    excel.add(new ExcelBean("????????","oid",0));
	    excel.add(new ExcelBean("??????","code",0));
	    excel.add(new ExcelBean("????","brand",0));
	    excel.add(new ExcelBean("????????","intlcode",0));
	    excel.add(new ExcelBean("????","model",0));
	    excel.add(new ExcelBean("??????","residual",0));
	    excel.add(new ExcelBean("????","original",0));
	    excel.add(new ExcelBean("????","status",0));
	    excel.add(new ExcelBean("????????","proddate",0));
	    excel.add(new ExcelBean("??????","creator",0));
	    excel.add(new ExcelBean("????????","createtime",0));
	    excel.add(new ExcelBean("??????","buyer",0));
	    excel.add(new ExcelBean("????????","bugdate",0));
	    excel.add(new ExcelBean("??????","sno",0));
	    excel.add(new ExcelBean("????????","crtm",0));
	    excel.add(new ExcelBean("????????","mdtm",0));
	    map.put(0, excel);
	    String sheetName = salaryDate ;
	    //????ExcelUtil??????
	    try {
			xssfWorkbook = ExcelUtil.createExcelFile(Device.class, list, map, sheetName);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return xssfWorkbook;
	}
	
}
