package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.Student;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller
public class StudentsController {

    @RequestMapping(value="/Students")
    public String listStudents(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
    	
        return "studentsList";    
    }
    
    @RequestMapping(value="/AddStudent")
    public String displayAddStudentForm(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
        return "studentForm";    
    }

    @RequestMapping(value="/CreateStudent", method=RequestMethod.POST)
    public String createStudent(@RequestParam(value="studentName", required=false) String name,
    							@RequestParam(value="studentSurname", required=false) String surname,
    							@RequestParam(value="studentPesel", required=false) String pesel,
    							@RequestParam(value="studentClass", required=false) String classId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	Student student = new Student();
    	
    	student.setName(name);
    	student.setSurname(surname);
    	student.setPesel(pesel);
    	student.setClass_id(classId);
    	
    	DatabaseConnector.getInstance().addStudent(student);    	
       	model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
    	model.addAttribute("message", "Nowy uczen zostal dodany");
         	
    	return "studentsList";
    }
    
    @RequestMapping(value="/DeleteStudent", method=RequestMethod.POST)
    public String deleteStudent(@RequestParam(value="studentId", required=false) String studentId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteStudent(studentId);    	
       	model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
    	model.addAttribute("message", "Uczen zostal usunięty");
         	
    	return "studentsList";
    }
    
    
    
    @RequestMapping(value="/EditStudent")
    public String displayEditStudentForm(@RequestParam(value="studentId") String studentId, 
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	model.addAttribute("student", DatabaseConnector.getInstance().getStudent(studentId));
    	
        return "editStudentForm";    
    }
    
    
    @RequestMapping(value="/UpdateStudent", method=RequestMethod.POST)
    public String editStudent(@RequestParam(value="studentId", required=true) String studentId,
                              @RequestParam(value="studentName", required=false) String name,
                              @RequestParam(value="studentSurname", required=false) String surname,
                              @RequestParam(value="studentPesel", required=false) String pesel,
                              @RequestParam(value="studentClass", required=false) String classId,
           Model model, HttpSession session){    	
           	if (session.getAttribute("userLogin") == null)
        		return "redirect:/Login";
           	
        	DatabaseConnector.getInstance().updateStudent(studentId, name, surname, pesel, classId);    	
           	model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
        	model.addAttribute("message", "updated");
             	
        	return "studentsList";
           }
    
}