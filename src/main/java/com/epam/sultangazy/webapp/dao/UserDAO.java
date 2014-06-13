package com.epam.sultangazy.webapp.dao;

import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.entity.User;

import java.util.List;
import java.util.Map;


public interface UserDAO {
    public boolean checkUser(String login, String password) throws DAOException;

    public boolean checkUser(String login) throws DAOException;

    public User findUser(String login) throws DAOException;

    public List<User> findUsers() throws DAOException;

    public void deleteUser(int id) throws DAOException;

    public Map<Integer, User> findRestorators() throws DAOException;

    public List<User> findCooperationRequests() throws DAOException;

    public void deleteCooperationRequest(int id) throws DAOException;

    public void insertUser(User user) throws DAOException;

    public void insertUserCooperation(User user) throws DAOException;

    public boolean updateUser(User user) throws DAOException;
}
