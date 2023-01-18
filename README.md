v# Library Loader

## Introduction

This is a simple library and application demonstrating the use of post-delegation classloaders in Kotlin.  Post-delegation classloaders are used to isolate plugins from their host components.

There are plenty of examples of post-delegation class loading for Java, but few for Kotlin.  This means to fill that gap.

## Motivation

### Motivating Example

A sample application is provided to demonstrate the use of the post-delegation class loader to isolate dynamically-loaded extensions from classes that have previously been loaded.  The application itself is built from an extensible component.

The sample application statically instantiates its own extensible component upon startup; that component then statically instantiates a single plugin.  Together, the app, its component, and the component's plugin constitute the 'core' of the application.

The sample application allows users to customize it by adding in new components, which may then add in new plugins.  This customization is all done dynamically, by loading the custom components and plugins via reflection, using their full-qualified names and the location where the classes can be found.

To demonstrate the post-delegation class loading mechanism and how it operates with respect to already-loaded software modules, a consumer-provided extension is dynamically loaded using the 'post-delegation' class loader.  That dynamically-loaded component then dynamically loads another extension -- a plugin to the component -- using the same mechanism.

Note that the post-delegation loader used to dynamically load the second component and its plugin overrides the default class loader behavior.  Unlike th edefault classloader, the [post-delegation loader first checksi the local class path (derived from the root directory where the extensions live) before delegating to the parent class loader. 

## Design Philosophy

Everything is interface-based. So the code is organized into pairs of files for interfaces and implementations.

## Overview of Code Organization

## Files

* Test/Example
** main - calls App
** app - simulates an application with a mix of statically- and dynamically-loaded classes
** ComponentImpl - dynamically-loaded doppleganger to the statically-loaded one in App
** PluginImpl - dynamically-loaded doppleganger to the statically-loaded one in App's Component

### Class Loading
* utils_classloading_instantiation
* utils_classloading_postdelegation
* utils_classloading_factory - unused
* 
### Primary Classes and Funcion

* loader_utils_initialization
* loader_utils_exensionloading - Funcation that encapsulates all instatiation & initialization
* spi_extension_interfaces
* spi_loadable_interfaces
* spi_environment_interfaces

* ComponentImpl

## Architecture



### Core Data Model

### Operations Module


## Usage

Maven coordinates: [offtheecliptic/loader 0.0.1]

### Import Statements

## Concepts

These are the key concepts the library deals with.

* **Class Loading**  - Loading a class from a class path.

* **Instantiation**  - Creating an instance of a class.

* **Initialization** - Initializing a newly-instantiated class instance so it can be used. 

### Special Considerations


## Examples

### Example 1 - 



### Example 2 - 


### Example 3 - 
                     
### Example 4 - 

### Example 5 - 

_________________________________________________________________________________________________________________
## Library Developers - Basics

### Maven 

To compile:
   mvn clean
   mvn compile
   mvn package

## Test

### Maven 

You can run the test cases using Maven.

To run, can do either of these:

java  -jar target/clocca-0.2.0-jar-with-dependencies.jar

kotlin -cp target/clocca-0.2.0-jar-with-dependencies.jar ....

_________________________________________________________________________________________________________________

_________________________________________________________________________________________________________________
## Test Cases


### Imports

#### Simple Test Cases


#### Mapping Test Cases


_________________________________________________________________________________________________________________
## Contributions
* Robert W Scanlon
* 

## References
1. Writing a post-delegation classloader, Alex Miller, 11/09/2006.
   - https://puredanger.github.io/tech.puredanger.com/2006/11/09/classloader/
2. Java: A Child-First Classloader, Isuru Weerarathna, 11/08/2018.
   - https://medium.com/@isuru89/java-a-child-first-class-loader-cbd9c3d0305
3. Call Class Constructor by Reflection in Kotlin, Michale, 4/16/2016.
   1. https://stackoverflow.com/questions/36665039/call-class-constructor-by-reflection-with-kotlin/36665249#36665249
4. 


_________________________________________________________________________________________________________________
## License

Copyright © 2022-2023 OffTheEcliptic

This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary Licenses when the conditions for such availability set forth in the Eclipse Public License, v. 2.0 are satisfied: GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version, with the GNU Classpath Exception which is available at https://www.gnu.org/software/classpath/license.html.

