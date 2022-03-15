package com.tts.cp.lib.service;

/**
 * @author Alley zhao created on 2022/3/14.
 */
/*
this是自身的一个对象，代表对象本身，可以理解为：指向对象本身的一个指针
super可以理解为指向父类对象的一个指针，这个父类指的是离自己最近的一个指针
* */
class Person {
    protected String name;

    // 构造函数 1必须于类名相同 2必须没有显式返回类型
    public Person(String name) {
        this.name = name;
    }
}

class Student extends Person {
    private String name;

    public Student(String name, String name1) { // 构造函数
        super(name);
        this.name = name1;
    }

    public void getInfo() {
        System.out.println(this.name);
        System.out.println(super.name);
    }
}

class Test {
    public static void main(String[] args) {
        Student student = new Student("Father", "Child");
        student.getInfo();
    }
}

/*
构造方法
特点 1.与类名一致 2.必须没有返回类型，连void都没有
作用 1.有参构造方法 & 无参构造方法都是为了创建类的实例而生的。如果类没有构造方法，创建对象时候会调用类的默认隐藏的构造方法
2.为了创建出来类的实例的初始化。（接口是没有构造方法的，所以接口是不能实例化的）
* */
class NotParam {
    // 构造方法 1.必须与类名称相同 2.必须没有显示返回类型
    NotParam() {
        System.out.println("这是无参默认构造函数，在对象创建时候调用");
    }

    public static void main(String[] args) {
        NotParam notParam = new NotParam();
//        NotParam2.
        /*
        static 的作用：
        1.创建独立于具体对象的域变量或者方法，不用创建对象也可以调用变量或者方法
        2.可以生成静态代码块，提高程序性能。（静态代码块：在类加载时候先执行，并且只会执行一次）
        * */
    }
}

//参数化构造方法
class NotParam2 {
    static int id;
    String name;

    NotParam2(int i, String n) {
        this.id = i;
        this.name = n;
    }

    void display() {
        System.out.println(id + " " + name);
    }

    public static void main(String[] args) {
        NotParam2 notParam = new NotParam2(1, "name1");
        NotParam2 notParam2 = new NotParam2(2, "name2");
        notParam.display();
        notParam2.display();
    }
}









