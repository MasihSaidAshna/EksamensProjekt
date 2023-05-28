package com.example.eksamensprojekt.repositories;

import com.example.eksamensprojekt.models.Module;
import com.example.eksamensprojekt.models.Project;
import com.example.eksamensprojekt.models.User;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModuleRepositoryTest {

    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private ModuleRepository moduleRepository;
    int moduleIDIncrement;

    @BeforeAll
    public void setup() {
        userRepository = new UserRepository();
        projectRepository = new ProjectRepository();
        moduleRepository = new ModuleRepository(projectRepository);
        moduleIDIncrement = getLatestIncrement(moduleRepository.getAllModules());
    }

    public int getLatestIncrement(ArrayList<Module> modules) {
        int mid = modules.size();
        for (Module m : modules){
            if (m.getModuleID() >= mid){
                mid = m.getModuleID() + 1;
            }
        }
        return mid;
    }

    public void assertModules(Module m1, Module m2) {
        assertEquals(m1.getModuleID(), m2.getModuleID());
        assertEquals(m1.getProjectID(), m2.getProjectID());
        assertEquals(m1.getUserID(), m2.getUserID());
        assertEquals(m1.getModuleName(), m2.getModuleName());
        assertEquals(m1.getDeadline(), m2.getDeadline());
        assertEquals(m1.getTimePeriod(), m2.getTimePeriod());
        assertEquals(m1.getTimeEstimate(), m2.getTimeEstimate());
        assertEquals(m1.getStatus(), m2.getStatus());
        assertEquals(m1.getAssignUser(), m2.getAssignUser());
    }


    @Test
    @Order(1)
    public void fetchModuleTest() {
        Module moduleTest = new Module(1, 1, 2, "Module random", LocalDate.of(2023,6,18), 4, Module.Status.DOING, "Unassigned");
        Module module = moduleRepository.fetchModule(1, 1);

        assertModules(moduleTest, module);
    }


    @Test
    @Order(2)
    public void getModulesTest() {
        Module mt1 = new Module(1, 1, 2, "Module random", LocalDate.of(2023,6,18), 4, Module.Status.DOING, "Unassigned");
        Module mt2 = new Module(2, 1, 2, "Module random 2", LocalDate.of(2023,6,30), 4, Module.Status.DOING, "Unassigned");

        ArrayList<Module> modulesTest = new ArrayList<>();
        modulesTest.add(mt1);
        modulesTest.add(mt2);

        Project project = projectRepository.fetchProject(1);

        ArrayList<Module> modules = moduleRepository.getModules(project);

        Iterator<Module> iterator1 = modulesTest.iterator();
        Iterator<Module> iterator2 = modules.iterator();
        while (iterator1.hasNext() && iterator2.hasNext()){
            assertModules(iterator1.next(), iterator2.next());
        }
    }


    @Test
    @Order(3)
    public void createModuleTest() { //Note you can't assign a projectID because it's auto incremented and timeEstimate will always be 0 when project has no modules.
        //System.out.println(moduleIDIncrement);
        Module moduleTest = new Module(moduleIDIncrement, 1, 2, "Test module random", LocalDate.of(2033,5,12), 4, Module.Status.DONE, "Unassigned");

        User user = userRepository.fetchUser(2);
        Project project = projectRepository.fetchProject(1);
        moduleRepository.createModule(user, project, moduleTest);

        Module module = moduleRepository.fetchModule(1, moduleIDIncrement);

        assertModules(moduleTest, module);
    }


    @Test
    @Order(4)
    public void editModuleTest() {
        //System.out.println(moduleIDIncrement);
        Module moduleTest = moduleRepository.fetchModule(1, moduleIDIncrement);
        moduleTest.setModuleName("UPDATED Test module random");
        moduleRepository.updateModule(moduleTest);

        assertEquals(moduleTest.getModuleName(), "UPDATED Test module random");
    }


    @Test
    @Order(5)
    public void deleteModuleTest() {
        moduleRepository.deleteModule(moduleIDIncrement, 1);
        assertNull(moduleRepository.fetchModule( 1, moduleIDIncrement));
        moduleRepository.resetModuleIDIncrement(); //Necessary to reset the automatic increment of ID's after deletion of module to prevent errors
    }

}
