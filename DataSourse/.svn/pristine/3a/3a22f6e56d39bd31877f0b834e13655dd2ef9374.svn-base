package com.shinetech.sql.test;

/**
 * 功能描述: 表test对应的bean 作者:<a href="mailto:husw11@hotmail.com">husw</a> <br>
 * 版本: 1.0.0<br>
 */
public class UserBean {
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserBean) {
			UserBean userBean = (UserBean) obj;
			String username = userBean.getName();
			long userid = userBean.getId();
			String userinfo = userBean.getInfo();
			byte[] userimg = userBean.getImg();
			if (username.equals(this.name) && userid == this.id
					&& userinfo.equals(this.info)) {
				if (userimg.length == img.length) {
					for (int i = 0; i < userimg.length; i++) {
						if (userimg[i] != this.img[i]) {
							return false;
						}
					}
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return super.equals(obj);
		}
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("{");
		if (img != null) {
			for (int i = 0; i < img.length; i++) {
				buffer.append(img[i]);
				if (i < img.length - 1) {
					buffer.append(",");
				} else {
					buffer.append("}");
				}
			}
		} else {
			buffer.append("}");
		}
		return "id=" + this.id + "; name=" + this.name + "; info=" + this.info
				+ "; img=" + buffer.toString();
	}

	public UserBean() {

	}

	public UserBean(long id, String name, String info, byte[] img) {
		this.id = id;
		this.name = name;
		this.info = info;
		this.img = img;
	}

	private long id;
	private String name;
	private String info;
	private byte[] img;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

}
