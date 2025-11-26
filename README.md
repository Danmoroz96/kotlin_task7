# kotlin_task7
Multi-Counter App (Task 7)

An Android application featuring a dynamic, scrollable list of counters using Jetpack Compose's LazyColumn.

Features

LazyColumn Implementation: Uses LazyColumn to efficiently render a scrollable list of items.

Dynamic List Management: Users can add new counters and remove specific counters (or clear all) at runtime.

Independent State: Each "Counter_#" maintains its own independent count value.

State Hoisting: Uses mutableStateListOf to manage the collection of counter data objects, ensuring the UI updates automatically when the list changes.

How it works

Data Model: A CounterItem data class holds a unique id and the current value.

State: The main screen holds a remember { mutableStateListOf<CounterItem>() }.

Rendering: The LazyColumn iterates through this list. When the "+" or "-" buttons are clicked, the specific item in the list is updated, triggering a recomposition of just that row.


![Screenshot 2025-11-26 222907](https://github.com/user-attachments/assets/8d8ed50a-cc0e-41a3-8211-2d0da9d8150c)
<img width="362" height="752" alt="image" src="https://github.com/user-attachments/assets/5ccf63dc-d581-4af9-8083-b07b2faa9a79" />
