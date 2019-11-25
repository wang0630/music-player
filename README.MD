# Music player
This is a music player forked from https://github.com/ThatGuyHughesy/music-player

## Purpose
Try to use design patterns to re-structure this project, which is for personal practice only


### 11/25: Refactor Consumer and Producer classes with template method
The original Consumer and Producer both have `run` method, and it has the same logic
```java
// Producer
public void run() {
    while(alive) {
        alive = boundedBuffer.insertChunk();
    }
}

// Consumer
public void run() {
    while(alive) {
        alive = boundedBuffer.removeChunk();
    }
}
```
Template method is used to move the same logic to a parent abstract class called `AbstractThreading`,
 and its `run` method will call an abstract method called `threadingAction`, which will be implemented by both `Consumer` and `Producer`
```java
// Runnable is implemented since it is required by thread
public abstract class AbstractThreading implements Runnable {
    protected BoundedBuffer boundedBuffer;
    protected boolean alive = true;
    abstract boolean threadingAction();

    public AbstractThreading(BoundedBuffer bb) {
        boundedBuffer = bb;
    }

    public void run() {
        while(alive) {
            alive = threadingAction();
        }
    }
}
```
1. `boundedBuffer` and `alive` are now `protected` since they should be accessible to the children class
2. `threadingAction` is an abstract method for children to implement themselves
3. `run` calls `threadingAction`, which is implemented by children(Producer and Consumer) differently
4. We no longer need to change both `Producer` and `Consumer` if we want to change the overall algorithm inside `run`, we just need to modify `AbstractThreading`

#### Differences between implement `Runnable` and extend `Thread` to create a thread
1. We cannot inherit from other classes if we extend `Thread`(multiple inheritance is not allowed), but we can still do it if we implement `Runnable`
2. `Thread` has more built-in functions such as `yield` and `interrupt`