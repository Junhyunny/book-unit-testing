package com.example.book;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ApplicationTests {

    @Test
    void creating_a_report() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        when(userRepository.getNumberOfUsers()).thenReturn(10);


        ReportService sut = new DefaultReportService(userRepository);
        Report report = sut.createReport();


        assertThat(report.getNumberOfUser()).isEqualTo(10);
        verify(userRepository, times(1)).getNumberOfUsers();
    }
}
