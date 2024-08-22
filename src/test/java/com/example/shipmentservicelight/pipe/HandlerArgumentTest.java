package com.example.shipmentservicelight.pipe;

import com.example.shipmentservicelight.ttt.args.HandlerArgument;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class HandlerArgumentTest {

    Class anyClass = Object.class;

    @Test
    public void testEquals_SameObject() {
        HandlerArgument arg1 = new HandlerArgument(Collection.class,anyClass);
        assertTrue(arg1.equals(arg1));
    }

    @Test
    public void testEquals_NullObject() {
        HandlerArgument arg1 = new HandlerArgument(Collection.class,anyClass);
        assertFalse(arg1.equals(null));
    }

    @Test
    public void testEquals_DifferentClass() {
        HandlerArgument arg1 = new HandlerArgument(Collection.class,anyClass);
        Object obj = new Object();
        assertFalse(arg1.equals(obj));
    }

    @Test
    public void testEquals_SameClassAndParams() {
        HandlerArgument arg1 = new HandlerArgument(String.class);
        HandlerArgument arg2 = new HandlerArgument(String.class);
        assertTrue(arg1.equals(arg2));
    }

    @Test
    public void testEquals_DifferentClassSameParams() {
        HandlerArgument arg1 = new HandlerArgument(String.class);
        HandlerArgument arg2 = new HandlerArgument(Integer.class);
        assertFalse(arg1.equals(arg2));
    }
    @Test
    public void testEquals_DifferentClassSameParamsInCollections() {
        HandlerArgument arg1 = new HandlerArgument(List.class,String.class);
        HandlerArgument arg2 = new HandlerArgument(List.class,Integer.class);
        assertFalse(arg1.equals(arg2));
    }

    @Test
    public void testEquals_SameClassDifferentParametrization() {
        HandlerArgument arg1 = new HandlerArgument(List.class,String.class);
        HandlerArgument arg2 = new HandlerArgument(Set.class,String.class);
        assertFalse(arg1.equals(arg2));
    }

    @Test
    public void testEquals_AnyClassWithParametrization() {
        HandlerArgument arg1 = new HandlerArgument(List.class,anyClass);
        HandlerArgument arg2 = new HandlerArgument(List.class,String.class);
        assertTrue(arg1.equals(arg2));
    }
    @Test
    public void testEquals_AnyClassWithParametrizationReverse() {
        HandlerArgument arg1 = new HandlerArgument(List.class,anyClass);
        HandlerArgument arg2 = new HandlerArgument(List.class,String.class);
        assertTrue(arg2.equals(arg1));
    }

    @Test
    public void testEquals_AnyClassWithoutParametrization() {
        HandlerArgument arg1 = new HandlerArgument(anyClass);
        HandlerArgument arg2 = new HandlerArgument(String.class);
        assertTrue(arg1.equals(arg2));
    }
    @Test
    public void testEquals_AnyClassWithoutParametrization2() {
        HandlerArgument arg1 = new HandlerArgument(List.class,anyClass);
        HandlerArgument arg2 = new HandlerArgument(String.class);
        assertTrue(arg1.equals(arg2));
    }
}

