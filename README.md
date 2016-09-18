# Wrapper-Droid

A library with the main objective of prototyping Android applications through wrapper classes. This is done by two ways:

##1. Creating an easier to manipulate workflow (Similar to Arduino):

After the library is imported, the developer will define an activity class, remove the onCreate method, and let the class inherit WrapperActivity class. The developer then overrides setup(),loop(), and close() to fit the workflow needed.

##2. Providing functionalities and hardware resources in the form of Java wrappers:

For example, obtaining accelerometer readings can be done by simply creating an object from class Accelerometer and calling its member fields that act as readings.

See SampleActivity.java for more details.