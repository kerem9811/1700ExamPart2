package com.example.eksamen2023part2;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import static org.hibernate.internal.CoreLogging.logger;

@RestController
public class AppController {

    @Autowired
    HttpSession session;

    @Autowired
    private CitizenRepository citizenRepo;

    private Logger logger = LoggerFactory.getLogger(AppController.class);

    /*Task 3
Java task: Create your first Controller class with the proper annotations. Create an endpoint
“/hello” to test that your controller is configured correctly. The endpoint should return a string
with a message that will be displayed on the browser when someone interrogates that
particular endpoint. (Be careful about the type of mapping you use for your endpoint).*/
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
    /*Task 4
Java & SQL task: Create a new model class in Java that would map the input fields you
created in the first task. Make sure to have all the field types similar. If you are going to use
Hibernate JPA, please make sure you use the proper adnotations. Also, please write the
SQL code necessary for the creation of a table that follows the rules mentioned above.
NB: Don't worry if the editor is set for Java, I don't search for SQL sintax perfection:)*/

    /*Task 5
Create a new endpoint in your controller that will take care of the input it receives from JS.
(The JS object you created at task 2.) Make sure that all the information you received is
mapped in the model class that you defined at task 4. Now comes the funny part, you have
to save that information into the DB.
Let’s consider that you already set up a connection for the DB and it works fine. You can
choose any way you want to save the data in the DB, if you use the “new way” with
Hibernate and JPA, please also define the interface. If the transaction with the DB is not
successful make sure to handle the error*/
    @GetMapping("/saveCitizen")
    public void saveCitizen(@RequestBody Citizen newcitizen, HttpServletResponse response) throws IOException {
        try {
            Citizen newCitizen = citizenRepo.save(newcitizen);
            System.out.println("Citizen saved " + newCitizen);
            logger.info("Citizen {} saved: ", newCitizen);
        } catch (Exception e) {
            System.out.println("Error saving citizen " + newcitizen);
            String error = "Could not save citizen" + e.getMessage();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, error);
        }
    }

    /*Java task: You have the next scenario: A user who is logged in (let's say an administrator)
would like to operate some sensitive changes to the databases. Let’s think of a situation
where you don’t like that citizens < 18 registered. If that’s the case please delete the citizens
< 18 from the DB and then logout.
You will need to create 2 endpoints to manage sessions. The first endpoint for login, the
second one for logout. ( pay attention on how you use the session object).
You will also have to create an endpoint to operate the changes in the DB. First, check if you
are logged in. If you are, then proceed with the changes. (Pay attention to the calls you have
to make in the Data Base. We first need to retrieve the list, then check the condition (citizen
age < 18), and in the end we have to interrogate the DB again in order to delete those who
don't fit the description).*/
    @GetMapping("/login")
    public void newUser(User newUser, HttpServletResponse response) throws IOException {
        try {
            session.setAttribute("loggedIn", newUser);
            logger.info("Logged in {}", newUser);
        } catch (Exception e) {
            response.sendError(400, "Could not log in");
            logger.info(String.valueOf(e));
        }
    }

    @GetMapping("/logout")
    public void logout() {
        session.removeAttribute("loggedIn");
        System.out.println("Logged out");
    }

    @GetMapping("/removeUnderage")
    public boolean removeUnderage(HttpServletResponse response) throws IOException {
        if (session.getAttribute("loggedIn") != null) {
            try {
                List<Citizen> citizenList = citizenRepo.findAll();
                for (Citizen citizen : citizenList) {
                    if (citizen.getAge() < 18) {
                        citizenRepo.delete(citizen);
                        System.out.println(citizen.getFirstname() + " " + citizen.getLastname() + "(" + citizen.getAge() + ") deleted.");
                        logger.info("{} deleted", citizen.getFirstname());
                    }
                    return true;
                }
            } catch (Exception e) {
                logger.error("Error changing database {}", e.getMessage());
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return false;
            }
        }
        return false;
    }

    /*Task 7
Retrieve all the citizens from the application using a new endpoint and send them in the
browser as a json response. Use a Logger to show all this data in your server. Return the
info by sorting alphabetically ascending using a Java method*/
    @GetMapping("/allCitizens")
    public List<Citizen> returnAll() {
        List<Citizen> newlist = citizenRepo.findAll();
        String stringlist = newlist.toString();
        logger.info("All citizens {}", stringlist);
        newlist.sort(Comparator.comparing(Citizen::getLastname));
        logger.info("All citizens sorted {}",newlist);
        return newlist;
    }
}
