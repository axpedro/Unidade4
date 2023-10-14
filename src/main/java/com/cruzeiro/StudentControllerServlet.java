package com.cruzeiro;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	private StudentDbUtil studentDbUtil;
    @Resource(name="jdbc/DbStudents")
    private DataSource dataSource;
    @Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try {
			
			studentDbUtil = new StudentDbUtil(dataSource);
			
			
		}
		catch(Exception e){
			throw new ServletException(e);
		}
	}

	/**
     * @see HttpServlet#HttpServlet()
     */
    public StudentControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			String command = request.getParameter("command");
			
			if(command ==null) {
				command = "LIST";
			}
			switch (command) {
			case "LIST":
				listStudents(request,response);
				break;
			case "ADD":
				addStudents(request,response);
				break;
			default:
				listStudents(request,response);
				
			
			
			
			}
			
		} catch (Exception e) {
			
			throw new ServletException(e);
			
		}
		
		
		
		
	}
	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//get students from database
		List<Student> students = studentDbUtil.getStudents();
		
		//add data from request
		request.setAttribute("STUDENTS_LIST", students);
		
		//send to JSP (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
		
	}
	
	
	private void addStudents(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//recover data sent
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		//new object student
		Student student = new Student(0,firstName,lastName,email);
		
		//save to database
		studentDbUtil.addStudent(student);
		//back to list
		listStudents(request, response);
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
