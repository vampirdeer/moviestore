package com.ezen.movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ezen.util.JDBCUtil;


public class MovieDAO {
	//싱글톤
	private static MovieDAO dao=new MovieDAO();
	private MovieDAO() {}
	public static MovieDAO getInstance() {
		return dao;
	}
	//---- booktbl crud 작업 메소드 정의
	// booktbl 모든 데이터를 읽어서 List 담아서 반환하는 메소드 정의
	public List<MovieVO> getBookList(){
		List<MovieVO> list=null;
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		//최신 등록 도서부터 가지고 오기
		String sql="select mno,title,director,actor,price,regdate "
				+ "from movietbl where disp='y' order by regdate desc";
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {//검색된 행이 하나이상이면
				list=new ArrayList<>();
				do {
					MovieVO vo=new MovieVO(rs.getInt("mno"),rs.getString("title")
							,rs.getString("director"),rs.getString("actor")
							,rs.getInt("price"),rs.getDate("regdate"));
					list.add(vo);							
				}while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		
		return list;
	}
	// 페이지용 movietbl의 모든 행의 개수를 반환 해주는 메소드
	public int getRowConut() {
		int result=0;
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select count(*) from movietbl where disp='y' ";
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result= rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		return result;
	}
	public List<MovieVO> getMovieList(int page, int displayRow) {
		List<MovieVO> list=null;
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		//최신 등록 도서부터 가지고 오기
		/*
		select * from (
			    select rownum rn, A.* from
			        (select p from booktbl order by bno desc) A
			    where rownum<=15 -- 10 page(현재페이지):3, displayRow(페이지당 표시 행수) : 5 
			) where rn>=11; -- 6 : page*displayRow-displayRow+1 : 3*5-5+1
		*/
		StringBuilder sb=new StringBuilder();
		sb.append("select * from (");
		sb.append("select rownum rn, A.* from");
		sb.append("(select mno,title,director,actor,price,regdate from movietbl where disp='y' order by mno desc) A");
		sb.append(" where rownum<=?");
		sb.append(") where rn>=?");
		System.out.println(sb.toString());
		try {
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, page*displayRow);
			pstmt.setInt(2, page*displayRow-displayRow+1);
			rs=pstmt.executeQuery();
			if(rs.next()) {//검색된 행이 하나이상이면
				list=new ArrayList<>();
				do {
					MovieVO vo=new MovieVO(rs.getInt("mno"),rs.getString("title")
							,rs.getString("director"),rs.getString("actor")
							,rs.getInt("price"),rs.getDate("regdate"));
					list.add(vo);							
				}while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn, pstmt, rs);
		}		
		
		return list;
	}
	public List<MovieVO> getMovieList(int page, int displayRow, String searchtype, String searchword) {
		List<MovieVO> list=null;
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		sb.append("select * from (");
		sb.append("select rownum rn, A.* from");
		sb.append("(select mno,title,director,actor,price,regdate from movietbl");
		sb.append(" where ");
		sb.append(searchtype);
		sb.append(" like ? and disp='y' order by bno desc) A");//최신순 정렬
		sb.append(" where rownum<=?");
		sb.append(") where rn>=?");
		System.out.println(sb.toString());
		try {
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, "%"+searchword+"%");
			pstmt.setInt(2, page*displayRow);
			pstmt.setInt(3, page*displayRow-displayRow+1);
			rs=pstmt.executeQuery();
			if(rs.next()) {//검색된 행이 하나이상이면
				list=new ArrayList<>();
				do {
					MovieVO vo=new MovieVO(rs.getInt("mno"),rs.getString("title")
							,rs.getString("director"),rs.getString("actor")
							,rs.getInt("price"),rs.getDate("regdate"));
					list.add(vo);							
				}while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn, pstmt, rs);
		}		
		
		return list;
	}
	public int getRowConut(String searchtype, String searchword) {
		int result=0;
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		//select count(*) from movietbl where title like '%자유%'
		String sql="select count(*) from movietbl where "+searchtype+" like ? and  disp='y' ";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, "%"+searchword+"%");
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result= rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		return result;
	}
	//----------- 삽입 메소드
	public int insertMovie(MovieVO vo) {
		int result=0;
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		String sql="insert into movietbl(mno,title,director,actor,price,content"
				+ ",srcFilename,saveFilename,savePath) "
				+ "values(movie_seq.nextval,?,?,?,?,?,?,?,?)";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getDirector());
			pstmt.setString(3, vo.getActor());
			pstmt.setInt(4, vo.getPrice());
			pstmt.setString(5, vo.getContent());
			pstmt.setString(6, vo.getSrcFilename());
			pstmt.setString(7, vo.getSaveFilename());
			pstmt.setString(8, vo.getSavePath());
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	// bno 도서번호를 받아서 검색해서 모든 필드를 vo 에 담아서 반환해 줌
	public MovieVO getMovie(int mno) {
		MovieVO vo=null;
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement("select * from movietbl where mno=? and disp='y' ");
			pstmt.setInt(1, mno);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				vo=new MovieVO(rs.getInt("mno"), rs.getString("title")
						,rs.getString("director"),rs.getString("actor") 
						,rs.getInt("price"), rs.getDate("regdate")
						, rs.getString("content"), rs.getString("srcFilename"),
						rs.getString("saveFilename"),rs.getString("savePath"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		
		return vo;
	}
	public int updateMovie(MovieVO vo) {
		int result=0;
		//update문 2개로 작성한다.
		//원본파일명이 null이 아니면 파일관련 3개의 필드 update 하고
		//원본파일명이 null이 아니면 파일관련 3개의 필드 update 제외
		String oFileName=vo.getSrcFilename();
		String sql=null;
		if(oFileName==null)
			sql="update movietbl set title=?,director=?"
					+ ",actor=?,price=?,content=? where mno=?";
		else 
			sql="update movietbl set title=?,director=?"
					+ ",actor=?,price=?,content=?,srcFilename=?"
					+ ",saveFilename=?,savepath=? where mno=?";
		
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		try {
			pstmt=conn.prepareStatement(sql);
			//?채우기
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getDirector());
			pstmt.setString(3, vo.getActor());
			pstmt.setInt(4, vo.getPrice());
			pstmt.setString(5, vo.getContent());
			if(oFileName!=null) {
				pstmt.setString(6, oFileName);
				pstmt.setString(7, vo.getSaveFilename());
				pstmt.setString(8, vo.getSavePath());
				pstmt.setInt(9,vo.getMno());
			}else {
				pstmt.setInt(6,vo.getMno());
			}
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt);
		}
		return result;
	}
	public int deleteMovie(int mno) {
		int result=0;
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		try {
			//pstmt=conn.prepareStatement("delete from booktbl where bno=?");
			pstmt=conn.prepareStatement("update movietbl set disp='n' where mno=?");
			pstmt.setInt(1, mno);
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt);
		}
		return result;
	}
	
}//class
