package com.ezen.movie;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezen.reviewer.ReviewerDAO;
import com.ezen.reviewer.ReviewerVO;



public class ReviewerService {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private final String path="/WEB-INF/views/reviewer/";


	public ReviewerService(HttpServletRequest req, HttpServletResponse resp) {
		request=req;response=resp;
	}

	public String exec() throws IOException {
		String cmd=request.getParameter("cmd");
		if(cmd.equals("login")) {
			return LoginService();
		}else if(cmd.equals("create")) {
				return CreateService();
			
		}else if(cmd.equals("logout")) {
			//세션얻기
			HttpSession s=request.getSession();
			//세션 지우기
			s.invalidate();
			//페이지이동-List
			return "movie?cmd=list";
		}
		return null;
	}

	
	private String CreateService() throws IOException {
		String method=request.getMethod().toUpperCase();
		if(method.equals("GET")) {
			return path+"create.jsp";
		}else {
			//view에서 넘어온 데이터를 받는다.
			String id=request.getParameter("userid");
			String pwd=request.getParameter("pwd");
			String name=request.getParameter("name");
			String email=request.getParameter("email");
			String phone=request.getParameter("phone");
			String grade=request.getParameter("grade");
			//vo 객체 생성
			ReviewerVO rvo=new ReviewerVO(id, pwd, name, email, phone, grade); 
			ReviewerDAO dao=ReviewerDAO.getInstance();
			int result=dao.create(rvo);
			if(result==1) {
				return path+"login.jsp";
			}
		}
		return null;
		
	}

	private String LoginService() {
		String method=request.getMethod().toUpperCase();
		if(method.equals("GET")) {
			return path+"login.jsp";
		}else {//post
			//파라메타 받기
			String id=request.getParameter("userid");
			String pwd=request.getParameter("pwd");
			//dao 객체생성
			ReviewerDAO dao=ReviewerDAO.getInstance();
			//메소드 수행 결과 받기 
			ReviewerVO mvo=dao.login(id, pwd);
			if(mvo!=null) {//로그인 성공
				//세션 저장->request를 통해서 세션객체 얻기
				HttpSession session=request.getSession();
				session.setAttribute("mvo", mvo);
				//페이지에서 필요로 하는 정보를 저장 ??
				//페이지이동 : list 쪽으로 이동
				return "movie?cmd=list";
			}else {//로그인 실패
				request.setAttribute("message", "로그인 실패!");
				return path+"login.jsp";
			}
		}
	}

}
