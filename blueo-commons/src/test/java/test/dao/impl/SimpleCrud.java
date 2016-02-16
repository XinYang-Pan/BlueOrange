package test.dao.impl;

import java.io.Serializable;
import java.util.List;

import test.dao.Crud;
import test.dao.ReadType;

public class SimpleCrud<T, K> implements Crud<T, K> {

	@Override
	public T getById(K id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getById(Serializable id, ReadType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public K save(T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<K> saveAll(List<T> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(T t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAll(List<T> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveOrUpdate(T t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveOrUpdateAll(List<T> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(T t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(List<T> ts) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Serializable id) {
		// TODO Auto-generated method stub
		
	}

}
