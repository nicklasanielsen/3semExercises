/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Nicklas Nielsen
 */
public class Tester {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();

        Person p1 = new Person("Nicklas", 1997);
        Person p2 = new Person("Andreas", 1999);

        Address a1 = new Address("Sjælør boulevard", 2450, "København SV");
        Address a2 = new Address("Holdkærs ager", 2770, "Kastrup");

        p1.setAddress(a1);
        p2.setAddress(a2);

        Fee f1 = new Fee(100);
        Fee f2 = new Fee(200);
        Fee f3 = new Fee(300);

        p1.addFee(f1);
        p1.addFee(f3);
        p2.addFee(f2);

        SwimStyle s1 = new SwimStyle("Crawl");
        SwimStyle s2 = new SwimStyle("ButterFly");
        SwimStyle s3 = new SwimStyle("Breast stroke");

        p1.addSwimStyle(s1);
        p1.addSwimStyle(s3);
        p2.addSwimStyle(s2);

        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p2);
        em.getTransaction().commit();

        em.getTransaction().begin();
        p1.removeSwimStyle(s3);
        em.getTransaction().commit();

        System.out.println("p1: " + p1.getP_id() + ", " + p1.getName());
        System.out.println("p2: " + p2.getP_id() + ", " + p2.getName());

        System.out.println("p1s gade: " + p1.getAddress().getStreet());
        System.out.println("p2s gade: " + p2.getAddress().getStreet());

        System.out.println("Lad os se om to-vejs virker: " + a1.getPerson().getName());

        System.out.println("Hvem har betalt f2? Det har: " + f2.getPerson().getName());

        TypedQuery<Fee> q1 = em.createQuery("SELECT f FROM Fee f", Fee.class);
        List<Fee> fees = q1.getResultList();

        System.out.println("Hvad har " + p1.getName() + " betalt?");
        fees.forEach(fee -> {
            System.out.println(fee.getPayDate() + ", " + fee.getAmount() + ",-, betalt af " + fee.getPerson().getName());
        });
    }

}
