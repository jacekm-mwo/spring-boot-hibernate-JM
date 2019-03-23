package pl.edu.agh.ki.mwo.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.edu.agh.ki.mwo.model.School;
import pl.edu.agh.ki.mwo.model.SchoolClass;
import pl.edu.agh.ki.mwo.model.Student;

public class DatabaseConnector {
	
	protected static DatabaseConnector instance = null;
	
	public static DatabaseConnector getInstance() {
		if (instance == null) {
			instance = new DatabaseConnector();
		}
		return instance;
	}
	
	Session session;

	protected DatabaseConnector() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public void teardown() {
		session.close();
		HibernateUtil.shutdown();
		instance = null;
	}
	
	public Iterable<School> getSchools() {
		String hql = "FROM School";
		Query query = session.createQuery(hql);
		List schools = query.list();
		
		return schools;
	}
	
	public void addSchool(School school) {
		Transaction transaction = session.beginTransaction();
		session.save(school);
		transaction.commit();
	}
	
	public void deleteSchool(String schoolId) {
		String hql = "FROM School S WHERE S.id=" + schoolId;
		Query query = session.createQuery(hql);
		List<School> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (School s : results) {
			session.delete(s);
		}
		transaction.commit();
	}

	public Iterable<SchoolClass> getSchoolClasses() {
		String hql = "FROM SchoolClass";
		Query query = session.createQuery(hql);
		List schoolClasses = query.list();
		
		return schoolClasses;
	}
	
	public void addSchoolClass(SchoolClass schoolClass, String schoolId) {
		String hql = "FROM School S WHERE S.id=" + schoolId;
		Query query = session.createQuery(hql);
		List<School> results = query.list();
		Transaction transaction = session.beginTransaction();
		if (results.size() == 0) {
			session.save(schoolClass);
		} else {
			School school = results.get(0);
			school.addClass(schoolClass);
			session.save(school);
		}
		transaction.commit();
	}
	
	public void deleteSchoolClass(String schoolClassId) {
		String hql = "FROM SchoolClass S WHERE S.id=" + schoolClassId;
		Query query = session.createQuery(hql);
		List<SchoolClass> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (SchoolClass s : results) {
			session.delete(s);
		}
		transaction.commit();
	}
	
	public Iterable<Student> getStudents() {
		String hql = "FROM Student";
		Query query = session.createQuery(hql);
		List students = query.list();
		
		return students;
	}
	
	
	public void addStudent(Student student) {
		Transaction transaction = session.beginTransaction();
		session.save(student);
		transaction.commit();
	}
	
	public void deleteStudent(String studentId) {
		String hql = "FROM Student S WHERE S.id=" + studentId;
		Query query = session.createQuery(hql);
		List<Student> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (Student s : results) {
			session.delete(s);
		}
		transaction.commit();
	}
	
	public void updateStudent(String studentId, String studentName, String studentSurname, String studentPesel, String studentClass) {
		String hql = "FROM Student S WHERE S.id=" + studentId;
		Query query = session.createQuery(hql);
		List<Student> results = query.list();
		Transaction transaction = session.beginTransaction();
		
		Student student = (Student) query.uniqueResult();
		
        student.setName(studentName);
        student.setSurname(studentSurname);
        student.setPesel(studentPesel);
        student.setClass_id(studentClass);
        session.update(student);
		transaction.commit();
	}
	
	public Student getStudent(String studentId) {
		String hql = "FROM Student S WHERE S.id=" + studentId;
		Query query = session.createQuery(hql);
		//List<Student> results = query.list();
	
		Student student = (Student) query.uniqueResult();
        return student;
	}
	
	public School getSchool(String schoolId) {
		String hql = "FROM School S WHERE S.id=" + schoolId;
		Query query = session.createQuery(hql);
		//List<Student> results = query.list();
	
		School school = (School) query.uniqueResult();
        return school;
	}
	
	public void updateSchool(String schoolId, String schoolName, String schoolAddress) {
		String hql = "FROM School S WHERE S.id=" + schoolId;
		Query query = session.createQuery(hql);
		//List<School> results = query.list();
		Transaction transaction = session.beginTransaction();
		
		School school = (School) query.uniqueResult();
		
        school.setName(schoolName);
        school.setAddress(schoolAddress);
        session.update(school);
		transaction.commit();
	}
	
	
	public SchoolClass getSchoolClass(String schoolClassId) {
		String hql = "FROM SchoolClass S WHERE S.id=" + schoolClassId;
		Query query = session.createQuery(hql);
	
		SchoolClass schoolc = (SchoolClass) query.uniqueResult();
        return schoolc;
	}
	
	
	public void updateSchoolClass(String schoolClassId, String startYear, String currentYear, String profile, String schoolId) {   //String startYear, String currentYear, 
		String hql = "FROM SchoolClass S WHERE S.id=" + schoolClassId;
		Query query = session.createQuery(hql);
		Transaction transaction = session.beginTransaction();
		
		SchoolClass schoolc = (SchoolClass) query.uniqueResult();

		int result1 = Integer.parseInt(currentYear);	
        int result2 = Integer.parseInt(startYear);	
		
        schoolc.setCurrentYear(result1);
        schoolc.setStartYear(result2);
        schoolc.setProfile(profile);
     
        session.update(schoolc);
        
        
       
        
		transaction.commit();
		addSchoolClass(schoolc, schoolId);
	}
}
