package com.ezen.movie;

import java.sql.Date;

public class MoviescoreVO {
	private int msno,mno;
	private String id;
	private double score;
	private String cmt;
	private Date regdate;
	public MoviescoreVO() {
		super();
	}
	public MoviescoreVO(int msno, int mno, String id, double score, String cmt, Date regdate) {
		super();
		this.msno = msno;
		this.mno = mno;
		this.id = id;
		this.score = score;
		this.cmt = cmt;
		this.regdate = regdate;
	}
	public int getMsno() {
		return msno;
	}
	public void setMsno(int msno) {
		this.msno = msno;
	}
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getCmt() {
		return cmt;
	}
	public void setCmt(String cmt) {
		this.cmt = cmt;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	@Override
	public String toString() {
		return "MoviescoreVO [msno=" + msno + ", mno=" + mno + ", id=" + id + ", score=" + score + ", cmt=" + cmt
				+ ", regdate=" + regdate + "]";
	}
	
	
}
