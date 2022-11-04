package com.barclays.homeloan;


import com.barclays.homeloan.entity.User;
import com.barclays.homeloan.repository.UserRepository;
import com.barclays.homeloan.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void add()
    {
        User user=new User();
        user.setId(1);
        user.setName("User");
        user.setEmail("user@gmail.com");
        user.setPassword("123");

        Mockito.when(userRepository.save(user)).thenReturn(user);
        //assertThat(userService.addAccount(user),equals(user));
    }

    @Test
    public void getAllUsers()
    {
        User user1=new User();
        user1.setId(1);
        user1.setName("User");
        user1.setEmail("user@gmail.com");
        user1.setPassword("123");

        User user2=new User();
        user2.setId(1);
        user2.setName("XYZ");
        user2.setEmail("XYZ@gmail.com");
        user2.setPassword("XYZ123");

        List<User> userList=new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        Mockito.when(userService.getAllUsers()).thenReturn(userList);

    }
}
