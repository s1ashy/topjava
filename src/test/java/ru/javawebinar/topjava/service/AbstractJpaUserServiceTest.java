package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.repository.JpaUtil;

abstract public class AbstractJpaUserServiceTest extends AbstractUserServiceTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected JpaUtil jpaUtil;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        jpaUtil.clear2ndLevelHibernateCache();
    }
}
