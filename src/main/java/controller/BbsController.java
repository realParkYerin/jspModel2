package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BbsDao;
import db.DBConnection;
import dto.BbsDto;

@WebServlet("/bbs")
public class BbsController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProc(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProc(req, resp);
	}

	public void doProc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DBConnection.initConnection();
		
		req.setCharacterEncoding("utf-8");
		
		String param = req.getParameter("param");

		if(param.equals("bbslist")) {
			
			// 추가
			String choice = req.getParameter("choice");
			String search = req.getParameter("search");
			if(choice == null || choice.equals("") || search == null) {
				choice = "";
				search = "";
			}
			
			////////////////현재 페이지 번호
			String sPageNumber = req.getParameter("pageNumber");
			int pageNumber = 0;
			if(sPageNumber != null && !sPageNumber.equals("")){
				pageNumber = Integer.parseInt(sPageNumber);
			}
			
			BbsDao dao = BbsDao.getInstance();
			
			//////////////////// 글의 목록
			// List<BbsDto> list = dao.getBbsSearchList(choice, search);
			List<BbsDto> list = dao.getBbsPageList(choice, search, pageNumber);
			
			//////////////////// 총글의 수
			int count = dao.getAllBbs(choice, search);
			// 페이지의 총수
			int pageBbs = count / 10;		// 10개씩 총글의 수를 나눔		26 / 10 -> 2
			if((count % 10) > 0){	// 6
				pageBbs = pageBbs + 1;		// 2 + 1	
			}
									
						
			req.setAttribute("bbslist", list);		// 짐싸!
			req.setAttribute("pageBbs", pageBbs);
			req.setAttribute("pageNumber", pageNumber);
			req.setAttribute("choice", choice);
			req.setAttribute("search", search);
			
			forward("bbslist.jsp", req, resp);	// 잘가!
		}
		else if(param.equals("bbswrite")) {
			
			resp.sendRedirect("bbswrite.jsp");
		}
		else if(param.equals("bbswriteAf")) {
			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			boolean isS = BbsDao.getInstance().writeBbs(new BbsDto(id, title, content));
			String bbswrite = "";
			if(isS) {
				bbswrite = "BBS_ADD_OK";
			}else {
				bbswrite = "BBS_ADD_NO";
			}
			
			req.setAttribute("bbswrite", bbswrite);			
			forward("message.jsp", req, resp);
		}
		else if(param.equals("bbsdetail")) {
			int seq = Integer.parseInt( req.getParameter("seq") );
			
			BbsDao dao = BbsDao.getInstance();
			dao.readcount(seq);
			
			BbsDto dto = dao.getBbs(seq);
			
			req.setAttribute("bbsdto", dto);		
			forward("bbsdetail.jsp", req, resp);
		}
		else if(param.equals("answer")) {
			int seq = Integer.parseInt( req.getParameter("seq") );
			
			BbsDao dao = BbsDao.getInstance();
			BbsDto dto = dao.getBbs(seq);
			
			req.setAttribute("bbsdto", dto);		
			forward("answer.jsp", req, resp);
		}
		else if(param.equals("answerAf")) {
			int seq = Integer.parseInt( req.getParameter("seq") );
			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			BbsDao dao = BbsDao.getInstance();
			boolean isS = dao.answer(seq, new BbsDto(id, title, content));
			String answer = "BBS_ANSWER_OK";
			if(!isS) {				
				answer = "BBS_ANSWER_NO";
			}
			
			req.setAttribute("seq", seq);		
			req.setAttribute("answer", answer);			
			forward("message.jsp", req, resp);
		}
	}
	
	public void forward(String linkName, HttpServletRequest req, HttpServletResponse resp) {
		RequestDispatcher dispatcher = req.getRequestDispatcher(linkName);		
		try {
			dispatcher.forward(req, resp);		
		} catch (ServletException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
}




