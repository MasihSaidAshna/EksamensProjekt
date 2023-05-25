package com.example.eksamensprojekt.repositories;

import com.example.eksamensprojekt.models.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectRepositoryTest {

    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private int projectIDIncrement;

    @BeforeEach
    public void setup() {
        userRepository = new UserRepository();
        projectRepository = new ProjectRepository();
        projectIDIncrement = getLatestIncrement(projectRepository.getProjects());
    }

    public int getLatestIncrement(ArrayList<Project> projects) {
        int pid = 0;
        for (Project p : projects){
            if (p.getProjectID() > pid){
                pid = p.getProjectID() + 1;
            }
        }
        return pid;
    }

    public void assertProjects(Project p1, Project p2) {
        assertEquals(p1.getProjectID(), p2.getProjectID());
        assertEquals(p1.getUserID(), p2.getUserID());
        assertEquals(p1.getProjectName(), p2.getProjectName());
        assertEquals(p1.getProjectCreator(), p2.getProjectCreator());
        assertEquals(p1.getDeadline(), p2.getDeadline());
        assertEquals(p1.getTimePeriod(), p2.getTimePeriod());
        assertEquals(p1.getTimeEstimate(), p2.getTimeEstimate());
    }


    @Test
    public void fetchProjectTest() {
        Project projectTest = new Project(1, 2, "Fun program", "Batman", LocalDate.of(2023, 8, 6), 8);
        Project project = projectRepository.fetchProject(1);

        assertProjects(projectTest, project);
    }


    @Test
    public void getProjectsTest() {
        Project pt1 = new Project(1, 2, "Fun program", "Batman", LocalDate.of(2023, 8, 6), 8);
        Project pt2 = new Project(2, 2, "Clever program", "Batman", LocalDate.of(2024, 6, 10), 3);
        Project pt3 = new Project(3, 1, "Program", "Admin1", LocalDate.of(2023, 8, 6), 5);

        ArrayList<Project> projectsTest = new ArrayList<>();
        projectsTest.add(pt1);
        projectsTest.add(pt2);
        projectsTest.add(pt3);

        ArrayList<Project> projects = projectRepository.getProjects();

        Iterator<Project> iterator1 = projectsTest.iterator();
        Iterator<Project> iterator2 = projects.iterator();
        while (iterator1.hasNext() && iterator2.hasNext()){
            assertProjects(iterator1.next(), iterator2.next());
        }
    }


    @Test
    @Order(1)
    public void createProjectTest() { //Note you can't assign a projectID because it's auto incremented and timeEstimate will always be 0 when project has no modules.
        Project projectTest = new Project(projectIDIncrement, 2, "Test program", "Batman", LocalDate.of(2030, 8, 6), 0);
        User user = userRepository.fetchUser(2);
        projectRepository.createProject(user, projectTest);

        Project project = projectRepository.fetchProject(projectTest.getProjectID());

        assertProjects(projectTest, project);
    }


    @Test
    @Order(2)
    public void editProjectTest() {
        System.out.println(projectIDIncrement);
        projectRepository.updateProject(projectIDIncrement, "UPDATED Test program", LocalDate.of(2030, 8, 6));

        Project project = projectRepository.fetchProject(projectIDIncrement);

        assertEquals(project.getProjectName(), "UPDATED Test program");
    }


    @Test
    @Order(3)
    public void deleteProjectTest() {
        projectRepository.deleteProject(projectIDIncrement);
        assertNull(projectRepository.fetchProject(projectIDIncrement));
        projectRepository.resetProjectIDIncrement(); //Necessary to reset the automatic increment of ID's after deletion of project to prevent errors
    }

}
