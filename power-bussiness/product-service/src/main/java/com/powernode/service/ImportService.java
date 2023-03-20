package com.powernode.service;

public interface ImportService {
    //全量导入
    void importAll();
    //增量导入
    void importUpdate();
}
