package com.shinetech.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.shinetech.util.FileOperate;
import com.shinetech.util.FileOperate.ILineHandle;

public class TestSplitCSV {
	/**
	 * Split one line of csv file
	 * 
	 * @return a String array results
	 */
	public static ArrayList<String> splitCSV(String src) throws Exception {
		if (src == null || src.equals(""))
			return new ArrayList<String>();
		StringBuffer st = new StringBuffer();
		ArrayList<String> result = new ArrayList<String>();
		boolean beginWithQuote = false;
		for (int i = 0; i < src.length(); i++) {
			char ch = src.charAt(i);
			if (ch == '\"') {
				if (beginWithQuote) {
					i++;
					if (i >= src.length()) {
						result.add(st.toString());
						st = new StringBuffer();
						beginWithQuote = false;
					} else {
						ch = src.charAt(i);
						if (ch == '\"') {
							st.append(ch);
						} else if (ch == ',') {
							result.add(st.toString());
							st = new StringBuffer();
							beginWithQuote = false;
						} else {
							throw new Exception(
									"Single double-quote char mustn't exist in filed "
											+ (result.size() + 1)
											+ " while it is begined with quote\nchar at:"
											+ i);
						}
					}
				} else if (st.length() == 0) {
					beginWithQuote = true;
				} else {
					throw new Exception(
							"Quote cannot exist in a filed which doesn't begin with quote!\nfield:"
									+ (result.size() + 1));
				}
			} else if (ch == ',') {
				if (beginWithQuote) {
					st.append(ch);
				} else {
					result.add(st.toString());
					st = new StringBuffer();
					beginWithQuote = false;
				}
			} else {
				st.append(ch);
			}
		}
		if (st.length() != 0) {
			if (beginWithQuote) {
				throw new Exception(
						"last field is begin with but not end with double quote");
			} else {
				result.add(st.toString());
			}
		}
		return result;
	}
	private List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
	 public List<ArrayList<String>> csvfile2Array(String filename){
	    	FileOperate op = new FileOperate();
	    	op.getLine(new File(filename), new ILineHandle<String>() {

				@Override
				public String lineHandle(String line) {
					try {
						list.add(splitCSV(line));
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			
	    	});
	    	return list;
	    }
	public static void main(String[] args) {
		String src1 = "\"fh,zg\",sdf,\"asfs,\",\",dsdf\",\"aadf\"\"\",\"\"\"hdfg\",\"fgh\"\"dgnh\",hgfg'dfh,\"asdfa\"\"\"\"\",\"\"\"\"\"fgjhg\",\"gfhg\"\"\"\"hb\"";
		FileOperate op = new FileOperate();
		src1 = op.getContent("data/test.csv");
		try {
			ArrayList<String> Ret = splitCSV(src1);
			for (int i = 0; i < Ret.size(); i++) {
				System.out.println(i + ": " + Ret.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
