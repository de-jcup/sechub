// SPDX-License-Identifier: MIT
package com.daimler.sechub.domain.administration.project;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.daimler.sechub.domain.administration.user.User;
import com.daimler.sechub.domain.administration.user.UserRepository;
import com.daimler.sechub.sharedkernel.UserContextService;
import com.daimler.sechub.sharedkernel.error.AlreadyExistsException;
import com.daimler.sechub.sharedkernel.logging.LogSanitizer;
import com.daimler.sechub.sharedkernel.messaging.DomainMessageService;
import com.daimler.sechub.sharedkernel.validation.UserInputAssertion;
import com.daimler.sechub.test.junit4.ExpectedExceptionFactory;

public class ProjectAssignUserServiceTest {

    private ProjectAssignUserService serviceToTest;
    private UserContextService userContext;
    private DomainMessageService eventBusService;
    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    private ProjectTransactionService transactionService;

    @Rule
    public ExpectedException expected = ExpectedExceptionFactory.none();

    @Before
    public void before() throws Exception {
        eventBusService = mock(DomainMessageService.class);
        projectRepository = mock(ProjectRepository.class);
        userRepository = mock(UserRepository.class);
        userContext = mock(UserContextService.class);

        transactionService = mock(ProjectTransactionService.class);

        serviceToTest = new ProjectAssignUserService();
        serviceToTest.eventBus = eventBusService;
        serviceToTest.projectRepository = projectRepository;
        serviceToTest.userRepository = userRepository;
        serviceToTest.logSanitizer = mock(LogSanitizer.class);
        serviceToTest.userContextService = userContext;
        serviceToTest.assertion = mock(UserInputAssertion.class);
        serviceToTest.transactionService = transactionService;
    }

    @Test
    public void assign_new_user_to_project() {

        User previousUser = mock(User.class);
        User newUser = mock(User.class);

        /* prepare */
        Project project1 = new Project();
        project1.id = "project1";
        project1.users.add(previousUser);

        when(projectRepository.findOrFailProject("project1")).thenReturn(project1);
        when(previousUser.getName()).thenReturn("previous");
        when(newUser.getName()).thenReturn("new");
        when(userRepository.findOrFailUser("new")).thenReturn(newUser);

        /* execute */
        serviceToTest.assignUserToProject(newUser.getName(), project1.getId());

        /* test */
        verify(transactionService).saveInOwnTransaction(project1, newUser);
    }

    @Test
    public void assign_already_added_user_to_project__throws_already_exists_exception() {

        User existingUser = mock(User.class);

        /* prepare */
        Project project1 = new Project();
        project1.id = "project1";
        project1.users.add(existingUser);

        when(projectRepository.findOrFailProject("project1")).thenReturn(project1);
        when(existingUser.getName()).thenReturn("existing");
        when(userRepository.findOrFailUser("existing")).thenReturn(existingUser);

        /* execute */
        /* test */
        assertThrows(AlreadyExistsException.class, () -> {
            serviceToTest.assignUserToProject(existingUser.getName(), project1.getId());
        });
    }

}
