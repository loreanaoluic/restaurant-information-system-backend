package com.app.restaurant.service.implementation;

import com.app.restaurant.dto.UserDTO;
import com.app.restaurant.exception.DuplicateEntityException;
import com.app.restaurant.exception.EmptyParameterException;
import com.app.restaurant.exception.InvalidValueException;
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
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
            throw new NotFoundException("No user found for "+ s + ".");
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        User user = this.findOne(id);
        if (user == null) {
            throw new NotFoundException("User with given id does not exist.");
        }
        user.setDeleted(true);
        this.save(user);
    }

    @Override
    public void deleteByUsername(String username) {
        User user = this.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("User with given username does not exist.");
        }
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

    public Salary updateSalary(int id, double salary) {
        User user = this.findOne(id);
        if (user == null) {
            throw new NotFoundException("User with given id does not exist.");
        }

        if(salary < 0) throw new InvalidValueException("Salary cannot be a negative integer.");

        Salary newSalary = new Salary();
        Salary salaryById = user.getSalary();

        if (salaryById == null || salaryById.getValue() != salary) {

            if(salaryById != null){
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
        Optional<User> updatedUser = userRepository.findById(user.getId());

        if (updatedUser.isPresent()) {
            updatedUser.get().setRole(user.getRole());
            updatedUser.get().setUsername(user.getUsername());
            updatedUser.get().setDeleted(user.getDeleted());
            updatedUser.get().setPassword(hashPassword(user.getPassword()));
            updatedUser.get().setEmailAddress(user.getEmailAddress());
            updatedUser.get().setName(user.getName());
            updatedUser.get().setLastName(user.getLastName());
            updatedUser.get().setSalary(updateSalary(updatedUser.get().getId(), user.getSalary().getValue()));

            userRepository.save(updatedUser.get());
        }
        else
            throw new NotFoundException("User with given id does not exist.");

        return user;
    }

    @Override
    public User create(User entity) throws Exception {
        if (userRepository.findByUsername(entity.getUsername()) != null)
            throw new DuplicateEntityException("User with given username already exists.");
        else
            userRepository.save(entity);

        return entity;
    }

//	@Override
//    public User getLoggedInUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof AnonymousAuthenticationToken)
//            return null;
//
////        User user = (User) authentication.getPrincipal();
//        User user = this.userRepository.findByUsername(authentication.getName());
//        return user;
//    }

    @Override
    public User updateDynamicUser(UserDTO userDTO) throws Exception {
        User user = null;

        switch(userDTO.getDtype()) {
            case "Manager":
                user = new Manager();
                user.setRole(new Role(2,"Manager"));
                break;
            case "Director":
                user = new Director();
                user.setRole(new Role(1,"Director"));
                break;
            case "Bartender":
                user = new Bartender();
                user.setRole(new Role(6,"Bartender"));
                break;
            case "Chef":
                user = new Chef();
                user.setRole(new Role(3,"Chef"));
                break;
            case "Cook":
                user = new Cook();
                user.setRole(new Role(4,"Cook"));
                break;
            case "HeadBartender":
                user = new HeadBartender();
                user.setRole(new Role(5,"HeadBartender"));
                break;
            case "Waiter":
                user = new Waiter();
                user.setRole(new Role(7,"Waiter"));
                break;
        }
        Optional<User> updatedUser= Optional.ofNullable(userRepository.findByUsername(userDTO.getUsername()));
        if(updatedUser.isEmpty()){
            throw new NotFoundException("User with given username does not exist.");
        }

        if(userDTO.getSalary() < 0) throw new InvalidValueException("Salary may not be negative.");

        updatedUser.get().setRole(user.getRole());
        updatedUser.get().setName(userDTO.getName());
        updatedUser.get().setLastName(userDTO.getLastName());
        updatedUser.get().setPassword(hashPassword(userDTO.getPassword()));
        updatedUser.get().setEmailAddress(userDTO.getEmailAddress());
        updatedUser.get().setUsername(userDTO.getUsername());
        updatedUser.get().setSalary(updateSalary(updatedUser.get().getId(),userDTO.getSalary()));

        userRepository.save(updatedUser.get());
        return updatedUser.get();
    }

    @Override
    public User createDynamicUser(UserDTO userDTO) throws Exception {
        User user = null ;

        switch(userDTO.getDtype()) {
            case "Manager":
                user = new Manager();
                user.setRole(new Role(2,"Manager"));
                break;
            case "Director":
                user = new Director();
                user.setRole(new Role(1,"Director"));
                break;
            case "Bartender":
                user = new Bartender();
                user.setRole(new Role(6,"Bartender"));
                break;
            case "Chef":
                user = new Chef();
                user.setRole(new Role(3,"Chef"));
                break;
            case "Cook":
                user = new Cook();
                user.setRole(new Role(2,"Cook"));
                break;
            case "HeadBartender":
                user = new HeadBartender();
                user.setRole(new Role(5,"HeadBartender"));
                break;
            case "Waiter":
                user = new Waiter();
                user.setRole(new Role(7,"Waiter"));
                break;
        }

        if(userDTO.getName().equals("") || userDTO.getLastName().equals("") || userDTO.getEmailAddress().equals("") || userDTO.getUsername().equals("") || userDTO.getPassword().equals("")){
            throw new EmptyParameterException("Bad input parameters.");
        }

        if(userDTO.getSalary() < 0) throw new InvalidValueException("Salary may not be negative.");

        Optional<User> tmp = Optional.ofNullable(userRepository.findByUsername(userDTO.getUsername()));
        if(tmp.isPresent()){
            throw new DuplicateEntityException("User with given username already exists.");
        }

        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user.setEmailAddress(userDTO.getEmailAddress());
        user.setUsername(userDTO.getUsername());
        user.setPassword(hashPassword(userDTO.getPassword()));
        user.setDeleted(userDTO.getDeleted());
        user.setSalary(new Salary(userDTO.getSalary(), System.currentTimeMillis(),user));
        user.setDeleted(false);

        this.create(user);
        return user;
    }

    public String hashPassword(String pass) {
        if(Objects.equals(pass, ""))
            throw new EmptyParameterException("Invalid password.");

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        return bCryptPasswordEncoder.encode(pass);
    }
}
