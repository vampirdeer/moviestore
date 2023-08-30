package com.ezen.reviewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ezen.util.JDBCUtil;

public class ReviewerDAO {
	private static ReviewerDAO dao=new ReviewerDAO();
	private ReviewerDAO() {}
	public static ReviewerDAO getInstance() {
		return dao;
	}
	
	public ReviewerVO create(String id,String pwd,String name,
			String email,String phone,String grade) {
		ReviewerVO vo = null;
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			String sql="insert into reviewertbl(id,pwd,name,email,phone,grade) values(?,?,?,?,?,?)";
			System.out.println(sql);
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			pstmt.setString(5, phone);
			pstmt.setString(6, grade);
			rs=pstmt.executeQuery();
			if(rs.next()) {//로그인 성공
				vo=new ReviewerVO(rs.getString("id"),rs.getString("pwd"),
						rs.getString("name"),rs.getString("email"),
						rs.getString("phone"),rs.getString("grade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		return vo;
	}
	//------ 로그인 메소드 -파라메타(아이디,패스워드), 반환 MemberVO(아이디,이름,등급)
	public ReviewerVO login(String id,String pwd) {
		ReviewerVO vo=null;
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement("select name,grade from reviewertbl where id=? and pwd=?");
			pstmt.setString(1, id);pstmt.setString(2, pwd);
			rs=pstmt.executeQuery();
			if(rs.next()) {//로그인 성공
				vo=new ReviewerVO(id,rs.getString("name"),rs.getString("grade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		return vo;
	}
}
