package com.easwarang.util;

import com.easwarang.dao.DepartmentRepository;
import com.easwarang.dao.RoleRepository;
import com.easwarang.entity.DepartmentEntity;
import com.easwarang.entity.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public void run(String... args) {

        RoleEntity roleEntityDev = new RoleEntity();
        roleEntityDev.setRoleId(1L);
        roleEntityDev.setRoleName("Developer");
        roleEntityDev.setRoleDescription("Developer role");

        RoleEntity roleEntityQa = new RoleEntity();
        roleEntityQa.setRoleId(2L);
        roleEntityQa.setRoleName("QA tester");
        roleEntityQa.setRoleDescription("QA tester role");

        RoleEntity roleEntityManager = new RoleEntity();
        roleEntityManager.setRoleId(3L);
        roleEntityManager.setRoleName("Manager");
        roleEntityManager.setRoleDescription("Manager role");

        roleRepository.save(roleEntityDev);
        roleRepository.save(roleEntityQa);
        roleRepository.save(roleEntityManager);

        DepartmentEntity entity = new DepartmentEntity();
        entity.setDepartmentId(1L);
        entity.setDepartmentName("HR");
        departmentRepository.save(entity);
    }
}