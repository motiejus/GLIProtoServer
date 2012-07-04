GLIProtoServer
==============

This package contains GLI wrapper for GLI-Java.

You should:

1. Implement a class which implements GLI interface
2. Change GLIExample to YourClassName in GLSProtoServer

Run `GLSProtoServer`.

`GLIExample` is a working example which receives the messages from GLS, prints
them on screen and returns a proper return value.

Generating proto classes
------------------------

These two commands generate `JInPiqi.java` and `JOutPiqi.java`:

    $ protoc -I=./ --java_out=./src j-in.piqi.proto
    $ protoc -I=./ --java_out=./src j-out.piqi.proto
