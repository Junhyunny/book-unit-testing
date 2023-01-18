package com.example.book;

public class DefaultReportService implements ReportService{

    private final UserRepository userRepository;

    public DefaultReportService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Report createReport() {
        return new Report(userRepository.getNumberOfUsers());
    }
}
