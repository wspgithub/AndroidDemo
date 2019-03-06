// IAnimalManager.aidl
package com.example.administrator.myapplication;
import com.example.administrator.myapplication.AIDLTest.Animal;
// Declare any non-default types here with import statements

interface IAnimalManager {
       List<Animal> getBookList();
        void addBook(in Animal animal);
        }
