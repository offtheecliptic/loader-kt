v# Library Loader

## Introduction

This is a simple library fdemonstrating the use of post-delegation classloaders for isolating plugins from their host components.

## Motivation

### Motivating Example

A sample application is provided to demonstrate the use of the post-delegation class loader to isolate dynamically-loaded extensions from classes that have previously been loaded.

The application statically instantiates a single component.  That component allows for extensions, so it statically instantiates a single plugin.  Together, the app, its component, and the component's plugin constitute the 'core' of he application.

However, the application also allows users to customize it by adding in new components, which may then add in new plugins.  This customization is all done dynamically, by loading the custom components and plugins using reflection, using their full-qualified names and the location where the classes cn be found.

Consumer-provided extensions are loaded with a 'post-delegation' class loader, which overrides the default class loader behavior, first checking the local class path (derived from the root directory where the extensions live) before delegating to the parent class loader.  That dynamically-loaded component then dynamically loads another extension -- a plugin to the component -- using the same mechanism.

There are plenty of examples for Java, but few for Kotlin.  This means to fill that gap.

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


_________________________________________________________________________________________________________________
## License

Copyright © 2021-2022 OffTheEcliptic

This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary Licenses when the conditions for such availability set forth in the Eclipse Public License, v. 2.0 are satisfied: GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version, with the GNU Classpath Exception which is available at https://www.gnu.org/software/classpath/license.html.

