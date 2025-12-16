package com.vbforge.design_principles.patterns.interface_driven_repo_service_design.repo;

import com.vbforge.design_principles.patterns.interface_driven_repo_service_design.model.User;

public interface UserRepository {

    User getUser();
    void filterByAge(int age);


}
