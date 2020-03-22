package com.damon;

import com.damon.model.Student;

import java.util.List;
import java.util.stream.Collectors;

public class stream {
    public static void main(String[] args) {
        test4();

    }

    public static void test1(){
        List<String> list = null;
        //Exception in thread "main" java.lang.NullPointerException
        System.err.println(list.stream().collect(Collectors.toList()));
    }

    public static void test2(){
        List<Integer> integers = List.of(1, 2, 3, 4, 5);
        List<Integer> collect = integers.stream().filter(i -> i == 0).collect(Collectors.toList());
        System.err.println(collect);
    }

    public static void test3(){
        List<Integer> integers = List.of(1, 2, 3, 4, 5);
        integers.forEach(integer -> {
            if (integer==1){
                integer=2;
            }
        });
        System.err.println(integers);   //[1, 2, 3, 4, 5]
    }

    public static void test4(){
        Student student=new Student("小王",10);
        List<Student> student1 = List.of(student);
        student1.forEach(s -> {
            s.setAge(11);
        });
        System.err.println(student1);   //Student(name=小王, age=11)]
    }
}
