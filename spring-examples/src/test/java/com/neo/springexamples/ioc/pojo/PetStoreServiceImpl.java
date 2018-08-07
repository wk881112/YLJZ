package com.neo.springexamples.ioc.pojo;

public class PetStoreServiceImpl {
    public JpaAccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(JpaAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public JpaItemDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(JpaItemDao itemDao) {
        this.itemDao = itemDao;
    }

    private JpaAccountDao accountDao;
    private JpaItemDao itemDao;

}
