package com.epam.sultangazy.webapp.dao;

import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.entity.User;

import java.util.List;
import java.util.Map;


public interface UserDAO {

    boolean checkUser(String login) throws DAOException;

    User findUser(String login) throws DAOException;

    List<User> findUsers() throws DAOException;

    void deleteUser(int id) throws DAOException;

    Map<Integer, User> findRestorators() throws DAOException;

    List<User> findCooperationRequests() throws DAOException;

    void deleteCooperationRequest(int id) throws DAOException;

    void insertUser(User user) throws DAOException;

    void insertUserCooperation(User user) throws DAOException;

    boolean updateUser(User user) throws DAOException;
}
