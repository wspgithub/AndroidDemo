package com.example.administrator.myapplication.MemoryTest;

public class MemoryMode {

    public IMemory iMemory;
    public interface IMemory{

    }

    public void setiMemory(IMemory iMemory) {
        this.iMemory = iMemory;
    }
}
