package com.ezen.movie;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ezen.util.JDBCUtil;

public class MoviescoreDAO {
	//싱글톤
	private static MoviescoreDAO dao=new MoviescoreDAO();
	private MoviescoreDAO() {}
	public static MoviescoreDAO getInstance() {
		return dao;
	}
	/*-------------------------------------
	 *  삽입 메소드
	 *  MoviescoreVO를 받아서 테이블에 삽입하는 메소드  
	 --------------------------------------*/
	public int insertMoviescore(MoviescoreVO vo) {
		int result=0;
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		String sql="insert into moviescore values(msno_seq.nextval,?,?,?,?,sysdate)";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getMno());
			pstmt.setString(2, vo.getId());
			pstmt.setDouble(3, vo.getScore());
			pstmt.setString(4, vo.getCmt());
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt);
		}
		return result;
	}
	public int getRowCount(int bno) {
		int result=0;
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select count(*) from moviescore where mno=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt,rs);
		}
		
		return result;
	}
	public List<MoviescoreVO> getMovieScore(int page, int mno, int displayRow) 	{
		List<MoviescoreVO> list=null;
		StringBuilder sb=new StringBuilder();
		sb.append("select * from (");
		sb.append("select rownum rn, A.* from ");
		sb.append("(select * from moviescore where mno=? order by msno desc) A ");
		sb.append(" where rownum<=?");
		sb.append(") where rn>=?");
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, mno);
			pstmt.setInt(2, page*displayRow);
			pstmt.setInt(3, page*displayRow-displayRow+1);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				list=new ArrayList<>();
				do {
//int sno, int bno, String id, double score, String cmt, Date regdate
					list.add(new MoviescoreVO(rs.getInt("msno"), rs.getInt("mno"),rs.getString("id") , rs.getDouble("score"), rs.getString("cmt"),  rs.getDate("regdate")));
				}while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		
		return list;
	}
	public ArrayList<Number> getAvgScore(int mno) {
		//double score=0;
		ArrayList<Number> list=null;
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select avg(score),count(*) from moviescore where mno=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, mno);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				//score=rs.getDouble(1);
				list=new ArrayList<>();
				list.add(rs.getDouble(1));
				list.add(rs.getInt(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		//return score;
		return list;
	}
	
}
