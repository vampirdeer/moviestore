package com.ezen.movie;

import java.sql.Date;

public class MovieVO {
	private int mno;
	private String title,director,actor;
	private int price;
	private Date regdate;
	private String content;
	private String srcFilename,saveFilename,savePath;
	public MovieVO() {
		super();
	}
	
	public MovieVO(String title, String director, String actor, int price, String content, String srcFilename,
			String saveFilename, String savePath) {
		super();
		this.title = title;
		this.director = director;
		this.actor = actor;
		this.price = price;
		this.content = content;
		this.srcFilename = srcFilename;
		this.saveFilename = saveFilename;
		this.savePath = savePath;
	}

	public MovieVO(String title, String director, String actor, int price, String content) {
		super();
		this.title = title;
		this.director = director;
		this.actor = actor;
		this.price = price;
		this.content = content;
	}

	public MovieVO(int mno, String title, String director, String actor, int price, Date regdate) {
		super();
		this.mno = mno;
		this.title = title;
		this.director = director;
		this.actor = actor;
		this.price = price;
		this.regdate = regdate;
	}
		
	public MovieVO(int mno, String title, String director, String actor, int price, Date regdate, String content,
			String srcFilename, String saveFilename, String savePath) {
		super();
		this.mno = mno;
		this.title = title;
		this.director = director;
		this.actor = actor;
		this.price = price;
		this.regdate = regdate;
		this.content = content;
		this.srcFilename = srcFilename;
		this.saveFilename = saveFilename;
		this.savePath = savePath;
	}

	public MovieVO(int mno, String title, String director, String actor, int price, String content,
			String srcFilename, String saveFilename, String savePath) {
		super();
		this.mno = mno;
		this.title = title;
		this.director = director;
		this.actor = actor;
		this.price = price;
		this.content = content;
		this.srcFilename = srcFilename;
		this.saveFilename = saveFilename;
		this.savePath = savePath;
	}

	public int getMno() {
		return mno;
	}

	public void setMno(int mno) {
		this.mno = mno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSrcFilename() {
		return srcFilename;
	}

	public void setSrcFilename(String srcFilename) {
		this.srcFilename = srcFilename;
	}

	public String getSaveFilename() {
		return saveFilename;
	}

	public void setSaveFilename(String saveFilename) {
		this.saveFilename = saveFilename;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	@Override
	public String toString() {
		return "MovieVO [mno=" + mno + ", title=" + title + ", director=" + director + ", actor=" + actor + ", price="
				+ price + ", regdate=" + regdate + ", content=" + content + ", srcFilename=" + srcFilename
				+ ", saveFilename=" + saveFilename + ", savePath=" + savePath + "]";
	}

	
	
}
