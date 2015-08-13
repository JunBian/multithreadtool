package com.hw.multithreadtool;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockMap<K, V> implements Map<K, V> {

  private int size = 0;

  private Map<K, V> entity;

  private static final int initSize = 0;

  final ReentrantLock lock;
  private final Condition notEmpty;

  public BlockMap() {
    entity = new HashMap<K, V>(initSize);
    lock = new ReentrantLock();
    notEmpty = lock.newCondition();
  }


  @Override
  public int size() {
    lock.lock();
    try {
      return (entity == null ? 0 : entity.size());
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean isEmpty() {
    lock.lock();
    try {
      return ((entity == null) ? true : entity.isEmpty());
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean containsKey(Object key) {
    lock.lock();
    try {
      return (entity == null) ? false : entity.containsKey(key);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean containsValue(Object value) {
    lock.lock();
    try {
      return (entity == null) ? false : entity.containsValue(value);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public V get(Object key) {
    lock.lock();
    try {
      V result = null;
      while (result == null) {
        notEmpty.await();
        result = entity.get(key);
      }
      return result;
    } catch (InterruptedException e) {
      e.printStackTrace();
      return null;
    } finally {
      lock.unlock();
    }
  }

  @Override
  public V put(K key, V value) {
    lock.lock();
    try {
      V result = entity.put(key, value);
      notEmpty.signalAll();
      return result;
    } finally {
      lock.unlock();
    }
  }

  @Override
  public V remove(Object key) {
    lock.lock();
    try {
      V result = null;
      while (result == null) {
        notEmpty.await();
        result = entity.remove(key);
      }
      return result;
    } catch (InterruptedException e) {
      e.printStackTrace();
      return null;
    } finally {
      lock.unlock();
    }
  }

  @Override
  public void putAll(Map m) {
    // do nothing.
  }

  @Override
  public void clear() {

  }

  @Override
  public Set keySet() {
    return null;
  }

  @Override
  public Collection values() {
    return null;
  }

  @Override
  public Set entrySet() {
    return null;
  }


}
