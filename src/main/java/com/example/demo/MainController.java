package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController
{
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private SubjectRepository subjectRepository;
	
    //--------------------------------------------------------------------------------------------------
	
	@GetMapping("/")
	public String welcomingPage()
	{
		return "defaultPage";
	}
	

	@GetMapping("/checkAdmins")
	public String adminCheckingPage(Model model)
	{
		model.addAttribute("adminList" , adminRepository.findAll());
		return "checkAdmins";
	}

    @GetMapping("/createAdmin")
    public String adminCreationForm(Model model)
    {
        Admin admin = new Admin();
        model.addAttribute("newAdmin", admin);
    	return "createAdmin";
    }

    @PostMapping("/saveAdmin")
    public String adminSavingForm(@ModelAttribute("newAdmin") Admin admin)
    {
    	adminRepository.save(admin);
        return "redirect:/";
    }

    
    @GetMapping("/loginAdmin")
    public String adminLoginForm(Model model)
    {
    	Admin auxAdmin = new Admin();
    	model.addAttribute("auxAdmin", auxAdmin);
    	return "loginAdmin";
    }
    
    @PostMapping("/verifyAdmin")
    public String adminVerifyForm(@ModelAttribute("auxAdmin") Admin auxAdmin)
    {
        String user = auxAdmin.getUser();
        Admin adminToCheck = adminRepository.findByUser(user);
        if (adminToCheck != null && auxAdmin.getPassword().equals(adminToCheck.getPassword()))
        {
            return "adminOptions";
        }
        return "redirect:/";
    }
    
    @GetMapping("/adminOptions")
    public String returnAdminOptions(Model model)
    {
    	return "adminOptions";
    }
    
    //----------
    
    @GetMapping("/checkStudents")
	public String studentCheckingPage(Model model)
	{
		model.addAttribute("studentList" , studentRepository.findAll());
		return "checkStudents";
	}

    @GetMapping("/createStudent")
    public String studentCreationForm(Model model)
    {
        Student student = new Student();
        model.addAttribute("newStudent", student);
    	return "createStudent";
    }

    @PostMapping("/saveStudent")
    public String studentSavingForm(@ModelAttribute("newStudent") Student student)
    {
    	studentRepository.save(student);
        return "redirect:/checkStudents";
    }
    
    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable(value = "id") Long id)
    {
    	studentRepository.deleteById(id);
    	return "redirect:/checkStudents";
    }
    
    @GetMapping("/updateStudent/{id}")
    public String studentUpdatingForm(@PathVariable(value = "id") Long id, Model model)
    {
    	Optional<Student> optional = studentRepository.findById(id);
    	Student student = null;
    	if (optional.isPresent())
    	{
    		student = optional.get();
    	}
    	else
    	{
    		throw new RuntimeException("Student not found for id: " + id);
    	}
    	
    	model.addAttribute("student", student);
    	return "updateStudent";
    }
    
    //----------
    
    @GetMapping("/checkTeachers")
	public String teacherCheckingPage(Model model)
	{
		model.addAttribute("teacherList" , teacherRepository.findAll());
		return "checkTeachers";
	}

    @GetMapping("/createTeacher")
    public String teacherCreationForm(Model model)
    {
        Teacher teacher = new Teacher();
        model.addAttribute("newTeacher", teacher);
    	return "createTeacher";
    }

    @PostMapping("/saveTeacher")
    public String TeacherSavingForm(@ModelAttribute("newTeacher") Teacher teacher)
    {
    	teacherRepository.save(teacher);
        return "redirect:/checkTeachers";
    }
    
    @GetMapping("/deleteTeacher/{id}")
    public String deleteTeacher(@PathVariable(value = "id") Long id)
    {
    	teacherRepository.deleteById(id);
    	return "redirect:/checkTeachers";
    }
    
    @GetMapping("/updateTeacher/{id}")
    public String teacherUpdatingForm(@PathVariable(value = "id") Long id, Model model)
    {
    	Optional<Teacher> optional = teacherRepository.findById(id);
    	Teacher teacher = null;
    	if (optional.isPresent())
    	{
    		teacher = optional.get();
    	}
    	else
    	{
    		throw new RuntimeException("Teacher not found for id: " + id);
    	}
    	
    	model.addAttribute("teacher", teacher);
    	return "updateTeacher";
    }
    
    //----------
    
    @GetMapping("/checkSubjects")
	public String subjectCheckingPage(Model model)
	{
		model.addAttribute("subjectList" , subjectRepository.findAll());
		return "checkSubjects";
	}

    @GetMapping("/createSubject")
    public String subjectCreationForm(Model model)
    {
        Subject subject = new Subject();
        model.addAttribute("newSubject", subject);
    	return "createSubject";
    }

    @PostMapping("/saveSubject")
    public String subjectSavingForm(@ModelAttribute("newSubject") Subject subject)
    {
    	subjectRepository.save(subject);
        return "redirect:/checkSubjects";
    }
    
    @GetMapping("/deleteSubject/{id}")
    public String deleteSubject(@PathVariable(value = "id") Long id)
    {
    	subjectRepository.deleteById(id);
    	return "redirect:/checkSubjects";
    }
    
    @GetMapping("/updateSubject/{id}")
    public String subjectUpdatingForm(@PathVariable(value = "id") Long id, Model model)
    {
    	Optional<Subject> optional = subjectRepository.findById(id);
    	Subject subject = null;
    	if (optional.isPresent())
    	{
    		subject = optional.get();
    	}
    	else
    	{
    		throw new RuntimeException("Student not found for id: " + id);
    	}
    	
    	model.addAttribute("subject", subject);
    	return "updateSubject";
    }
    
    //----------
    
    @GetMapping("/addTeacher/{id}")
    public String teacherAditionForm(@PathVariable(value = "id") Long id, HttpSession session, Model model)
    {
    	session.setAttribute("subjectId", id);
        Teacher teacher = new Teacher();
        model.addAttribute("teacherToAdd", teacher);
    	return "addTEacher";
    }
    
    @PostMapping("/verifyAddTeacher")
    public String teacherAddVerifyForm(@ModelAttribute("teacherToAdd") Teacher auxTeacher, HttpSession session, Model model) {
        Long subjectId = (Long) session.getAttribute("subjectId");
        if (subjectId != null) {
            Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
            if (optionalSubject.isPresent()) {
                Subject actualSubject = optionalSubject.get();
                String user = auxTeacher.getUser();
                Teacher teacherToAdd = teacherRepository.findByUser(user);
                if (teacherToAdd != null) {
                	teacherToAdd.getSubjects().add(actualSubject);
                    teacherRepository.save(teacherToAdd); // Save updated student
                }
            }
        }
        return "redirect:/seeSubjectsStudent";
    }
    
    //----------------------------------------------------------------------------------------------------
    
    @GetMapping("/loginStudent")
    public String studentLoginForm(Model model)
    {
    	Student auxStudent = new Student();
    	model.addAttribute("auxStudent", auxStudent);
    	return "loginStudent";
    }
    
    @PostMapping("/verifyStudent")
    public String studentVerifyForm(@ModelAttribute("auxStudent") Student auxStudent, HttpSession session, Model model) {
        String user = auxStudent.getUser();
        Student studentToCheck = studentRepository.findByUser(user);
        if (studentToCheck != null && auxStudent.getPassword().equals(studentToCheck.getPassword())) {
            session.setAttribute("studentId", studentToCheck.getId());
            return "redirect:/studentOptions";
        }
        return "redirect:/";
    }
    
    @GetMapping("/studentOptions")
    public String returnStudentOptions(Model model)
    {
    	return "studentOptions";
    }
    
    @GetMapping("/seeSubjectsStudent")
    public String subjectFromStudent(HttpSession session, Model model) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId != null) {
            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if (optionalStudent.isPresent()) {
                Student student = optionalStudent.get();
                model.addAttribute("subjectList", student.getSubjects());
                return "seeSubjectsStudent";
            }
        }
        return "redirect:/";
    }
    
    @GetMapping("/addSubject")
    public String subjectAditionForm(Model model)
    {
        Subject subject = new Subject();
        model.addAttribute("subjectToAdd", subject);
    	return "addSubject";
    }
    
    @PostMapping("/verifySubject")
    public String subjectVerifyForm(@ModelAttribute("subjectToAdd") Subject auxSubject, HttpSession session, Model model) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId != null) {
            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if (optionalStudent.isPresent()) {
                Student actualStudent = optionalStudent.get();
                String name = auxSubject.getName();
                Subject subjectToAdd = subjectRepository.findByName(name);
                if (subjectToAdd != null) {
                    actualStudent.getSubjects().add(subjectToAdd);
                    studentRepository.save(actualStudent); // Save updated student

                    subjectToAdd.incrementNStudents(); // Increment nStudents
                    subjectRepository.save(subjectToAdd); // Save updated subject

                    model.addAttribute("student", actualStudent);
                }
            }
        }
        return "redirect:/seeSubjectsStudent";
    }
    
    @GetMapping("/deleteSubjectFromList/{id}")
    public String deleteSubjectFromList(@PathVariable(value = "id") Long id, HttpSession session) {
        Optional<Subject> optional = subjectRepository.findById(id);
        if (optional.isPresent()) {
            Subject subject = optional.get();

            // Decrement the number of students for the subject
            subject.decrementNStudents();

            // Get the current student
            Long studentId = (Long) session.getAttribute("studentId");
            if (studentId != null) {
                Optional<Student> optionalStudent = studentRepository.findById(studentId);
                if (optionalStudent.isPresent()) {
                    Student actualStudent = optionalStudent.get();

                    // Remove the subject from the student's list
                    actualStudent.getSubjects().remove(subject);

                    // Save the changes to the database
                    studentRepository.save(actualStudent);
                }
            }
        }
        return "redirect:/seeSubjectsStudent";
    }
    
    //----------------------------------------------------------------------------------------------------
    
    @GetMapping("/loginTeacher")
    public String teacherLoginForm(Model model)
    {
    	Teacher auxTeacher = new Teacher();
    	model.addAttribute("auxTeacher", auxTeacher);
    	return "loginTeacher";
    }
    
    @PostMapping("/verifyTeacher")
    public String teacherVerifyForm(@ModelAttribute("auxTeacher") Teacher auxTeacher, HttpSession session, Model model) {
        String user = auxTeacher.getUser();
        Teacher teacherToCheck = teacherRepository.findByUser(user);
        if (teacherToCheck != null && auxTeacher.getPassword().equals(teacherToCheck.getPassword())) {
            session.setAttribute("teacherId", teacherToCheck.getId());
            return "redirect:/teacherOptions";
        }
        return "redirect:/";
    }
    
    @GetMapping("/teacherOptions")
    public String returnTeacherOptions(Model model)
    {
    	return "teacherOptions";
    }
    
    @GetMapping("/seeSubjectsTeacher")
    public String subjectFromTeacher(HttpSession session, Model model) {
        Long teacherId = (Long) session.getAttribute("teacherId");
        if (teacherId != null) {
            Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
            if (optionalTeacher.isPresent()) {
                Teacher teacher = optionalTeacher.get();
                model.addAttribute("subjectList", teacher.getSubjects());
                return "seeSubjectsTeacher";
            }
        }
        return "redirect:/";
    }
    
    @GetMapping("/setApproval/{id}")
    public String setNumberOfApprovals(@PathVariable(value = "id") Long id, HttpSession session, Model model)
    {
    	session.setAttribute("subjectId", id);
    	int a = 0;
        model.addAttribute("nApprovals", a);
    	return "setApproval";
    }
    
    @PostMapping("/saveApproval")
    public String setApproval(@ModelAttribute("a") int nApprovals, HttpSession session) {
        Long subjectId = (Long) session.getAttribute("subjectId");
        if (subjectId != null) {
            Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
            if (optionalSubject.isPresent()) {
                Subject actualSubject = optionalSubject.get();
                actualSubject.setnApproved(nApprovals);
                subjectRepository.save(actualSubject); // Save the updated subject
            }
        }
        return "redirect:/seeSubjectsTeacher";
    }
}
