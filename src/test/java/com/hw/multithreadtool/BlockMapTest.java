package com.hw.multithreadtool;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Map;

import org.junit.Test;

public class BlockMapTest {

  @Test
  public void testSize() {
    Map map = new BlockMap<>();
    map.put("test1", new Object());
    map.put("test2", new Object());
    assertEquals(map.size(), 2);
  }

  @Test
  public void testIsEmpty() {
    Map map = new BlockMap<>();
    assertEquals(map.isEmpty(), true);
    map.put("test1", new Object());
    assertEquals(map.isEmpty(), false);
  }

  @Test
  public void testContainsKey() {
    Map map = new BlockMap<>();
    map.put("test1", new Object());
    assertTrue(map.containsKey("test1"));
  }

  @Test
  public void testContainsValue() {
    Map map = new BlockMap<>();
    map.put("test1", "value1");
    assertTrue(map.containsValue("value1"));
  }

  @Test
  public void testGet() {
    final Map map = new BlockMap<>();
    new Thread(new Runnable() {
      @Override
      public void run() {

        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        map.put("test1", "value1");
        System.out.println("puting data into map");
      }
    }).start();

    System.out.println("before geting the value of test1 time:" + new Date().getSeconds());
    assertEquals(map.get("test1"), "value1");
    System.out.println("After geting the value of test1 time:" + new Date().getSeconds());
  }

  @Test
  public void testRemove() {
    final Map map = new BlockMap<>();
    new Thread(new Runnable() {
      @Override
      public void run() {

        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        map.put("test1", "value1");
        System.out.println("puting data into map");
      }
    }).start();

    System.out.println("before remove the value of test1 time:" + new Date().getSeconds());
    assertEquals(map.remove("test1"), "value1");
    assertEquals(map.size(), 0);
    System.out.println("After remove the value of test1 time:" + new Date().getSeconds());
  }

}
