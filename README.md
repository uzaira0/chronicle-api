Chronicle API
===============

Contains object models and interfaces for interacting with chronicle backend.

On the backend, controllers directly implement retroifit annotated interfaces with Jackson handling the JSON marshalling on both sides.

Using retrofit makes it to generate typesafe clients for making RESTful requests in JVM based languages.

This is currently used by chronicle server tests and the Android application.
