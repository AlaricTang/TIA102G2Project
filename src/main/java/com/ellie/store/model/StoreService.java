package com.ellie.store.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.util.compositeQuery.CompositeQuery_Store; // 對應的複合查詢工具類

@Service("storeService")
public class StoreService {

    @Autowired
    private StoreRepository repository;

    @Autowired
    private SessionFactory sessionFactory;

    public void addStore(StoreVO storeVO) {
        repository.save(storeVO);
    }

    public void updateStore(StoreVO storeVO) {
        repository.save(storeVO);
    }

    public void deleteStore(Integer storeId) {
        if (repository.existsById(storeId)) {
            repository.deleteByStoreId(storeId);
        }
    }

    public StoreVO getOneStore(Integer storeId) {
        Optional<StoreVO> optional = repository.findById(storeId);
        return optional.orElse(null);
    }

    public List<StoreVO> getAll() {
        return repository.findAll();
    }

    public List<StoreVO> getAll(Map<String, String[]> map) {
        return CompositeQuery_Store.getAllC(map, sessionFactory.openSession());
    }
}

