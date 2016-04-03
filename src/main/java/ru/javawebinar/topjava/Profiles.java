package ru.javawebinar.topjava;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class Profiles {
    public static final String
            POSTGRES = "postgres",
            HSQLDB = "hsqldb",
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "datajpa";

    // Set active spring profiles here
    public static final String ACTIVE_RDBMS_PROFILE = POSTGRES;
    public static final String ACTIVE_PERSISTENCE_PROFILE = DATAJPA;
}
