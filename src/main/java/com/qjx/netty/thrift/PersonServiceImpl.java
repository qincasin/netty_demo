package com.qjx.netty.thrift;

import org.apache.thrift.TException;
import thrift.generated.DataException;
import thrift.generated.Person;
import thrift.generated.PersonService;

public class PersonServiceImpl implements PersonService.Iface {
    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        System.out.println("Go client param:" + username);


        Person person = new Person();
        person.setName(username);
        person.setAge(20);
        person.setMarried(false);

        return person;

    }

    @Override
    public void savePerson(Person person) throws DataException, TException {

        System.out.println("go client param:");

        System.out.println(person.getName());
        System.out.println(person.getAge());
        System.out.println(person.isMarried());


    }
}
