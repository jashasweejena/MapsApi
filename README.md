# Maps

First of all, a few screenshots:

Map!          
:-------------------------:|
![Map](https://github.com/jashasweejena/MapsApi/raw/master/screenshots/map.png)  

Home          |  Fuel Stations  |   Restaurants
:-------------------------:|:-------------------------:|:-------------------------: 
![Home](https://github.com/jashasweejena/MapsApi/raw/master/screenshots/home.png)  |  ![Fuel](https://github.com/jashasweejena/MapsApi/raw/master/screenshots/fuel.png) | ![Restaurants](https://github.com/jashasweejena/MapsApi/raw/master/screenshots/restaurants.png)


## What is this?
A simple app to fetch Restaurants and Fuel Stations around a particular location entered. Click on a card to open the Maps app with that particular location pinned.

## Dependencies

The focus of this project lies on making use of the Google Places API to discover Fuel Pumps and Restaurants around a place

- Volley
- FastAdapter
- Butterknife

## Supported devices

The app supports every device with a SDK level of at least 23 (Android Android 6.0+).


## Quick walkthrough

Basically we have two Activities and one Fragment monitoring the jobs. 
* MainActivity
* ResultsActivity 
* MasterFragment 

### Activities

*MainActivity* is the class which handles 

* The Homepage to enter Location
    

*ResultsActivity* is the class which handles

* Using fragment to show data
* Switching between Restaurant and Fuel tabs

*MasterFragment* is the class which handles

* Used in ResultsActivity
* Displays data using RecyclerView 







 

