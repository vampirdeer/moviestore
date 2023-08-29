package com.ezen.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezen.movie.ReviewerService;

/**
 * Servlet implementation class MemberController
 */
@WebServlet("/reviewer")
public class ReviewerController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//encoding
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		//MemberService 객체 생성->exec()메소드 호출-> 이동할 주소 반환
		String view=new ReviewerService(req,resp).exec();
		//
		if(view!=null)req.getRequestDispatcher(view).forward(req, resp);
	}
    

}
