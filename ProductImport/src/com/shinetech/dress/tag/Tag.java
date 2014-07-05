package com.shinetech.dress.tag;

public class Tag {
	private String name;
	private long tagId;

	private String parent;
	private long parentId;

	public Tag(long parentId, String parent, long tagId, String name) {
		this.parent = parent;
		this.parentId = parentId;
		this.tagId = tagId;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
}
