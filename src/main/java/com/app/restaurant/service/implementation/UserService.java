package com.app.restaurant.service.implementation;

import com.app.restaurant.dto.UserDTO;
import com.app.restaurant.exception.DuplicateEntityException;
import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.Role;
import com.app.restaurant.model.Salary;
import com.app.restaurant.model.users.*;
import com.app.restaurant.repository.UserRepository;
import com.app.restaurant.service.IGenericService;
import com.app.restaurant.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService , IGenericService<User> {

    private final UserRepository userRepository;
    private final SalaryService salaryService;

    @Autowired
    public UserService(UserRepository userRepository, SalaryService salaryService) {
        this.userRepository = userRepository;
        this.salaryService = salaryService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("No user found for "+ s + ".");
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        User user = userRepository.getById(id);
        userRepository.delete(user);
    }

    @Override
    public void deleteByUsername(String username) {
        User user = this.findByUsername(username);
        user.setDeleted(true);
        this.save(user);
    }

    @Override
    public User findOne(Integer id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User save(User entity) {
        return this.userRepository.save(entity);
    }

    public Salary updateSalary(int id,double salary) throws Exception {
        User user = findOne(id);
        Salary newSalary = new Salary();

        if (user == null) {
            throw new NotFoundException("User does not exist");
        }

        Salary salaryById=user.getSalary();

            if (salaryById==null||salaryById.getValue() != salary) {
                if(salaryById!=null){

                    salaryById.setEndDate(System.currentTimeMillis());
                    salaryService.save(salaryById);
                }
                newSalary.setValue(salary);
                newSalary.setStartDate(System.currentTimeMillis());
                salaryService.save(newSalary);
                user.setSalary(newSalary);

                userRepository.save(user);

                return newSalary;
            }

        return salaryById;
    }

    @Override
    public User update(User user) throws Exception {
        Optional<User> u = userRepository.findById(user.getId());
        if (u.isPresent()) {
            u.get().setRole(user.getRole());
            u.get().setUsername(user.getUsername());
            u.get().setDeleted(user.getDeleted());
            u.get().setPassword(user.getPassword());
            u.get().setEmailAddress(user.getEmailAddress());
            u.get().setName(user.getName());
            u.get().setLastName(user.getLastName());

            u.get().setSalary(updateSalary(u.get().getId(),user.getSalary().getValue()));
            userRepository.save(u.get());
        }else
            throw new NotFoundException("User does not exist.");

        return user;
    }

    @Override
    public User create(User entity) throws Exception {
        if (userRepository.findByUsername(entity.getUsername()) != null)
            throw new DuplicateEntityException("User already exists.");
        else
            userRepository.save(entity);

        return entity;
    }

	@Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            return null;

//        User user = (User) authentication.getPrincipal();
        User user = this.userRepository.findByUsername(authentication.getName());
        return user;
    }

    @Override
    public User updateDynamicUser(UserDTO dto) throws Exception {
        User u = null;

        switch(dto.getDtype()) {
            case "Manager":
                u=new Manager();
                u.setRole(new Role(2,"Manager"));
                break;
            case "Director":
                u=new Director();
                u.setRole(new Role(1,"Director"));
                break;
            case "Bartender":
                u=new Bartender();
                u.setRole(new Role(6,"Bartender"));
                break;
            case "Chef":
                u=new Chef();
                u.setRole(new Role(3,"Chef"));
                break;
            case "Cook":
                u=new Cook();
                u.setRole(new Role(4,"Cook"));
                break;
            case "HeadBartender":
                u=new HeadBartender();
                u.setRole(new Role(5,"HeadBartender"));
                break;
            case "Waiter":
                u=new Waiter();
                u.setRole(new Role(7,"Waiter"));
                break;
        }
        Optional<User> tmp= Optional.ofNullable(userRepository.findByUsername(dto.getUsername()));
        if(!tmp.isPresent()){
            throw new NotFoundException("User with given id does not exist.");
        }

        //u.setId(tmp.get().getId());
        tmp.get().setRole(u.getRole());
        tmp.get().setName(dto.getName());
        tmp.get().setLastName(dto.getLastName());
        tmp.get().setEmailAddress(dto.getEmailAddress());
        tmp.get().setUsername(dto.getUsername());
        tmp.get().setSalary(updateSalary(tmp.get().getId(),dto.getSalary()));
//        this.userRepository.delete(tmp.get());
//        this.create(u);
        System.out.println(dto.getName());
        System.out.println(tmp.get());
        update(tmp.get());

        return u;
    }

    @Override
    public User createDynamicUser(UserDTO dto) throws Exception {
        User u = null ;

        switch(dto.getDtype()) {
            case "Manager":
                u=new Manager();
                u.setRole(new Role(2,"Manager"));
                break;
            case "Director":
                u=new Director();
                u.setRole(new Role(1,"Director"));
                break;
            case "Bartender":
                u=new Bartender();
                u.setRole(new Role(6,"Bartender"));
                break;
            case "Chef":
                u=new Chef();
                u.setRole(new Role(3,"Chef"));
                break;
            case "Cook":
                u=new Cook();
                u.setRole(new Role(2,"Cook"));
                break;
            case "HeadBartender":
                u=new HeadBartender();
                u.setRole(new Role(5,"HeadBartender"));
                break;
            case "Waiter":
                u=new Waiter();
                u.setRole(new Role(7,"Waiter"));
                break;
        }
        if(dto.getName().equals("")||dto.getLastName().equals("")||dto.getEmailAddress().equals("")||dto.getUsername().equals("")||dto.getPassword().equals("")){
            throw new Exception("Bad input Parameters");
        }

        Optional<User> tmp= Optional.ofNullable(userRepository.findByUsername(dto.getUsername()));
        if(tmp.isPresent()){
            throw new DuplicateEntityException("Duplicate username");
        }

        u.setName(dto.getName());
        u.setLastName(dto.getLastName());
        u.setEmailAddress(dto.getEmailAddress());
        u.setUsername(dto.getUsername());
        u.setPassword(dto.getPassword());
        u.setDeleted(dto.getDeleted());
        u.setSalary(new Salary(dto.getSalary(), System.currentTimeMillis(),u));

        this.create(u);
        return u;
    }



}
