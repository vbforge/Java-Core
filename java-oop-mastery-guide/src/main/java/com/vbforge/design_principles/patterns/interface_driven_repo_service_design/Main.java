package com.vbforge.design_principles.patterns.interface_driven_repo_service_design;

import com.vbforge.design_principles.patterns.interface_driven_repo_service_design.repo.MongoDBRepositoryImpl;
import com.vbforge.design_principles.patterns.interface_driven_repo_service_design.repo.MySqlDBRepositoryImpl;
import com.vbforge.design_principles.patterns.interface_driven_repo_service_design.repo.UserRepository;
import com.vbforge.design_principles.patterns.interface_driven_repo_service_design.service.UserService;

public class Main {
    public static void main(String[] args) {


        UserRepository mongoRepo = new MongoDBRepositoryImpl("John", 18);
        UserService<UserRepository> mongoService = new UserService<>(mongoRepo);
        mongoService.checkUserAge(18);

        UserRepository mysqlRepo = new MySqlDBRepositoryImpl("Alice", 25);
        UserService<UserRepository> mysqlService = new UserService<>(mysqlRepo);
        mysqlService.checkUserAge(21);
    }
}
