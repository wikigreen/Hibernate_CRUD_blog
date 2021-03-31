[![Build Status](https://travis-ci.com/wikigreen/Hibernate_CRUD_blog.svg?branch=master)](https://travis-ci.com/wikigreen/Hibernate_CRUD_blog)

# CRUD_blog
Studying console CRUD application

I want to present you my studying console CRUD application. App represents database for blog. 

## Table of Contents
* [Problem Statemen](#problem-statemen)
* [Requirements](#requirements)
* [How to run](#how-to-run)

## Problem Statemen
Problem Statement: I must implement console CRUD application. It should have following entities: 

* User (id, firstName, lastName, List<Post> posts, Region region, Role role)
* Post (id, content, created, updated)
* Region (id, name)
* Role (enum ADMIN, MODERATOR, USER)

As a database should be used PostgreSQL. Hibernate should be used for interaction with DB. Flyway is for database migration.
User from should be able to create, get, update and delete entities.

Layers:
* model - POJO classes
* view - classes that handle console.
* controller - classes that implements processing of requests from user
* repository - classes that implement access to service classes
* service - classes that implement to database. 

For example: User, UserRepository, UserController, UserView, UserView.


It is advisable to user basic interface for repository layer:
interface GenericRepository<T,ID>

class UserRepository implements GenericRepository<User, Long>

## Requirements
* IDE
* Java 14 or newer
* Git
* Maven
* PostgreSQL 13.2

## How to run 
Database migration:
1. Login to PostgreSQL as root

2. CREATE USER blogger with encrypted password 'blogger';

3. CREATE DATABASE crud_blog;

4. GRANT ALL PRIVILEGES ON DATABASE crud_blog to blogger;

5. $mvn clean compile flyway:migrate

Steps to run this application using your IDE:

1. Use command 'git clone https://github.com/wikigreen/Hibernate_CRUD_blog'
2. Go to Main.java in src/main/java/com/vladimir/crud.blog/Main.java
3. Right click the file and Run as Java application
4. Follow the instructions from console
