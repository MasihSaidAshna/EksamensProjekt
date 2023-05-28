package com.example.eksamensprojekt.repositories;

import com.example.eksamensprojekt.models.User;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository = new UserRepository();
    }


    public void assertUsers(User user1, User user2) {
        assertEquals(user1.getUserID(), user2.getUserID());
        assertEquals(user1.getUserName(), user2.getUserName());
        assertEquals(user1.getPassword(), user2.getPassword());
        assertEquals(user1.getEmail(), user2.getEmail());
        assertEquals(user1.getRole(), user2.getRole());
    }


    @Test
    public void fetchUserTest() {
        User userTest = new User(1, "Admin", "root", "Ad@Min.com", User.Role.ADMIN);
        User user = userRepository.fetchUser(1);

        assertUsers(userTest, user);
    }


    @Test
    public void getUsersTest() {
        User userTest1 = new User(1, "Admin", "root", "Ad@Min.com", User.Role.ADMIN);
        User userTest2 = new User(2, "Batman", "bat", "Bat@Man.com", User.Role.MANAGER);
        User userTest3 = new User(3, "Mojo", "jojo", "Mo@Jo.com", User.Role.EMPLOYEE);
        User userTest4 = new User(4, "Joe", "joe", "J@J.com", User.Role.EMPLOYEE);

        ArrayList<User> usersTest = new ArrayList<>();
        usersTest.add(userTest1);
        usersTest.add(userTest2);
        usersTest.add(userTest3);
        usersTest.add(userTest4);

        ArrayList<User> users = userRepository.getUsers();

        Iterator<User> iterator1 = users.iterator();
        Iterator<User> iterator2 = usersTest.iterator();
        while (iterator1.hasNext() && iterator2.hasNext()){
            assertUsers(iterator1.next(), iterator2.next());
        }
    }


    @Test
    @Order(1)
    public void createUserTest() {
        User userTest = new User(10, "Test user", "test", "Test@Test.com", User.Role.EMPLOYEE);
        userRepository.createUser(userTest);

        User user = userRepository.fetchUser(10);

        assertUsers(userTest, user);
    }


    @Test
    @Order(2)
    public void editUserTest() {
        userRepository.editUser(10, "EDITED TEST USER", "test", "Test@Test.com", User.Role.EMPLOYEE);

        User user = userRepository.fetchUser(10);

        assertEquals(user.getUserName(), "EDITED TEST USER");
    }


    @Test
    @Order(3)
    public void deleteUserTest() {
        userRepository.deleteUser(10);
        assertNull(userRepository.fetchUser(10));
    }

}
