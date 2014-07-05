package com.shinetech.dress.tag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.shinetech.handle.ShowroomCategoryHandle;
import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;
import com.shinetech.util.Const;
import com.shinetech.util.DateUtil;
import com.shinetech.util.FileOperate;
import com.shinetech.util.FileUtil;
import com.shinetech.util.Tools;

public class GetShowroomQ {
	private int lineNo = 1;
	private PrintWriter printer = null;
	private String output = FileUtil.PathAppendEx(System.getProperty("user.dir"), "result.tsv");
	private List<ShowroomInfo> list = new ArrayList<ShowroomInfo>();
	private FileOperate op = new FileOperate();
	private boolean isCheck = false;//是否为检查阶段，在阶段生产 in.${date}.res.tsv
	private String inTsvFile;//初始处理文件
	private String outResFile;//处理后的文件
	public void setIsCheck(boolean isCheck){
		this.isCheck = isCheck;
		int index =  inTsvFile.lastIndexOf(".tsv");
		if(index > -1){
			outResFile = inTsvFile.substring(0, index);
			output = outResFile + "_result" +inTsvFile.substring(index);
			outResFile += "_new"+inTsvFile.substring(index);
			File file = new File(outResFile);
			if(file.exists()){
				String bakfile = inTsvFile.substring(0,index)+"_"+DateUtil.getToday()+"_"+System.currentTimeMillis()+inTsvFile.substring(index);
				file.renameTo(new File(bakfile));
				logger.error("输出文件："+outResFile+"已存在，将原输出文件"+outResFile+"备份为："+bakfile);
			}
			op.addContentToEndOfFile2(outResFile, "Show Name\tKeyword\tminp\tmaxp\ttag");
		}else{
			logger.error("输入文件格式不正确，请检查是否为 .tsv文件，输入的文件："+inTsvFile);
			System.exit(1);
		}
	}
	private Logger logger = Logger.getLogger(this.getClass());
	private static int PAGE_SIZE = 32;

	private List<String> filterList = new ArrayList<String>();

	//检查标签
	public static void main(String[] args) {
		Const.initLogger();
		String inTsvFile = "assets/in.tsv";
		GetShowroomQ gq = new GetShowroomQ(inTsvFile);
		gq.setIsCheck(true);
		gq.handle();
	}

	/**
	 * 目的和功能：关键词中除特殊情况（a、and、as、by、for、of、on、in、is、so、the、to、up、under、with）外，单词首字母均需大写；
	 * 注意事项：
	 * @param inTsvFile
	 */
	public GetShowroomQ(String inTsvFile) {
		this.inTsvFile = inTsvFile;
//		filterList.add("to");
//		filterList.add("for");
		filterList.addAll(Arrays.asList("a,and,as,by,for,of,on,in,is,so,the,to,up,under,with".split(",")));
	}

	public void handle() {
		// 生成一个结果集处理方法
		// 是否处理全部或指定分页数的数据
		excute(new PagingHandle(), true);
	}

