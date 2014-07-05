package com.shinetech.dress.tag;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.shinetech.sql.DatabaseAccessBuilder;
import com.shinetech.sql.IDatabaseAccess;
import com.shinetech.sql.ResultSetHandler;
import com.shinetech.sql.exception.DBException;

public class TagAdapter {

	private HashMap<String, HashMap<String, List<Tag>>> showNameMapSC = new HashMap<String, HashMap<String, List<Tag>>>();
	private HashMap<String, HashMap<String, List<Tag>>> showNameMapIC = new HashMap<String, HashMap<String, List<Tag>>>();
	private static TagAdapter instance = null;
	private boolean debug = true;

	public static synchronized TagAdapter getInstance() {
		if (instance == null) {
			instance = new TagAdapter();
		}
		return instance;
	}

	public HashMap<String, List<Tag>> getTagMap(String catalog) {
		return showNameMapSC.get(catalog);
	}

	public boolean checkTag(String catalog, String tag) {
		return checkTag(catalog, tag, true);
	}

	public boolean checkTag(String catalog, String tag, Boolean CaseSensive) {
		if (catalog == null) {
			return (getTag(tag, CaseSensive) != null);
		} else {
			return (getTag(catalog, tag, CaseSensive) != null);
		}
	}

	public Tag getTag(String catalog, String tag) {
		return getTag(catalog, tag, true);
	}

	public Tag getTag(String catalog, String tag, Boolean CaseSensive) {
		if (CaseSensive) {
			if (showNameMapSC.containsKey(catalog) == false) {
				return null;
			}
			HashMap<String, List<Tag>> map = showNameMapSC.get(catalog);
			Iterator<List<Tag>> iter = map.values().iterator();
			while (iter.hasNext()) {
				List<Tag> list = iter.next();
				for (Tag tagBean : list) {
					if (tagBean.getName().equals(tag)) {
						return tagBean;
					}
				}
			}
			return null;
		} else {
			if (showNameMapIC.containsKey(catalog.toLowerCase().trim()) == false) {
				return null;
			}
			HashMap<String, List<Tag>> map = showNameMapIC.get(catalog.toLowerCase().trim());
			Iterator<List<Tag>> iter = map.values().iterator();
			while (iter.hasNext()) {
				List<Tag> list = iter.next();
				for (Tag tagBean : list) {
					if (tagBean.getName().equals(tag.toLowerCase().trim())) {
						return tagBean;
					}
				}
			}
			return null;
		}
	}

	public Tag getTag(String tag) {
		return getTag(tag, true);
	}

	public Tag getTag(String tag, Boolean CaseSensive) {
		if (CaseSensive) {
			Iterator<HashMap<String, List<Tag>>> citer = showNameMapSC.values().iterator();
			while (citer.hasNext()) {
				HashMap<String, List<Tag>> map = citer.next();
				Iterator<String> iter = map.keySet().iterator();
				while (iter.hasNext()) {
					String key = iter.next();
					List<Tag> list = map.get(key);
					for (Tag tagBean : list) {
						if (tagBean.getName().equals(tag)) {
							return tagBean;
						}
					}
				}
			}
			return null;
		} else {
			Iterator<HashMap<String, List<Tag>>> citer = showNameMapIC.values().iterator();
			while (citer.hasNext()) {
				HashMap<String, List<Tag>> map = citer.next();
				Iterator<String> iter = map.keySet().iterator();
				while (iter.hasNext()) {
					String key = iter.next();
					List<Tag> list = map.get(key);
					for (Tag tagBean : list) {
						if (tagBean.getName().equals(tag.toLowerCase().trim())) {
							return tagBean;
						}
					}
				}
			}
			return null;
		}
	}

	private TagAdapter() {
		load();
	}

	private void load() {
		IDatabaseAccess db = (new DatabaseAccessBuilder()).getDatabaseAccess();
		// , a.match_name catalog_match_name, b.match_name tag_match_name
		String sql = "SELECT a.id catalog_id, a.show_name catalog_show_name, b.show_name tag_show_name, b.id tag_id FROM dr_tag_catalogs a, dr_tag b where a.id = b.catalog_id";
		try {
			db.handleList(sql, new ResultSetHandler() {
				@Override
				public void handle(ResultSet rs) {
					try {
						String catalogShowName = rs.getString("catalog_show_name");
						String tagShowName = rs.getString("tag_show_name");
						long catalogId = rs.getLong("catalog_id");
						long tagId = rs.getLong("tag_id");

						if (!showNameMapSC.containsKey(catalogShowName)) {
							showNameMapSC.put(catalogShowName, new HashMap<String, List<Tag>>());
						}

						if (!showNameMapIC.containsKey(catalogShowName.toLowerCase())) {
							showNameMapIC.put(catalogShowName.toLowerCase(), new HashMap<String, List<Tag>>());
						}

						List<Tag> listSC = new ArrayList<Tag>();
						List<Tag> listIC = new ArrayList<Tag>();
						boolean match = false;
						StringBuffer buf = new StringBuffer();
						for (int i = 0; i < tagShowName.length(); i++) {
							String c = tagShowName.substring(i, i + 1);
							if (c.equals("\"")) {
								match = !match;
								continue;
							} else if (c.equals("/")) {
								if (match) {
									buf.append(c);
									continue;
								} else {
									String tag = buf.toString();
									listSC.add(new Tag(catalogId, catalogShowName, tagId, tag));
									listIC.add(new Tag(catalogId, catalogShowName.toLowerCase(), tagId, tag.toLowerCase()));
									buf = new StringBuffer();
									continue;
								}
							} else {
								buf.append(c);
							}
						}
						String tag = buf.toString();
						listSC.add(new Tag(catalogId, catalogShowName, tagId, tag));
						listIC.add(new Tag(catalogId, catalogShowName.toLowerCase(), tagId, tag.toLowerCase()));
						showNameMapSC.get(catalogShowName).put(tagShowName, listSC);
						showNameMapIC.get(catalogShowName.toLowerCase()).put(tagShowName, listIC);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
