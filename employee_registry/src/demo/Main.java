package demo;

import entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //create session factory
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();

        //create a session
        Session session = factory.getCurrentSession();

        try{
            //create 3 employee objects
            System.out.println("Creating 3 employee objects...");
            Employee tempEmp = new Employee("Manager", "Ellie", "Ingram", "ellie@code.com");
            Employee tempEmp1 = new Employee("Engineer","Laila", "Lindsay", "laila@code.com");
            Employee tempEmp2 = new Employee("Clerk","Jill", "Jack", "jill@code.com");

            //start a transaction
            session.beginTransaction();
            System.out.println("Beginning transaction...");

            //save the employee object
            System.out.println("Saving the new employees...");
            session.save(tempEmp);
            session.save(tempEmp1);
            session.save(tempEmp2);

            //commit the transaction
            session.getTransaction().commit();

            //***Code for Reading the object***
            //Get a new session and start a transaction
            session = factory.getCurrentSession();
            session.beginTransaction();

            //Retrieve employee based on the id: primary key
            System.out.println("Getting employee with id: " + tempEmp1.getId());
            Employee newEmp = session.get(Employee.class, tempEmp1.getId());
            System.out.println("Info: " + newEmp);

            //commit the transaction
            session.getTransaction().commit();

            //Get a new session and start a transaction
            session = factory.getCurrentSession();
            session.beginTransaction();

            //Query All Employees
            List<Employee> allEmp = session.createQuery("from Employee").getResultList();
            //Display All Employees
            System.out.println("List of All Employees: ");
            for (Employee emp: allEmp)
                System.out.println(emp);

            //commit the transaction
            session.getTransaction().commit();


            int empId = 3;
            //Get a new session and start a transaction
            session = factory.getCurrentSession();
            session.beginTransaction();

            //Delete employee using primary key
            Employee delEmp = session.get(Employee.class, empId);
            System.out.println("Deleting Employee:  " + delEmp);
            session.delete(delEmp);

            //commit the transaction
            session.getTransaction().commit();

            System.out.println("Done!");

        } finally {
            factory.close();
        }
    }
}