	private void initOutput() {
		try {
			printer = new PrintWriter(new FileWriter(output, false), true);
			output("shown name\tkeywords\taverage benefit\tpage count\taverage score");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void output(String buf) {
		printer.println(buf);
	}

	private void closeOutput() {
		printer.flush();
		printer.close();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void excute(ResultSetHandler handle, boolean paging) {
		String filename = inTsvFile;//"assets/in.tsv";
		loadConfig(filename);
		initOutput();
		for (ShowroomInfo info : list) {
			StringBuffer mbuf = new StringBuffer();
			StringBuffer wbuf = new StringBuffer();
			int idx = 1;
			mbuf.append(" from dr_original_product a, dr_product_tag b ");
			wbuf.append(" where a.product_status = '1' and a.tag_status = '1' and a.id = b.product_id ");
			if (info.isSetPrice()) {
				if (info.getMaxPrice() > -1) {
					wbuf.append(" and 9dresses_real_price <=").append(info.getMaxPrice());
				}
				if (info.getMinPrice() > -1) {
					wbuf.append(" and 9dresses_real_price >=").append(info.getMinPrice());
				}
			}
			HashMap<String, SqlState> map = new HashMap<String, SqlState>();
			boolean isFull = false;
			for (ShowroomTag tag : info.getTagList()) {
				if (tag.isAll()) {
					isFull = true;
					break;
				}
				String p = tag.getParent();
				if (map.containsKey(p)) {
					SqlState ss = map.get(p);
					String c = ss.getC() + " or b_" + ss.getIdx() + ".tag_id = "
							+ TagAdapter.getInstance().getTag(tag.getParent(), tag.getTag(), false).getTagId();
					ss.setC(c);
					map.put(p, ss);
				} else {
					SqlState ss = new SqlState();
					ss.setIdx(idx);
					ss.setM(", dr_product_tag b_" + idx);
					ss.setU(" and a.id = b_" + idx + ".product_id");
					ss.setC("b_" + idx + ".tag_id = " + TagAdapter.getInstance().getTag(tag.getParent(), tag.getTag(), false).getTagId());
					map.put(p, ss);
					idx++;
				}
			}
			Iterator<SqlState> iter = map.values().iterator();
			while (iter.hasNext()) {
				SqlState ss = iter.next();
				mbuf.append(ss.getM());
				wbuf.append(ss.getU());
				wbuf.append(" and (").append(ss.getC()).append(")");
			}

			String countSql = "select count(distinct a.id) " + mbuf.toString() + wbuf.toString();
			String dataSql = "select * from dr_original_product where id in (select distinct id " + mbuf.toString() + wbuf.toString()
					+ " order by sort_score" + ") order by sort_score desc limit 0, ";

			if (paging) {
				dataSql += PAGE_SIZE * 5;
			}
			IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
			if (handle instanceof PagingHandle) {
				QcBean qc = new QcBean();
				try {
					qc.setPcount((Integer) db.queryFirst(Integer.class, countSql));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				((PagingHandle) handle).setInfo(qc, dataSql);
			}else if((handle instanceof ShowroomCategoryHandle)){
				((ShowroomCategoryHandle) handle).setInfo(info.getShowName());
			}
			try {
				db.handleList(dataSql, handle);
				//保存分类
				if(handle instanceof ShowroomCategoryHandle){
					((ShowroomCategoryHandle) handle).saveCategoryProduct();
				}
				
				if (isFull) {
					System.out.println("正在处理:" + info.getName() + ":" + dataSql);
				} else {
					System.out.println("正在处理:" + info.getName());
				}
				if (handle instanceof PagingHandle) {
					((PagingHandle) handle).outputData(info);
				}
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		closeOutput();
	}

	private class PagingHandle implements ResultSetHandler {
		int idx = 0;
		QcBean qc;
		@SuppressWarnings("unused")
		String sql;

		public void setInfo(QcBean bean, String sql) {
			idx = 0;
			qc = bean;
			this.sql = sql;
		}

		public void handle(ResultSet rs) {
			try {
				if (idx < PAGE_SIZE) {
					if (PAGE_SIZE < qc.getPcount()) {
						qc.setBenefit(rs.getFloat("9dresses_real_price") - rs.getFloat("cost_price") / 6, 0.6f, PAGE_SIZE);
						qc.setScore(rs.getFloat("sort_score"), 0.6f, PAGE_SIZE);
					} else {
						qc.setBenefit(rs.getFloat("9dresses_real_price") - rs.getFloat("cost_price") / 6, 1.0f, qc.getPcount());
						qc.setScore(rs.getFloat("sort_score"), 1.0f, qc.getPcount());
					}
					// System.out.println("First Page:" + qc.getBenefit() + ":"
					// + qc.getScore());
				} else if (idx < PAGE_SIZE * 2) {
					if (PAGE_SIZE < (qc.getPcount() - PAGE_SIZE)) {
						qc.setBenefit(rs.getFloat("9dresses_real_price") - rs.getFloat("cost_price") / 6, 0.3f, PAGE_SIZE);
						qc.setScore(rs.getFloat("sort_score"), 0.3f, PAGE_SIZE);
					} else {
						qc.setBenefit(rs.getFloat("9dresses_real_price") - rs.getFloat("cost_price") / 6, 0.3f, qc.getPcount() - PAGE_SIZE);
						qc.setScore(rs.getFloat("sort_score"), 0.4f, qc.getPcount() - PAGE_SIZE);
					}
					// System.out.println("Second Page:" + qc.getBenefit() + ":"
					// + qc.getScore());
				} else {
					if (PAGE_SIZE * 3 < (qc.getPcount() - PAGE_SIZE * 2)) {
						qc.setBenefit(rs.getFloat("9dresses_real_price") - rs.getFloat("cost_price") / 6, 0.1f, PAGE_SIZE * 3);
						qc.setScore(rs.getFloat("sort_score"), 0.1f, PAGE_SIZE * 3);
					} else {
						qc.setBenefit(rs.getFloat("9dresses_real_price") - rs.getFloat("cost_price") / 6, 0.1f, qc.getPcount() - PAGE_SIZE * 2);
						qc.setScore(rs.getFloat("sort_score"), 0.1f, qc.getPcount() - PAGE_SIZE * 2);
					}
					// System.out.println("Other Page:" + qc.getBenefit() + ":"
					// + qc.getScore());
				}
				idx++;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		public void outputData(ShowroomInfo info) {
			output(info.getShowName() + "\t" + info.getName() + "\t" + qc.getBenefit() + "\t" + qc.getPcount() + "\t" + qc.getScore());
		}
	}

	private void loadConfig(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "utf-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				if (lineNo == 1) {
					// 忽略首行
					lineNo++;
					continue;
				}
				if (line.trim().length() == 0) {
					continue;
				}
				ShowroomInfo si = parseLine(line);
				if (si != null) {
					list.add(si);
				}
			}
			reader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private ShowroomInfo parseLine(String line) {
		ShowroomInfo info = new ShowroomInfo();
		String[] items = Tools.split(line, "\t");
		if (items.length < 5) {
			System.out.println("无效的行:" + line);
			return null;
		}
		info.setShowName(getFormattedName(items[0], items[1]));
		info.setName(items[1]);
		info.setMinPrice(getNum(items[2]));
		info.setMaxPrice(getNum(items[3]));
		
		if(isCheck){
			System.out.println(info.getShowName()+"\t"+line.trim());
			op.addContentToEndOfFile2(this.outResFile, info.getShowName()+"\t"+line.trim());
		}
		
		String[] tags = Tools.split(items[4], ";");
		for (int i = 0; i < tags.length; i++) {
			String tag = tags[i];
			if (tag.trim().length() == 0) {
				continue;
			}
			if (tag.contains("}") && tag.contains("{")) {
				int pos = tag.indexOf("}");
				ShowroomTag st = new ShowroomTag(lineNo, tag.substring(1, pos), Tools.replace(tag.substring(pos + 1), "\"", ""));
				info.getTagList().add(st);
			} else {
				ShowroomTag st = new ShowroomTag(lineNo, null, Tools.replace(tag, "\"", ""));
				info.getTagList().add(st);
			}
		}
		lineNo++;
		return info;
	}

	private String getFormattedName(String showName, String keyword) {
		if (showName.trim().length() > 0) {
			return showName;
		}
		StringBuffer buf = new StringBuffer();
		boolean append = false;
		StringTokenizer st = new StringTokenizer(keyword, " ");
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			if (isFilter(s)) {
				if (append) {
					buf.append(" ");
				}
				buf.append(s);
			} else {
				if (append) {
					buf.append(" ");
				}
				buf.append(upperFirst(s));
			}
			append = true;
		}
		return buf.toString();
	}

	private String upperFirst(String s) {
		return (s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase());
	}

	private boolean isFilter(String s) {
		if (filterList.contains(s.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}

	private int getNum(String s) {
		if (s == null || s.trim().length() == 0) {
			return -1;
		}

		try {
			int r = Integer.parseInt(s);
			return r;
		} catch (Exception ex) {
			System.out.println("无效的行:" + lineNo);
			System.exit(0);
		}
		return -1;
	}
}

class ShowroomInfo {
	private String showName;
	private int lineNo;
	private String name;
	private boolean isSetPrice;
	private int minPrice;
	private int maxPrice;
	private List<ShowroomTag> tagList = new ArrayList<ShowroomTag>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSetPrice() {
		return isSetPrice;
	}

	public void setSetPrice(boolean isSetPrice) {
		this.isSetPrice = isSetPrice;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
		if (this.minPrice > -1) {
			this.setSetPrice(true);
		}
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
		if (this.maxPrice > -1) {
			this.setSetPrice(true);
		}
	}

	public List<ShowroomTag> getTagList() {
		return tagList;
	}

	public void setTagList(List<ShowroomTag> tagList) {
		this.tagList = tagList;
	}

	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

}

class ShowroomTag {
	private String parent;
	private String tag;
	private boolean isAll;

	public ShowroomTag(int lineNo, String parent, String tag) {
		isAll = false;
		if (tag.equalsIgnoreCase("all")) {
			isAll = true;
			return;
		}
		if (TagAdapter.getInstance().checkTag(parent, tag, false)) {
			if (parent == null) {
				this.parent = TagAdapter.getInstance().getTag(tag, false).getParent();
			} else {
				this.parent = parent;
			}
			this.tag = tag;
		} else {
			System.out.println("无效的行:" + lineNo + " ;tag:{" + Tools.revertNull(parent) + "}" + tag);
			System.exit(0);
		}
	}

	public String getParent() {
		return parent;
	}

	public String getTag() {
		return tag;
	}

	public boolean isAll() {
		return isAll;
	}
}

class SqlState {

	private int idx;
	private String m;
	private String u;
	private String c;

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}
}