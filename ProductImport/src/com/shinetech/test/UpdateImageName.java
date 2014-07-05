package com.shinetech.test;

import java.io.File;
import java.sql.ResultSet;

import com.shinetech.dao.DressDAO;
import com.shinetech.dao.MagentoProductDAO;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.util.Const;
import com.shinetech.util.FileOperate;

public class UpdateImageName {
	
	private DressDAO dressDAO = new DressDAO();
	private MagentoProductDAO magentoDAO = new MagentoProductDAO();
	
	public static void main(String[] args) {
		Const.initLogger();
		UpdateImageName update  = new UpdateImageName();
		update.update();
	}
	
	public void update(){
		try{
			dressDAO.getProductInfo(10,new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs) {
					long productId = 0;
					try{
						 productId= rs.getInt("id");
						int entityId = magentoDAO.getProductId(productId+"");//产品在线上的id
						System.out.println("-----------"+productId);
						System.out.println("");
						dressDAO.getImageByProId(productId, new HandleImage(entityId));
					}catch(Exception e){
						System.err.println(productId);
						e.printStackTrace();
					}
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
class HandleImage implements ResultSetHandler{
	private FileOperate op = new FileOperate();
	private MagentoProductDAO magentoDAO = new MagentoProductDAO();
	
	private int entityId = 0;
	public HandleImage(int entityId){
		this.entityId = entityId;
	}
	@Override
	public void handle(ResultSet rs) {
		try{
			final int imageId = rs.getInt("id");
			String path = rs.getString("path");
			String temp [] = path.split("/");
			String imgName = temp[temp.length -1].toLowerCase();
			magentoDAO.getMediaImageByName(imgName, entityId, new ResultSetHandler() {
				
				@Override
				public void handle(ResultSet rs2) {
					try{
						String path = rs2.getString("value");
						String fullPath = Const.CATALOG_PRODUCT+path;
						File file = new File(fullPath);
						if(file.exists()){
							String name = file.getName();
							String suffix = name.substring(name.lastIndexOf("."));
							String t = name.substring(0, name.lastIndexOf("."));
							boolean success = false;
							if(name.startsWith("t_")){
								System.out.println("thumb:"+file.getParent()+"/t_"+imageId+suffix);
								success = file.renameTo(new File(file.getParent()+"/t_"+imageId+suffix));
							}else if(name.startsWith("s_")){//是大图和小图
								System.out.println("small:"+file.getParent()+"/s_"+imageId+suffix);
								success = file.renameTo(new File(file.getParent()+"/s_"+imageId+suffix));
							}else{
								System.out.println("big:"+file.getParent()+"/"+imageId+suffix);
								success = file.renameTo(new File(file.getParent()+"/"+imageId+suffix));
							}
							String newpath = path.replace(t, imageId+"");
							System.out.println(rs2.getInt("value_id")+"\t"+newpath);
							if(success){
								magentoDAO.updateMediaImage(rs2.getInt("value_id"),newpath);
							}else{
								op.addContentToEndOfFile2("data/faild.txt", "entity_id:"+entityId+",value_id:"+rs2.getInt("value_id"));
							}
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
			magentoDAO.getVarcharImageByName(imgName, entityId, new ResultSetHandler() {
				
				@SuppressWarnings("unused")
				@Override
				public void handle(ResultSet rs2) {
					try{
						String path = rs2.getString("value");
						String fullPath = Const.CATALOG_PRODUCT+path;
						File file = new File(fullPath);
						if(file.exists()){
							boolean success = false;
							String name = file.getName();
							String suffix = name.substring(name.lastIndexOf("."));
							String t = name.substring(0, name.lastIndexOf("."));
							if(name.startsWith("t_")){
								System.out.println("thumb:"+file.getParent()+"/t_"+imageId+suffix);
								success = file.renameTo(new File(file.getParent()+"/t_"+imageId+suffix));
							}else if(name.startsWith("s_")){//是大图和小图
								System.out.println("small:"+file.getParent()+"/s_"+imageId+suffix);
								success = file.renameTo(new File(file.getParent()+"/s_"+imageId+suffix));
							}else{
								System.out.println("big:"+file.getParent()+"/"+imageId+suffix);
								success = file.renameTo(new File(file.getParent()+"/"+imageId+suffix));
							}
						}
							String newpath = path.replace("", imageId+"");
							System.out.println(rs2.getInt("value_id")+"\t"+newpath);
//							magentoDAO.updateVarcharImage(rs2.getInt("value_id"),newpath);
							magentoDAO.updateMediaImage(rs2.getInt("value_id"),newpath);
						 
						
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
