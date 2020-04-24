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

