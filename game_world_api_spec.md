# API Spec FacadeGameWorld







## API Usage

To application can be executed using the following command:

```shell
java -classpath [api].jar;[implementation].jar;[client].jar [Main_UI_Class] [Main_Iml_Class]
```

with:

- [api] : name of the jar file containing the API
- [implementation] : name of the jar file containing the game world implementation
- [client] : name of the jar file containing a correct user Interface
- [Main_UI_Class] : name of the main class of the user interface in the client. The recommended path of the class is `client.main.ClientMainClass`
- [Main_Impl_Class] : name of the main class of the implementation which implements the API. The recommended path of the class is `impl.root.ImplementationGameWorld`



Here is an example command:

```shell
java -classpath gameworldapi.jar;robotgame.jar;blockr.jar client.main.ClientMainClass impl.root.ImplementationGameWorld
```



## valid Game World API implementation



## valid Game World API client

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



The implementation instance can be used to run all the Implementation functions