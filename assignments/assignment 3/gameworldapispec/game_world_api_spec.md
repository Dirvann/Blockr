# API Spec FacadeGameWorld

## Valid Game World API implementation

A valid game world API implementation must contain a main class `impl.root.ImplementationGameWorld` which implements the `FacadeGameWorld` API interface.

The implementation must also contain the jar file of the API as a dependency. The interface can be accessed with `game_world.api.FacadeGameWorld`.

The main class must correctly implement all the functions from the API Interface for a normal execution of the game.

## Valid Game World API client

A valid game world API client must contain a runnable main class `client.main.ClientMainClass`.

The jar file of the API must be used as a dependency of the client. The interface can be accessed with `game_world.api.FacadeGameWorld`.

This main class must contain a class loader which loads the Game World Implementation class in runtime. The path of the Implementation class will be given as a command line argument. 

With the use of the API function `FacadeGameWorld FacadeGameWorld.newInstance(Class<?> c)`, a new instance of the Implementation is returned.

Example of class loader and instance creation in main class (without error handling):

```java
// ClientMainClass.java
game_world.api.FacadeGameWorld;
//..
public static void main(String[] args) {
        ClassLoader classLoader = ClientMainClass.class.getClassLoader();
    	String implClassPath = args[0];
        Class<?> implClass = classLoader.loadClass(implClassPath);
        FacadeGameWorld implInstance = FacadeGameWorld.newInstance(implClass);
	}
//..
```



The implementation instance can be used to run all the Implementation functions. 

To display the game world the `implementationInstance.draw(Graphics g)` method must be used. This should be in the `paint(Graphics g)`method of a class which extends Canvas.

```java
// GameWorldCanvas.java    extends Canvas
//..
@Override
public paint(Graphics g) {
    super.paint();
    implInstance.draw(g, getWidth(), getHeight());
}
//..
```

